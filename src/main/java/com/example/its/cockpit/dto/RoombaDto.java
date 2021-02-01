package com.example.its.cockpit.dto;

public class RoombaDto {

    private String id;
    private String state;
    private String location;
    private BatteryDto battery;
    private WheelsDto wheels;
    private BumperDto bumper;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BatteryDto getBattery() {
        return battery;
    }

    public void setBattery(BatteryDto battery) {
        this.battery = battery;
    }

    public WheelsDto getWheels() {
        return wheels;
    }

    public void setWheels(WheelsDto wheels) {
        this.wheels = wheels;
    }

    public BumperDto getBumper() {
        return bumper;
    }

    public void setBumper(BumperDto bumper) {
        this.bumper = bumper;
    }
}
