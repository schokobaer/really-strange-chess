package at.apf.reallystrangechess.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<TeamPlayer> players = new ArrayList<>();
    private int currentPlayer;
    private boolean castlingable = true;
    private Long time;
    private List<Figure> hitFigures = new ArrayList<>();



    public List<TeamPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<TeamPlayer> players) {
        this.players = players;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isCastlingable() {
        return castlingable;
    }

    public void setCastlingable(boolean castlingable) {
        this.castlingable = castlingable;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<Figure> getHitFigures() {
        return hitFigures;
    }

    public void setHitFigures(List<Figure> hitFigures) {
        this.hitFigures = hitFigures;
    }
}
