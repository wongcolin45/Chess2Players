import type {PositionDTO} from "./dto.ts";


function getPositionDTO(id: string): PositionDTO {
    const parts: string[] = id.split(',');
    return {
        row: Number(parts[0]),
        col: Number(parts[1])
    }
}


export {
    getPositionDTO
}