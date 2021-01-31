import React, { Fragment } from 'react';

class StopAction extends React.Component<Props, State> {

    state: State = {
        angle: 0
    }

    render () {
        return <div className="roomba-action turn-action">
            <button onClick={() => this.props.onStop()}>Stop</button>
        </div>
    }
}

interface Props {
    onStop: () => void
}
interface State {
}

export default StopAction