import {CreateGameRequest, GameDto, JoinGameRequest, MoveRequest} from '../dto/dtos'

const api = '/api/game'
export default class RestClient {

    listOpenGames(): Promise<Array<GameDto>>{
        return fetch(`${api}/`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            },
            cache: 'no-cache'
        }).then(resp => {
            if (resp.ok) {
                return resp.json().then(data => {
                    return data as Array<GameDto>
                })
            }
            throw 'Could not load games'
        })
    }

    getGame(gameid: string): Promise<GameDto> {
        return fetch(`${api}/${gameid}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            },
            cache: 'no-cache'
        }).then(resp => {
            if (resp.ok) {
                return resp.json().then(data => {
                    return data
                })
            }
            throw 'Could not load game'
        })
    }

    create(playerid: string, req: CreateGameRequest): Promise<string> {
        return fetch(`${api}/`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'playerid': playerid
            },
            cache: 'no-cache',
            body: JSON.stringify(req)
        }).then(resp => {
            if (resp.ok) {
                return resp.text()
            }
            throw 'Could not create game'
        })
    }

    join(gameid: string, playerid: string, req: JoinGameRequest): Promise<any> {
        return fetch(`${api}/${gameid}/join`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'playerid': playerid
            },
            cache: 'no-cache',
            body: JSON.stringify(req)
        }).then(resp => {
            if (resp.ok) {
                return ""
            }
            throw 'Could not join table'
        })
    }

    move(gameid: string, playerid: string, req: MoveRequest): Promise<any> {
        return fetch(`${api}/${gameid}/move`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'playerid': playerid
            },
            cache: 'no-cache',
            body: JSON.stringify(req)
        }).then(resp => {
            if (resp.ok) {
                return ""
            }
            throw 'Could not make move'
        })
    }
}