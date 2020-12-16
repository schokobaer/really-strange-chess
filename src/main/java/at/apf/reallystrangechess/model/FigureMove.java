package at.apf.reallystrangechess.model;

public class FigureMove {

    private BoardField from;
    private BoardField to;

    public FigureMove(BoardField from, BoardField to) {
        this.from = from;
        this.to = to;
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
}
