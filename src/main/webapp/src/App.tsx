import React, {CSSProperties, Fragment} from 'react';
import './App.css';
import WebSocketClient from './rest/WebSocketClient'
import RoombaRestClient from "./rest/RoombaRestClient";
import Sidebar from "./component/Sidebar";
import SystemPage from "./page/SystemPage";
import RoombaPage from "./page/RoombaPage";

class App extends React.Component<Props, State> {

    state: State = {
        page: "ROOMBA",
        ready: false
    }

    ws: WebSocketClient = new WebSocketClient()

    componentDidMount(): void {
        this.ws.connect()
            .then(() => this.setState({ready: true}))
            .catch(err => console.error(`could not connect to ws: ${err}`))
    }

    render () {

        if (!this.state.ready) {
            return <div>Connecting ... </div>
        }

        return <div className="page">
            <div className="header">ITS - Cockpit</div>
            <div className="pagebody">
                <Sidebar active={this.state.page} onChange={page => this.setState({page: page})} />
                { this.state.page === "SYSTEM" && <SystemPage /> }
                { this.state.page === "ROOMBA" && <RoombaPage ws={this.ws} /> }
            </div>
        </div>
    }
}

interface Props {}
interface State {
    page: 'SYSTEM' | 'ROOMBA'
    ready: boolean
}

export default App;
