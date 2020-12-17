package at.apf.reallystrangechess.model;

public enum FigureType {
    BAUER("B"),
    LAUFER("L"),
    SPRINGER("S"),
    TURM("T"),
    DAME("D"),
    KING("K")
    ;

    private String c;

    FigureType(String c) {
        this.c = c;
    }

    public String getChar() {
        return c;
    }
}
