package at.apf.reallystrangechess.dto;

import at.apf.reallystrangechess.model.Color;

public class CreateGameRequest {
    private Long timeWhite; // sec
    private Long timeBlack; // sec
    private Color team;
    private String name;
    private BoardStyle style;
    private MineConfig mineConfig;

    public Long getTimeWhite() {
        return timeWhite;
    }

    public void setTimeWhite(Long timeWhite) {
        this.timeWhite = timeWhite;
    }

    public Long getTimeBlack() {
        return timeBlack;
    }

    public void setTimeBlack(Long timeBlack) {
        this.timeBlack = timeBlack;
    }

    public Color getTeam() {
        return team;
    }

    public void setTeam(Color team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoardStyle getStyle() {
        return style;
    }

    public void setStyle(BoardStyle style) {
        this.style = style;
    }

    public MineConfig getMineConfig() {
        return mineConfig;
    }

    public void setMineConfig(MineConfig mineConfig) {
        this.mineConfig = mineConfig;
    }

    public class MineConfig {
        private int interval;
        private int offset;

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }
    }
}
