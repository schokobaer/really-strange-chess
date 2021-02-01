import {CreateGameRequest, GameDto, JoinGameRequest, MoveRequest} from '../dto/dtos'
import RoombaDto from "../dto/RoombaDto";

const api = '/api/roomba'
export default class RoombaRestClient {

    listAvailableRoombas(): Promise<Array<string>> {
        return fetch(`${api}/`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            },
            cache: 'no-cache'
        }).then(resp => {
            if (resp.ok) {
                return resp.json().then(data => {
                    return data as Array<string>
                })
            }
            throw 'Could not load available roombas'
        })
    }

    getRoomba(roombaid: string): Promise<RoombaDto> {
        return fetch(`${api}/${roombaid}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            },
            cache: 'no-cache'
        }).then(resp => {
            if (resp.ok) {
                return resp.json().then(data => {
                    return data as RoombaDto
                })
            }
            throw 'Could not load roomba'
        })
    }

    drive(roombaid: string, speed: number): Promise<any> {
        return fetch(`${api}/${roombaid}/drive`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            cache: 'no-cache',
            body: speed.toString()
        }).then(resp => {
            if (resp.ok) {
                return ""
            }
            throw 'Could not execute drive action'
        })
    }

    turn(roombaid: string, angle: number): Promise<any> {
        return fetch(`${api}/${roombaid}/turn`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            cache: 'no-cache',
            body: angle.toString()
        }).then(resp => {
            if (resp.ok) {
                return ""
            }
            throw 'Could not execute turn action'
        })
    }

    stop(roombaid: string): Promise<any> {
        return fetch(`${api}/${roombaid}/stop`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            cache: 'no-cache'
        }).then(resp => {
            if (resp.ok) {
                return ""
            }
            throw 'Could not execute stop action'
        })
    }

    driveTo(roombaid: string, target: string): Promise<any> {
        return fetch(`${api}/${roombaid}/driveto`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            cache: 'no-cache',
            body: target
        }).then(resp => {
            if (resp.ok) {
                return ""
            }
            throw 'Could not execute driveTo action'
        })
    }
}