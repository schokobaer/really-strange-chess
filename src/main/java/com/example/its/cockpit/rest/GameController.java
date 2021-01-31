package com.example.its.cockpit.rest;

import com.example.its.cockpit.dto.GameDto;
import com.example.its.cockpit.dto.JoinGameRequest;
import com.example.its.cockpit.dto.MoveRequest;
import com.example.its.cockpit.mapper.GameMapper;
import com.example.its.cockpit.model.Game;
import com.example.its.cockpit.model.Player;
import com.example.its.cockpit.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameMapper gameMapper;

    @GetMapping("/api/game/{id}")
    public GameDto get(@PathVariable String id) {
        Game game = gameService.get(id);
        return gameMapper.toDto(game);
    }

    @PostMapping("/api/game/{id}/join")
    public void join(@PathVariable String id, @RequestHeader String playerid, @RequestBody JoinGameRequest req) {
        // Player joins the table
        Player player = new Player();
        player.setId(playerid);
        player.setName(req.getName());
        gameService.join(id, player, req.getColor());
    }

    @PostMapping("/api/game/{id}/move")
    public void move(@PathVariable String id, @RequestHeader String playerid, @RequestBody MoveRequest req) {
        // starts on the first mvoe
        gameService.move(id, playerid, req.getFrom(), req.getTo());
    }

    @PostMapping("/api/game/{id}/undo")
    public void undo(@PathVariable String id, @RequestHeader String playerid) {
        // undoes the last move
        gameService.undo(id, playerid);
    }

    @PostMapping("/api/game/{id}/timeout")
    public void timeout(@PathVariable String id, @RequestHeader String playerid) {
        // undoes the last move
        gameService.reportTimeout(id);
    }

}
