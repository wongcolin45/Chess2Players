package com.chess.game.Model.Checker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Board.ChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.GameResult;
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
public class ChessBoardChecker implements BoardChecker {

  private final Board board;

  public ChessBoardChecker(Board board) {
    this.board = board;
  }

  @Override
  public boolean kingInCheck(Color color) {
    Position kingPos = getKingPosition(color);
    if (kingPos == null) {
      return false;
    }

    Piece queen = ChessPieceFactory.buildPiece(color, PieceType.QUEEN);
    Piece knight = ChessPieceFactory.buildPiece(color, PieceType.KNIGHT);

    List<Position> knightMoves = knight.getMoves(board, kingPos);

    // Check for knight captures
    for (Position pos : knightMoves) {
      if (containsOpposingPiece(color, pos) &&
          board.getPiece(pos).getType() == PieceType.KNIGHT) {
        return true;
      }
    }

    List<Position> queenMoves = queen.getMoves(board, kingPos);

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
    List<Position> pawnMoves = pawn.getMoves(board, kingPos)
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
    List<Position> kingMoves = king.getMoves(board, kingPos);
    for (Position pos : kingMoves) {
      if (containsOpposingPiece(color,pos) && board.getPiece(pos).getType() == PieceType.KING) {
        return true;
      }
    }

    return false;
  }

  @Override
  public List<Position> filterMoves(Position start, List<Position> ends) {
    boolean isKing = board.getPiece(start).getType() == PieceType.KING;

    if (isKing) {

    }

    if (!isKing && !isPiecePinned(start)) {
      return ends;
    }

    if (isKing || kingInCheck(board.getTurn())) {
      return getLegalMoves(start, ends);
    }

    // Piece is pinned:
    Position kingPos = getKingPosition(board.getTurn());
    if (kingPos == null) {
      return ends;
    }

    if (kingPos.getRow() == start.getRow()) {
      return ends.stream().filter(end -> end.getRow() == kingPos.getRow()).toList();
    } else if (kingPos.getCol() == start.getCol()) {
      return ends.stream().filter(end -> end.getCol() == kingPos.getCol()).toList();
    }

    List<Position> diagonals = ends.stream().filter(end -> onKingsDiagonal(end)).toList();

    // filter for right row;
    if (start.getRow() < kingPos.getRow()) {
        diagonals = diagonals.stream()
                .filter(end -> end.getRow() < kingPos.getRow()).toList();
    } else {
      diagonals = diagonals.stream()
              .filter(end -> end.getRow() > kingPos.getRow()).toList();
    }
    // filter for right col;
    if (start.getCol() < kingPos.getCol()) {
      return diagonals.stream()
              .filter(end -> end.getCol() < kingPos.getCol()).toList();
    }
    return diagonals.stream()
            .filter(end -> end.getCol() > kingPos.getCol()).toList();
  }

  // Get moves that don't allow king to be captured
  private List<Position> getLegalMoves(Position start, List<Position> ends) {
    List<Position> moves = new ArrayList<>();
    for (Position end : ends) {
      Board copy = board.getCopy(false);



      copy.selectedPiece(start);
      copy.movePiece(end);
      copy = copy.getCopy(true);
      if (!copy.kingInCheck(board.getTurn())) {
        moves.add(end);
      }
    }
    //return moves;
    return filterNoCastlesInCheck(start, moves);
  }

  private List<Position> filterNoCastlesInCheck(Position start, List<Position> ends) {
    if (!(board.getPiece(start).getType() == PieceType.KING) || !kingInCheck(board.getTurn())) {
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
    Position kingPos = getKingPosition(board.getTurn());
    return Math.abs(pos.getRow() - kingPos.getRow()) == Math.abs(pos.getCol() - kingPos.getCol());
  }

  /**
   * Generate moves for queens and the king position.
   * If the piece on the position of one of these moves then it is considered pinned
   * @param pos the position of the piece to check
   */
  @Override
  public boolean isPiecePinned(Position pos) {
    Color turn = board.getTurn();
    Board state = board.getCopy(true);
    state.removePiece(pos);
    return state.kingInCheck(turn);
  }

  private boolean containsOpposingPiece(Color color, Position pos) {
    return !board.isEmpty(pos) && board.getPiece(pos).getColor() == color.opposing();
  }

  private Position getKingPosition(Color color) {
    ChessView view = new ChessTerminalView(board);
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





}
