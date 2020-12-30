import React, { Fragment } from 'react'
import RestClient from "../rest/RestClient";
import WebSocketClient from "../rest/WebSocketClient";
import {Color, GameDto, TeamPlayerDto, Position, MoveRequest, TeamDto, JoinGameRequest} from "../dto/dtos";
import ChessBoard from "../component/Board";
import {getUserId, getUserName, soundOn} from "../util/GameRepo";
import {BaseChessLogic, colFlip} from "../logic/BaseChessLogic";
import ChessTeam from "../component/Team";
import Button from "react-bootstrap/Button";

const moveSound = new Audio('sound/move.mp3')
const hitSound = new Audio('sound/hit.wav')


class GamePage extends React.Component<Props, State> {

    state: State = {
        loading: true
    }

    timer: number = 0
    logic = new BaseChessLogic()
    rest: RestClient = new RestClient()
    bcc: BroadcastChannel = new BroadcastChannel('game')

    componentDidMount() {

        this.props.websocket.subscribeToGame(this.props.gameid)

        this.bcc.onmessage = (msg: MessageEvent) => {
            console.info('GamePage: Received a BCC Msg', msg)
            const game = msg.data as GameDto
            if (soundOn() && game.currentTeam !== this.state.game?.currentTeam) {
                if (game.history.length > 0 && game.history[game.history.length - 1].to.figure !== null) {
                    hitSound.play().then(() => console.info('Played hit sound successfully'))
                        .catch(err => console.error('Cound not play hit sound: ', err))
                } else {
                    moveSound.play().then(() => console.info('Played move sound successfully'))
                        .catch(err => console.error('Cound not play move sound: ', err))
                }
            }
            this.setState({game: game, loading: false})
        }

        this.loadGame()

        this.timer = window.setInterval(() => this.countdown(), 1000);
    }

    componentWillUnmount() {
        this.bcc.close()
        window.clearInterval(this.timer)
    }

    countdown() {
        if (this.state.game) {
            if (this.state.game.state === "PLAYING") {
                const game = this.state.game

                if (this.getTeamColor() === "WHITE" && this.calcTime(game.white) === 0
                    || this.getTeamColor() === "BLACK" && this.calcTime(game.black) === 0) {
                    this.reportTimeout()
                }

                this.setState({game: game})
            }
        }
    }


    loadGame() {
        this.rest.getGame(this.props.gameid).then(game => {
            this.setState({game: game, loading: false})
        })
    }

    getPlayer() {
        if (this.state.game) {
            let player = this.state.game.white.players.find(p => p.name === getUserName())
            if (player === undefined) {
                player = this.state.game.black.players.find(p => p.name === getUserName())
            }
            if (player) {
                return player
            }
        }
        return null
    }

    reportTimeout() {
        this.rest.timeout(this.props.gameid, getUserId()!)
    }

    makeMove(from: Position, to: Position) {
        console.log('Mode made')
        console.log('from', from)
        console.log('to', to)
        const req: MoveRequest = {
            from: from,
            to: to
        }
        this.rest.move(this.props.gameid, getUserId()!, req)
        const game = this.state.game!
        const fromField = this.logic.getField(game.board, from)
        const toField = this.logic.getField(game.board, to)
        game.currentTeam = colFlip(game.currentTeam)
        game.board = this.logic.move(game.board, from, to)
        game.history.push({from: fromField, to: toField, time: new Date().getTime()})
        this.setState({game: game})
    }

    getTeamColor(): Color | undefined {
        if (this.state.game) {
            if (this.state.game.white.players.find(p => p.name === getUserName())) {
                return "WHITE"
            }
            if (this.state.game.black.players.find(p => p.name === getUserName())) {
                return "BLACK"
            }
        }
    }

    canMove(): boolean {
        if (this.state.game!.state === "FINISHED" || this.state.game!.currentTeam !== this.getTeamColor()
            || this.state.game!.white.players.length === 0 || this.state.game!.black.players.length === 0) {
            return false
        }
        const team = this.getTeamColor() === "WHITE" ? this.state.game!.white :
                     this.getTeamColor() === "BLACK" ? this.state.game!.black : null
        if (team === null || this.getPlayer() === undefined || team.curPlayer !== this.getPlayer()!.order) {
            return false
        }
        return true
    }

    join(color: Color) {
        const req: JoinGameRequest = {
            color: color,
            name: getUserName()!
        }
        this.rest.join(this.props.gameid, getUserId()!, req).finally(() => {
            this.loadGame()
            this.setState({loading: false})
        })

        this.setState({loading: true})
    }

    undo() {
        this.rest.undo(this.props.gameid, getUserId()!)
        const game = this.state.game!
        game.currentTeam = colFlip(game.currentTeam)
        this.setState({game: game})
    }

    calcTime(team: TeamDto): number | null{

        if (team.time === null) {
            return null
        }

        if (this.state.game!.state !== "PLAYING") {
            return team.time
        }

        if (!this.isInCharge(team)) {
            return team.time
        }

        if (this.state.game!.history.length === 0) {
            return team.time
        }
        const lastMove = this.state.game!.history[this.state.game!.history.length - 1]
        const now = new Date()
        const ereased = Math.floor((now.getTime() - lastMove.time) / 1000)
        return Math.max(team.time - ereased, 0)
    }

    isInCharge(team: TeamDto): boolean {
        const game = this.state.game!
        if (game.state === "FINISHED") {
            return false
        }

        if (game.state === "PENDING") {
            return game.white === team && game.black.players.length > 0 && game.white.players.length > 0
        }

        // Playing
        return (game.currentTeam === "WHITE" && game.white === team
            || game.currentTeam === "BLACK" && game.black === team)
    }

    render () {
        // No data loaded yet
        if (this.state.loading) {
            return <div>Fetching data ...</div>
        }

        if (this.state.game === undefined) {
            return <div>No game found</div>
        }

        const teamColor = this.getTeamColor()
        const teamTop = teamColor === "BLACK" ? this.state.game.white : this.state.game.black
        const teamBottom =  teamColor === "BLACK" ? this.state.game.black : this.state.game.white

        const joinable = this.state.game.state === "PENDING" && teamColor === undefined

        let undo = <Fragment></Fragment>
        if (this.state.game.history.length > 0 && teamColor === this.state.game.currentTeam) {
            undo = <Fragment>
                <Button variant="warning" onClick={() => this.undo()}>Undo</Button>
            </Fragment>
        }

        return <Fragment>
            <ChessTeam
                players={teamTop.players}
                time={this.calcTime(teamTop)}
                currentPlayer={teamTop.curPlayer}
                hitFigures={teamTop.hitFigures}
                inCharge={this.isInCharge(teamTop)}
                onJoin={joinable ? () => this.join("BLACK") : undefined} />

            <ChessBoard
                history={this.state.game.history}
                onMove={(from: Position, to: Position) => this.makeMove(from, to)}
                fields={this.state.game.board}
                color={this.getTeamColor()}
                canMove={this.canMove()} />

            <ChessTeam
                players={teamBottom.players}
                time={this.calcTime(teamBottom)}
                currentPlayer={teamBottom.curPlayer}
                hitFigures={teamBottom.hitFigures}
                inCharge={this.isInCharge(teamBottom)}
                onJoin={joinable ? () => this.join("WHITE") : undefined} />

            {undo}
        </Fragment>

      
    }
}
  
interface Props {
    gameid: string
    websocket: WebSocketClient
}
interface State {
    loading: boolean
    game?: GameDto
}

export default GamePage;