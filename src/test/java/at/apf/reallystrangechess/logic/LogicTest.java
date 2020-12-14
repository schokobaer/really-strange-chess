package at.apf.reallystrangechess.logic;

import at.apf.reallystrangechess.model.BoardField;
import at.apf.reallystrangechess.model.BoardFieldColor;
import at.apf.reallystrangechess.model.Color;
import at.apf.reallystrangechess.model.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LogicTest {

    private BaseChessLogic logic = new BaseChessLogic();

    public void printBoardColor(List<BoardField> board) {
        Position dim = logic.getBoardDimensions(board);

        for (int y = 1; y <= dim.getY(); y++) {
            for (int x = 1; x <= dim.getX(); x++) {
                final Position p = new Position(x, y);
                BoardField f = board.stream().filter(field -> field.getPosition().equals(p)).findFirst().orElse(new BoardField(p));
                System.out.print(f.getColor() == BoardFieldColor.WHITE ? "W" : f.getColor() == BoardFieldColor.BLACK ? "B" : " ");
            }
            System.out.println();
        }
    }

    public void printBoardFigs(List<BoardField> board) {
        Position dim = logic.getBoardDimensions(board);

        for (int y = 1; y <= dim.getY(); y++) {
            for (int x = 1; x <= dim.getX(); x++) {
                final Position p = new Position(x, y);
                BoardField f = board.stream().filter(field -> field.getPosition().equals(p)).findFirst().orElse(new BoardField(p));
                System.out.print(f.getFigure() != null ? f.getFigure().getType().getChar() : " ");
            }
            System.out.println();
        }
    }

    public void printBoardFigColors(List<BoardField> board) {
        Position dim = logic.getBoardDimensions(board);

        for (int y = 1; y <= dim.getY(); y++) {
            for (int x = 1; x <= dim.getX(); x++) {
                final Position p = new Position(x, y);
                BoardField f = board.stream().filter(field -> field.getPosition().equals(p)).findFirst().orElse(new BoardField(p));
                System.out.print(f.getFigure() != null ? (f.getFigure().getColor() == Color.WHITE ? "W" : "B") : " ");
            }
            System.out.println();
        }
    }

    @Test
    public void printBoard() {
        List<BoardField> board = logic.generateBoard();
        System.out.println("Board Color:");
        printBoardColor(board);

        System.out.println();
        System.out.println("Figures:");
        printBoardFigs(board);

        System.out.println();
        System.out.println("Figures Colors:");
        printBoardFigColors(board);
    }
}
