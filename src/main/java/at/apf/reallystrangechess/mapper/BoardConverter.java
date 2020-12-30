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
        while (true) {
            String line = lines[i].toLowerCase();
            if (line.isEmpty()) {
                i++;
                break;
            }
            for (int j = 0; j < lines[i].length(); j++) {
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

        // white figures
        int i2 = 0;
        while (true) {
            String line = lines[i].toUpperCase();
            if (line.isEmpty()) {
                i++;
                break;
            }
            for (int j = 0; j < lines[i].length(); j++) {
                Figure fig = null;
                final String c = line.charAt(j) + "";
                FigureType type = Stream.of(FigureType.values()).filter(t -> t.getChar().equals(c)).findFirst().orElse(null);
                if (type != null) {
                    fig = new Figure(Color.WHITE, type);
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

        // black figures
        i2 = 0;
        while (true) {
            if (i >= lines.length) {
                break;
            }
            String line = lines[i].toUpperCase();
            if (line.isEmpty()) {
                i++;
                break;
            }
            for (int j = 0; j < lines[i].length(); j++) {
                Figure fig = null;
                final String c = line.charAt(j) + "";
                FigureType type = Stream.of(FigureType.values()).filter(t -> t.getChar().equals(c)).findFirst().orElse(null);
                if (type != null) {
                    fig = new Figure(Color.BLACK, type);
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
