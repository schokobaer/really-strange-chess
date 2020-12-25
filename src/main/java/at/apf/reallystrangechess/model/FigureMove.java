package at.apf.reallystrangechess.model;


import java.util.Date;

public class FigureMove {

    private BoardField from;
    private BoardField to;
    private long time;

    public FigureMove(BoardField from, BoardField to) {
        this.from = from;
        this.to = to;
        this.time = new Date().getTime();
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
