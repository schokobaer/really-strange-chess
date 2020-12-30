
export interface GameDto {
    id: string
    white: TeamDto
    black: TeamDto
    currentTeam: Color
    state: GameState
    board: Array<BoardField>
    history: Array<FigureMoveDto>
}

export interface TeamDto {
    players: Array<TeamPlayerDto>
    curPlayer: number
    time: number | null
    hitFigures: Array<Figure>
}

export type Color = 'WHITE' | 'BLACK'

export type GameState = 'PENDING' | 'PLAYING' | 'FINISHED'

export type FigureType = 'BAUER' | 'LAUFER' | 'SPRINGER' | 'TURM' | 'DAME' | 'KING'

export interface BoardField {
    position: Position
    color: 'WHITE' | 'BLACK' | 'EMPTY'
    figure: Figure | null
    mine: number | null
}

export interface Position {
    x: number
    y: number
}

export interface Figure {
    color: Color
    type: FigureType
}

export interface FigureMoveDto {
    from: BoardField
    to: BoardField
    time: number
}

export interface TeamPlayerDto {
    name: string
    order: number
}


export interface CreateGameRequest {
    timeWhite: number | null // sec
    timeBlack: number | null // sec
    team: Color
    name: string
    style: BoardStyle
    mineConfig: MineConfig | null
    board: string | null
}

export interface MineConfig {
    interval: number
    offset: number
}

export interface JoinGameRequest {
    name: string
    color: Color
}

export interface MoveRequest {
    from: Position
    to: Position
}

export type BoardStyle = 'CLASSIC' | 'GRID5X5' | 'CUSTOM'