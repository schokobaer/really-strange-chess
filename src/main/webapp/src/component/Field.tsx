import React, { Fragment } from 'react'
import {BoardField, Color} from "../dto/dtos";
import './Field.css'


class ChessField extends React.Component<Props, State> {

    state: State = {

    }

    getFigure() {
        const path = 'img/figure/'
        if (this.props.field.figure === null) {
            return path + 'nill.png'
        }
        let result = this.props.field.figure.color === "WHITE" ? 'w' : 'b'
        switch (this.props.field.figure.type) {
            case "BAUER":
                result += 'b'
                break
            case "LAUFER":
                result += 'l'
                break
            case "SPRINGER":
                result += 's'
                break
            case "TURM":
                result += 't'
                break
            case "DAME":
                result += 'd'
                break
            case "KING":
                result += 'k'
                break
            default:
                result += 'x'
                break
        }
        result += '.png'
        return path + result
    }

    render () {

        const fieldColor = this.props.field.color === "WHITE" ? "field-white" :
                           this.props.field.color === "BLACK" ? "field-black" :
                           "field-empty"

        return <div className={'field ' + fieldColor}>
                   <div className={'fieldselect'}>
                       <img src={this.getFigure()} />
                   </div>
               </div>
    }
}

interface Props {
    field: BoardField
    selected?: boolean
    danger?: boolean
    hint?: boolean
    lastMove?: boolean
}
interface State {

}

export default ChessField;