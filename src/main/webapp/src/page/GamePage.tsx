import React, { Fragment } from 'react'
import RestClient from "../rest/RestClient";
import WebSocketClient from "../rest/WebSocketClient";
import {Color, GameDto, TeamPlayerDto, Position, MoveRequest, TeamDto, JoinGameRequest} from "../dto/dtos";
import ChessBoard from "../component/Board";
import {getUserId, getUserName} from "../util/GameRepo";
import {BaseChessLogic, colFlip} from "../logic/BaseChessLogic";
import ChessTeam from "../component/Team";


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
        game.lastMove = {
            time: new Date().getTime(),
            from: fromField,
            to: toField
        }
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

    isCastlingable(): boolean {
        return this.getTeamColor() === "WHITE" ? this.state.game!.white.castlingable :
            this.getTeamColor() === "BLACK" ? this.state.game!.black.castlingable : false
    }

    canMove(): boolean {
        if (this.state.game!.state === "FINISHED" || this.state.game!.currentTeam !== this.getTeamColor()) {
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

        if (this.state.game!.lastMove === null) {
            return team.time
        }

        const now = new Date()
        const ereased = Math.floor((now.getTime() - this.state.game!.lastMove.time) / 1000)
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

        return <Fragment>
            <ChessTeam
                players={teamTop.players}
                time={this.calcTime(teamTop)}
                currentPlayer={teamTop.curPlayer}
                hitFigures={teamTop.hitFigures}
                inCharge={this.isInCharge(teamTop)}
                onJoin={joinable ? () => this.join("BLACK") : undefined} />

            <ChessBoard
                castlingable={this.isCastlingable()}
                lastMove={this.state.game.lastMove}
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