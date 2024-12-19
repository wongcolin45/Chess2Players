package com.chess.test.Model;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.GameResult;
import com.chess.game.Model.Pieces.King;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ChessBoardTest {
  private Board board;



  @Before
  public void init() {
    board = new ChessBoard();
    board.setPieces();
  }

  private void printTextGrid(String[][] grid) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
  }

  @Test
  public void testGetTextGrid() {
    printTextGrid(board.getTextGrid(Color.WHITE));
  }

  private void movePiece(Board board, String a, String b) {
    board.selectedPiece(new Position(a));
    board.movePiece(new Position(b));
  }

  @Test
  public void testKingInCheck() {
    movePiece(board, "e2","e4");
    movePiece(board, "e7","e5");
    movePiece(board, "d2","d4");
    movePiece(board, "f7","f5");
    movePiece(board, "d1","h5");
    ChessView view = new ChessTerminalView(board);
    view.render();
    Assert.assertTrue(board.kingInCheck(Color.BLACK));
    Assert.assertFalse(board.kingInCheck(Color.WHITE));
  }

  @Test
  public void testGameOverWhiteCheckMate() {
    board.setPieces();
    movePiece(board, "e2","e4");
    movePiece(board, "e7","e5");
    movePiece(board,"f1","c4");
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
    movePiece(board, "e2","e4");
    movePiece(board, "e7","e5");
    movePiece(board,"f1","c4");
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
}
