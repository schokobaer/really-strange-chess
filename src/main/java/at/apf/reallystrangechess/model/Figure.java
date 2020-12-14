package at.apf.reallystrangechess.model;

public class Figure {
    private Color color;
    private FigureType type;

    public Figure(Color color, FigureType type) {
        this.color = color;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public FigureType getType() {
        return type;
    }

    public void setType(FigureType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return (color == Color.WHITE ? "W" : "B") + type.getChar();
    }
}
