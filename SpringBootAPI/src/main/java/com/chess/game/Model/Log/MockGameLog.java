package com.chess.game.Model.Log;

import com.chess.game.Model.Log.ChessLog;
import com.chess.game.Model.Color;
import com.chess.game.Model.Move;
import com.chess.game.Model.Position;

import java.util.List;
import java.util.Stack;

public class MockGameLog implements ChessLog {
  @Override
  public void setPieceLocations() {

  }

  @Override
  public void addMove(Move move) {

  }

  @Override
  public Move getLastMove() {
    return null;
  }

  @Override
  public void removeLastMove() {

  }

  @Override
  public boolean squareMoved(Position pos) {
    return false;
  }

  @Override
  public List<Position> getLocations(Color color) {
    return null;
  }

  @Override
  public void setMoves(Stack<Move> moves) {

  }

  @Override
  public Stack<Move> getMoves() {
    return null;
  }

  @Override
  public int getSize() {
    return 0;
  }
}
