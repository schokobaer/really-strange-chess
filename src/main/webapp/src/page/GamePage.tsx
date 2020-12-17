import React, { Fragment } from 'react'
import RestClient from "../rest/RestClient";
import WebSocketClient from "../rest/WebSocketClient";
import {Color, GameDto, TeamPlayerDto} from "../dto/dtos";
import ChessBoard from "../component/Board";
import {getUserName} from "../util/GameRepo";


class GamePage extends React.Component<Props, State> {

    state: State = {
        loading: true
    }

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

    render () {
        // No data loaded yet
        if (this.state.loading) {
            return <div>Fetching data ...</div>
        }

        if (this.state.game) {
            return <ChessBoard fields={this.state.game.board} color={this.getTeamColor()} canMove={this.state.game.currentTeam === this.getTeamColor()} />
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