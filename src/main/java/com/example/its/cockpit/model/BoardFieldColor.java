package com.example.its.cockpit.model;

public enum BoardFieldColor  {
    WHITE,
    BLACK,
    EMPTY
    ;

    public BoardFieldColor flip() {
        switch (this) {
            case BLACK: return WHITE;
            case WHITE: return BLACK;
            case EMPTY: return EMPTY;
            default: return this;
        }
    }
}
