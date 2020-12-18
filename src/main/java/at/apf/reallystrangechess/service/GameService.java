package at.apf.reallystrangechess.service;

import at.apf.reallystrangechess.dto.CreateGameRequest;
import at.apf.reallystrangechess.logic.BaseChessLogic;
import at.apf.reallystrangechess.model.*;
import at.apf.reallystrangechess.repo.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    BaseChessLogic logic = new BaseChessLogic();

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private NotifyService notifyService;

    public Game createGame(Player player, Long timeWhite, Long timeBlack, Color color) {
        Game game = new Game();
        game.setCurrentTeam(Color.WHITE);
        game.setState(GameState.PENDING);
        game.setBoard(logic.generate5x5WithCorners());

        TeamPlayer p = new TeamPlayer();
        p.setId(player.getId());
        p.setName(player.getName());
        p.setOrder(0);

        if (color == Color.WHITE) {
            game.getWhite().getPlayers().add(p);
        } else {
            game.getBlack().getPlayers().add(p);
        }

        game.getWhite().setTime(timeWhite);
        game.getBlack().setTime(timeBlack);

        gameRepo.add(game);

        notifyService.loungeUpdate();

        return game;
    }

    public Game get(String gameid) {
        return gameRepo.read(gameid);
    }

    public List<Game> getOpenGames() {
        return gameRepo.all().stream().filter(g -> g.getState() == GameState.PENDING).collect(Collectors.toList());
    }

    public void join(String gameid, Player player, Color color) {
        Game game = gameRepo.readLocked(gameid);

        if (game.getState() != GameState.PENDING) {
            gameRepo.release(gameid);
            throw new RuntimeException("Wrong State");
        }

        Team team = color == Color.WHITE ? game.getWhite() : game.getBlack();

        if (team.getPlayers().stream().filter(p -> p.getName().equals(player.getName())).findAny().isPresent()) {
            gameRepo.release(gameid);
            throw new RuntimeException("Player already in team");
        }

        TeamPlayer p = new TeamPlayer();
        p.setId(player.getId());
        p.setName(player.getName());
        p.setOrder(team.getPlayers().size());
        team.getPlayers().add(p);


        gameRepo.writeBack(game);

        notifyService.loungeUpdate();
        notifyService.gameUpdate(game);
    }

    public void move(String gameid, String playerid, Position from, Position to) {
        Game game = gameRepo.readLocked(gameid);

        if (game.getState() == GameState.FINISHED) {
            gameRepo.release(gameid);
            throw new RuntimeException("Game has already finished");
        }

        // Check if its the first move
        if (game.getState() == GameState.PENDING) {
            if (game.getWhite().getPlayers().isEmpty() || game.getBlack().getPlayers().isEmpty()) {
                gameRepo.release(gameid);
                throw new RuntimeException("One has no players");
            }
            game.setState(GameState.PLAYING);
        }

        Team team = game.getCurrentTeam() == Color.WHITE ? game.getWhite() : game.getBlack();
        TeamPlayer player = team.getPlayers().stream().filter(p -> p.getId().equals(playerid)).findAny().orElse(null);
        if (player == null || team.getCurrentPlayer() != player.getOrder()) {
            gameRepo.release(gameid);
            throw new RuntimeException("Player is not in charge");
        }

        Date now = new Date();
        Long neededSeconds = 0L;
        if (game.getLastMove() != null) {
            neededSeconds = (now.getTime() - game.getLastMove().getTime()) / 1000;
        }
        if (team.getTime() != null && team.getTime() <= neededSeconds) {
            game.setState(GameState.FINISHED);
            team.setTime(0L);
            gameRepo.writeBack(game);
            return;
        }

        BoardField source = logic.getField(game.getBoard(), from);
        BoardField target = logic.getField(game.getBoard(), to);
        boolean moveIsValid = logic.getMoveableFields(game.getBoard(), source, team.isCastlingable()).stream()
                .filter(f -> f.getPosition().equals(to))
                .findAny().isPresent();
        if (!moveIsValid || source.getColor() == BoardFieldColor.EMPTY || source.getFigure().getColor() != game.getCurrentTeam()) {
            gameRepo.release(gameid);
            throw new RuntimeException("Not a valid move");
        }

        game.getHistory().add(new FigureMove(source, target)); // Add move to history
        if (target.getFigure() != null) {
            team.getHitFigures().add(target.getFigure().getType()); // add hitted figure to hitFigures of team
        }
        game.setBoard(logic.move(game.getBoard(), from, to)); // change board
        if (team.getTime() != null && game.getLastMove() != null) {
            team.setTime(team.getTime() - neededSeconds); // set remaining time of team
        }
        game.setLastMove(now); // update last move

        team.setCurrentPlayer( (player.getOrder() + 1) % team.getPlayers().size() ); // next player of team
        game.setCurrentTeam(game.getCurrentTeam().flip()); // flip current Team


        if (logic.isCheckmate(game.getBoard(), game.getCurrentTeam())
                || logic.isPatt(game.getBoard(), game.getCurrentTeam())) {
            game.setState(GameState.FINISHED); // End game
        }

        gameRepo.writeBack(game);

        notifyService.gameUpdate(game);
    }

    public void undo(String gameid) {
        Game game = gameRepo.readLocked(gameid);

        if (game.getState() == GameState.PENDING || game.getHistory().isEmpty()) {
            gameRepo.release(gameid);
            throw new RuntimeException("No move made yet");
        }

        FigureMove move = game.getHistory().remove(game.getHistory().size() - 1); // remove last move of history
        game.setBoard(game.getBoard().stream().map(f -> { // undo board
            if (f.getPosition().equals(move.getFrom().getPosition())) {
                return move.getFrom();
            } else if (f.getPosition().equals(move.getTo().getPosition())) {
                return move.getTo();
            } else {
                return f;
            }
        }).collect(Collectors.toList()));
        game.setCurrentTeam(game.getCurrentTeam().flip()); // flip current Team
        game.setLastMove(new Date()); // update last move
        Team team = game.getCurrentTeam() == Color.WHITE ? game.getWhite() : game.getBlack();
        if (move.getTo().getFigure() != null) {
            team.getHitFigures().remove(team.getHitFigures().size() - 1); // remove hitted figure
        }
        team.setCurrentPlayer( (team.getCurrentPlayer() - 1 + team.getPlayers().size()) % team.getPlayers().size() ); // previous player of previous
        if (team.getTime() > 0) {
            // played time will stay ereased
            game.setState(GameState.PLAYING); // if game was ended, set it to playing (if the team has remaining time)
        }

        gameRepo.writeBack(game);

        notifyService.gameUpdate(game);
    }

    public void updateTime(String gameid, String playerid, Long timeWhite, Long timeBlack) {
        // sets the times for each team and sets the state to playing
    }

    public void reportTimeout(String gameid) {
        Game game = gameRepo.readLocked(gameid);

        if (game.getState() != GameState.PLAYING) {
            gameRepo.release(gameid);
            throw new RuntimeException("Game is finished");
        }

        Team team = game.getCurrentTeam() == Color.WHITE ? game.getWhite() : game.getBlack();
        if (team.getTime() == null) {
            gameRepo.release(gameid);
            throw new RuntimeException("Game is not timed");
        }

        Date now = new Date();
        Long neededSeconds = 0L;
        if (game.getLastMove() != null) {
            neededSeconds = (now.getTime() - game.getLastMove().getTime()) / 1000;
        }
        if (team.getTime() != null && team.getTime() <= neededSeconds) {
            game.setState(GameState.FINISHED);
            team.setTime(0L);
        }

        gameRepo.writeBack(game);

        notifyService.gameUpdate(game);
    }

}
