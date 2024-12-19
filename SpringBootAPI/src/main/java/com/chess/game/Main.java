package com.chess.game;

import com.chess.game.Controller.ChessController;
import com.chess.game.Controller.ChessTerminalController;

public class Main {
  public static void main(String[] args) {
    ChessController controller = new ChessTerminalController();
    controller.startGame();
  }
}
