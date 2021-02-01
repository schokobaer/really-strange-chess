import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export default class WebSocketClient {
    private socket?: WebSocket
    private client?: Stomp.Client
    private establieshed = false
    private connectionCalls: Array<Function> = []
    private subscriptions = new Map<string, Stomp.Subscription>()

    private roombaBCC = new BroadcastChannel('roomba')

    connect() {
        this.socket = new SockJS('/event/websocket');
        this.client = Stomp.over(this.socket);
        const that = this;
        console.info("Trying to connect to websocket endpoint")
        this.client.connect({}, function(frame: any) {
            that.establieshed = true
            that.connectionCalls.forEach(f => f())
            console.info("Connect fallback")

            // Subscribe on connect:
            /*console.info(frame)
            that.subscribe('/event/lounge', (msg: any) => {
                console.info("Received a websocket message for lounge")
                that.loungeBCC.postMessage("update")
            });*/
        }, function (err: any) {
            console.error("Error in WebSocket Connection", err)
            console.info("Trying to reconnect to WebSocketEndpoint")
            that.establieshed = false
            that.connectionCalls = []
            that.connect()
        });
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