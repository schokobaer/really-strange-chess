package at.apf.reallystrangechess.mapper;

import at.apf.reallystrangechess.dto.FigureMoveDto;
import at.apf.reallystrangechess.dto.GameDto;
import at.apf.reallystrangechess.dto.TeamDto;
import at.apf.reallystrangechess.dto.TeamPlayerDto;
import at.apf.reallystrangechess.model.Game;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameMapper {

    public GameDto toDto(Game game) {
        GameDto g = new GameDto();

        TeamDto white = new TeamDto();
        white.setTime(game.getWhite().getTime());
        white.setCurPlayer(game.getWhite().getCurrentPlayer());
        white.setHitFigures(game.getWhite().getHitFigures());
        white.setPlayers(game.getWhite().getPlayers().stream()
                .map(p -> new TeamPlayerDto(p.getName(), p.getOrder()))
                .collect(Collectors.toList()));
        g.setWhite(white);

        TeamDto black = new TeamDto();
        black.setTime(game.getBlack().getTime());
        black.setCurPlayer(game.getBlack().getCurrentPlayer());
        black.setHitFigures(game.getBlack().getHitFigures());
        black.setPlayers(game.getBlack().getPlayers().stream()
                .map(p -> new TeamPlayerDto(p.getName(), p.getOrder()))
                .collect(Collectors.toList()));
        g.setBlack(black);

        g.setId(game.getId());
        g.setCurrentTeam(game.getCurrentTeam());
        g.setState(game.getState());
        g.setHistory(game.getHistory().stream().map(h -> new FigureMoveDto(h.getFrom(), h.getTo(), h.getTime())).collect(Collectors.toList()));

        g.setBoard(game.getBoard());

        return g;
    }
}
