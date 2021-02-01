package com.example.its.cockpit.listener;

import com.example.its.cockpit.config.RabbitConfig;
import com.example.its.cockpit.dto.RoombaDto;
import com.example.its.cockpit.repo.RoombaRepo;
import com.example.its.cockpit.service.NotifyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class RoombaDataListener {

    @Autowired
    private RoombaRepo repo;

    @Autowired
    private NotifyService notifyer;

    @RabbitListener(queues = RabbitConfig.DATA_ROOMBA_QUEUE)
    public void listen(RoombaDto roomba, @Header String roomba_id) {
        roomba.setId(roomba_id);
        repo.put(roomba);
        notifyer.roombaUpdate(roomba);
    }
}
