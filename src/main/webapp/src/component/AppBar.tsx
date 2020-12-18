import React, { Fragment } from 'react'
import {BoardField, Color} from "../dto/dtos";
import './AppBar.css'


class AppBar extends React.Component<Props, State> {

    state: State = {

    }


    render () {

        return <div className="appbar">{this.props.title}</div>
    }
}

interface Props {
    title: string
}
interface State {

}

export default AppBar;