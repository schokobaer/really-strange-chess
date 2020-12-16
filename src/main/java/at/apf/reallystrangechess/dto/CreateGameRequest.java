package at.apf.reallystrangechess.dto;

import at.apf.reallystrangechess.model.Color;

public class CreateGameRequest {
    private Long timeWhite;
    private Long timeBlack;
    private Color team;
    private String name;

    public Long getTimeWhite() {
        return timeWhite;
    }

    public void setTimeWhite(Long timeWhite) {
        this.timeWhite = timeWhite;
    }

    public Long getTimeBlack() {
        return timeBlack;
    }

    public void setTimeBlack(Long timeBlack) {
        this.timeBlack = timeBlack;
    }

    public Color getTeam() {
        return team;
    }

    public void setTeam(Color team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
