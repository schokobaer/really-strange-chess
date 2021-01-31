package com.example.its.cockpit.rest;

import com.example.its.cockpit.dto.CreateGameRequest;
import com.example.its.cockpit.dto.GameDto;
import com.example.its.cockpit.mapper.GameMapper;
import com.example.its.cockpit.model.Game;
import com.example.its.cockpit.model.Player;
import com.example.its.cockpit.service.GameService;
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

        Game game = gameService.createGame(player, req.getTimeWhite(), req.getTimeBlack(), req.getTeam(), req.getStyle(), req.getMineConfig(), req.getBoard());

        return game.getId();
    }

    @GetMapping("/api/game")
    public List<GameDto> getOpen() {
        return gameService.getOpenGames().stream().map(g -> gameMapper.toDto(g)).collect(Collectors.toList());
    }

}
