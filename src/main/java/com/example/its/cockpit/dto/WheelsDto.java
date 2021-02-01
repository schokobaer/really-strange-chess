package com.example.its.cockpit.dto;

public class WheelsDto {
    private boolean grounded;
    private SpeedDto speed;

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public SpeedDto getSpeed() {
        return speed;
    }

    public void setSpeed(SpeedDto speed) {
        this.speed = speed;
    }
}
