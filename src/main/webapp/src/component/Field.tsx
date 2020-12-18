import React, { Fragment } from 'react'
import {BoardField, Color} from "../dto/dtos";
import './Field.css'
import {getFigureImgPath} from "../util/utils";


class ChessField extends React.Component<Props, State> {

    state: State = {

    }

    render () {

        const fieldColor = this.props.field.color === "WHITE" ? "field-white" :
                           this.props.field.color === "BLACK" ? "field-black" :
                           "field-empty"

        let selectField =   this.props.selected === true ? ' fieldselected' :
                            this.props.danger === true ? ' fielddanger' :
                            this.props.hint === true ? ' fieldhint' :
                            this.props.lastMove === true ? ' fieldlastmove' : '';

        return <div className={'field ' + fieldColor} onClick={() => this.props.onClick(this.props.field)}>
                   <div className={'fieldselect' + selectField}>
                       <img src={getFigureImgPath(this.props.field.figure)} />
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
    onClick: (field: BoardField) => void
}
interface State {

}

export default ChessField;