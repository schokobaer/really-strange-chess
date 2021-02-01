package com.example.its.cockpit.service;

import com.example.its.cockpit.repo.RoombaRepo;
import com.example.its.cockpit.dto.RoombaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoombaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoombaService.class);
    private static final String EXCHANGE = "its";
    private static final String ACTION_ROUTING_KEY = "its.action";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RoombaRepo repo;

    public RoombaDto get(String id) {
        return repo.getAll().stream().filter(r -> id.equals(r.getId())).findFirst().orElse(null);
    }

    public List<RoombaDto> getAll() {
        return repo.getAll();
    }

    public void drive(String roombaid, int speed) {
        LOGGER.info("Sending drive message to roomba " + roombaid);
        rabbitTemplate.convertAndSend(EXCHANGE, ACTION_ROUTING_KEY + "." + roombaid, speed, m -> {
            m.getMessageProperties().getHeaders().put("action", "drive");
            return m;
        });
    }

    public void stop(String roombaid) {
        rabbitTemplate.convertAndSend(EXCHANGE, ACTION_ROUTING_KEY + "." + roombaid, "", m -> {
            m.getMessageProperties().getHeaders().put("action", "stop");
            return m;
        });
    }

    public void turn(String roombaid, int angle) {
        rabbitTemplate.convertAndSend(EXCHANGE, ACTION_ROUTING_KEY + "." + roombaid, angle, m -> {
            m.getMessageProperties().getHeaders().put("action", "turn");
            return m;
        });
    }

    public void driveTo(String roombaid, String target) {
        rabbitTemplate.convertAndSend(EXCHANGE, ACTION_ROUTING_KEY + "." + roombaid, target, m -> {
            m.getMessageProperties().getHeaders().put("action", "driveTo");
            return m;
        });
    }
}
