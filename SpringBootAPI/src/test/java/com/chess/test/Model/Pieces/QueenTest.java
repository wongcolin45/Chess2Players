package com.chess.test.Model.Pieces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.Queen;
import com.chess.game.Model.Position;

import static org.junit.Assert.*;

public class QueenTest {
  private Board board;

  @Before
  public void init() {
    board = new ChessBoard();
  }

  @Test
  public void testQueenStartMoves() {
    Piece rook = new Queen(Color.WHITE, "Q");
    Position pos = new Position("a1");
    List<Position> moves = rook.getMoves(board, pos);
    Assert.assertEquals(21, moves.size());

    for (int r = 2; r <= 8; r++) {
      Position check = new Position("a" + r);
      Assert.assertTrue(moves.contains(check));
    }

    // check straight moves
    for (String c : new String[]{"b", "c", "d", "e", "f", "g", "h"}) {
      Position check = new Position(c + 1);
      Assert.assertTrue(moves.contains(check));
    }

    //check diagonal moves
    int i = 2;
    System.out.println(moves);
    for (String c : new String[]{"b", "c", "d", "e", "f", "g", "h"}) {
      Position check = new Position(c + (i));
      Assert.assertTrue(moves.contains(check));
      i++;
    }

  }
}