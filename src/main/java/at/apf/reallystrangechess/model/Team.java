package at.apf.reallystrangechess.model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<TeamPlayer> players = new ArrayList<>();
    private Integer currentPlayer;
    private boolean castlingable;
    private Long time;
    private List<Figure> hitFigures = new ArrayList<>();



    public List<TeamPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<TeamPlayer> players) {
        this.players = players;
    }

    public Integer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Integer currentPlayer) {
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

}
