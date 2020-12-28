import React, { Fragment } from 'react';
import './App.css';
import GamePage from './page/GamePage';
import WebSocketClient from './rest/WebSocketClient'
import { uuid } from 'uuidv4'
import {getUserId, getUserName, setUserId, setUserName} from "./util/GameRepo";
import SetupPage from "./page/SetupPage";
import {sha256} from "./util/utils";
import CreatePage from "./page/CreatePage";
import Button from "react-bootstrap/Button"
import {CreateGameRequest} from "./dto/dtos";
import RestClient from "./rest/RestClient";

class App extends React.Component<Props, State> {

    state: State = {
        gameid: undefined,
        userName: getUserName(),
        creating: false,
        loading: false
    }

    ws: WebSocketClient = new WebSocketClient()
    rest: RestClient = new RestClient()

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

    initPlayer(name: string, phrase: string) {
        if (name.length < 2 || phrase.length < 1) {
            return
        }
        sha256(name + '_' + phrase).then((userid: string) => {
            setUserId(userid)
            setUserName(name)
            this.setState({userName: name})
        })
    }

    createGame(req: CreateGameRequest) {
        console.info(req)
        this.rest.create(getUserId()!, req).then((gameid: string) => {
            console.info('Create response: ' + gameid)
            window.location.hash = '#' + gameid
        }).finally( () => this.setState({loading: false}))
        this.setState({creating: false, loading: true})
    }

    render () {

        if (getUserName() === null) {
            return <SetupPage onSubmit={(name: string, phrase: string) => this.initPlayer(name, phrase)} />
        }

        if (this.state.creating) {
            return <CreatePage onSubmit={(req: CreateGameRequest) => this.createGame(req)} />
        }

        if (this.state.gameid) {
            return <GamePage gameid={this.state.gameid} websocket={this.ws} />
        }

        return <div>Hallo Strange Chess<div><Button onClick={() => this.setState({creating: true})}>Create</Button></div></div>
    }
}

interface Props {}
interface State {
    gameid?: string
    userName: string | null
    creating: boolean
    loading: boolean
}

export default App;
