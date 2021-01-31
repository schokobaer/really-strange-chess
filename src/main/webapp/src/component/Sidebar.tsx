import React, { Fragment } from 'react';
import './Sidebar.css'

class Sidebar extends React.Component<Props, State> {

    itemClicked(page: 'SYSTEM' | 'ROOMBA') {
        if (this.props.active !== page) {
            this.props.onChange(page)
        }
    }

    render () {

        return <div className="sidebar">
            <div className={"sidebar-entry " + (this.props.active === "SYSTEM" ? "sidebar-entry-active" : "")} onClick={() => this.itemClicked('SYSTEM')}>System</div>
            <div className={"sidebar-entry " + (this.props.active === "ROOMBA" ? "sidebar-entry-active" : "")} onClick={() => this.itemClicked('ROOMBA')}>Roomba</div>
        </div>
    }
}

interface Props {
    onChange: (page: 'SYSTEM' | 'ROOMBA') => void
    active: 'SYSTEM' | 'ROOMBA'
}
interface State {

}

export default Sidebar