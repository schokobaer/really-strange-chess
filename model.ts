interface Player {
    id: string
    name: string
}

interface GamePlayer extends Player {
    order: number
}

enum GameState {
    PENDING,
    PLAYING,
    FINISHED
}

interface Position {
    x: number
    y: number
}

interface Move {
    figure: Figure
    from: Position
    to: Position
}

enum Color {
    WHITE,
    BLACK
}

enum FigureType {
    BAUER,
    SPRINGER,
    LAUFER,
    TURM,
    KOENIG,
    DAME
}

enum BoardFieldColor {
    WHITE,
    BLACK,
    EMPTY
}

interface Figure {
    color: Color
    type: FigureType
}

interface BoardField {
    position: Position
    color: BoardFieldColor
    figure?: Figure
    mine?: number
}

interface Team {
    players: Array<Player>
    currentPlayerOrder: number
    time?: number
}

interface Game {
    white: Array<Team>
    black: Array<Team>
    state: GameState
    currentTeam?: Color
    history: Array<Move>
    board: Array<BoardField>
}