package com.example.its.cockpit.repo;

import com.example.its.cockpit.dto.RoombaDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoombaRepo {

    private List<RoombaDto> data = new ArrayList<>();

    public void add(RoombaDto roomba) {
        data.add(roomba);
    }

    public List<RoombaDto> getAll() {
        return data;
    }
}
