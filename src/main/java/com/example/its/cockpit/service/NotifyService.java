package com.example.its.cockpit.service;

import com.example.its.cockpit.dto.RoombaDto;
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
    private ObjectMapper jsonMapper;

    public void roombaUpdate(RoombaDto roomba) {
        try {
            String json = jsonMapper.writeValueAsString(roomba);
            simp.convertAndSend("/event/roomba/" + roomba.getId(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
