package at.apf.reallystrangechess.dto;

import at.apf.reallystrangechess.model.Color;
import at.apf.reallystrangechess.model.GameState;

public class GameDto {

    private TeamDto white;
    private TeamDto black;
    private Color currentTeam;
    private GameState state;
    private FigureMoveDto lastMove;

    public TeamDto getWhite() {
        return white;
    }

    public void setWhite(TeamDto white) {
        this.white = white;
    }

    public TeamDto getBlack() {
        return black;
    }

    public void setBlack(TeamDto black) {
        this.black = black;
    }

    public Color getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Color currentTeam) {
        this.currentTeam = currentTeam;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public FigureMoveDto getLastMove() {
        return lastMove;
    }

    public void setLastMove(FigureMoveDto lastMove) {
        this.lastMove = lastMove;
    }
}
