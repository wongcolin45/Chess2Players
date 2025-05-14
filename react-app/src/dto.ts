

interface PositionDTO {
    row: number;
    col: number;
}

interface CapturedPiecesDTO {
    whiteCaptures: string[]
    blackCaptures: string[]
}


interface RoleAssignmentDTO {
    role: string;
    roleId: string;
}

interface PieceSelectionDTO {
    piece: string;
}

export type {
    PositionDTO,
    RoleAssignmentDTO,
    PieceSelectionDTO,
    CapturedPiecesDTO,
}