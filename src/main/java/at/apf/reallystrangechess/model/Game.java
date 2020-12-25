package at.apf.reallystrangechess.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {

    private String id;
    private Team white = new Team();
    private Team black = new Team();
    private Color currentTeam;
    private List<FigureMove> history = new ArrayList<>();
    private List<BoardField> board;
    private GameState state = GameState.PENDING;
    // TODO: Add GameResult {winner?: Color, reason?: TIME | CHECKMATE }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Team getWhite() {
        return white;
    }

    public void setWhite(Team white) {
        this.white = white;
    }

    public Team getBlack() {
        return black;
    }

    public void setBlack(Team black) {
        this.black = black;
    }

    public Color getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Color currentTeam) {
        this.currentTeam = currentTeam;
    }

    public List<FigureMove> getHistory() {
        return history;
    }

    public void setHistory(List<FigureMove> history) {
        this.history = history;
    }

    public List<BoardField> getBoard() {
        return board;
    }

    public void setBoard(List<BoardField> board) {
        this.board = board;
    }

    public FigureMove getLastMove() {
        return history.isEmpty() ? null : history.get(history.size() - 1);
    }

}
