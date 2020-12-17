import React, { Fragment } from 'react'
import {BoardField, Color} from "../dto/dtos";
import ChessField from "./Field";


class ChessBoard extends React.Component<Props, State> {

    state: State = {

    }

    render () {

        const rows = []
        if (this.props.color === "BLACK") {
            for (let i = 1; i <= 8; i++) {
                const curRowFields = this.props.fields.filter(f => f.position.y === i).sort((a, b) => a.position.x - b.position.x)
                rows.push(<div className={'chessrow'}>{curRowFields.map(f =><ChessField field={f} />)}</div>)
            }
        } else {
            for (let i = 8; i >= 1; i--) {
                const curRowFields = this.props.fields.filter(f => f.position.y === i).sort((a, b) => b.position.x - a.position.x)
                rows.push(<div className={'chessrow'}>{curRowFields.map(f => <ChessField field={f} />)}</div>)
            }
        }

        return <div className='boardbox'>
                   <div className='boardct'>
                      {rows}
                   </div>
               </div>
    }
}

interface Props {
    fields: Array<BoardField>
    color?: Color
}
interface State {

}

export default ChessBoard;