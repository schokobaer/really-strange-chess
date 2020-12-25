import {BoardField, Color, Figure, FigureMoveDto, Position} from "../dto/dtos";

export function posEq(a: Position, b: Position): boolean {
    return a.x === b.x && a.y === b.y
}

export function figEq(a: Figure | null, b: Figure | null): boolean {
    if (a === null || b == null) {
        return false
    }
    return a.type === b.type && a.color === b.color
}

export function posMove(pos: Position, x: number, y: number): Position {
    return {
        x: pos.x + x,
        y: pos.y + y
    }
}

export function colFlip(color: Color): Color {
    switch (color) {
        case "WHITE": return "BLACK"
        case "BLACK": return "WHITE"
        default: return color
    }
}

export class BaseChessLogic {

    getField(board: Array<BoardField>, pos: Position): BoardField {
        return board.find(f => posEq(f.position, pos)) || {
            position: pos,
            figure: null,
            color: "EMPTY",
            mine: null
        }
    }

    boardDimensions(board: Array<BoardField>): Position {
        let width = 0
        let height = 0
        board.forEach(f => {
            width = Math.max(width, f.position.x)
            height = Math.max(height, f.position.y)
        })
        return {
            x: width,
            y: height
        }
    }

    private lauferAccessFields(board: Array<BoardField>, field: BoardField): Array<BoardField> {
        const result: Array<BoardField> = []
        const dim = this.boardDimensions(board)
        const fig = field.figure!

        let x = field.position.x - 1
        let y = field.position.y - 1
        while (x > 0 && y > 0) {
            const f = this.getField(board, {x: x, y: y})
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                result.push(f)
                break;
            } else {
                break;
            }
            x--;
            y--;
        }

        x = field.position.x + 1
        y = field.position.y - 1
        while (x <= dim.x && y > 0) {
            const f = this.getField(board, {x: x, y: y})
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                result.push(f)
                break;
            } else {
                break;
            }
            x++;
            y--;
        }

        x = field.position.x - 1
        y = field.position.y + 1
        while (x > 0 && y <= dim.y) {
            const f = this.getField(board, {x: x, y: y})
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                result.push(f)
                break;
            } else {
                break;
            }
            x--;
            y++;
        }

        x = field.position.x + 1
        y = field.position.y + 1
        while (x <= dim.x && y <= dim.y) {
            const f = this.getField(board, {x: x, y: y})
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                result.push(f)
                break;
            } else {
                break;
            }
            x++;
            y++;
        }

        return result
    }

    private turmAccessFields(board: Array<BoardField>, field: BoardField): Array<BoardField> {
        const result: Array<BoardField> = []
        const dim = this.boardDimensions(board)
        const fig = field.figure!

        let x = field.position.x - 1
        while (x > 0) {
            const f = this.getField(board, {x: x, y: field.position.y})
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                result.push(f)
                break;
            } else {
                break;
            }
            x--;
        }

        x = field.position.x + 1
        while (x <= dim.x) {
            const f = this.getField(board, {x: x, y: field.position.y})
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                result.push(f)
                break;
            } else {
                break;
            }
            x++;
        }

        let y = field.position.y - 1
        while (y > 0) {
            const f = this.getField(board, {x: field.position.x, y: y})
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                result.push(f)
                break;
            } else {
                break;
            }
            y--;
        }

        y = field.position.y + 1
        while (y <= dim.y) {
            const f = this.getField(board, {x: field.position.x, y: y})
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                result.push(f)
                break;
            } else {
                break;
            }
            y++;
        }

        return result
    }

    private hitableFields(board: Array<BoardField>, field: BoardField): Array<BoardField> {
        const result: Array<BoardField> = []
        const dim = this.boardDimensions(board)

        if (field.figure === null) {
            return result
        }

        const fig = field.figure!

        switch (fig.type) {
            case "BAUER": {
                const yt = fig.color === "WHITE" ? 1 : -1
                let f = this.getField(board, posMove(field.position, -1, yt))
                if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 1, yt))
                if (f.color !== "EMPTY" && f.figure !== null && f.figure.color !== fig.color) {
                    result.push(f)
                }
                break
            }
            case "LAUFER": {
                this.lauferAccessFields(board, field).forEach(f => result.push(f))
                break
            }
            case "SPRINGER": {
                let f = this.getField(board, posMove(field.position, -1, -2))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 1, -2))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 2, -1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 2, 1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 1, 2))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, -1, 2))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, -2, 1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, -2, -1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                break
            }
            case "TURM": {
                this.turmAccessFields(board, field).forEach(f => result.push(f))
                break
            }
            case "DAME": {
                this.lauferAccessFields(board, field).forEach(f => result.push(f))
                this.turmAccessFields(board, field).forEach(f => result.push(f))
                break
            }
            case "KING": {
                let f = this.getField(board, posMove(field.position, -1, -1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 0, -1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 1, -1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, -1, 0))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 1, 0))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, -1, 1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 0, 1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                f = this.getField(board, posMove(field.position, 1, 1))
                if (f.color !== "EMPTY" && (f.figure === null || f.figure.color !== fig.color)) {
                    result.push(f)
                }
                break
            }
        }

        return result
    }

    private accessFields(board: Array<BoardField>, field: BoardField): Array<BoardField> {
        const result: Array<BoardField> = this.hitableFields(board, field)
        const dim = this.boardDimensions(board)

        if (field.figure === null || field.figure.type !== "BAUER") {
            return result
        }

        const fig = field.figure!

        if (fig.color === "WHITE") {
            let f = this.getField(board, posMove(field.position, 0, 1))
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            }
            if (field.position.y <= 2) {
                f = this.getField(board, posMove(field.position, 0, 2))
                if (f.color !== "EMPTY" && f.figure === null) {
                    result.push(f)
                }
            }
        } else if (fig.color === "BLACK") {
            let f = this.getField(board, posMove(field.position, 0, -1))
            if (f.color !== "EMPTY" && f.figure === null) {
                result.push(f)
            }
            if (field.position.y > dim.y - 2) {
                f = this.getField(board, posMove(field.position, 0, -2))
                if (f.color !== "EMPTY" && f.figure === null) {
                    result.push(f)
                }
            }
        }

        return result
    }

    /**
     * Returns the king fields of the given color where the king is check
     * @param board
     * @param color
     */
    public getKingsInCheck(board: Array<BoardField>, color: Color): Array<BoardField> {
        return board.filter(f => f.figure !== null && f.figure.color === color && f.figure.type === "KING")
            .filter(kingfield =>
                board.filter(f => f.figure !== null && f.figure.color === colFlip(color))
                    .map(f => this.hitableFields(board, f))
                    .flat()
                    .filter(f => posEq(f.position, kingfield.position)).length > 0
            )
    }

    public isCheck(board: Array<BoardField>, color: Color): boolean {
        return this.getKingsInCheck(board, color).length > 0
    }

    private canMakeMoveWithoutBeeingInCheck(board: Array<BoardField>, color: Color): boolean {
        return board.filter(f => f.figure !== null && f.figure.color === color)
            .filter(f => this.accessFields(board, f).length > 0)
            .length > 0
    }

    public isPatt(board: Array<BoardField>, color: Color): boolean {
        return this.getKingsInCheck(board, color).length === 0 && !this.canMakeMoveWithoutBeeingInCheck(board, color)
    }

    public isCheckmate(board: Array<BoardField>, color: Color): boolean {
        return this.getKingsInCheck(board, color).length > 0 && !this.canMakeMoveWithoutBeeingInCheck(board, color)
    }

    public move(board: Array<BoardField>, from: Position, to: Position): Array<BoardField> {
        const dim = this.boardDimensions(board)
        const fig = this.getField(board, from).figure!
        // find turm position and postTurmPosition if it is a castling move
        let turmField: BoardField | null = null
        let postTurmField: BoardField | null = null
        if (this.isCastlingMove(board, from, to)) {
            const direction = (Math.sign(from.x - to.x) * -1) as -1 | 1
            const king = this.getField(board, from)
            turmField = this.castlingTurm(board, king, direction)
            postTurmField = this.getField(board, {x: king.position.x + direction, y: king.position.y})
        }
        return board.map(f => {
            // Handle the turm movement if it is a castlingmove
            if (turmField !== null && posEq(turmField.position, f.position)) {
                return {position: f.position, color: f.color, figure: null, mine: f.mine}
            } else if (postTurmField !== null && posEq(postTurmField.position, f.position)) {
                return {position: f.position, color: f.color, figure: turmField!.figure, mine: f.mine}
            }

            if (posEq(f.position, from)) {
                return {position: from, color: f.color, figure: null, mine: f.mine}
            } else if (posEq(f.position, to)) {
                let figNew: Figure | null = null
                if (fig !== null) {
                    figNew = {type: fig.type, color: fig.color}
                }
                if (figNew !== null && figNew.type === "BAUER"
                    && (figNew.color === "WHITE" && to.y === dim.y
                        || figNew.color === "BLACK" && to.y === 1)) {
                    figNew.type = "DAME"
                }
                return {position: f.position, color: f.color, figure: figNew, mine: f.mine}
            }
            return f
        })
    }

    public isCastlingMove(board: Array<BoardField>, from: Position, to: Position): boolean {
        const fig = this.getField(board, from).figure
        if (fig === null || fig.type !== "KING") {
            return false
        }

        return Math.abs(from.x - to.x) > 1
    }

    private castlingTurm(board: Array<BoardField>, king: BoardField, direction: 1 | -1): BoardField | null {
        if (king.figure === null || king.figure.type !== "KING") {
            return null
        }

        const dim = this.boardDimensions(board)
        let x = king.position.x + direction
        const y = king.position.y
        while (x > 0 && x <= dim.x) {
            const f = this.getField(board, {x: x, y: y})
            if (f.color != "EMPTY" && f.figure == null) {
                x += direction
            } else if (f.color !== "EMPTY" && f.figure !== null && f.figure.color === king.figure.color && f.figure.type === "TURM") {
                return f
            } else {
                return null
            }
        }

        return null
    }

    private figureMoved(field: BoardField, history: Array<FigureMoveDto>): boolean {

        if (field.figure === null) {
            return false
        }

        return history.filter(f => posEq(f.to.position, field.position)).length > 0
    }

    public moveableFields(board: Array<BoardField>, field: BoardField, history: Array<FigureMoveDto>): Array<BoardField> {
        if (field.figure === null) {
            return []
        }
        const fig = field.figure

        const result = this.accessFields(board, field).filter(to => {
            const postBoard = this.move(board, field.position, to.position)
            return !this.isCheck(postBoard, fig.color)
        })

        // castling
        if (fig.type === "KING" && !this.figureMoved(field, history)) {
            const castlingTurmLeft = this.castlingTurm(board, field, -1)
            if (castlingTurmLeft !== null && !this.figureMoved(castlingTurmLeft, history)) {
                const f1 = this.getField(board, {x: field.position.x - 1, y: field.position.y})
                const postBoard1 = this.move(board, field.position, f1.position)
                const f2 = this.getField(board, {x: field.position.x - 2, y: field.position.y})
                const postBoard2 = this.move(board, field.position, f2.position)

                if (!this.isCheck(postBoard1, fig.color) && !this.isCheck(postBoard2, fig.color)) {
                    result.push(f2)
                }
            }
            const castlingTurmRight = this.castlingTurm(board, field, 1)
            if (castlingTurmRight != null && !this.figureMoved(castlingTurmRight, history)) {
                const f1 = this.getField(board, {x: field.position.x + 1, y: field.position.y})
                const postBoard1 = this.move(board, field.position, f1.position)
                const f2 = this.getField(board, {x: field.position.x + 2, y: field.position.y})
                const postBoard2 = this.move(board, field.position, f2.position)

                if (!this.isCheck(postBoard1, fig.color) && !this.isCheck(postBoard2, fig.color)) {
                    result.push(f2)
                }
            }
        }

        return result
    }

}