package com.chess.test.Model.Checker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.Bishop;
import com.chess.game.Model.Pieces.King;
import com.chess.game.Model.Pieces.Knight;
import com.chess.game.Model.Pieces.Pawn;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.Queen;
import com.chess.game.Model.Pieces.Rook;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;


import static org.junit.Assert.*;

public class ChessBoardCheckerTest {
  private Board board;
  private Piece whiteKing;
  private Piece blackKing;

  private Piece whitePawn;
  private Piece blackPawn;
  private Piece whiteKnight;
  private Piece blackKnight;
  private Piece whiteBishop;
  private Piece blackBishop;
  private Piece whiteRook;
  private Piece blackRook;
  private Piece whiteQueen;
  private Piece blackQueen;

  private ChessView view;


  @Before
  public void init() {
    board = new ChessBoard();
    whiteKing = new King(Color.WHITE, "K");
    blackKing = new King(Color.BLACK, "k");

    whitePawn = new Pawn(Color.WHITE, "P");
    blackPawn = new Pawn(Color.BLACK, "p");

    whiteKnight = new Knight(Color.WHITE, "H");
    blackKnight = new Knight(Color.BLACK, "h");

    whiteBishop = new Bishop(Color.WHITE, "B");
    blackBishop = new Bishop(Color.BLACK, "b");

    whiteRook = new Rook(Color.WHITE, "R");
    blackRook = new Rook(Color.BLACK, "r");

    whiteQueen = new Queen(Color.WHITE, "Q");
    blackQueen = new Queen(Color.BLACK, "q");

    view = new ChessTerminalView(board);

  }

  @Test
  public void testKingInCheckBishop() {
    board.placePiece(whiteKing, new Position("e4"));
    board.placePiece(blackBishop, new Position("b7"));
    Assert.assertTrue(board.kingInCheck(Color.WHITE));
  }

  @Test
  public void testKingInCheckRook() {
    board.placePiece(whiteKing, new Position("e4"));
    board.placePiece(blackRook, new Position("e1"));
    Assert.assertTrue(board.kingInCheck(Color.WHITE));
  }

  @Test
  public void testKingInCheckQueenStraight() {
    board.placePiece(whiteKing, new Position("e4"));
    board.placePiece(blackQueen, new Position("e8"));
    Assert.assertTrue(board.kingInCheck(Color.WHITE));
  }

  @Test
  public void testKingInCheckQueenDiagonal() {
    board.placePiece(whiteKing, new Position("e4"));
    board.placePiece(blackQueen, new Position("c6"));
    Assert.assertTrue(board.kingInCheck(Color.WHITE));
  }

  @Test
  public void testPinnedPawnStraight() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whitePawn, new Position("e2"));
    board.placePiece(blackPawn, new Position("d3"));
    board.placePiece(blackPawn, new Position("f3"));
    board.placePiece(blackQueen, new Position("e8"));

    board.selectedPiece(new Position("e2"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(2, moves.size());
    Assert.assertTrue(moves.contains(new Position("e3")));
    Assert.assertTrue(moves.contains(new Position("e4")));
  }

  @Test
  public void testPinnedPawnDiagonal() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whitePawn, new Position("d2"));
    board.placePiece(blackPawn, new Position("e3"));
    board.placePiece(blackQueen, new Position("a5"));
    System.out.println(view);
    board.selectedPiece(new Position("d2"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(0, moves.size());
  }

  @Test
  public void testPinnedKnightStraight() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whiteKnight, new Position("e3"));
    board.placePiece(blackQueen, new Position("e8"));
    System.out.println(view);
    board.selectedPiece(new Position("e3"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(0, moves.size());
  }

  @Test
  public void testPinnedKnightDiagonal() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whiteKnight, new Position("d2"));
    board.placePiece(blackPawn, new Position("e3"));
    board.placePiece(blackQueen, new Position("a5"));
    System.out.println(view);
    board.selectedPiece(new Position("d2"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(0, moves.size());
  }

  @Test
  public void testPinnedBishopStraight() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whiteBishop, new Position("e4"));
    board.placePiece(blackQueen, new Position("e8"));
    System.out.println(view);
    board.selectedPiece(new Position("e4"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(0, moves.size());
  }

  @Test
  public void testPinnedBishopDiagonal() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whiteBishop, new Position("f2"));
    board.placePiece(blackQueen, new Position("h4"));
    System.out.println(view);
    board.selectedPiece(new Position("f2"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(2, moves.size());
    Assert.assertTrue(moves.contains(new Position("g3")));
    Assert.assertTrue(moves.contains(new Position("h4")));
  }

  @Test
  public void testPinnedRookStraight() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whiteRook, new Position("e4"));
    board.placePiece(blackQueen, new Position("e8"));
    System.out.println(view);
    board.selectedPiece(new Position("e4"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(6, moves.size());
    String[] expectedMoves = {"e5", "e6", "e7", "e8", "e3", "e2"};
    for (String move : expectedMoves) {
      Assert.assertTrue(moves.contains(new Position(move)));
    }
  }

  @Test
  public void testPinnedRookDiagonal() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whiteRook, new Position("f2"));
    board.placePiece(blackQueen, new Position("h4"));
    System.out.println(view);
    board.selectedPiece(new Position("f2"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(0, moves.size());
  }

  @Test
  public void testPinnedQueenStraight() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whiteQueen, new Position("e4"));
    board.placePiece(blackQueen, new Position("e8"));
    System.out.println(view);
    board.selectedPiece(new Position("e4"));
    List<Position> moves = board.getPossibleMoves();
    Assert.assertEquals(6, moves.size());
    String[] expectedMoves = {"e5", "e6", "e7", "e8", "e3", "e2"};
    for (String move : expectedMoves) {
      Assert.assertTrue(moves.contains(new Position(move)));
    }
  }

  @Test
  public void testPinnedQueenDiagonal() {
    board.placePiece(whiteKing, new Position("e1"));
    board.placePiece(whiteQueen, new Position("f2"));
    board.placePiece(blackQueen, new Position("h4"));
    System.out.println(view);
    board.selectedPiece(new Position("f2"));
    List<Position> moves = board.getPossibleMoves();
    System.out.println(moves);
    Assert.assertEquals(2, moves.size());
    Assert.assertTrue(moves.contains(new Position("g3")));
    Assert.assertTrue(moves.contains(new Position("h4")));
  }









}