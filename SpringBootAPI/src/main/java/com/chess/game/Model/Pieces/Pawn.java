package com.chess.game.Model.Pieces;

import com.chess.game.Model.Board.Board;

import java.util.ArrayList;
import java.util.List;

import com.chess.game.Model.Color;
import com.chess.game.Model.Move;
import com.chess.game.Model.Position;

/**
 * This is the implementation of a pawn.
 */
public class Pawn extends AbstractPiece {

  private final int forward;

  private final String symbol;

  /**
   * Initializes the color and which direction is forward for the pawn.
   * @param color the color of the pawn
   */
  public Pawn(Color color, String symbol) {
    super(color, symbol);
    if (color == Color.WHITE) {
      this.forward = -1;
      this.symbol = "♟";
    } else {
      this.forward = 1;
      this.symbol = "♙";
    }

  }

  protected void checkOpenMove(Board board, List<Position> moves, int r, int c) {
    Position check = new Position(r, c);
    if (onBoard(r,c) && board.isEmpty(check)) {
      moves.add(check);
    }
  }

  private void checkEnPassant(Board board, List<Position> moves, int r, int c) {
    if (board.movesMade() == 0) {
      return;
    }
    Move move = board.getLastMove();
    Position start = move.getStart();
    Position end = move.getEnd();
    boolean doubleMove = Math.abs(start.getRow() - end.getRow()) == 2;
    Piece piece = move.getPiece();
    boolean isRightPiece = piece.getType() == PieceType.PAWN && piece.getColor() == color.opposing();
    boolean nextTo = end.getRow() == (r - forward) && end.getCol() == c;
    Position check = new Position(r, c);
    if (doubleMove && isRightPiece && nextTo && board.isSquareEmpty(new Position(r, c))) {
      moves.add(check);
    }
  }


  protected void checkCapture(Board board, List<Position> moves, int r, int c) {
    Position check = new Position(r, c);
    // check for previous moves for en passant


    if (onBoard(r, c) && !board.isEmpty(check) && board.getPiece(check).getColor() == color.opposing()) {
      moves.add(check);
    }
  }

  @Override
  public List<Position> getMoves(Board board, Position pos) {
    List<Position> moves = new ArrayList<>();

    int r = pos.getRow();
    int c = pos.getCol();


    // Check first move
    if ((color == Color.WHITE && r == 6) || (color == Color.BLACK && r == 1)) {
      checkOpenMove(board, moves, r + 2 * forward, c);
    }
    // Check moving forward
    checkOpenMove(board, moves, r + forward, c);
    // Check diagonal captures
    for (int i = -1; i <= 1; i+= 2) {
      checkCapture(board, moves, r + forward, c + i);
      checkEnPassant(board, moves, r + forward, c+ i);
    }
    return moves;
  }

  @Override
  public PieceType getType() {
    return PieceType.PAWN;
  }



}
