package at.apf.reallystrangechess.mapper;

import at.apf.reallystrangechess.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class BoardConverter {


    public List<BoardField> fromString(String str) {
        List<BoardField> board = new ArrayList<>();

        String[] lines = str.split("\n");
        int i = 0;

        // board color fields
        while (i < lines.length) {
            String line = lines[i].toLowerCase();
            if (line.isEmpty()) {
                i++;
                break;
            }
            for (int j = 0; j < line.length(); j++) {
                BoardFieldColor color = BoardFieldColor.EMPTY;
                if (line.charAt(j) == 'w') {
                    color = BoardFieldColor.WHITE;
                } else if (line.charAt(j) == 'b') {
                    color = BoardFieldColor.BLACK;
                }
                board.add(new BoardField(new Position(j + 1, i + 1), color, null, null));
            }
            i++;
        }

        // figures
        int i2 = 0;
        while (i < lines.length) {
            String line = lines[i];
            if (line.isEmpty()) {
                i++;
                break;
            }
            for (int j = 0; j < line.length(); j++) {
                Figure fig = null;
                final String c = (line.charAt(j) + "").toUpperCase();
                FigureType type = Stream.of(FigureType.values()).filter(t -> t.getChar().equals(c)).findFirst().orElse(null);
                if (type != null) {
                    Color color = c.equals(line.charAt(j) + "") ? Color.BLACK : Color.WHITE;
                    fig = new Figure(color, type);
                }
                Position pos = new Position(j + 1, i2 + 1);
                Optional<BoardField> field = board.stream().filter(f -> f.getPosition().equals(pos)).findFirst();
                if (field.isPresent() && fig != null) {
                    field.get().setFigure(fig);
                }
            }
            i++;
            i2++;
        }

        return board;
    }
}
