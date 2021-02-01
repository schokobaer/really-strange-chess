package com.example.its.cockpit.rest;

import com.example.its.cockpit.dto.RoombaDto;
import com.example.its.cockpit.service.RoombaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RoombaRestController {

    @Autowired
    private RoombaService roombaService;

    @GetMapping("/api/roomba")
    public List<String> getAvailable() {
        return roombaService.getAll().stream().map(r -> r.getId()).collect(Collectors.toList());
    }

    @GetMapping("/api/roomba/{id}")
    public RoombaDto get(@PathVariable String id) {
        return roombaService.get(id);
    }

    @PostMapping("/api/roomba/{id}/drive")
    public void drive(@PathVariable String id, @RequestBody String speed) {
        roombaService.drive(id, Integer.parseInt(speed));
    }

    @PostMapping("/api/roomba/{id}/stop")
    public void stop(@PathVariable String id) {
        roombaService.stop(id);
    }

    @PostMapping("/api/roomba/{id}/turn")
    public void turn(@PathVariable String id, @RequestBody String angle) {
        roombaService.turn(id, Integer.parseInt(angle));
    }

    @PostMapping("/api/roomba/{id}/driveTo")
    public void driveTo(@PathVariable String id, @RequestBody String target) {
        roombaService.driveTo(id, target);
    }

}
