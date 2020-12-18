import React, { Fragment } from 'react'
import {BoardField, Color, FigureMoveDto, Position} from "../dto/dtos";
import ChessField from "./Field";
import {BaseChessLogic, posEq} from "../logic/BaseChessLogic";


class ChessBoard extends React.Component<Props, State> {

    state: State = {

    }

    private logic = new BaseChessLogic()

    fieldClick(field: BoardField) {
        if (!this.props.canMove) {
            return;
        }
        if (this.state.selected === undefined) {
            if (field.figure === null || field.figure.color !== this.props.color) {
                return;
            }
            this.setState({selected: field})
            return;
        }
        if (this.state.selected === field) {
            this.setState({selected: undefined})
            return;
        }
        const hints = this.logic.moveableFields(this.props.fields, this.state.selected, this.props.castlingable)
        if (this.containsField(hints, field)) {
            this.props.onMove(this.state.selected.position, field.position)
            this.setState({selected: undefined})
            return;
        }
        if (field.figure === null || field.figure.color !== this.props.color) {
            this.setState({selected: undefined})
            return;
        }
        this.setState({selected: field})
    }

    involvedInLastMove(field: BoardField): boolean {
        if (this.props.lastMove) {
            return posEq(this.props.lastMove.from.position, field.position)
                || posEq(this.props.lastMove.to.position, field.position)
        }
        return false
    }

    containsField(fields: Array<BoardField>, field: BoardField): boolean {
        return fields.filter(f => posEq(f.position, field.position)).length > 0
    }

    render () {

        const kingsInDanger = this.logic.getKingsInCheck(this.props.fields, "WHITE").concat(this.logic.getKingsInCheck(this.props.fields, "BLACK"))
        const hints = this.state.selected ? this.logic.moveableFields(this.props.fields, this.state.selected, this.props.castlingable) : []

        const rows = []
        const dim = this.logic.boardDimensions(this.props.fields)
        const iy0 = this.props.color === "BLACK" ? 1 : dim.y
        const iyn = this.props.color === "BLACK" ? dim.y + 1 : 0
        const inc = this.props.color === "BLACK" ? 1 : -1
        const ix0 = this.props.color === "BLACK" ? 1 : dim.x
        const ixn = this.props.color === "BLACK" ? dim.x + 1 : 0
        for (let y = iy0; y !== iyn; y += inc) {
            const curRowFields = []
            for (let x = ix0; x !== ixn; x += inc) {
                const f = this.logic.getField(this.props.fields, {x: x, y: y})
                curRowFields.push(f)
            }
            //const curRowFields = this.props.fields.filter(f => f.position.y === i).sort((a, b) => a.position.x - b.position.x)
            rows.push(<div className={'chessrow'}>{curRowFields.map(f => <ChessField hint={this.containsField(hints, f)} danger={this.containsField(kingsInDanger, f)} lastMove={this.involvedInLastMove(f)} selected={this.state.selected === f} field={f} onClick={(f: BoardField) => this.fieldClick(f)} />)}</div>)
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
    canMove: boolean
    castlingable: boolean
    lastMove: FigureMoveDto | null
    onMove: (from: Position, to: Position) => void
}
interface State {
    selected?: BoardField
}

export default ChessBoard;