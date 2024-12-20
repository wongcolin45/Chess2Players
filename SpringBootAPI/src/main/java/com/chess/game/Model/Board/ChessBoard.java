package com.chess.game.Model.Board;

import com.chess.game.Model.Log.ChessLog;
import com.chess.game.Model.Checker.ChessBoardChecker;

import java.util.ArrayList;

import java.util.List;

import com.chess.game.Model.Checker.BoardChecker;
import com.chess.game.Model.Checker.MockBoardChecker;
import com.chess.game.Model.Log.ChessGameLog;
import com.chess.game.Model.Color;
import com.chess.game.Model.GameResult;
import com.chess.game.Model.Log.MockGameLog;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.ChessPieceFactory;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;
import com.chess.game.View.ChessTerminalView;
import com.chess.game.View.ChessView;

public class ChessBoard implements Board {

  private final BoardChecker boardChecker;


  private Position pawnToPromote;
  private final ChessLog log;
  private final Square[][] grid;
  private Color turn;
  private Square selectedSquare;

  public ChessBoard() {
    grid = new Square[8][8];
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        Position pos = new Position(r, c);
        grid[r][c] = new BoardSquare(pos);
      }
    }
    //setBoard();
    turn = Color.WHITE;
    boardChecker = new ChessBoardChecker(this);
    log = new ChessGameLog(this);
  }

  @Override
  public void setPieces() {
    setSide(Color.WHITE);
    setSide(Color.BLACK);
    log.setPieceLocations();
  }

  @Override
  public void placePiece(Piece piece, Position pos) {
    Square current = getSquare(pos);
    current.clear();
    current.setPiece(piece);
  }

  @Override
  public void undoMove() {

  }

  @Override
  public boolean isPawnPromotion() {
    return pawnToPromote != null;
  }

  @Override
  public void promotePawn(PieceType type) {
    if (pawnToPromote == null) {
      throw new IllegalStateException("There is no pawn to promote!");
    }
    if (type == PieceType.PAWN || type == PieceType.KING) {
      throw new IllegalArgumentException("You cannot promote to a pawn or king!");
    }
    Color color = getPiece(pawnToPromote).getColor();
    Piece newPiece = ChessPieceFactory.buildPiece(color, type);
    Square square = getSquare(pawnToPromote);
    square.setPiece(newPiece);
    turn = turn.opposing();
    pawnToPromote = null;
  }

  private void placePiece(Color color, PieceType type, int r, int c) {
    Square current = grid[r][c];
    Piece piece = ChessPieceFactory.buildPiece(color, type);
    current.setPiece(piece);
  }

  private void setSide(Color color) {
    int r = (color == Color.WHITE) ? 6 : 1;
    for (int c = 0; c < 8; c++) {
      placePiece(color, PieceType.PAWN, r, c);
    }
    r = (r == 6) ? r + 1 : r - 1;
    for (int c = 0; c <= 7; c += 7) {
      placePiece(color, PieceType.ROOK, r, c);
    }
    for (int c = 1; c <= 6; c += 5) {
      placePiece(color, PieceType.KNIGHT, r, c);
    }
    for (int c = 2; c <= 5; c += 3) {
      placePiece(color, PieceType.BISHOP, r, c);
    }
    placePiece(color, PieceType.QUEEN, r, 3);
    placePiece(color, PieceType.KING, r, 4);
  }

  private Square getSquare(Position pos) {
    return grid[pos.getRow()][pos.getCol()];
  }

  @Override
  public boolean isEmpty(Position pos) {
    return !getSquare(pos).isOccupied();
  }

  @Override
  public Piece getPiece(Position pos) {
    if (isEmpty(pos)) {
      ChessView view = new ChessTerminalView(this);
      view.render();
      String message = (selectedSquare != null) ? selectedSquare.getPosition().toString() : "no selection for square";
      throw new IllegalArgumentException("There is not piece here: "+pos +" "+ message);
    }
    return this.getSquare(pos).getPiece();
  }

  @Override
  public void selectedPiece(Position pos) {
    if (pawnToPromote != null) {
      throw new IllegalStateException("You need to promote your pawn first");
    }
    Square current = getSquare(pos);
    if (current.isOccupied() && current.getPiece().getColor() != turn) {
      throw new IllegalArgumentException("You are not allowed to move " + turn.opposing().toString() + "'s pieces!");
    }
    if (!current.isOccupied()) {
      throw new IllegalArgumentException("There is not piece on "+pos + "!");
    }
    if (current.getPiece().getMoves(this, pos).isEmpty()) {
      throw new IllegalArgumentException("This piece has no valid moves!");
    }
    selectedSquare = getSquare(pos);
  }

  @Override
  public void unselectedPiece() {
    selectedSquare = null;
  }

  @Override
  public void movePiece(Position pos) {
    if (selectedSquare == null) {
      throw new IllegalStateException("You need to select a piece before you can move!");
    }

    if (!getPossibleMoves().contains(pos)) {
      selectedSquare = null;
      throw new IllegalArgumentException(pos + " is not a valid move!");
    }
    Piece capture = (isSquareEmpty(pos)) ? null : getPiece(pos);
    Square current = getSquare(pos);
    Piece piece = selectedSquare.getPiece();
    current.setPiece(piece);
    Position start = selectedSquare.getPosition();
    selectedSquare.clear();
    selectedSquare = null;
    turn = turn.opposing();

    // move rook for castles;
    if (piece.getType() == PieceType.KING && Math.abs(start.getCol() - pos.getCol()) >= 2) {
      int row = (piece.getColor() == Color.WHITE) ? 7 : 0;
      int col = (pos.getCol() == 6) ? 7 : 0;
      // why am I double-checking rook here?
      if (isSquareEmpty(new Position(row, col))) {
        return;
      }
      Piece rook = getPiece(new Position(row, col));
      grid[row][col].clear();
      int iter = (pos.getCol() == 6) ? -1 : 1;
      grid[pos.getRow()][pos.getCol()+iter].setPiece(rook);
    }
    // move captured pawn
    if (piece.getType() == PieceType.PAWN ) {
      if (pos.getCol() != start.getCol()
          &&
          capture == null) {
        Position piecePos = new Position(start.getRow(), pos.getCol());
        removePiece(piecePos);
      } else if (pos.getRow() == 7 || pos.getRow() == 0) {
        pawnToPromote = pos;
        turn = turn.opposing();
      }


    }
    // add to log
    Move move = new Move(piece, start, pos);
    log.addMove(move);

    // recalculate game over
  }

  @Override
  public void move(String start, String end) {
    selectedPiece(new Position(start));
    movePiece(new Position(end));
  }

  @Override
  public void removePiece(Position pos) {
    Square current = grid[pos.getRow()][pos.getCol()];
    if (!current.isOccupied()) {
      throw new IllegalArgumentException("There is no piece here to remove");
    }
    current.clear();
  }

  @Override
  public boolean kingInCheck(Color color) {
    return boardChecker.kingInCheck(color);
  }

  @Override
  public Color getTurn() {
    return turn;
  }

  @Override
  public boolean isGameOver() {

    List<Position> locations = log.getLocations(turn);
    for (Position pos : locations) {
      if (pos != null && !getPossibleMoves(pos).isEmpty()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public GameResult getGameResult() {
    if (!isGameOver()) {
      return null;
    }

    if (!boardChecker.kingInCheck(turn)) {
      return GameResult.STALEMATE_FORCED;
    }

    if (turn == Color.WHITE) {
      return GameResult.BLACK_CHECKMATE;
    }
    return GameResult.WHITE_CHECKMATE;

  }

  @Override
  public boolean isPiecePinned() {
    return selectedSquare != null && boardChecker.isPiecePinned(selectedSquare.getPosition());
  }

  @Override
  public boolean isSquareEmpty(Position pos) {
    Square square = getSquare(pos);
    return !square.isOccupied();
  }


  @Override
  public Move getLastMove() {
    return log.getLastMove();
  }

  @Override
  public int movesMade() {
    return log.getSize();
  }

  @Override
  public boolean squareNotMoved(Position pos) {
    return !log.squareMoved(pos);
  }

  @Override
  public boolean squareNotMoved(int r, int c) {
    return !log.squareMoved(new Position(r, c));
  }


  private Square[][] getGridCopy(Square[][] grid) {
    if (grid.length != 8 || grid[0].length != 8) {
      throw new IllegalArgumentException("Grid must by 8x8");
    }
    Square[][] copy = new Square[8][8];
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        copy[r][c] = grid[r][c].getCopy();
      }
    }
    return copy;
  }


  private ChessBoard(Square[][] grid, Color turn, boolean check) {
    this.grid = getGridCopy(grid);
    this.turn = turn;
    if (check) {
      boardChecker = new ChessBoardChecker(this);
    } else {
      boardChecker = new MockBoardChecker();
    }
    log = new MockGameLog();
    pawnToPromote = null;
  }

  @Override
  public Board getCopy(boolean check) {
    return new ChessBoard(grid, turn, check);
  }


  @Override
  public String[][] getTextGrid() {
    String[][] textGrid = new String[8][8];
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        Square current = grid[r][c];
        textGrid[r][c] = current.toString();
      }
    }
    return textGrid;
  }

  @Override
  public String[][] getTextGrid(Color color) {
    String[][] textGrid = new String[8][8];
    int r = 0;
    int iter = 1;
    int colStart = 0;
    if (color == Color.BLACK) {
      r = 7;
      iter = -1;
      colStart = 7;
    }
    for (int i = 0; i < 8; i++) {
      int c = colStart;
      for (int j = 0; j < 8; j++) {
        Square current = grid[r][c];
        textGrid[i][j] = current.toString();
        c += (iter);
      }
      r+=iter;
    }
    return textGrid;
  }




  @Override
  public List<Position> getPossibleMoves() {
    if (selectedSquare == null) {
      return new ArrayList<>();
    }
    Piece piece = selectedSquare.getPiece();
    // Filter for checks
    Position start = selectedSquare.getPosition();
    return getPossibleMoves(start);
    //List<Position> ends = piece.getMoves(this, selectedSquare.getPosition());
    //return boardChecker.filterMoves(start, ends);
  }

  private List<Position> getPossibleMoves(Position pos) {
    Piece piece = getPiece(pos);
    List<Position> ends = piece.getMoves(this, pos);
    return boardChecker.filterMoves(pos, ends);
  }
}
