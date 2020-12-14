package at.apf.reallystrangechess.model;

import java.util.List;

public class Game {
    private Team white;
    private Team black;
    private Color currentTeam;
    private List<FigureMove> history;
    private List<BoardField> board;
}
