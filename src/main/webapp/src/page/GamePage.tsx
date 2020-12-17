import React, { Fragment } from 'react'
import RestClient from "../rest/RestClient";
import WebSocketClient from "../rest/WebSocketClient";
import {GameDto} from "../dto/dtos";
import ChessBoard from "../component/Board";


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

    render () {
        // No data loaded yet
        if (this.state.loading) {
            return <div>Fetching data ...</div>
        }

        if (this.state.game) {
            return <ChessBoard fields={this.state.game.board} />
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