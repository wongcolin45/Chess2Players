package com.chess.game.Model.Log;

import com.chess.game.Model.Color;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;

import java.util.List;
import java.util.Stack;

public interface ViewableGameLog {

  Move getLastMove();


  boolean squareMoved(Position pos);

  List<Position> getLocations(Color color);

  List<PieceType> getWhiteCaptures();

  List<PieceType> getBlackCaptures();

  Stack<Move> getMoves();

  int getHalfMoves();

  int getFullMoves();

  int getMoveCount();

  GameLog getCopy();
}
