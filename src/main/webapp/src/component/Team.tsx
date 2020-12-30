import React, { Fragment } from 'react'
import {BoardField, Color, Figure, FigureType, TeamPlayerDto} from "../dto/dtos";
import './Team.css'
import {getFigureImgPath} from "../util/utils";
import Button from "react-bootstrap/Button";
import Jumbotron from "react-bootstrap/Jumbotron";


class ChessTeam extends React.Component<Props, State> {

    state: State = {

    }

    timeToWatch(total: number): string {
        let min = Math.floor(total / 60)
        let rest = total % 60
        let sec = rest < 10 ? "0" + rest : rest
        return min + ':' + sec
    }

    render () {
        /*let timeCt = <div className={"teamtimeCt"}>
            <div className={"timewatch" + (this.props.inCharge ? " active" : "")}>{this.timeToWatch(197)}</div>
        </div>*/
        let timeCt = <Fragment></Fragment>
        if (this.props.time !== null) {
            timeCt = <div className={"teamtimeCt"}>
                <div className={"timewatch" + (this.props.inCharge ? " active" : "")}>{this.timeToWatch(this.props.time)}</div>
            </div>
        }

        let joinBtn = <Fragment></Fragment>
        if (this.props.onJoin !== undefined) {
            joinBtn = <Button variant="primary" onClick={() => this.props.onJoin!()}>Join</Button>
        }

        return <Fragment>

                <div className={"teamCt" + (this.props.inCharge || this.props.onJoin !== undefined ? "" : " inactive")}>
                    <div className="teamCtTop">
                        <div className={"teamplayersCt"}>
                            {this.props.players.map(p => <div className={"teamplayer "}>
                                {p.order === this.props.currentPlayer ? "👉" : ""}
                                {p.name}
                            </div>)}
                            {joinBtn}
                        </div>
                        {timeCt}
                    </div>
                    <div className="hitfiguresCt">
                        {this.props.hitFigures.map(f => <img src={getFigureImgPath(f)} />)}
                    </div>
                </div>

        </Fragment>
    }
}

interface Props {
    players: Array<TeamPlayerDto>
    time: number | null
    currentPlayer: number // player order number
    inCharge: boolean // team has to play
    hitFigures: Array<Figure>
    onJoin?: () => void
}
interface State {

}

export default ChessTeam;