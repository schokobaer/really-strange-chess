package com.example.its.cockpit.dto;

import com.example.its.cockpit.model.Position;

public class MoveRequest {

    private Position from;
    private Position to;

    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }
}
