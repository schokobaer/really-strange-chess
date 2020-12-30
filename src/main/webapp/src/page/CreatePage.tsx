import React, { Fragment, useState } from 'react'
import Jumbotron from "react-bootstrap/Jumbotron";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button"
import {BoardStyle, Color, CreateGameRequest} from "../dto/dtos";
import Select from 'react-select';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import TimePicker, {TimePickerValue} from "react-time-picker";
import {getUserName} from "../util/GameRepo";

const boardstyles = [
    {value: 'CLASSIC', label: 'Classic'},
    {value: 'GRID5X5', label: '5x5 Grid'},
    {value: 'CUSTOM', label: 'Custom'}
]

class CreatePage extends React.Component<Props, State> {

    state: State = {
        color: "WHITE",
        timeWhite: '00:05:00',
        timeBlack: '00:05:00',
        time: false,
        mine: false,
        mineOffset: 10,
        mineInterval: 4,
        board: '',
        style: 'CLASSIC'
    }

    colorChange(col: Color) {
        this.setState({color: col})
        console.info('Changed color: ' + col)
    }

    styleChange(style: BoardStyle) {
        this.setState({style: style})
        console.info('Changed style: ' + style)

    }

    submit() {
        const req: CreateGameRequest = {
            name: getUserName()!,
            style: this.state.style,
            team: this.state.color,
            timeWhite: null,
            timeBlack: null,
            mineConfig: null,
            board: null
        }

        if (this.state.mine) {
            req.mineConfig = {
                interval: this.state.mineInterval,
                offset: this.state.mineOffset
            }
        }

        if (this.state.style === "CUSTOM") {
            req.board = this.state.board
        }

        if (this.state.time) {
            const white = this.state.timeWhite.toString().split(':')
            const black = this.state.timeBlack.toString().split(':')

            req.timeWhite = parseInt(white[1]) * 60 + parseInt(white[2])
            req.timeBlack = parseInt(black[1]) * 60 + parseInt(black[2])
        }

        this.props.onSubmit(req)
    }

    render () {

        let board : any = ''
        if (this.state.style === "CUSTOM") {
            board = <Form.Group>
                <Form.Label>Board:</Form.Label>
                <textarea onChange={e => this.setState({board: e.target.value})}>{this.state.board}</textarea>
            </Form.Group>
        }

        return <Fragment>
            <Jumbotron>
                <Form>
                    <Form.Group>
                        <Container>
                            <Row>
                                <Col>
                                    <Form.Label>Color: </Form.Label>
                                </Col>
                                <Col>
                                    <input type="radio" name="teamcolor" onChange={e => this.colorChange("WHITE")} checked={this.state.color === "WHITE"} /> WHITE
                                </Col>
                                <Col>
                                    <input type="radio" name="teamcolor" onChange={e => this.colorChange("BLACK")} checked={this.state.color === "BLACK"} /> BLACK
                                </Col>
                            </Row>
                            <Row>
                                <Col><Form.Label><input type="checkbox" onChange={e => this.setState({time: e.target.checked})} checked={this.state.time} /> Time</Form.Label></Col>
                                { this.state.time ? <Col><TimePicker maxDetail="second" value={this.state.timeWhite} onChange={(v: TimePickerValue) => this.setState({timeWhite: v})} /></Col> : '' }
                                { this.state.time ? <Col><TimePicker maxDetail="second" value={this.state.timeBlack} onChange={(v: TimePickerValue) => this.setState({timeBlack: v})} /></Col> : '' }
                            </Row>
                            <Row>
                                <Col><Form.Label><input type="checkbox" onChange={e => this.setState({mine: e.target.checked})} checked={this.state.mine} /> Mines</Form.Label></Col>
                                {this.state.mine ? <Col>Offset: <input type="number" value={this.state.mineOffset} onChange={e => this.setState({mineOffset: parseInt(e.target.value)})} /></Col> : ''}
                                {this.state.mine ? <Col>Interval: <input type="number" value={this.state.mineInterval} onChange={e => this.setState({mineInterval: parseInt(e.target.value)})} /></Col> : ''}
                            </Row>
                        </Container>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Style: </Form.Label>
                        <Select defaultValue={boardstyles[0]} options={boardstyles} onChange={(v: any) => this.styleChange(v.value as BoardStyle)} />
                    </Form.Group>
                    {board}
                    <Button onClick={() => this.submit()} >Start</Button>
                </Form>
            </Jumbotron>
        </Fragment>
    }
}

interface Props {
    onSubmit: (req: CreateGameRequest) => void
}
interface State {
    color: Color
    timeWhite: TimePickerValue
    timeBlack: TimePickerValue
    time: boolean
    mine: boolean
    mineOffset: number
    mineInterval: number
    board: string
    style: BoardStyle
}

export default CreatePage;