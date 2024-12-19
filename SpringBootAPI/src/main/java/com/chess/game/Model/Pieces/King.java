package com.chess.game.Model.Pieces;

import com.chess.game.Model.Board.Board;

import java.util.ArrayList;
import java.util.List;

import com.chess.game.Model.Color;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

public class King extends AbstractPiece {

  private final int[] rowIters = {-1, -1, -1, 0, 0 , 1, 1, 1};
  private final int[] colIters = {-1, 0, 1, -1, 1 , -1, 0, 1};

  public King(Color color, String symbol) {
    super(color, symbol);
  }


  private void addCastleMoves(Board board, List<Position> moves, Position pos) {
    if (board.squareNotMoved(pos)) {
      int row = (color == Color.WHITE) ? 7 : 0;
      Position rook1 = new Position(row, 0);
      String r = (color == Color.BLACK) ? "8" : "1";

      if (board.squareNotMoved(rook1) && !board.isSquareEmpty(rook1)) {
        Position a = new Position("f"+r);
        Position b = new Position("g"+r);
        if (board.isSquareEmpty(a) && board.isSquareEmpty(b)) {
          moves.add(new Position("g"+r));
        }
      }
      Position rook2 = new Position(row, 7);
      if (board.squareNotMoved(rook2) && !board.isSquareEmpty(rook2)) {
        Position a = new Position("d"+r);
        Position b = new Position("c"+r);
        Position c = new Position("b"+r);
        if (board.isSquareEmpty(a) && board.isSquareEmpty(b) && board.isSquareEmpty(c)) {
          moves.add(new Position("c"+r));
        }


      }

    }
  }


  @Override
  public List<Position> getMoves(Board board, Position pos) {
    List<Position> moves = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
      checkValidMove(board, moves, pos,pos.getRow() + rowIters[i], pos.getCol() + colIters[i]);
    }

    // check if king has moved
    if (pos.getRow() == 7 || pos.getRow() == 0) {
      addCastleMoves(board, moves, pos);
    }


    return moves;
  }

  @Override
  public PieceType getType() {
    return PieceType.KING;
  }


}
