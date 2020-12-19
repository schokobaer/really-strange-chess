import React, { Fragment } from 'react'
import Jumbotron from "react-bootstrap/Jumbotron";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button"

class SetupPage extends React.Component<Props, State> {

    state: State = {
        name: ''
    }

    render () {

        return <Fragment>
            <Jumbotron>
                <Form>
                    <Form.Group>
                        <Form.Label>Name</Form.Label>
                        <Form.Control type="text" onChange={(e) => this.setState({name: e.target.value})} id="tbxname"/>
                    </Form.Group>
                    <Button onClick={() => this.props.onSubmit(this.state.name)}>Ok</Button>
                </Form>
            </Jumbotron>
        </Fragment>
    }
}

interface Props {
    onSubmit: (name: string) => void
}
interface State {
    name: string
}

export default SetupPage;