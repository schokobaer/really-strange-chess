import React, { Fragment } from 'react';
import './DriveToAction.css'

class DriveToAction extends React.Component<Props, State> {

    state: State = {
        target: ''
    }

    render () {
        return <div className="roomba-action driveto-action">
            <div>
                <div>Target:</div>
                <input type="text" value={this.state.target} onChange={e => this.setState({target: e.target.value})} />
            </div>
            <button onClick={() => this.props.onDriveTo(this.state.target)}>Drive To</button>
        </div>
    }
}

interface Props {
    onDriveTo: (target: string) => void
}
interface State {
    target: string
}

export default DriveToAction