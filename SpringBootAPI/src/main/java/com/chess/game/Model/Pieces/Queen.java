package com.chess.game.Model.Pieces;

import com.chess.game.Model.Board.Board;

import java.util.List;
import java.util.stream.Stream;

import com.chess.game.Model.Color;
import com.chess.game.Model.Position;

public class Queen extends AbstractPiece {

  public Queen(Color color, String symbol) {
    super(color, symbol);
  }

  @Override
  public List<Position> getMoves(Board board, Position pos) {
    Piece rook = new Rook(getColor(), "r");
    Piece bishop = new Bishop(getColor(),"b");

    List<Position> straightMoves = rook.getMoves(board, pos);
    List<Position> diagonalMoves = bishop.getMoves(board, pos);

    return Stream.concat(straightMoves.stream(), diagonalMoves.stream()).toList();

  }

  @Override
  public PieceType getType() {
    return PieceType.QUEEN;
  }
}
