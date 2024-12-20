package com.chess.api;




import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.GameResult;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {
  private ChessBoard board;

  public GameController() {
    board = new ChessBoard();
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/")
  public String testAPI() {
    return "server running";
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/start")
  public ResponseEntity<String[][]> startGame() {
//    if (board != null) {
//      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//    }
    board = new ChessBoard();
    board.setPieces();
    Color turn = board.getTurn();
    return ResponseEntity.ok(board.getTextGrid(turn));
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PutMapping("/select/{notation}")
  public ResponseEntity<List<String>> selectedPiece(@PathVariable String notation) {
    try {
      Position pos = new Position(notation);
      board.selectedPiece(pos);
      List<Position> moves = board.getPossibleMoves();
      List<String> notations = new ArrayList<>();
      for (Position move : moves) {
        notations.add(move.toString());
      }
      return ResponseEntity.ok(notations);
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }


  @CrossOrigin(origins = "http://localhost:5173")
  @PutMapping("/move/{notation}")
  public ResponseEntity<String[][]> movePiece(@PathVariable String notation) {
    try {
      Position pos = new Position(notation);
      board.movePiece(pos);
      return ResponseEntity.ok(board.getTextGrid());
    } catch (Exception ex) {
      String error = ex.getMessage();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String[][]{{error}});
    }

  }


  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/board/{player}")
  public ResponseEntity<String[][]> getBoard(@PathVariable String player) {
    if (board == null) {
      return ResponseEntity.status(400).body(null);
    }
    Color color = (player.equalsIgnoreCase("white")) ? Color.WHITE : Color.BLACK;
    return ResponseEntity.ok(board.getTextGrid(color));
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/king/{color}")
  public ResponseEntity<Boolean> getCheck(@PathVariable String color) {
    Color player = (color.equalsIgnoreCase("white")) ? Color.WHITE : Color.BLACK;
    return ResponseEntity.ok(board.kingInCheck(player));
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/turn")
  public ResponseEntity<String> getTurn() {
    return ResponseEntity.ok(board.getTurn().toString());
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/gameOver")
  public ResponseEntity<Boolean> isGameOver() {
    if (board == null) {
      return ResponseEntity.status(400).body(false);
    }
    return ResponseEntity.ok(board.isGameOver());
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/result")
  public ResponseEntity<String> getGameResult() {
    if (!board.isGameOver()) {
      return ResponseEntity.ok("In Progress");
    }

    GameResult result = board.getGameResult();
    if (result == null) {
      return ResponseEntity.ok("In Progress");
    }
    return switch (result) {
      case GameResult.WHITE_CHECKMATE -> ResponseEntity.ok("White Wins!");
      case GameResult.BLACK_CHECKMATE -> ResponseEntity.ok("Black Wins!");
      case GameResult.STALEMATE_FORCED -> ResponseEntity.ok("Stalemate");
      case GameResult.STALEMATE_INSUFFICIENT_MATERIAL ->
              ResponseEntity.ok("Stalemate by insufficient material");
      case GameResult.STALEMATE_THREEFOLD_REPETITION ->
              ResponseEntity.ok("Stalemate by repetition");
      case GameResult.STALEMATE_FIFTY_MOVE_RULE -> ResponseEntity.ok("Stalemate by 5 move rule");
      default -> ResponseEntity.ok("In Progress");
    };
  }

  @CrossOrigin(origins = "http://localhost:5173")
  @PutMapping("/promote/{piece}")
  public ResponseEntity<Boolean> promotePawn(@PathVariable String piece) {
    if (!board.isPawnPromotion()) {
      return ResponseEntity.status(400).body(false);
    }
    switch (piece.toLowerCase()) {
      case "knight":
        board.promotePawn(PieceType.KNIGHT);
        break;
      case "bishop":
        board.promotePawn(PieceType.BISHOP);
        break;
      case "rook":
        board.promotePawn(PieceType.ROOK);
      case "queen":
        board.promotePawn(PieceType.QUEEN);
      default:
        return ResponseEntity.status(400).body(false);
    }
    return ResponseEntity.ok(true);
  }




}
