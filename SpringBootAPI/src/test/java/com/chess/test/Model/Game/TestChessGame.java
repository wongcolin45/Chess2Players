package com.chess.test.Model.Game;

import com.chess.game.Model.Color;
import com.chess.game.Model.Position;
import com.chess.test.Model.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestChessGame extends AbstractTest {

  @Test
  public void testKingInCheck() {
    game.movePiece(e2, e4);
    game.movePiece(f7, f5);
    game.movePiece(d1, h5);
    Assertions.assertTrue(game.kingInCheck(Color.BLACK));
    List<Position> moves = game.getPossibleMoves(d8);
    view.render();
    Assertions.assertTrue(moves.isEmpty());
  }

  @Test
  public void testCheckmate() {
    game.movePiece(e2, e4);
    game.movePiece(e7, e5);
    game.movePiece(f1, c4);
    game.movePiece(b8, c6);
    game.movePiece(d1, f3);
    game.movePiece(c6, d4);
    game.movePiece(f3, f7);
    Assertions.assertTrue(game.isGameOver());
    view.render();
  }


}
