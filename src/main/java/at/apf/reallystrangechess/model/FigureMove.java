package at.apf.reallystrangechess.model;

public class FigureMove {
    private Figure figure;
    private Position from;
    private Position to;

    public FigureMove(Figure figure, Position from, Position to) {
        this.figure = figure;
        this.from = from;
        this.to = to;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Position getFrom() {
        return from;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }
}
