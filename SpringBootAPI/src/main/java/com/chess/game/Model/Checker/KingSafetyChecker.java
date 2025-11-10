package com.chess.game.Model.Checker;

import java.util.ArrayList;
import java.util.List;

import com.chess.game.Model.Board.MutableBoard;
import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ChessGame;
import com.chess.game.Model.Game.SandboxGame;
import com.chess.game.Model.Game.ViewableGame;
import com.chess.game.Model.Pieces.ChessPieceFactory;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

/**
 * This is the chess board check that handles:
 * - checking if king is in check
 * - checking for castle moves for king
 * - checking for gameOver and game results
 */
public class KingSafetyChecker implements ViewableKingSafetyChecker {

  private final ViewableGame game;
  private final ViewableBoard board;

  public KingSafetyChecker(ViewableGame game) {
    this.game = game;
    board = this.game.getViewableBoard();
  }

  @Override
  public boolean kingInCheck(Color color) {
    Position kingPos = getKingPosition(color);
    if (kingPos == null) {
      return false;
    }

    Piece queen = ChessPieceFactory.buildPiece(color, PieceType.QUEEN);
    Piece knight = ChessPieceFactory.buildPiece(color, PieceType.KNIGHT);

    List<Position> knightMoves = knight.getMoves(game, kingPos);

    // Check for knight captures
    for (Position pos : knightMoves) {
      if (containsOpposingPiece(color, pos) &&
          board.getPiece(pos).getType() == PieceType.KNIGHT) {
        return true;
      }
    }

    List<Position> queenMoves = queen.getMoves(game, kingPos);

    // Check for bishop rooks or queen.
    for (Position pos : queenMoves) {
      if (containsOpposingPiece(color, pos)) {
        // check if its straight for rook
        Piece piece = board.getPiece(pos);

        if (piece.getType() == PieceType.QUEEN) {
          return true;
        }
        if (kingPos.getRow() == pos.getRow() || kingPos.getCol() == pos.getCol()) {
          if (piece.getType() == PieceType.ROOK) {
            return true;
          }
        } else {
          if (piece.getType() == PieceType.BISHOP) {
            return true;
          }
        }
      }
    }

    // check for pawn captures
    Piece pawn = ChessPieceFactory.buildPiece(color, PieceType.PAWN);
    List<Position> pawnMoves = pawn.getMoves(game, kingPos)
            .stream()
            .filter(pos -> pos.getCol() != kingPos.getCol())
            .toList();
    for (Position pos : pawnMoves) {
      if (containsOpposingPiece(color, pos) && board.getPiece(pos).getType() == PieceType.PAWN) {
        return true;
      }
    }

    // check for king captures
    Piece king = ChessPieceFactory.buildPiece(color, PieceType.KING);
    List<Position> kingMoves = king.getMoves(game, kingPos);
    for (Position pos : kingMoves) {
      if (containsOpposingPiece(color,pos) && board.getPiece(pos).getType() == PieceType.KING) {
        return true;
      }
    }

    return false;
  }

  // Get moves that don't allow king to be captured
  private List<Position> getLegalMoves(Position start, List<Position> ends) {
    Color turn = board.getPiece(start).getColor();
    List<Position> moves = new ArrayList<>();
    boolean wasInCheck = kingInCheck(turn);
    for (Position end : ends) {
      SandboxGame gameCopy = game.getCopy();
      gameCopy.forceMove(start, end);
      if (!gameCopy.kingInCheck(turn)) {
        moves.add(end);
      }
    }
    return filterNoCastlesInCheck(start, moves);
  }

  @Override
  public List<Position> filterMoves(Position start, List<Position> ends) {
    if (board.isEmpty(start)) {
      throw new IllegalArgumentException("There is no piece on "+start);
    }
    if (getKingPosition(board.getPiece(start).getColor()) == null) {
      return ends;
    }
    if (!kingInCheck(board.getPiece(start).getColor()) && isPiecePinned(start)) {
      return ends;
    }
    return getLegalMoves(start, ends);
  }

  private List<Position> filterNoCastlesInCheck(Position start, List<Position> ends) {
    if (!(board.getPiece(start).getType() == PieceType.KING) || !kingInCheck(game.getTurn())) {
      return ends;
    }
    List<Position> newEnds = new ArrayList<>();
    for (Position end : ends) {
      if (!(Math.abs(start.getCol() - end.getCol()) >= 2)) {
        newEnds.add(end);
      }
    }
    return newEnds;
  }

  private boolean onKingsDiagonal(Position pos) {
    Position kingPos = getKingPosition(game.getTurn());
    return Math.abs(pos.getRow() - kingPos.getRow()) == Math.abs(pos.getCol() - kingPos.getCol());
  }

  private List<Position> getPinnerPositions(Color color) {
    List<Position> pinnerPositions = new ArrayList<>();
    List<Position> positions = game.getLog().getLocations(color);
    for (Position position : positions) {
      PieceType type = board.getPiece(position).getType();
      if (type == PieceType.BISHOP || type == PieceType.ROOK || type == PieceType.QUEEN) {
        pinnerPositions.add(position);
      }
    }
    return pinnerPositions;
  }

  private boolean containsOpposingPiece(Color color, Position pos) {
    return !board.isEmpty(pos) && board.getPiece(pos).getColor() == color.opposing();
  }

  @Override
  public Position getKingPosition(Color color) {
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        Position pos = new Position(r, c);
        if (!board.isEmpty(pos) &&
            board.getPiece(pos).getColor() == color &&
            board.getPiece(pos).getType() == PieceType.KING) {
          return new Position(r, c);
        }
      }
    }
    return null;
    //throw new IllegalStateException("Cannot find the "+color.toString() + " king bro!\n"+board.getTextGrid());
  }

  @Override
  public boolean isPiecePinned(Position pos) {
    Color turn = board.getPiece(pos).getColor();
    Position kingPos = getKingPosition(turn);
    List<Position> aroundKing = new ArrayList<>();
    Piece queen = ChessPieceFactory.buildPiece(turn.opposing(), PieceType.QUEEN);
    // built queen of opposite color and king and get captures to check for pieces above king
    for (Position move : queen.getMoves(game, kingPos)) {
      // check if contains pieces same color as king
      if (containsOpposingPiece(turn, move)) {
        aroundKing.add(move);
      }
    }
    if (!aroundKing.contains(pos)) {
      return false;
    }
    // get potential pinners
    SandboxGame gameCopy;
    List<Position> pinnerPositions = getPinnerPositions(turn.opposing());
    for (Position pinnerPos : pinnerPositions) {
      Piece pinner = board.getPiece(pinnerPos);
      List<Position> moves = pinner.getMoves(game, kingPos);
      gameCopy = game.getCopy();
      gameCopy.clearSquare(pos);
      List<Position> movesAfter  = pinner.getMoves(gameCopy, kingPos);
      if (!moves.contains(pos) && movesAfter.contains(kingPos)) {
        return true;
      }
    }
    return false;
  }




}
