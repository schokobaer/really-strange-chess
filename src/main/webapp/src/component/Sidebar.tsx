import React, { Fragment } from 'react';
import './Sidebar.css'

class Sidebar extends React.Component<Props, State> {

    render () {

        return <div className="sidebar">
            <div className="sidebar-entry sidebar-entry-active">System</div>
            <div className="sidebar-entry">Roomba</div>
        </div>
    }
}

interface Props {}
interface State {
}

export default Sidebar