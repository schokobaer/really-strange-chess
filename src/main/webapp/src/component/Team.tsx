import React, { Fragment } from 'react'
import {BoardField, Color, TeamPlayerDto} from "../dto/dtos";
import './Team.css'


class ChessTeam extends React.Component<Props, State> {

    state: State = {

    }

    timeToWatch(total: number): string {
        let min = Math.round(total / 60)
        let rest = total % 60
        let sec = rest < 10 ? "0" + rest : rest
        return min + ':' + sec
    }

    render () {
        let timeCt = <div className={"teamtimeCt"}>
            <div className={"timewatch" + (this.props.inCharge ? " active" : "")}>{this.timeToWatch(197)}</div>
        </div>
        if (this.props.time !== null) {
            timeCt = <div className={"teamtimeCt"}>
                <div className={"timewatch" + this.props.inCharge ? " active" : ""}>{this.timeToWatch(this.props.time)}</div>
            </div>
        }

        return <div className={"teamCt" + (this.props.inCharge ? "" : " inactive")}>
            <div className={"teamplayersCt"}>
                {this.props.players.map(p => <div className={"teamplayer "}>
                    {p.order === this.props.currentPlayer ? "🔥" : ""}
                    {p.name}
                </div>)}
            </div>
            {timeCt}
        </div>
    }
}

interface Props {
    players: Array<TeamPlayerDto>
    time: number | null
    currentPlayer: number // player order number
    inCharge: boolean // team has to play
    onJoin: () => void
}
interface State {

}

export default ChessTeam;