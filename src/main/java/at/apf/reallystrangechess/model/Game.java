package at.apf.reallystrangechess.model;

import java.util.List;

public class Game {
    private Team white;
    private Team black;
    private Color currentTeam;
    private List<FigureMove> history;
    private List<BoardField> board;

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
}
