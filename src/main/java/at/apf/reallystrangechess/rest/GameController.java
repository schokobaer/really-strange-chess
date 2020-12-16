package at.apf.reallystrangechess.rest;

import at.apf.reallystrangechess.dto.JoinGameRequest;
import at.apf.reallystrangechess.dto.MoveRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {


    @PostMapping("/api/game/{id}/join")
    public void join(@PathVariable String id, @CookieValue String playerid, @RequestBody JoinGameRequest req) {
        // Player joins the table
    }

    @PostMapping("/api/game/{id}/move")
    public void move(@PathVariable String id, @CookieValue String playerid, @RequestBody MoveRequest req) {
        // starts on the first mvoe
    }

    @PostMapping("/api/game/{id}/udno")
    public void undo(@PathVariable String id, @CookieValue String playerid) {
        // undoes the last move
    }

}
