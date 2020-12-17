package at.apf.reallystrangechess.dto;

import at.apf.reallystrangechess.model.FigureType;

import java.util.List;

public class TeamDto {

    private List<TeamPlayerDto> players;
    private int curPlayer;
    private Long time; // sec
    private List<FigureType> hitFigures;
    private boolean castlingable;

    public List<TeamPlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<TeamPlayerDto> players) {
        this.players = players;
    }

    public int getCurPlayer() {
        return curPlayer;
    }

    public void setCurPlayer(int curPlayer) {
        this.curPlayer = curPlayer;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<FigureType> getHitFigures() {
        return hitFigures;
    }

    public void setHitFigures(List<FigureType> hitFigures) {
        this.hitFigures = hitFigures;
    }

    public boolean isCastlingable() {
        return castlingable;
    }

    public void setCastlingable(boolean castlingable) {
        this.castlingable = castlingable;
    }
}
