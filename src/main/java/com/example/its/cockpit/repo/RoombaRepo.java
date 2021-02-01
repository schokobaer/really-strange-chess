package com.example.its.cockpit.repo;

import com.example.its.cockpit.dto.RoombaDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RoombaRepo {

    private Map<String, RoombaDto> data = new HashMap<>();

    public void put(RoombaDto roomba) {
        data.put(roomba.getId(), roomba);
    }

    public Collection<RoombaDto> getAll() {
        return data.values();
    }
}
