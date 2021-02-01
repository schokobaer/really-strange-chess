export default interface RoombaDto {
    id: string
    state: string
    location?: string
    battery: BatteryDto
    wheels: WheelsDto
    bumper: BumperDto
}

interface BatteryDto {
    energy: number
}

interface WheelsDto {
    grounded: boolean
    speed: SpeedDto
}

interface SpeedDto {
    left: number
    right: number
}

interface BumperDto {
    bumped: boolean
}