package com.chess.test.Model.Checker;

import com.chess.game.Model.Checker.KingSafetyChecker;
import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ChessGame;
import com.chess.game.Model.Game.SandboxGame;
import com.chess.test.Model.AbstractTest;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestKingSafetyChecker extends AbstractTest {

  private SandboxGame game;
  private KingSafetyChecker checker;

  public TestKingSafetyChecker() {
    game = new ChessGame();
    checker = new KingSafetyChecker(game);
  }

  @Test
  public void testStartKingInCheck() {
    Assertions.assertFalse(checker.kingInCheck(Color.WHITE));
    Assertions.assertFalse(checker.kingInCheck(Color.BLACK));
  }




}
