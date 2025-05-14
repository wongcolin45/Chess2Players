package com.chess.test.Model.Game;

import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ChessGame;
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


}
