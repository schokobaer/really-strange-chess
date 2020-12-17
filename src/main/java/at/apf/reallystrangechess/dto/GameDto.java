package at.apf.reallystrangechess.dto;

import at.apf.reallystrangechess.model.BoardField;
import at.apf.reallystrangechess.model.Color;
import at.apf.reallystrangechess.model.GameState;

import java.util.List;

public class GameDto {

    private String id;
    private TeamDto white;
    private TeamDto black;
    private Color currentTeam;
    private GameState state;
    private List<BoardField> board;
    private FigureMoveDto lastMove;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<BoardField> getBoard() {
        return board;
    }

    public void setBoard(List<BoardField> board) {
        this.board = board;
    }
}
