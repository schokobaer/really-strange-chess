import React, { Fragment } from 'react';
import Select from 'react-select';
import './RoombaPage.css'
import ValueObjective from "../component/ValueObjective";
import DriveAction from "../component/action/DriveAction";
import TurnAction from "../component/action/TurnAction";
import StopAction from "../component/action/StopAction";
import DriveToAction from "../component/action/DriveToAction";
import RoombaRestClient from "../rest/RoombaRestClient";
import WebSocketClient from "../rest/WebSocketClient";
import RoombaDto from "../dto/RoombaDto"

class RoombaPage extends React.Component<Props, State> {

    state: State = {
        roombas: [],
        refreshing: false
    }

    rest: RoombaRestClient = new RoombaRestClient()
    bbc: BroadcastChannel = new BroadcastChannel('roomba')

    componentDidMount(): void {
        this.refreshInstnces()
        this.bbc.onmessage = (msg: MessageEvent) => {
            console.info('RoombaPage: Received a BCC Msg', msg)
            const roomba = msg.data as RoombaDto
            if (this.state.roomba || roomba.id === this.state.roomba) {
                this.setState({roomba: roomba})
            }
        }
    }

    componentWillUnmount(): void {
        if (this.state.roomba) {
            this.props.ws.unsubscribeFromRoomba(this.state.roomba.id)
        }
        this.bbc.close()
    }

    refreshInstnces() {
        this.setState({refreshing: true})
        this.rest.listAvailableRoombas()
            .then(data => {
                this.setState({roombas: data})
            })
            .catch(err => console.error(err))
            .finally(() => this.setState({refreshing: false}))
    }

    roombaSelected(roombaid: string) {
        if (this.state.roomba) {
            this.props.ws.unsubscribeFromRoomba(this.state.roomba.id)
        }

        this.props.ws.subscribeToRoomba(roombaid)
        this.rest.getRoomba(roombaid)
            .then(roomba => this.setState({roomba: roomba}))
            .catch(err => console.error(err))
    }

    drive(speed: number) {
        if (this.state.roomba) {
            this.rest.drive(this.state.roomba.id, speed)
                .catch(err => console.error(err))
        }
    }

    turn(angle: number) {
        if (this.state.roomba) {
            this.rest.turn(this.state.roomba.id, angle)
                .catch(err => console.error(err))
        }
    }

    stop() {
        if (this.state.roomba) {
            this.rest.stop(this.state.roomba.id)
                .catch(err => console.error(err))
        }
    }

    driveTo(target: string) {
        if (this.state.roomba) {
            this.rest.driveTo(this.state.roomba.id, target)
                .catch(err => console.error(err))
        }
    }

    render () {
        return <div className="roomba-page">
            <div className="roomba-select-ct">
                Instance: <Select options={this.state.roombas.map(r => {return {value: r, label: r}})} onChange={(v: any) => this.roombaSelected(v.value)} />
                <img className={"" + (this.state.refreshing && "rotating")} src="/img/animation/refresh.png" onClick={() => this.refreshInstnces()} />
            </div>

            {this.state.roomba !== undefined &&
            <div className="roomba-ct">
                <div>
                    <div>Data</div>
                    <div className="roomba-values-ct">
                        <ValueObjective description="Wheel Left" value={this.state.roomba.wheels.speed.left} unit={"rpm"}/>
                        <ValueObjective description="Wheel Right" value={this.state.roomba.wheels.speed.right} unit={"rpm"}/>
                        <ValueObjective description="Location" value={this.state.roomba.location || '?'}/>
                        <ValueObjective description="Battery" value={Math.round(this.state.roomba.battery.energy * 100)} unit={"%"}/>
                        <ValueObjective description="State" value={this.state.roomba.state}/>
                        <ValueObjective description="Bumped" value={this.state.roomba.bumper.bumped.toString()}/>
                        <ValueObjective description="Grounded" value={this.state.roomba.wheels.grounded.toString()}/>
                    </div>
                </div>
                <div>
                    <div>Actions</div>
                    <DriveAction onDrive={speed => this.drive(speed)} />
                    <TurnAction onTurn={angle => this.turn(angle)} />
                    <StopAction onStop={() => this.stop()} />
                    <DriveToAction onDriveTo={target => this.driveTo(target)} />
                </div>
            </div>
            }

        </div>
    }
}

interface Props {
    ws: WebSocketClient
}
interface State {
    roombas: Array<string>
    roomba?: RoombaDto
    refreshing: boolean
}

export default RoombaPage