package at.apf.reallystrangechess.logic;

import at.apf.reallystrangechess.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BaseChessLogic {


    private BoardField getField(List<BoardField> board, Position pos) {
        return board.stream().filter(f -> f.getPosition().equals(pos)).findAny().orElse(new BoardField(pos));
    }

    private Position getBoardDimensions(List<BoardField> board) {
        int width = board.stream()
                .max(Comparator.comparingInt(a -> a.getPosition().getX()))
                .orElse(new BoardField(new Position(0, 0)))
                .getPosition().getX();
        int height = board.stream()
                .max(Comparator.comparingInt(a -> a.getPosition().getY()))
                .orElse(new BoardField(new Position(0, 0)))
                .getPosition().getY();
        return new Position(width, height);
    }

    private List<BoardField> revertBoard(List<BoardField> board) {
        Position dimension = getBoardDimensions(board);
        return board.stream()
                .map(f -> new BoardField(
                        new Position(
                                dimension.getX() - (f.getPosition().getY() - 1),
                                dimension.getY() - (f.getPosition().getY() - 1)
                        ),
                        f.getColor(),
                        f.getFigure(),
                        f.getMine()
                ))
                .collect(Collectors.toList());
    }

    private List<BoardField> getLauferMoveableFields(List<BoardField> b, BoardField field) {
        List<BoardField> moveableFields = new ArrayList<>();
        Position dimension = getBoardDimensions(b);

        Figure fig = field.getFigure();

        int x = field.getPosition().getX() - 1;
        int y = field.getPosition().getY() - 1;
        while (x > 0 && y > 0) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            } else {
                break;
            }
            x--;
            y--;
        }
        x = field.getPosition().getX() + 1;
        y = field.getPosition().getY() - 1;
        while (x <= dimension.getX() && y > 0) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            } else {
                break;
            }
            x++;
            y--;
        }
        x = field.getPosition().getX() - 1;
        y = field.getPosition().getY() + 1;
        while (x > 0 && y <= dimension.getY()) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            } else {
                break;
            }
            x--;
            y++;
        }
        x = field.getPosition().getX() + 1;
        y = field.getPosition().getY() + 1;
        while (x <= dimension.getX() && y <= dimension.getY()) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            } else {
                break;
            }
            x++;
            y++;
        }

        return moveableFields;
    }

    private List<BoardField> getTurmMoveableFields(List<BoardField> b, BoardField field) {
        List<BoardField> moveableFields = new ArrayList<>();
        Position dimension = getBoardDimensions(b);

        Figure fig = field.getFigure();

        int x = field.getPosition().getX() - 1;
        int y = field.getPosition().getY();
        while (x > 0) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            } else {
                break;
            }
            x--;
        }
        x = field.getPosition().getX() + 1;
        y = field.getPosition().getY();
        while (x <= dimension.getX()) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            } else {
                break;
            }
            x++;
        }
        x = field.getPosition().getX();
        y = field.getPosition().getY() - 1;
        while (y > 0) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            } else {
                break;
            }
            y--;
        }
        x = field.getPosition().getX();
        y = field.getPosition().getY() + 1;
        while (y <= dimension.getY()) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            } else {
                break;
            }
            y++;
        }
        return moveableFields;
    }

    private List<BoardField> getMoveableFields(List<BoardField> board, BoardField field) {
        List<BoardField> moveableFields = new ArrayList<>();

        if (field.getFigure() == null) {
            return moveableFields;
        }

        Figure fig = field.getFigure();
        Position dimension = getBoardDimensions(board);

        List<BoardField> b = board;
        if (fig.getColor() == Color.BLACK) {
            b = revertBoard(board);
        }

        if (fig.getType() == FigureType.BAUER) {
            {
                BoardField up = getField(b, field.getPosition().move(0, 1));
                if (up.getColor() != BoardFieldColor.EMPTY && up.getFigure() == null) {
                    moveableFields.add(up);
                }
            }
            {
                BoardField upLeft = getField(b, field.getPosition().move(-1, 1));
                if (upLeft.getColor() != BoardFieldColor.EMPTY && upLeft.getFigure() != null && upLeft.getFigure().getColor() != fig.getColor()) {
                    moveableFields.add(upLeft);
                }
            }
            {
                BoardField upRight = getField(b, field.getPosition().move(1, 1));
                if (upRight.getColor() != BoardFieldColor.EMPTY && upRight.getFigure() != null && upRight.getFigure().getColor() != fig.getColor()) {
                    moveableFields.add(upRight);
                }
            }
            if (field.getPosition().getY() <= 2) {
                BoardField up2 = getField(b, field.getPosition().move(0, 2));
                if (up2.getColor() != BoardFieldColor.EMPTY && up2.getFigure() == null) {
                    moveableFields.add(up2);
                }
            }
        } else if (fig.getType() == FigureType.LAUEFER) {
            moveableFields.addAll(getLauferMoveableFields(b, field));
        } else if (fig.getType() == FigureType.SPRINGER) {
            BoardField f = getField(b, field.getPosition().move(-1, -2));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(1, -2));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(2, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(2, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(1, 2));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(-1, 2));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(-2, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(-2, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
        } else if (fig.getType() == FigureType.TURM) {
            moveableFields.addAll(getTurmMoveableFields(b, field));
        } else if (fig.getType() == FigureType.DAME) {
            moveableFields.addAll(getTurmMoveableFields(b, field));
            moveableFields.addAll(getLauferMoveableFields(b, field));
        } else if (fig.getType() == FigureType.KING) {
            BoardField f = getField(b, field.getPosition().move(-1, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(0, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(1, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(-1, 0));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(1, 0));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(-1, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(0, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(b, field.getPosition().move(1, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
        }

        return moveableFields;
    }


}
