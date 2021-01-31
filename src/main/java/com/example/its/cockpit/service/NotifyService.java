package com.example.its.cockpit.service;

import com.example.its.cockpit.dto.GameDto;
import com.example.its.cockpit.mapper.GameMapper;
import com.example.its.cockpit.model.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class NotifyService {

    @Autowired
    private SimpMessageSendingOperations simp;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private ObjectMapper jsonMapper;

    public void loungeUpdate() {
        simp.convertAndSend("/event/lounge", "");
    }

    public void gameUpdate(Game game) {

        try {
            GameDto dto = gameMapper.toDto(game);
            String json = jsonMapper.writeValueAsString(dto);
            simp.convertAndSend("/event/game/" + game.getId(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
