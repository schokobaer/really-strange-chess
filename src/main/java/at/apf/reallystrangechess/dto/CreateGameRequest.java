package at.apf.reallystrangechess.dto;

import at.apf.reallystrangechess.model.Color;

public class CreateGameRequest {
    private Long timeWhite;
    private Long timeBlack;

    private Color team;
    private String name;

}
