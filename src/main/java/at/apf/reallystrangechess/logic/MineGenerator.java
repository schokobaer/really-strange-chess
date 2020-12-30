package at.apf.reallystrangechess.logic;

import at.apf.reallystrangechess.model.BoardField;
import at.apf.reallystrangechess.model.BoardFieldColor;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MineGenerator {

    private int offset;
    private int interval;
    private int minMine = 3;
    private int maxMine = 9;
    private BoardFieldColor color = BoardFieldColor.WHITE;

    private static final Random rnd = new Random();

    public MineGenerator(int offset, int interval) {
        this.offset = offset;
        this.interval = interval;
    }

    public List<BoardField> step(List<BoardField> board) {

        if (offset > 0) {
            offset--;
            return board;
        }

        BoardField mineField = null;
        List<BoardField> mineableFields = board.stream().filter(f -> f.getFigure() == null && f.getColor() == color)
                .collect(Collectors.toList());

        if (!mineableFields.isEmpty()) {
            mineField = mineableFields.get(rnd.nextInt(mineableFields.size()));
        }

        final BoardField mineFieldConst = mineField;
        int mineLen = minMine + rnd.nextInt(maxMine - minMine + 1);
        offset = interval;
        color = color.flip();

        return board.stream().map(f -> {
            if (f == mineFieldConst) {
                return new BoardField(f.getPosition(), f.getColor(), f.getFigure(), mineLen);
            }
            return new BoardField(f.getPosition(), f.getColor(), f.getFigure(), f.getMine());
        }).collect(Collectors.toList());
    }
}
