package com.chess.game.Model.Checker;

import com.chess.game.Model.Color;
import com.chess.game.Model.GameResult;
import com.chess.game.Model.Position;

import java.util.List;

public class MockBoardChecker implements BoardChecker{
  @Override
  public boolean kingInCheck(Color color) {
    return false;
  }

  @Override
  public boolean isPiecePinned(Position pos) {
    return false;
  }

  @Override
  public List<Position> filterMoves(Position start, List<Position> ends) {
    return ends;
  }


}
