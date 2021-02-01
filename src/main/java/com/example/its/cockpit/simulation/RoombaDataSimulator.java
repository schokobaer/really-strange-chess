package com.example.its.cockpit.simulation;

import com.example.its.cockpit.dto.*;
import com.example.its.cockpit.repo.RoombaRepo;
import com.example.its.cockpit.service.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RoombaDataSimulator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoombaDataSimulator.class);

    @Autowired
    private NotifyService notifyer;
    private RoombaRepo repo;

    private RoombaDto roomba1, roomba2;
    private Random rnd = new Random();

    @Autowired
    public void setRepo(RoombaRepo repo) {
        this.repo = repo;

        roomba1 = new RoombaDto();
        roomba1.setId("roomba1");
        roomba1.setState("IDLE");
        roomba1.setBattery(new BatteryDto());
        roomba1.getBattery().setEnergy(0.99);
        roomba1.setBumper(new BumperDto());
        roomba1.setWheels(new WheelsDto());
        roomba1.getWheels().setSpeed(new SpeedDto());
        roomba1.getWheels().setGrounded(true);

        roomba2 = new RoombaDto();
        roomba2.setId("roomba2");
        roomba2.setState("IDLE");
        roomba2.setBattery(new BatteryDto());
        roomba2.getBattery().setEnergy(0.51);
        roomba2.setBumper(new BumperDto());
        roomba2.getBumper().setBumped(true);
        roomba2.setWheels(new WheelsDto());
        roomba2.getWheels().setSpeed(new SpeedDto());
        roomba2.getWheels().setGrounded(true);

        repo.put(roomba1);
        repo.put(roomba2);

        LOGGER.info("Created roomba1 and roomba2");
    }

    //@Scheduled(fixedRate = 1000)
    public void simulate() {

        if (rnd.nextBoolean()) {
            roomba1.getWheels().getSpeed().setLeft(100);
            roomba1.getWheels().getSpeed().setRight(100);
            roomba1.setState("MANUAL");
            LOGGER.info("Roomba1 now manual driving");
        } else {
            roomba1.getWheels().getSpeed().setLeft(0);
            roomba1.getWheels().getSpeed().setRight(0);
            roomba1.setState("IDLE");
            LOGGER.info("Roomba1 now idle");
        }

        roomba2.getBumper().setBumped(rnd.nextBoolean());
        roomba2.getWheels().setGrounded(rnd.nextBoolean());
        LOGGER.info("Roomba2 also updated");


        notifyer.roombaUpdate(roomba1);
        notifyer.roombaUpdate(roomba2);
    }
}
