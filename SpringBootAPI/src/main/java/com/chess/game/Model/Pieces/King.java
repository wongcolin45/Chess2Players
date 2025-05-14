package com.chess.game.Model.Pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ViewableGame;
import com.chess.game.Model.Log.ViewableGameLog;
import com.chess.game.Model.Position;

public class King extends AbstractPiece {

  private final int[] rowIters = {-1, -1, -1, 0, 0 , 1, 1, 1};
  private final int[] colIters = {-1, 0, 1, -1, 1 , -1, 0, 1};

  public King(Color color, String symbol) {
    super(color, symbol);
  }


  private void addCastleMoves(ViewableGame game, List<Position> moves, Position pos) {
    ViewableBoard board = game.getViewableBoard();
    ViewableGameLog log = game.getLog();
    if (!log.squareMoved(pos)) {
      int row = (color == Color.WHITE) ? 7 : 0;
      Position rook1 = new Position(row, 0);
      String r = (color == Color.BLACK) ? "8" : "1";

      if (!log.squareMoved(rook1) && !board.isEmpty(rook1)) {
        Position a = new Position("f"+r);
        Position b = new Position("g"+r);
        if (board.isEmpty(a) && board.isEmpty(b)) {
          moves.add(new Position("g"+r));
        }
      }
      Position rook2 = new Position(row, 7);
      if (!log.squareMoved(rook2) && !board.isEmpty(rook2)) {
        Position a = new Position("d"+r);
        Position b = new Position("c"+r);
        Position c = new Position("b"+r);
        if (board.isEmpty(a) && board.isEmpty(b) && board.isEmpty(c)) {
          moves.add(new Position("c"+r));
        }


      }

    }
  }


  @Override
  public List<Position> getMoves(ViewableGame game, Position pos) {
    ViewableBoard board = game.getViewableBoard();
    List<Position> moves = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
      checkValidMove(board, moves, pos,pos.getRow() + rowIters[i], pos.getCol() + colIters[i]);
    }

    // check if king has moved
    if (pos.getRow() == 7 || pos.getRow() == 0) {
      addCastleMoves(game, moves, pos);
    }


    return moves;
  }

  @Override
  public PieceType getType() {
    return PieceType.KING;
  }


}
