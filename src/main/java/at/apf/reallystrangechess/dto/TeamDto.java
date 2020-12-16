package at.apf.reallystrangechess.dto;

import java.util.List;

public class TeamDto {

    private List<TeamPlayerDto> players;
    private int curPlayer;
    private Long time;

    public List<TeamPlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<TeamPlayerDto> players) {
        this.players = players;
    }

    public int getCurPlayer() {
        return curPlayer;
    }

    public void setCurPlayer(int curPlayer) {
        this.curPlayer = curPlayer;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
