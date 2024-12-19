package com.chess.test.Model.Pieces;
import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.Pawn;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class PawnTest {

  private Board board;

  private Piece whitePawn;
  private Piece blackPawn;

  private ChessView view;
  @Before
  public void init() {
    board = new ChessBoard();
    view = new ChessTerminalView(board);
    whitePawn = new Pawn(Color.WHITE, "P");
    blackPawn = new Pawn(Color.BLACK, "p");

  }

  @Test
  public void testWhitePawnFirstMoves() {
    Piece pawn = new Pawn(Color.WHITE, "P");
    Position pos = new Position("e2");
    List<Position> moves = pawn.getMoves(board, pos);

    Assert.assertEquals(2, moves.size());

    Position forward1 = new Position("e3");
    Position forward2 = new Position("e4");

    Assert.assertTrue(moves.contains(forward1));
    Assert.assertTrue(moves.contains(forward2));
  }

  @Test
  public void testBlackPawnFirstMoves() {
    Piece pawn = new Pawn(Color.BLACK, "p");
    Position pos = new Position("d7");

    List<Position> moves = pawn.getMoves(board, pos);

    Assert.assertEquals(2, moves.size());

    Position forward1 = new Position("d6");
    Position forward2 = new Position("d5");

    Assert.assertTrue(moves.contains(forward1));
    Assert.assertTrue(moves.contains(forward2));
  }

  @Test
  public void testWhitePawnSecondMove() {
    Piece pawn = new Pawn(Color.WHITE, "P");
    Position pos = new Position("f6");

    List<Position> moves = pawn.getMoves(board, pos);
    Assert.assertEquals(1, moves.size());
    Position forward1 = new Position("f7");
    Assert.assertTrue(moves.contains(forward1));

    ChessView v = new ChessTerminalView(board);
  }

  @Test
  public void testBlackPawnSecondMove() {
    Piece pawn = new Pawn(Color.BLACK, "p");
    Position pos = new Position("d5");

    List<Position> moves = pawn.getMoves(board, pos);
    Assert.assertEquals(1, moves.size());
    Position forward1 = new Position("d4");
    Assert.assertTrue(moves.contains(forward1));
  }

  @Test
  public void testWhitePawnCaptures() {
    Position start = new Position("e3");
    Position capture1 = new Position("d4");
    Position capture2 = new Position("f4");

    board.placePiece(whitePawn, start);
    board.placePiece(blackPawn, capture1);
    board.placePiece(blackPawn, capture2);

    List<Position> moves = whitePawn.getMoves(board, start);

    Assert.assertEquals(3, moves.size());

    Assert.assertTrue(moves.contains(capture1));
    Assert.assertTrue(moves.contains(capture2));
  }

  @Test
  public void testBlackPawnCaptures() {
    Position start = new Position("d6");
    Position capture1 = new Position("c5");
    Position capture2 = new Position("e5");

    board.placePiece(blackPawn, start);
    board.placePiece(whitePawn, capture1);
    board.placePiece(whitePawn, capture2);

    List<Position> moves = blackPawn.getMoves(board, start);
    Assert.assertEquals(3, moves.size());

    Assert.assertTrue(moves.contains(capture1));
    Assert.assertTrue(moves.contains(capture2));
  }



  @Test
  public void testEnPassant() {
    board.setPieces();
    board.move("e2", "e4");
    board.move("g8", "h6");
    board.move("e4", "e5");
    board.move("d7", "d5");
    List<Position> moves = whitePawn.getMoves(board, new Position("e5"));
    System.out.println(moves);
    Assert.assertEquals(2, moves.size());
    Assert.assertTrue(moves.contains(new Position("e6")));
    Assert.assertTrue(moves.contains(new Position("d6")));


  }



}