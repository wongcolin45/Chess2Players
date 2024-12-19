package com.chess.test.View;

import org.junit.Before;
import org.junit.Test;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;


public class ChessTerminalViewTest {
  private Board board;

  @Before
  public void init() {
    board = new ChessBoard();
    board.setPieces();
  }

  @Test
  public void testInitialBoardWhitePOV() {
    ChessView view = new ChessTerminalView(board);
    System.out.println(view);
  }

  @Test
  public void testInitialBoardBlackPOV() {
    ChessView view = new ChessTerminalView(board);
    view.flip();
    System.out.println(view);
  }
}