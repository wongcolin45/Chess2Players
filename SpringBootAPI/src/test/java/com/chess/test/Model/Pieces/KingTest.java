package com.chess.test.Model.Pieces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.King;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

public class KingTest {

  private Board board;
  private Board castleBoard;
  private ChessView view;

  @Before
  public void init() {
    castleBoard = new ChessBoard();
    castleBoard.setPieces();
    board = new ChessBoard();
    view = new ChessTerminalView(board);

    // set up castle Board
    //move pawns out
    movePiece(castleBoard, "e2", "e4");
    movePiece(castleBoard, "e7", "e5");
    movePiece(castleBoard, "d2", "d4");
    movePiece(castleBoard, "d7", "d5");
    //bishops out;
    movePiece(castleBoard, "f1", "d3");
    movePiece(castleBoard, "f8", "d6");
    movePiece(castleBoard, "c1", "d2");
    movePiece(castleBoard, "c8", "d7");
    //knights out
    movePiece(castleBoard, "g1", "f3");
    movePiece(castleBoard, "g8", "f6");
    movePiece(castleBoard, "b1", "c3");
    movePiece(castleBoard, "b8", "c6");
    // queen out
    movePiece(castleBoard, "d1", "e2");
    movePiece( castleBoard, "d8", "e7");
  }

  private void movePiece(Board board, String a, String b) {
    board.selectedPiece(new Position(a));
    board.movePiece(new Position(b));
  }

  @Test
  public void testKingInCenter() {
    Piece king = new King(Color.WHITE, "K");
    Position start = new Position("e4");
    List<Position> moves = king.getMoves(board, start);
    //System.out.println(view);
    Assert.assertEquals(8, moves.size());
    String[] expectedMoves = {"d5", "e5", "f5", "d4", "f4", "d3", "e3", "f3"};
    for (String m : expectedMoves) {
      Position pos = new Position(m);
      Assert.assertTrue(moves.contains(pos));
    }
  }

  @Test
  public void testKingInGame() {
    board.setPieces();
    movePiece(board, "e2", "e4");
    movePiece(board, "d7", "d5");
    board.selectedPiece(new Position("e1"));
    //view.render();
    System.out.println(board.getPossibleMoves());


  }

  private void print(Board board) {
    ChessView view = new ChessTerminalView(board);
    view.render();
  }

  @Test
  public void testCastleWhiteKing() {
    Piece king = new King(Color.WHITE, "K");
    Position start = new Position("e1");
    print(castleBoard);
    System.out.println(castleBoard.getTurn().toString());
    List<Position> moves = king.getMoves(castleBoard, start);
    Assert.assertTrue(moves.contains(new Position("f1")));
    Assert.assertTrue(moves.contains(new Position("g1")));
    ChessView view = new ChessTerminalView(castleBoard);
    movePiece(castleBoard,"e1", "g1");
    view.render();
  }

  @Test
  public void testCastleBlackKing() {
    movePiece(castleBoard, "e2", "e3");
    Piece king = new King(Color.BLACK, "K");
    Position start = new Position("e8");
    List<Position> moves = king.getMoves(castleBoard, start);
//    System.out.println(moves);
//    System.out.println("castleBoard "+castleBoard.getTurn());
//    ChessView view = new ChessTerminalView(castleBoard);
//    view.flip();
//    view.render();
    Assert.assertTrue(moves.contains(new Position("c8")));
    Assert.assertTrue(moves.contains(new Position("g8")));
  }

  @Test
  public void blackKingMoves() {
    board.setPieces();
    movePiece(board, "e2", "e4");
    movePiece(board, "e7", "e5");
    print(board);
    Piece king = new King(Color.BLACK, "k");
    List<Position> moves = king.getMoves(board, new Position("e8"));
    System.out.println(moves);
  }

  @Test
  public void testNoCastleWhite() {
    Piece king = new King(Color.BLACK, "K");
    Position start = new Position("e8");
    List<Position> moves = king.getMoves(castleBoard, start);
  }



}