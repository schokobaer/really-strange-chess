package at.apf.reallystrangechess.model;

public class BoardField {
    private Position position;
    private BoardFieldColor color;
    private Figure figure;
    private Integer mine;

    public BoardField(Position position, BoardFieldColor color, Figure figure, Integer mine) {
        this.position = position;
        this.color = color;
        this.figure = figure;
        this.mine = mine;
    }

    /**
     * Creates an empty/not existing field
     * @param position
     */
    public BoardField(Position position) {
        this(position, BoardFieldColor.EMPTY, null, null);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public BoardFieldColor getColor() {
        return color;
    }

    public void setColor(BoardFieldColor color) {
        this.color = color;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Integer getMine() {
        return mine;
    }

    public void setMine(Integer mine) {
        this.mine = mine;
    }
}
