import React, { Fragment } from 'react'
import Jumbotron from "react-bootstrap/Jumbotron";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button"

class CreatePage extends React.Component<Props, State> {

    state: State = {
        name: '',
        phrase: ''
    }

    render () {

        return <Fragment>
            <Jumbotron>
                <Form>
                    <Form.Group>
                        <Form.Label>Color</Form.Label>
                        <Form.Check onChange={(e) => console.error(e)} />
                    </Form.Group>
                    <Button>Ok</Button>
                </Form>
            </Jumbotron>
        </Fragment>
    }
}

interface Props {

}
interface State {
    name: string,
    phrase: string
}

export default CreatePage;