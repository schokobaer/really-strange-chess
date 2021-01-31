import React, { Fragment } from 'react';
import './ValueObjective.css'

class ValueObjective extends React.Component<Props, State> {

    render () {
        return <div className="value-objective">
            <div>{this.props.description}:</div>
            <div>{this.props.value} {this.props.unit} </div>
        </div>
    }
}

interface Props {
    description: string
    value: any
    unit?: string
}
interface State {
}

export default ValueObjective