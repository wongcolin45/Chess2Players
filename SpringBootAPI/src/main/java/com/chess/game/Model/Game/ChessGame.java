package com.chess.game.Model.Game;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.MutableBoard;
import com.chess.game.Model.Board.Square;
import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Checker.ViewableKingSafetyChecker;
import com.chess.game.Model.Checker.KingSafetyChecker;
import com.chess.game.Model.Color;
import com.chess.game.Model.GameStatus;
import com.chess.game.Model.Log.GameLog;
import com.chess.game.Model.Log.MutableGameLog;
import com.chess.game.Model.Log.ViewableGameLog;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.ChessPieceFactory;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;

import java.util.ArrayList;
import java.util.List;

public class ChessGame implements SandboxGame {

  private final MutableBoard board;
  private final MutableGameLog log;

  private final ViewableKingSafetyChecker checker;

  private Position pawnToPromote;
  private Color turn;



  public ChessGame() {
    board = new Board();
    board.setPieces();
    log = new GameLog();
    checker = new KingSafetyChecker(this);
    log.setPieceLocations(board);
    turn = Color.WHITE;
  }

  @Override
  public boolean kingInCheck(Color color) {
    return checker.kingInCheck(color);
  }

  @Override
  public boolean canPromotePawn() {
    return pawnToPromote != null;
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
    Piece selectedPiece = selectedSquare.getPiece();
    if (selectedPiece.getColor() != turn) {
      throw new IllegalArgumentException("It is "+turn+"'s turn you cannot move the piece on "+startPos);
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
  public void promotePawn(PieceType pieceType) {
    Piece piece = ChessPieceFactory.buildPiece(turn, pieceType);
    Square selectedSquare = board.getSquare(pawnToPromote);
    selectedSquare.clear();
    selectedSquare.setPiece(piece);
    pawnToPromote = null;
    turn = turn.opposing();
  }


  @Override
  public Color getTurn() {
    return turn;
  }

  @Override
  public ViewableGameLog getLog() {
    return log;
  }


  @Override
  public ViewableBoard getViewableBoard() {
    return board;
  }

  private ChessGame(MutableBoard board, MutableGameLog log, Color turn) {
    this.board = board.getCopy();
    this.log = log.getCopy();
    this.checker = new KingSafetyChecker(this);
    this.turn = turn;
  }


  @Override
  public SandboxGame getCopy() {
    return new ChessGame(board, log, turn);
  }

  @Override
  public void forceMove(Position start, Position end) {
    Square startSquare = board.getSquare(start);
    Square endSquare = board.getSquare(end);
    if (!startSquare.isOccupied()) {
      throw new IllegalArgumentException("There is no piece here to move");
    }
    Piece piece = startSquare.getPiece();
    endSquare.clear();
    endSquare.setPiece(piece);
    startSquare.clear();

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
