package com.chess.test.Model.Pieces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.ChessPieceFactory;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;


public class KnightTest {
  private Board board;

  @Before
  public void init() {
    board = new ChessBoard();
  }

  @Test
  public void testKnightInMiddle() {
    Piece knight = ChessPieceFactory.buildPiece(Color.WHITE, PieceType.KNIGHT);
    Position pos = new Position("d4");
    List<Position> moves = knight.getMoves(board, pos);

    Assert.assertEquals(8, moves.size());

    String[] expectedMoves = {"b5", "c6", "e6", "f5", "b3", "c2", "e2", "f3"};

    for (String m : expectedMoves) {
      Position check = new Position(m);
      Assert.assertTrue(moves.contains(check));
    }
  }

  @Test
  public void testKnightOnEdge() {
    Piece knight = ChessPieceFactory.buildPiece(Color.WHITE, PieceType.KNIGHT);
    Position pos = new Position("g8");
    List<Position> moves = knight.getMoves(board, pos);

    Assert.assertEquals(3, moves.size());

    String[] expectedMoves = {"e7", "f6", "h6"};

    for (String m : expectedMoves) {
      Position check = new Position(m);
      Assert.assertTrue(moves.contains(check));
    }
  }
}