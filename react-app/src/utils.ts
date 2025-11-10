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


export {
    getPositionDTO,
    isSamePosition
}