import React, {CSSProperties, Fragment} from 'react';
import './App.css';
import WebSocketClient from './rest/WebSocketClient'
import RestClient from "./rest/RestClient";
import Sidebar from "./component/Sidebar";

class App extends React.Component<Props, State> {


    ws: WebSocketClient = new WebSocketClient()
    rest: RestClient = new RestClient()

    initWebSockets() {
        //this.ws.connect()

    }





    render () {

        return <div className="page">
            <div className="header">ITS - Cockpit</div>
            <div className="pagebody">
                <Sidebar />
                <div>Some Content</div>
            </div>
        </div>
    }
}

interface Props {}
interface State {
}

export default App;
