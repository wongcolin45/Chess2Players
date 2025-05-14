package com.chess.api.dto;

import com.chess.game.Model.Pieces.PieceType;

public class PieceSelectionDTO {
    private final PieceType piece;

    public PieceSelectionDTO(PieceType piece) {
        this.piece = piece;
    }

    public PieceType getPiece() {
        return piece;
    }

}
