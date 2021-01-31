import React, { Fragment } from 'react';
import Select from 'react-select';
import './RoombaPage.css'
import ValueObjective from "../component/ValueObjective";
import DriveAction from "../component/action/DriveAction";
import TurnAction from "../component/action/TurnAction";
import StopAction from "../component/action/StopAction";
import DriveToAction from "../component/action/DriveToAction";

class RoombaPage extends React.Component<Props, State> {

    state: State = {
        roombas: ['roomba1', 'roomba2'],
        selectedRoomba: undefined
    }

    roombaSelected(roombaid: string) {
        this.setState({selectedRoomba: roombaid})
    }

    drive(speed: number) {

    }

    turn(angle: number) {

    }

    stop() {

    }

    driveTo(target: string) {

    }

    render () {
        return <div className="roomba-page">
            <div className="roomba-select-ct">
                Instance: <Select options={this.state.roombas.map(r => {return {value: r, label: r}})} onChange={(v: any) => this.roombaSelected(v.value)} />
            </div>

            <div className="roomba-ct">
                <div>
                    <div>Data</div>
                    <div className="roomba-values-ct">
                        <ValueObjective description="Wheel Left" value={50} unit={"rpm"} />
                        <ValueObjective description="Wheel Right" value={50} unit={"rpm"} />
                        <ValueObjective description="Location" value={"?"} />
                        <ValueObjective description="Battery" value={54.4} unit={"%"} />
                        <ValueObjective description="State" value={"IDLE"} />
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

        </div>
    }
}

interface Props {}
interface State {
    roombas: Array<string>
    selectedRoomba?: string
}

export default RoombaPage