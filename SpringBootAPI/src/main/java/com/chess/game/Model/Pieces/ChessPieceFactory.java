package com.chess.game.Model.Pieces;
import com.chess.game.Model.Color;


public class ChessPieceFactory {

    private static final String[] blackSymbols = {"♙", "♘", "♗", "♖", "♕", "♔"};
    private static final String[] whiteSymbols = {"♟", "♞", "♝", "♜", "♛", "♚"};

    private static String getSymbol(Color color, int i) {
      return (color == Color.WHITE) ? whiteSymbols[i] : blackSymbols[i];
    }

  public static Piece buildPiece(Color color, PieceType type) {
    switch (type) {
      case PAWN:
        return new Pawn(color, getSymbol(color, 0));
      case KNIGHT:
        return new Knight(color, getSymbol(color, 1));
      case BISHOP:
        return new Bishop(color, getSymbol(color, 2));
      case ROOK:
        return new Rook(color, getSymbol(color, 3));
      case QUEEN:
        return new Queen(color, getSymbol(color, 4));
      case KING:
        return new King(color, getSymbol(color, 5));
      default:
        throw new IllegalArgumentException("Invalid piece type: " + type);
    }
  }



}
