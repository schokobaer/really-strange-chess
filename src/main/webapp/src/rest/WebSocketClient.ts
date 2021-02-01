import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export default class WebSocketClient {
    private socket?: WebSocket
    private client?: Stomp.Client
    private establieshed = false
    private connectionCalls: Array<Function> = []
    private subscriptions = new Map<string, Stomp.Subscription>()

    private roombaBCC = new BroadcastChannel('roomba')

    connect(): Promise<Stomp.Frame> {

        this.socket = new SockJS('/event/websocket');
        this.client = Stomp.over(this.socket);
        const that = this;
        const client = this.client
        console.info("Trying to connect to websocket endpoint")

        return new Promise((resolve, fail)=> {
            client.connect({}, function(frame?: Stomp.Frame) {
                that.establieshed = true
                that.connectionCalls.forEach(f => f())
                console.info("Connect fallback")
                resolve(frame)
            }, function (err: any) {
                console.error("Error in WebSocket Connection", err)
                console.info("Trying to reconnect to WebSocketEndpoint")
                that.establieshed = false
                that.connectionCalls = []
                that.connect()
                fail()
            });
        })
    }

    subscribeToRoomba(roombaid: string) {
        console.info('WebSocketClient subscribes to roomba ' + roombaid)
        this.subscribe(`/event/roomba/${roombaid}`, (msg: Stomp.Message) => {
            console.info("Received a websocket message for roomba " + roombaid)
            const game = JSON.parse(msg.body)
            console.info(game)
            this.roombaBCC.postMessage(game)
        })
    }

    unsubscribeFromRoomba(roombaid: string) {
        const sub = this.subscriptions.get(`/event/roomba/${roombaid}`)
        sub?.unsubscribe()
    }


    private subscribe(path: string, msgHandler: (msg: Stomp.Message) => any) {
        const f = () => {
            const sub = this.client!.subscribe(path, msgHandler);
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