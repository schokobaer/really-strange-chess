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
                           "field-empty";

        let selectField =   this.props.selected === true ? ' fieldselected' :
                            this.props.danger === true ? ' fielddanger' :
                            this.props.hint === true ? ' fieldhint' :
                            this.props.lastMove === true ? ' fieldlastmove' : '';

        const mine = this.props.field.mine !== null ? ' fieldmine' : '';

        return <div className={'field ' + fieldColor} onClick={() => this.props.onClick(this.props.field)}>
                   <div className={'fieldselect' + selectField + mine}>
                       <img src={getFigureImgPath(this.props.field.figure)} />
                   </div>
            {this.props.field.mine !== null ? <div style={{marginTop: '-1.3rem'}}>{this.props.field.mine}</div> : ''}
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