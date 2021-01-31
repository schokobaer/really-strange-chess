import React, { Fragment } from 'react';
import './TurnAction.css'

class TurnAction extends React.Component<Props, State> {

    state: State = {
        angle: 0
    }

    render () {
        return <div className="roomba-action turn-action">
            <div>
                <div>Angle:</div>
                <input type="range" min="-180" max="180" value={this.state.angle} onChange={e => this.setState({angle: parseInt(e.target.value)})} />
                <input type="number" value={this.state.angle} onChange={e => this.setState({angle: parseInt(e.target.value)})} />
            </div>
            <button onClick={() => this.props.onTurn(this.state.angle)}>Turn</button>
        </div>
    }
}

interface Props {
    onTurn: (angle: number) => void
}
interface State {
    angle: number
}

export default TurnAction