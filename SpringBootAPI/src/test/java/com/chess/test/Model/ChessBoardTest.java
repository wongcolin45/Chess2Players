package com.chess.test.Model;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ChessBoardTest {
  private Board board;

  private void movePiece(Board board, String a, String b) {
    board.selectedPiece(new Position(a));
    board.movePiece(new Position(b));
  }

  private void printTextGrid(String[][] grid) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }


  @Before
  public void init() {
    board = new ChessBoard();
    board.setPieces();
  }

  @Test
  public void testGetTextGrid() {
    printTextGrid(board.getTextGrid(Color.WHITE));
  }

  @Test
  public void testKingInCheck() {
    movePiece(board, "e2", "e4");
    movePiece(board, "e7", "e5");
    movePiece(board, "d2", "d4");
    movePiece(board, "f7", "f5");
    movePiece(board, "d1", "h5");
    ChessView view = new ChessTerminalView(board);
    view.render();
    Assert.assertTrue(board.kingInCheck(Color.BLACK));
    Assert.assertFalse(board.kingInCheck(Color.WHITE));
  }

  @Test
  public void testGameOverWhiteCheckMate() {
    board.setPieces();
    movePiece(board, "e2", "e4");
    movePiece(board, "e7", "e5");
    movePiece(board, "f1", "c4");
    movePiece(board, "a7", "a6");
    movePiece(board, "d1", "f3");
    movePiece(board, "a6", "a5");
    movePiece(board, "f3", "f7");
    ChessView view = new ChessTerminalView(board);
    view.render();
    Assert.assertTrue(board.isGameOver());
  }

  @Test
  public void testKingMoves() {
    board.setPieces();
    movePiece(board, "e2", "e4");
    movePiece(board, "e7", "e5");
    movePiece(board, "f1", "c4");
    movePiece(board, "a7", "a6");
    movePiece(board, "d1", "f3");
    movePiece(board, "a6", "a5");
    movePiece(board, "f3", "g4");
    movePiece(board, "d7", "d6");
    movePiece(board, "c4", "f7");
    board.selectedPiece(new Position("e8"));

    List<Position> moves = board.getPossibleMoves();
    System.out.println(moves);
    Assert.assertEquals(2, moves.size());
    Assert.assertTrue(moves.contains(new Position("e7")));
    Assert.assertTrue(moves.contains(new Position("f7")));
    Assert.assertFalse(board.isGameOver());
  }

  @Test
  public void testBlackPawnPromotionAvailable() {
    ChessView view = new ChessTerminalView(board);
    board.move("e2", "e4");
    board.move("d7", "d5");
    board.move("f2", "f3");
    board.move("d5", "e4");
    board.move("h2", "h3");
    board.move("e4", "f3");
    board.move("h3", "h4");
    board.move("f3", "g2");
    board.move("h4", "h5");
    board.move("g2", "h1");
    Assert.assertTrue(board.promotionAvailable());
    view.render();

  }

  @Test
  public void testBlackPawnPromotionToRook() {
    ChessView view = new ChessTerminalView(board);
    board.move("e2", "e4");
    board.move("d7", "d5");
    board.move("f2", "f3");
    board.move("d5", "e4");
    board.move("h2", "h3");
    board.move("e4", "f3");
    board.move("h3", "h4");
    board.move("f3", "g2");
    board.move("h4", "h5");
    board.move("g2", "h1");
    Assert.assertTrue(board.promotionAvailable());
    board.promotePawn(PieceType.ROOK);
    Piece piece = board.getPiece(new Position("h1"));
    Assert.assertEquals(PieceType.ROOK, piece.getType());
  }
}
