import {BoardField, BoardFieldColor, Figure, FigureType, Position} from "../dto/dtos";
import {posEq} from "../logic/BaseChessLogic";


export default function boardFromString(str: string): Array<BoardField> {
    const board: Array<BoardField> = []
    const lines = str.split('\n')

    let i = 0

    // Field colors
    while (i < lines.length) {
        const line = lines[i].toLocaleLowerCase()
        if (line.length === 0) {
            i++
            break;
        }
        for (let j = 0; j < line.length; j++) {
            let color: BoardFieldColor = 'EMPTY'
            if (line.charAt(j) === 'w') {
                color = 'WHITE'
            } else if (line.charAt(j) === 'b') {
                color = 'BLACK'
            }
            board.push({position: {x: j + 1, y: i + 1}, color: color, figure: null, mine: null})
        }
        i++
    }

    // white figures
    let i2 = 0
    while (i < lines.length) {
        const line = lines[i].toLowerCase()
        if (line.length === 0) {
            i++
            break;
        }
        for (let j = 0; j < line.length; j++) {
            let fig: Figure | null = null
            const c = line.charAt(j)
            let type: FigureType | null = null
            if (c === 'b') {
                type = "BAUER"
            } else if (c === 'l') {
                type = "LAUFER"
            } else if (c === 's') {
                type = "SPRINGER"
            } else if (c === 't') {
                type = "TURM"
            } else if (c === 'd') {
                type = "DAME"
            } else if (c === 'k') {
                type = "KING"
            }
            if (type != null) {
                fig = {color: "WHITE", type: type}
            }
            const pos: Position = {x: j + 1, y: i2 + 1}
            const field: BoardField | undefined = board.find(f => posEq(f.position, pos))
            if (field !== undefined && fig !== null) {
                field.figure = fig
            }
        }
        i++
        i2++
    }

    // black figures
    i2 = 0
    while (i < lines.length) {
        const line = lines[i].toLowerCase()
        if (line.length === 0) {
            i++
            break;
        }
        for (let j = 0; j < line.length; j++) {
            let fig: Figure | null = null
            const c = line.charAt(j)
            let type: FigureType | null = null
            if (c === 'b') {
                type = "BAUER"
            } else if (c === 'l') {
                type = "LAUFER"
            } else if (c === 's') {
                type = "SPRINGER"
            } else if (c === 't') {
                type = "TURM"
            } else if (c === 'd') {
                type = "DAME"
            } else if (c === 'k') {
                type = "KING"
            }
            if (type != null) {
                fig = {color: "BLACK", type: type}
            }
            const pos: Position = {x: j + 1, y: i2 + 1}
            const field: BoardField | undefined = board.find(f => posEq(f.position, pos))
            if (field !== undefined && fig !== null) {
                field.figure = fig
            }
        }
        i++
        i2++
    }

    return board
}