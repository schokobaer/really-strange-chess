package com.example.its.cockpit.dto;

import com.example.its.cockpit.model.BoardField;

public class FigureMoveDto {

    private BoardField from;
    private BoardField to;
    private long time;

    public FigureMoveDto() {
    }

    public FigureMoveDto(BoardField from, BoardField to, long time) {
        this.from = from;
        this.to = to;
        this.time = time;
    }

    public BoardField getFrom() {
        return from;
    }

    public void setFrom(BoardField from) {
        this.from = from;
    }

    public BoardField getTo() {
        return to;
    }

    public void setTo(BoardField to) {
        this.to = to;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
