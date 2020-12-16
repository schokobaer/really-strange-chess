package at.apf.reallystrangechess.logic;

import at.apf.reallystrangechess.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LogicTest {

    private BaseChessLogic logic = new BaseChessLogic();

    private List<BoardField> board4() {
        List<BoardField> board = new ArrayList<>();
        board.add(new BoardField(new Position(1, 1), BoardFieldColor.WHITE, null, null));
        board.add(new BoardField(new Position(2, 1), BoardFieldColor.BLACK, null, null));
        board.add(new BoardField(new Position(3, 1), BoardFieldColor.WHITE, null, null));
        board.add(new BoardField(new Position(4, 1), BoardFieldColor.BLACK, null, null));

        board.add(new BoardField(new Position(1, 2), BoardFieldColor.BLACK, null, null));
        board.add(new BoardField(new Position(2, 2), BoardFieldColor.WHITE, null, null));
        board.add(new BoardField(new Position(3, 2), BoardFieldColor.BLACK, null, null));
        board.add(new BoardField(new Position(4, 2), BoardFieldColor.WHITE, null, null));

        board.add(new BoardField(new Position(1, 3), BoardFieldColor.WHITE, null, null));
        board.add(new BoardField(new Position(2, 3), BoardFieldColor.BLACK, null, null));
        board.add(new BoardField(new Position(3, 3), BoardFieldColor.WHITE, null, null));
        board.add(new BoardField(new Position(4, 3), BoardFieldColor.BLACK, null, null));

        board.add(new BoardField(new Position(1, 4), BoardFieldColor.BLACK, null, null));
        board.add(new BoardField(new Position(2, 4), BoardFieldColor.WHITE, null, null));
        board.add(new BoardField(new Position(3, 4), BoardFieldColor.BLACK, null, null));
        board.add(new BoardField(new Position(4, 4), BoardFieldColor.WHITE, null, null));

        return board;
    }

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

    @Test
    public void testCheck() {
        List<BoardField> board = board4();
        board.stream().filter(f -> f.getPosition().equals(new Position(2, 1))).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.KING));
        board.stream().filter(f -> f.getPosition().equals(new Position(4, 2))).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.LAUEFER));
        board.stream().filter(f -> f.getPosition().equals(new Position(3, 3))).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.LAUEFER));

        Assert.assertFalse(logic.isCheck(board, Color.WHITE));

        logic.getMoveableFields(board, board.stream().filter(f -> f.getPosition().equals(new Position(2, 1))).findFirst().get(), true)
                .stream().forEach(f -> System.out.println(f));
        System.out.println();

        board = logic.move(board, new Position(2, 1), new Position(1, 1));
        Assert.assertTrue(logic.isCheck(board, Color.WHITE));

        logic.getMoveableFields(board, board.stream().filter(f -> f.getPosition().equals(new Position(1, 1))).findFirst().get(), true)
                .stream().forEach(f -> System.out.println(f));
        System.out.println();

        Assert.assertFalse(logic.isCheckmate(board, Color.WHITE));
        Assert.assertFalse(logic.isPatt(board, Color.WHITE));

        board = logic.move(board, new Position(1, 1), new Position(2, 1));
        board.stream().filter(f -> f.getPosition().equals(new Position(4, 2))).findFirst().get().getFigure().setType(FigureType.DAME); // Rechter Laufer zu Dame
        Assert.assertFalse(logic.isCheckmate(board, Color.WHITE));
        Assert.assertTrue(logic.isPatt(board, Color.WHITE));

        board = logic.move(board, new Position(4, 2), new Position(2, 2));
        Assert.assertTrue(logic.isCheckmate(board, Color.WHITE));
        Assert.assertFalse(logic.isPatt(board, Color.WHITE));
    }

    @Test
    public void someMoves() {
        List<BoardField> board = logic.generateBoard();
        printBoardFigs(board);
        System.out.println("-----------------");

        board = logic.move(board, new Position(5,2), new Position(5,4));
        printBoardFigs(board);
        System.out.println("-----------------");

        logic.getMoveableFields(board, board.stream().filter(f -> f.getPosition().equals(new Position(5, 4))).findFirst().get(), false)
                .stream().forEach(f -> System.out.println(f.getPosition()));
    }
}
