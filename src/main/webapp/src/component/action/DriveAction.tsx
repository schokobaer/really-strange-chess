import React, { Fragment } from 'react';
import './DriveAction.css'

class DriveAction extends React.Component<Props, State> {

    state: State = {
        speed: 100
    }

    render () {
        return <div className="roomba-action drive-action">
            <div>
                <div>Speed:</div>
                <input type="range" min="-200" step={10} max="200" value={this.state.speed} onChange={e => this.setState({speed: parseInt(e.target.value)})} />
                <input type="number" value={this.state.speed} onChange={e => this.setState({speed: parseInt(e.target.value)})} />
            </div>
            <button onClick={() => this.props.onDrive(this.state.speed)}>Drive</button>
        </div>
    }
}

interface Props {
    onDrive: (speed: number) => void
}
interface State {
    speed: number
}

export default DriveAction