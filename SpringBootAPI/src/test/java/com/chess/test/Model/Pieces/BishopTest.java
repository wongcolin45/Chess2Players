package com.chess.test.Model.Pieces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.Bishop;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

public class BishopTest {

  private Board board;

  @Before
  public void init() {
    board = new ChessBoard();
  }

  @Test
  public void testBishopInMiddle() {
    Piece bishop = new Bishop(Color.WHITE, "B");
    Position pos = new Position("c4");
    List<Position> moves = bishop.getMoves(board, pos);

    Assert.assertEquals(11, moves.size());


    String[] expectedMoves = {"b5", "a6", "d5", "e6", "f7", "g8", "b3", "a2", "d3", "e2", "f1"};

    for (String m : expectedMoves) {
      Position check = new Position(m);

      Assert.assertTrue(moves.contains(check));
    }
  }


  @Test
  public void testBishopInCorner() {
    Piece bishop = new Bishop(Color.BLACK, "b");
    Position pos = new Position("a8");
    List<Position> moves = bishop.getMoves(board, pos);

    Assert.assertEquals(7, moves.size());


    String[] expectedMoves = {"b7", "c6", "d5", "e4", "f3", "g2", "h1"};

    for (String m : expectedMoves) {
      Position check = new Position(m);

      Assert.assertTrue(moves.contains(check));
    }
  }
}