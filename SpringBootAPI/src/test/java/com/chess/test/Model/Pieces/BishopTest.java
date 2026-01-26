package com.chess.test.Model.Pieces;

import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.Bishop;
import com.chess.game.Model.Pieces.ChessPieceFactory;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.test.Model.AbstractTest;

public class BishopTest extends AbstractTest {

    private final Piece blackBishop;
    private final Piece whiteBishop;

    public BishopTest() {
        blackBishop = ChessPieceFactory.buildPiece(Color.BLACK, PieceType.BISHOP);
        whiteBishop = ChessPieceFactory.buildPiece(Color.WHITE, PieceType.BISHOP);
    }


    public void testValidBishopMoves() {

    }
}
