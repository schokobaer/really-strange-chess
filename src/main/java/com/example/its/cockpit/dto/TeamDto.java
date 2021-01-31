package com.example.its.cockpit.dto;

import com.example.its.cockpit.model.Figure;

import java.util.List;

public class TeamDto {

    private List<TeamPlayerDto> players;
    private int curPlayer;
    private Long time; // sec
    private List<Figure> hitFigures;
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

    public List<Figure> getHitFigures() {
        return hitFigures;
    }

    public void setHitFigures(List<Figure> hitFigures) {
        this.hitFigures = hitFigures;
    }

    public boolean isCastlingable() {
        return castlingable;
    }

    public void setCastlingable(boolean castlingable) {
        this.castlingable = castlingable;
    }
}
