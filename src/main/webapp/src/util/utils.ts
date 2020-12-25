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


export async function sha256(message: string) {
    // encode as UTF-8
    const msgBuffer = new TextEncoder().encode(message);

    // hash the message
    const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);

    // convert ArrayBuffer to Array
    const hashArray = Array.from(new Uint8Array(hashBuffer));

    // convert bytes to hex string
    const hashHex = hashArray.map(b => ('00' + b.toString(16)).slice(-2)).join('');
    return hashHex;
}
