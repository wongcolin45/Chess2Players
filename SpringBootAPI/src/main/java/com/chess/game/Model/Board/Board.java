package com.chess.game.Model.Board;

import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.ChessPieceFactory;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;

/**
 * New implementation of a ChessBoard, simply represent the board state.
 */
public class Board implements MutableBoard {
  private final Square[][] grid;

  public Board() {
    grid = new Square[8][8];
    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        Position pos = new Position(r, c);
        grid[r][c] = new Square(pos);
      }
    }
  }

  @Override
  public void setPieces() {
    setSide(Color.WHITE);
    setSide(Color.BLACK);

  }

  @Override
  public void placePiece(Piece piece, Position pos) {
    Square current = getSquare(pos);
    current.clear();
    current.setPiece(piece);
  }

  @Override
  public Piece removePiece(Position pos) {
    Square current = grid[pos.getRow()][pos.getCol()];
    if (!current.isOccupied()) {
      throw new IllegalArgumentException("There is no piece here to remove");
    }
    Piece piece = current.getPiece();
    current.clear();
    return piece;
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

  @Override
  public Square getSquare(Position pos) {
    return grid[pos.getRow()][pos.getCol()];
  }

  @Override
  public boolean isEmpty(Position pos) {
    return !getSquare(pos).isOccupied();
  }

  @Override
  public Piece getPiece(Position pos) {
    if (isEmpty(pos)) {;
      throw new IllegalArgumentException("There is not piece here: "+pos);
    }
    return getSquare(pos).getPiece();
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


  private Board(Square[][] grid) {
    this.grid = getGridCopy(grid);
  }

  @Override
  public Board getCopy() {
    return new Board(grid);
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
  public void clear() {
    for (Square[] squares : grid) {
      for (int j = 0; j < grid[0].length; j++) {
        squares[j].clear();
      }
    }
  }

}
