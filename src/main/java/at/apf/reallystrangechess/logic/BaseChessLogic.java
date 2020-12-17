package at.apf.reallystrangechess.logic;

import at.apf.reallystrangechess.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BaseChessLogic {


    public BoardField getField(List<BoardField> board, Position pos) {
        return board.stream().filter(f -> f.getPosition().equals(pos)).findAny().orElse(new BoardField(pos));
    }

    public Position getBoardDimensions(List<BoardField> board) {
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

    private List<BoardField> getLauferMoveableFields(List<BoardField> b, BoardField field) {
        List<BoardField> moveableFields = new ArrayList<>();
        Position dimension = getBoardDimensions(b);

        Figure fig = field.getFigure();

        int x = field.getPosition().getX() - 1;
        int y = field.getPosition().getY() - 1;
        while (x > 0 && y > 0) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() == null) {
                moveableFields.add(f);
            } else if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() != null && f.getFigure().getColor() != fig.getColor()) {
                moveableFields.add(f);
                break;
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
            if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() == null) {
                moveableFields.add(f);
            } else if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() != null && f.getFigure().getColor() != fig.getColor()) {
                moveableFields.add(f);
                break;
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
            if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() == null) {
                moveableFields.add(f);
            } else if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() != null && f.getFigure().getColor() != fig.getColor()) {
                moveableFields.add(f);
                break;
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
            if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() == null) {
                moveableFields.add(f);
            } else if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() != null && f.getFigure().getColor() != fig.getColor()) {
                moveableFields.add(f);
                break;
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
            if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() == null) {
                moveableFields.add(f);
            } else if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() != null && f.getFigure().getColor() != fig.getColor()) {
                moveableFields.add(f);
                break;
            } else {
                break;
            }
            x--;
        }
        x = field.getPosition().getX() + 1;
        y = field.getPosition().getY();
        while (x <= dimension.getX()) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() == null) {
                moveableFields.add(f);
            } else if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() != null && f.getFigure().getColor() != fig.getColor()) {
                moveableFields.add(f);
                break;
            } else {
                break;
            }
            x++;
        }
        x = field.getPosition().getX();
        y = field.getPosition().getY() - 1;
        while (y > 0) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() == null) {
                moveableFields.add(f);
            } else if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() != null && f.getFigure().getColor() != fig.getColor()) {
                moveableFields.add(f);
                break;
            } else {
                break;
            }
            y--;
        }
        x = field.getPosition().getX();
        y = field.getPosition().getY() + 1;
        while (y <= dimension.getY()) {
            BoardField f = getField(b, new Position(x, y));
            if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() == null) {
                moveableFields.add(f);
            } else if (f.getColor() != BoardFieldColor.EMPTY && f.getFigure() != null && f.getFigure().getColor() != fig.getColor()) {
                moveableFields.add(f);
                break;
            } else {
                break;
            }
            y++;
        }
        return moveableFields;
    }

    /**
     * Returns all fields the figure could move, without respect to beeing in chess or not
     * @param board
     * @param field
     * @return
     */
    private List<BoardField> getAllMoveableFields(List<BoardField> board, BoardField field) {
        List<BoardField> moveableFields = getAllHitableFields(board, field);

        if (field.getFigure() == null || field.getFigure().getType() != FigureType.BAUER) {
            return moveableFields;
        }

        Figure fig = field.getFigure();
        Position dim = getBoardDimensions(board);

        if (fig.getColor() == Color.WHITE) {
            BoardField single = getField(board, field.getPosition().move(0, 1));
            if (single.getColor() != BoardFieldColor.EMPTY && single.getFigure() == null) {
                moveableFields.add(single);
            }
            if (field.getPosition().getY() <= 2) {
                BoardField twostep = getField(board, field.getPosition().move(0, 2));
                if (twostep.getColor() != BoardFieldColor.EMPTY && twostep.getFigure() == null) {
                    moveableFields.add(twostep);
                }
            }
        } else {
            BoardField single = getField(board, field.getPosition().move(0, -1));
            if (single.getColor() != BoardFieldColor.EMPTY && single.getFigure() == null) {
                moveableFields.add(single);
            }
            if (field.getPosition().getY() >= dim.getY() - 2) {
                BoardField twostep = getField(board, field.getPosition().move(0, -2));
                if (twostep.getColor() != BoardFieldColor.EMPTY && twostep.getFigure() == null) {
                    moveableFields.add(twostep);
                }
            }
        }


        return moveableFields;
    }

    /**
     * Returns all fields the figure could hit a figure if at that field is a figure.
     * @param board
     * @param field
     * @return
     */
    private List<BoardField> getAllHitableFields(List<BoardField> board, BoardField field) {
        List<BoardField> moveableFields = new ArrayList<>();

        if (field.getFigure() == null) {
            return moveableFields;
        }

        Figure fig = field.getFigure();

        if (fig.getType() == FigureType.BAUER) {
            if (fig.getColor() == Color.WHITE) {
                BoardField left = getField(board, field.getPosition().move(-1, 1));
                if (left.getColor() != BoardFieldColor.EMPTY && left.getFigure() != null && left.getFigure().getColor() == Color.BLACK) {
                    moveableFields.add(left);
                }
                BoardField right = getField(board, field.getPosition().move(1, 1));
                if (right.getColor() != BoardFieldColor.EMPTY && right.getFigure() != null && right.getFigure().getColor() == Color.BLACK) {
                    moveableFields.add(right);
                }
            } else {
                BoardField left = getField(board, field.getPosition().move(-1, -1));
                if (left.getColor() != BoardFieldColor.EMPTY && left.getFigure() != null && left.getFigure().getColor() == Color.WHITE) {
                    moveableFields.add(left);
                }
                BoardField right = getField(board, field.getPosition().move(1, -1));
                if (right.getColor() != BoardFieldColor.EMPTY && right.getFigure() != null && right.getFigure().getColor() == Color.WHITE) {
                    moveableFields.add(right);
                }
            }
        } else if (fig.getType() == FigureType.LAUFER) {
            moveableFields.addAll(getLauferMoveableFields(board, field));
        } else if (fig.getType() == FigureType.SPRINGER) {
            BoardField f = getField(board, field.getPosition().move(-1, -2));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(1, -2));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(2, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(2, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(1, 2));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(-1, 2));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(-2, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(-2, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
        } else if (fig.getType() == FigureType.TURM) {
            moveableFields.addAll(getTurmMoveableFields(board, field));
        } else if (fig.getType() == FigureType.DAME) {
            moveableFields.addAll(getTurmMoveableFields(board, field));
            moveableFields.addAll(getLauferMoveableFields(board, field));
        } else if (fig.getType() == FigureType.KING) {
            BoardField f = getField(board, field.getPosition().move(-1, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(0, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(1, -1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(-1, 0));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(1, 0));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(-1, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(0, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
            f = getField(board, field.getPosition().move(1, 1));
            if (f.getColor() != BoardFieldColor.EMPTY && (f.getFigure() == null || f.getFigure().getColor() != fig.getColor())) {
                moveableFields.add(f);
            }
        }

        return moveableFields;
    }

    public boolean isCheck(List<BoardField> board, Color color) {
        return board.stream()
                .filter(f -> f.getFigure() != null && f.getFigure().getColor() == color
                        && f.getFigure().getType() == FigureType.KING)
                .anyMatch(kingField -> board.stream()
                        .filter(f -> f.getFigure() != null && f.getFigure().getColor() == color.flip()) // get all oponent figures
                        .map(f -> getAllHitableFields(board, f)) // Get all fields the opponent attacks
                        .flatMap(f -> f.stream())
                        .filter(f -> f.getPosition().equals(kingField.getPosition()))
                        .findAny().isPresent());
    }

    private boolean canMakeMoveWithoutBeeingInCheck(List<BoardField> board, Color color) {
        return board.stream()
                .filter(f -> f.getFigure() != null && f.getFigure().getColor() == color) // get all figures of color
                .anyMatch(figField -> !getMoveableFields(board, figField, true).isEmpty()) // check if there exist one figure, that can make a move
                ;
    }

    public boolean isPatt(List<BoardField> board, Color color) {
        return getCheckKings(board, color).isEmpty() && !canMakeMoveWithoutBeeingInCheck(board, color);
    }

    public boolean isCheckmate(List<BoardField> board, Color color) {
        return !getCheckKings(board, color).isEmpty() && !canMakeMoveWithoutBeeingInCheck(board, color);
    }

    public List<BoardField> getCheckKings(List<BoardField> board, Color color) {
        return board.stream()
                .filter(f -> f.getFigure() != null && f.getFigure().getColor() == color
                        && f.getFigure().getType() == FigureType.KING)
                .filter(kingField -> board.stream()
                        .filter(f -> f.getFigure() != null && f.getFigure().getColor() == color.flip()) // get all oponent figures
                        .map(f -> getAllHitableFields(board, f))
                        .flatMap(f -> f.stream())
                        .filter(f -> f.getPosition().equals(kingField.getPosition()))
                        .findAny().isPresent())
                .collect(Collectors.toList());
    }

    public List<BoardField> move(List<BoardField> board, Position from, Position to) {
        Figure fig = board.stream().filter(f -> f.getPosition().equals(from)).findFirst().orElse(new BoardField(from)).getFigure();
        Position dim = getBoardDimensions(board);
        return board.stream().map(f -> {
            if (f.getPosition().equals(from)) {
                return new BoardField(f.getPosition(), f.getColor(), null, f.getMine());
            } else if (f.getPosition().equals(to)) {
                if (fig.getType() == FigureType.BAUER &&
                        (fig.getColor() == Color.WHITE && to.getY() == dim.getY()
                         || fig.getColor() == Color.BLACK && to.getY() == 1)) {
                    fig.setType(FigureType.DAME);
                }
                return new BoardField(f.getPosition(), f.getColor(), fig, f.getMine());
            } else {
                return f;
            }
        }).collect(Collectors.toList());
    }

    public List<BoardField> getMoveableFields(List<BoardField> board, BoardField field, boolean castlingable) {
        if (field.getFigure() == null) {
            return new ArrayList<>();
        }
        Figure fig = field.getFigure();

        List<BoardField> moveable = getAllMoveableFields(board, field).stream().filter(to -> {
            List<BoardField> postBoard = move(board, field.getPosition(), to.getPosition());
            return !isCheck(postBoard, fig.getColor());
        }).collect(Collectors.toList());
        // TODO: Add castling
        return moveable;
    }


    public List<BoardField> generateBoard() {
        List<BoardField> board = new ArrayList<>();

        BoardFieldColor col = BoardFieldColor.BLACK;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                board.add(new BoardField(new Position(x, y), col, null, null));
                col = col.flip();
            }
            col = col.flip();
        }

        board.stream().filter(f -> f.getPosition().getX() == 1 && f.getPosition().getY() == 1).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.TURM));
        board.stream().filter(f -> f.getPosition().getX() == 2 && f.getPosition().getY() == 1).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.SPRINGER));
        board.stream().filter(f -> f.getPosition().getX() == 3 && f.getPosition().getY() == 1).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.LAUFER));
        board.stream().filter(f -> f.getPosition().getX() == 4 && f.getPosition().getY() == 1).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.DAME));
        board.stream().filter(f -> f.getPosition().getX() == 5 && f.getPosition().getY() == 1).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.KING));
        board.stream().filter(f -> f.getPosition().getX() == 6 && f.getPosition().getY() == 1).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.LAUFER));
        board.stream().filter(f -> f.getPosition().getX() == 7 && f.getPosition().getY() == 1).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.SPRINGER));
        board.stream().filter(f -> f.getPosition().getX() == 8 && f.getPosition().getY() == 1).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.TURM));

        board.stream().filter(f -> f.getPosition().getX() == 1 && f.getPosition().getY() == 8).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.TURM));
        board.stream().filter(f -> f.getPosition().getX() == 2 && f.getPosition().getY() == 8).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.SPRINGER));
        board.stream().filter(f -> f.getPosition().getX() == 3 && f.getPosition().getY() == 8).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.LAUFER));
        board.stream().filter(f -> f.getPosition().getX() == 4 && f.getPosition().getY() == 8).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.DAME));
        board.stream().filter(f -> f.getPosition().getX() == 5 && f.getPosition().getY() == 8).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.KING));
        board.stream().filter(f -> f.getPosition().getX() == 6 && f.getPosition().getY() == 8).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.LAUFER));
        board.stream().filter(f -> f.getPosition().getX() == 7 && f.getPosition().getY() == 8).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.SPRINGER));
        board.stream().filter(f -> f.getPosition().getX() == 8 && f.getPosition().getY() == 8).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.TURM));

        for (int x = 1; x <= 8; x++) {
            final int xx = x;
            board.stream().filter(f -> f.getPosition().getX() == xx && f.getPosition().getY() == 2).findFirst().get().setFigure(new Figure(Color.WHITE, FigureType.BAUER));
            board.stream().filter(f -> f.getPosition().getX() == xx && f.getPosition().getY() == 7).findFirst().get().setFigure(new Figure(Color.BLACK, FigureType.BAUER));
        }

        return board;
    }



}
