import {Figure} from "../dto/dtos";

export function getFigureImgPath(figure: Figure | null): string {
    const path = 'img/figure/'
    if (figure === null) {
        return path + 'nill.png'
    }
    let result = figure.color === "WHITE" ? 'w' : 'b'
    switch (figure.type) {
        case "BAUER":
            result += 'b'
            break
        case "LAUFER":
            result += 'l'
            break
        case "SPRINGER":
            result += 's'
            break
        case "TURM":
            result += 't'
            break
        case "DAME":
            result += 'd'
            break
        case "KING":
            result += 'k'
            break
        default:
            result += 'x'
            break
    }
    result += '.png'
    return path + result
}