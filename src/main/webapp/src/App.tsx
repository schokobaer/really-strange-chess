import React, { Fragment } from 'react';
import './App.css';
import GamePage from './page/GamePage';
import WebSocketClient from './rest/WebSocketClient'
import { uuid } from 'uuidv4'
import {getUserId, getUserName, setUserId, setUserName} from "./util/GameRepo";
import SetupPage from "./page/SetupPage";

class App extends React.Component<Props, State> {

    state: State = {
        gameid: undefined,
        userName: getUserName()
    }

    ws: WebSocketClient = new WebSocketClient()

    initWebSockets() {
        this.ws.connect()

    }

    getTableId = () => document.location.hash.length < 2 ? undefined : document.location.hash.substring(1)

    componentWillMount() {

        this.setState({gameid: this.getTableId()})
        window.onhashchange = () => {
            this.setState({gameid: this.getTableId()})
        }
        if (getUserName()) {
            this.initWebSockets()
        }
    }

    initPlayer(name: string) {
        if (name.length < 2) {
            return
        }
        const userid = uuid()
        setUserId(userid)
        setUserName(name)
        this.setState({userName: name})
    }

    render () {

        if (getUserName() === null) {
            return <SetupPage onSubmit={(name: string) => this.initPlayer(name)} />
        }

        if (this.state.gameid) {
            return <GamePage gameid={this.state.gameid} websocket={this.ws} />
        }

        return <div>Hallo Strange Chess</div>
    }
}

interface Props {}
interface State {
    gameid?: string
    userName: string | null
}

export default App;
