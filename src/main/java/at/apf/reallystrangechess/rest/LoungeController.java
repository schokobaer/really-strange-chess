package at.apf.reallystrangechess.rest;

import at.apf.reallystrangechess.dto.CreateGameRequest;
import at.apf.reallystrangechess.dto.GameDto;
import at.apf.reallystrangechess.mapper.GameMapper;
import at.apf.reallystrangechess.model.Game;
import at.apf.reallystrangechess.model.Player;
import at.apf.reallystrangechess.service.GameService;
import at.apf.reallystrangechess.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoungeController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameMapper gameMapper;

    @PostMapping("/api/game")
    public String create(@RequestHeader String playerid, @RequestBody CreateGameRequest req) {
        Player player = new Player();
        player.setId(playerid);
        player.setName(req.getName());

        Game game = gameService.createGame(player, req.getTimeWhite(), req.getTimeBlack(), req.getTeam(), req.getStyle());

        return game.getId();
    }

    @GetMapping("/api/game")
    public List<GameDto> getOpen() {
        return gameService.getOpenGames().stream().map(g -> gameMapper.toDto(g)).collect(Collectors.toList());
    }

}
