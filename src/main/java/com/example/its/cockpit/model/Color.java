package com.example.its.cockpit.model;

public enum Color {
    WHITE,
    BLACK
    ;

    public Color flip() {
        switch (this) {
            case WHITE: return BLACK;
            case BLACK: return WHITE;
            default: return this;
        }
    }
}
