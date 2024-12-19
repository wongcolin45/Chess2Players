package com.chess.game.Model.Log;

import com.chess.game.Model.Color;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public interface ChessLog {

  void setPieceLocations();

  void addMove(Move move);

  Move getLastMove();

  void removeLastMove();


  boolean squareMoved(Position pos);

  List<Position> getLocations(Color color);

  void setMoves(Stack<Move> moves);


  Stack<Move> getMoves();

  int getSize();

}
