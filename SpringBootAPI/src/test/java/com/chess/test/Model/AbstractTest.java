package com.chess.test.Model.Pieces;

import com.chess.game.Model.Game.ChessGame;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

import org.junit.Assert;

import java.util.List;

public class AbstractPieceTest {

  protected ChessGame chessGame;
  protected ChessView view;

  protected Position a1, a2, a3, a4, a5, a6, a7, a8;
  protected Position b1, b2, b3, b4, b5, b6, b7, b8;
  protected Position c1, c2, c3, c4, c5, c6, c7, c8;
  protected Position d1, d2, d3, d4, d5, d6, d7, d8;
  protected Position e1, e2, e3, e4, e5, e6, e7, e8;
  protected Position f1, f2, f3, f4, f5, f6, f7, f8;
  protected Position g1, g2, g3, g4, g5, g6, g7, g8;
  protected Position h1, h2, h3, h4, h5, h6, h7, h8;

  protected AbstractPieceTest() {
    chessGame = new ChessGame();
    view = new ChessTerminalView(chessGame);

    a1 = new Position("a1"); a2 = new Position("a2"); a3 = new Position("a3"); a4 = new Position("a4");
    a5 = new Position("a5"); a6 = new Position("a6"); a7 = new Position("a7"); a8 = new Position("a8");

    b1 = new Position("b1"); b2 = new Position("b2"); b3 = new Position("b3"); b4 = new Position("b4");
    b5 = new Position("b5"); b6 = new Position("b6"); b7 = new Position("b7"); b8 = new Position("b8");

    c1 = new Position("c1"); c2 = new Position("c2"); c3 = new Position("c3"); c4 = new Position("c4");
    c5 = new Position("c5"); c6 = new Position("c6"); c7 = new Position("c7"); c8 = new Position("c8");

    d1 = new Position("d1"); d2 = new Position("d2"); d3 = new Position("d3"); d4 = new Position("d4");
    d5 = new Position("d5"); d6 = new Position("d6"); d7 = new Position("d7"); d8 = new Position("d8");

    e1 = new Position("e1"); e2 = new Position("e2"); e3 = new Position("e3"); e4 = new Position("e4");
    e5 = new Position("e5"); e6 = new Position("e6"); e7 = new Position("e7"); e8 = new Position("e8");

    f1 = new Position("f1"); f2 = new Position("f2"); f3 = new Position("f3"); f4 = new Position("f4");
    f5 = new Position("f5"); f6 = new Position("f6"); f7 = new Position("f7"); f8 = new Position("f8");

    g1 = new Position("g1"); g2 = new Position("g2"); g3 = new Position("g3"); g4 = new Position("g4");
    g5 = new Position("g5"); g6 = new Position("g6"); g7 = new Position("g7"); g8 = new Position("g8");

    h1 = new Position("h1"); h2 = new Position("h2"); h3 = new Position("h3"); h4 = new Position("h4");
    h5 = new Position("h5"); h6 = new Position("h6"); h7 = new Position("h7"); h8 = new Position("h8");
  }


  protected void checkMoves(List<Position>actualMoves, List<Position> expectedMoves) {
    Assert.assertEquals(actualMoves.size(), expectedMoves.size());
    for (Position pos : actualMoves) {
      Assert.assertTrue(expectedMoves.contains(pos));
    }
  }

}
