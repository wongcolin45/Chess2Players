package com.chess.api.dto;

import com.chess.game.Model.Pieces.PieceType;

public class PieceSelectionDTO {
    private final String piece;

    public PieceSelectionDTO(String piece) {
        this.piece = piece;
    }

    public String getPiece() {
        return piece;
    }

}
