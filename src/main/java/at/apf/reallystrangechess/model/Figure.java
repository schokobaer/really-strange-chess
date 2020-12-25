package at.apf.reallystrangechess.model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return color == figure.color &&
                type == figure.type;
    }

    @Override
    public int hashCode() {
        return color.ordinal() * 10 + type.ordinal();
    }

    @Override
    public String toString() {
        return (color == Color.WHITE ? "W" : "B") + type.getChar();
    }
}
