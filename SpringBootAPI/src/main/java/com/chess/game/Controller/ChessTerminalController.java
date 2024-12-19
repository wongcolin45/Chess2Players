package com.chess.game.Controller;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

import java.util.Scanner;

/**
 * This is the implementation of a chess game controller to be used in the terminal.
 */
public class ChessTerminalController implements ChessController {

  private final Scanner scanner;

  private final Board board;

  private final ChessView view;

  private boolean quit;

  public ChessTerminalController() {
    scanner = new Scanner(System.in);
    board = new ChessBoard();
    board.setPieces();
    view = new ChessTerminalView(board);
    quit = false;
  }

  private boolean makeValidSelection() {
    String message = "Select a piece:";
    while (true) {
      view.render();
      System.out.println(message);

      String input = scanner.nextLine();

      if (input.equals("q")) {
        quit = true;
        System.out.println("quit is true");
        return false;
      }
      input = input.substring(0, 2);
      Position pos = new Position(input);

      if (pos.onBoard()) {
        try {
          board.selectedPiece(pos);
          return true;
        } catch (IllegalArgumentException ex) {
          message = ex.getMessage() + " Try again: ";
        }

      } else {
        message = "Bad Input! Try again: ";
      }

    }
  }

  private boolean makeValidMove() {
    String message = "Select a place to move: ";
    if (board.isPiecePinned()) {
      message = "(This piece is pinned btw) | " + message;
    }
    while (true) {
      view.render();
      System.out.println(message);
      String input = scanner.nextLine();

      if (input.equals("q")) {
        quit = true;
        return false;
      }

      input = input.substring(0, 2);

      Position pos = new Position(input);
      try {
        board.movePiece(pos);
        return true;
      } catch (IllegalArgumentException ex){
        message = ex.getMessage() + " Try again: ";
        input = scanner.nextLine();
      }
    }
  }

  private void changeTurns() {
    view.render();
    System.out.println("Press 'enter' to switch turns:");
    String pause = scanner.nextLine();
    view.flip();
  }


  @Override
  public void startGame() {
    boolean playing = true;
    while (!board.isGameOver()) {
      if (makeValidSelection()) {
        if (makeValidMove()) {
          changeTurns();
        }
      }


      if (quit) {
        break;
      }
    }

    if (quit) {
      view.render();
      System.out.println("Game quit!");
    }





  }
}
