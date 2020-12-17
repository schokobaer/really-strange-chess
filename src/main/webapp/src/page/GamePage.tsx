import React, { Fragment } from 'react'
import RestClient from "../rest/RestClient";
import WebSocketClient from "../rest/WebSocketClient";
import {Color, GameDto, TeamPlayerDto, Position, MoveRequest} from "../dto/dtos";
import ChessBoard from "../component/Board";
import {getUserId, getUserName} from "../util/GameRepo";
import {BaseChessLogic, colFlip} from "../logic/BaseChessLogic";


class GamePage extends React.Component<Props, State> {

    state: State = {
        loading: true
    }

    logic = new BaseChessLogic()
    rest: RestClient = new RestClient()
    bcc: BroadcastChannel = new BroadcastChannel('game')

    componentDidMount() {

        this.props.websocket.subscribeToGame(this.props.gameid)

        this.bcc.onmessage = (msg: MessageEvent) => {
            console.info('GamePage: Received a BCC Msg', msg)
            const game = msg.data as GameDto
            this.setState({game: game})
        }

        this.loadGame()
    }

    componentWillUnmount() {
        this.bcc.close()
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
            time: new Date(),
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

    render () {
        // No data loaded yet
        if (this.state.loading) {
            return <div>Fetching data ...</div>
        }

        if (this.state.game) {
            return <ChessBoard
                castlingable={this.isCastlingable()}
                lastMove={this.state.game.lastMove}
                onMove={(from: Position, to: Position) => this.makeMove(from, to)}
                fields={this.state.game.board}
                color={this.getTeamColor()}
                canMove={this.canMove()} />
        }
        
        return <div>Yes</div>
      
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