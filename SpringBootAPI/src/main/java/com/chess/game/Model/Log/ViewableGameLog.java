package com.chess.game.Model.Log;

import com.chess.game.Model.Color;
import com.chess.game.Model.Move;
import com.chess.game.Model.Position;

import java.util.List;
import java.util.Stack;

public interface ViewableChessGameLog {

  Move getLastMove();


  boolean squareMoved(Position pos);

  List<Position> getLocations(Color color);


  Stack<Move> getMoves();

  int getHalfMoves();

  int getFullMoves();

  int getMoveCount();

  ChessGameGameGameLog getCopy();
}
