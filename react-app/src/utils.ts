import type {PositionDTO} from "./dto.ts";


function getPositionDTO(id: string): PositionDTO {
    const parts: string[] = id.split(',');
    return {
        row: Number(parts[0]),
        col: Number(parts[1])
    }
}

function isSamePosition(x: PositionDTO, y: PositionDTO) {
    return x.row == y.row && x.col == y.col;
}


// White pieces are uppercase (P N B R Q K), black pieces are lowercase (p n b r q k)
function isOwnPiece(value: string, role: string): boolean {
    if (value === ' ') return false;
    const isWhitePiece = value === value.toUpperCase();
    return (role === 'WHITE' && isWhitePiece) || (role === 'BLACK' && !isWhitePiece);
}

export {
    getPositionDTO,
    isSamePosition,
    isOwnPiece,
}