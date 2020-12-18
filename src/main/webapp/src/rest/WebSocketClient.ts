import SockJS from 'sockjs-client';
import Stomp, {Frame} from 'stompjs';

export default class WebSocketClient {
    private socket: any
    private client: any
    private establieshed = false
    private connectionCalls: Array<Function> = []
    private subscriptions = new Map<string, any>()

    private loungeBCC = new BroadcastChannel('lounge')
    private gameBCC = new BroadcastChannel('game')

    connect() {
        this.socket = new SockJS('/event/websocket');
        this.client = Stomp.over(this.socket);
        const that = this;
        console.info("Trying to connect to websocket endpoint")
        this.client.connect({}, function(frame: any) {
            that.establieshed = true
            that.connectionCalls.forEach(f => f())
            console.info("Connect fallback")
            console.info(frame)
            that.subscribe('/event/lounge', (msg: any) => {
                console.info("Received a websocket message for lounge")
                that.loungeBCC.postMessage("update")
            });
        }, function (err: any) {
            console.error("Error in WebSocket Connection", err)
            console.info("Trying to reconnect to WebSocketEndpoint")
            that.establieshed = false
            that.connectionCalls = []
            that.connect()
        });
    }

    subscribeToGame(gameid: string) {
        console.info('WebSocketClient subscribes to ' + gameid)
        this.subscribe(`/event/game/${gameid}`, (msg: Frame) => {
            console.info("Received a websocket message for game " + gameid)
            const game = JSON.parse(msg.body)
            console.info(game)
            this.gameBCC.postMessage(game)
        })
    }


    private subscribe(path: string, msgHandler: Function) {
        const f = () => {
            let sub = this.client.subscribe(path, msgHandler);
            console.info("Subscribing for topic " + path)
            this.subscriptions.set(path, sub);
        }
        if (this.establieshed === true) {
            f()
        } else {
            this.connectionCalls.push(f)
        }
    }

}