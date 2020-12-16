package at.apf.reallystrangechess.dto;

import at.apf.reallystrangechess.model.BoardField;

import java.util.Date;

public class FigureMoveDto {

    private BoardField from;
    private BoardField to;
    private Date time;

    public FigureMoveDto() {
    }

    public FigureMoveDto(BoardField from, BoardField to, Date time) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
