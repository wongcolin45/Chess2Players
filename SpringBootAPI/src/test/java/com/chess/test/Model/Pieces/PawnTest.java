package com.chess.test.Model.Pieces;

import com.chess.game.Model.Position;
import com.chess.test.Model.AbstractTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PawnTest extends AbstractTest {

  @Test
  public void testWhiteKingPawn() {
    List<Position> moves = game.getPossibleMoves(e2);
    List<Position> expectedMoves = List.of(e4, e3);
    view.render();
    System.out.println(moves);
    //checkMoves(moves, expectedMoves);
    game.movePiece(e2, e4);
    //Assert.assertEquals(game.getPossibleMoves(e4).size(), 1);
    Assert.assertEquals(game.getPossibleMoves(e4).getFirst(), e5);
  }

  @Test
  public void testBlackQueenPawn() {
    game.movePiece(e2, e3);
    List<Position> moves = game.getPossibleMoves(d7);
    List<Position> expectedMoves = List.of(d5, d6);
    checkMoves(moves, expectedMoves);
    game.movePiece(d7, d6);
    Assert.assertEquals(game.getPossibleMoves(d6).size(), 1);
    Assert.assertEquals(game.getPossibleMoves(d6).getFirst(), d5);
  }

  @Test
  public void testWhitePawnCapture() {
    game.movePiece(e2, e4);
    game.movePiece(d7, d5);
    List<Position> moves = game.getPossibleMoves(e4);
    List<Position> expectedMoves = List.of(d5, e5);
    checkMoves(moves, expectedMoves);
  }

  @Test
  public void testBlackPawnCapture() {
    game.movePiece(e2, e4);
    game.movePiece(d7, d5);
    List<Position> moves = game.getPossibleMoves(d5);
    List<Position> expectedMoves = List.of(d4, e4);
    checkMoves(moves, expectedMoves);
  }

  @Test
  public void testPawnBlocked() {
    game.movePiece(e2, e4);
    game.movePiece(e7, e5);
    Assert.assertTrue(game.getPossibleMoves(e4).isEmpty());
    Assert.assertTrue(game.getPossibleMoves(e5).isEmpty());

  }

}
