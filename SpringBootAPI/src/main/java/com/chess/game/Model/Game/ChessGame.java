package com.chess.game.Model.Game;

import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Board.MutableChessBoard;
import com.chess.game.Model.Board.Square;
import com.chess.game.Model.Board.ViewableChessBoard;
import com.chess.game.Model.Checker.ViewableKingSafetyChecker;
import com.chess.game.Model.Checker.KingSafetyChecker;
import com.chess.game.Model.Color;
import com.chess.game.Model.GameStatus;
import com.chess.game.Model.Log.ChessGameGameGameLog;
import com.chess.game.Model.Log.MutableChessGameGameLog;
import com.chess.game.Model.Log.ViewableChessGameLog;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.ChessPieceFactory;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

import java.util.ArrayList;
import java.util.List;

public class ChessGame implements SandboxChessGame {

  private final MutableChessBoard board;
  private final MutableChessGameGameLog log;

  private final ViewableKingSafetyChecker checker;

  private Position pawnToPromote;
  private Color turn;


  public ChessGame() {
    board = new ChessBoard();
    board.setPieces();
    log = new ChessGameGameGameLog();
    checker = new KingSafetyChecker(this);
    log.setPieceLocations(board);
    turn = Color.WHITE;
  }

  @Override
  public boolean kingInCheck(Color color) {
    return checker.kingInCheck(color);
  }

  @Override
  public List<Position> getPossibleMoves(Position pos) {
    if (board.isEmpty(pos)) {
      return new ArrayList<>();
    }
    Piece piece = board.getPiece(pos);
    List<Position> ends = piece.getMoves(this, pos);
    return checker.filterMoves(pos, ends);
  }

  @Override
  public boolean isGameOver() {
    // 50 - rule move
    if (log.getHalfMoves() >= 50) {
      return true;
    }
    List<Position> locations = log.getLocations(turn);
    for (Position pos : locations) {
      if (pos != null && !getPossibleMoves(pos).isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public GameStatus getGameStatus() {
    if (!isGameOver()) {
      return GameStatus.IN_PROGRESS;
    }
    if (!checker.kingInCheck(turn)) {
      return GameStatus.STALEMATE_FORCED;
    }
    if (turn == Color.WHITE) {
      return GameStatus.BLACK_CHECKMATE;
    }
    return GameStatus.WHITE_CHECKMATE;

  }

  @Override
  public void movePiece(Position startPos, Position pos) {

    Square selectedSquare = board.getSquare(startPos);
    if (!selectedSquare.isOccupied()) {
      throw new IllegalArgumentException("There is no piece here to move");
    }
    if (selectedSquare.getPiece().getColor() != turn) {
      throw new IllegalArgumentException("It is "+turn+"'s turn you cannot move opposing pieces");
    }
    if (!getPossibleMoves(startPos).contains(pos)) {
      throw new IllegalArgumentException(pos + " is not a valid move!");
    }

    Piece capture = (board.isEmpty(pos)) ? null : board.getPiece(pos);

    Square current = board.getSquare(pos);
    Piece piece = selectedSquare.getPiece();
    current.setPiece(piece);
    Position start = selectedSquare.getPosition();
    selectedSquare.clear();

    turn = turn.opposing();

    // move rook for castles;
    if (piece.getType() == PieceType.KING && Math.abs(start.getCol() - pos.getCol()) >= 2) {
      int row = (piece.getColor() == Color.WHITE) ? 7 : 0;
      int col = (pos.getCol() == 6) ? 7 : 0;
      // why am I double-checking rook here?
      if (board.isEmpty(new Position(row, col))) {
        return;
      }

      Piece rook = board.getPiece(new Position(row, col));
      Square square = board.getSquare(new Position(row, col));
      square.clear();
      int iter = (pos.getCol() == 6) ? -1 : 1;
      square = board.getSquare(new Position(pos.getRow(), pos.getCol()+iter));
      square.setPiece(rook);
    }
    // move captured pawn
    if (piece.getType() == PieceType.PAWN ) {
      if (pos.getCol() != start.getCol()
              &&
              capture == null) {
        Position piecePos = new Position(start.getRow(), pos.getCol());
        capture = board.getPiece(piecePos);
        board.removePiece(piecePos);
      } else if (pos.getRow() == 7 || pos.getRow() == 0) {
        pawnToPromote = pos;
        turn = turn.opposing();
      }
    }

    // add to log
    Move move;
    if (capture == null) {
      move = new Move(piece, start, pos);
    } else {
      move = new Move(piece, start, pos, capture);
    }
    log.addMove(move);
  }



  @Override
  public Color getTurn() {
    return turn;
  }

  @Override
  public ViewableChessGameLog getLog() {
    return log;
  }


  @Override
  public ViewableChessBoard getViewableBoard() {
    return board;
  }

  private ChessGame(MutableChessBoard board, MutableChessGameGameLog log) {
    this.board = board.getCopy();
    this.log = log.getCopy();
    this.checker = new KingSafetyChecker(this);
  }


  @Override
  public SandboxChessGame getCopy() {
    return new ChessGame(board, log);
  }

  @Override
  public void forceMove(Position start, Position end) {
    Square startSquare = board.getSquare(start);
    Square endSquare = board.getSquare(end);
    if (!startSquare.isOccupied()) {
      endSquare.clear();
      return;
    }
    Piece piece = startSquare.getPiece();
    endSquare.clear();
    endSquare.setPiece(piece);

  }

  @Override
  public void clearSquare(Position pos) {
    Square square = board.getSquare(pos);
    square.clear();
  }

  @Override
  public void placePiece(Piece piece, Position pos) {
    if (!pos.onBoard()) {
      throw new IllegalArgumentException("Position: "+pos+" is not valid.");
    }
    Square square = board.getSquare(pos);
    square.clear();
    square.setPiece(piece);
  }

  @Override
  public void clearBoard() {
    board.clear();
  }
}
