package com.chess.game.Model.Checker;

import java.util.List;

import com.chess.game.Model.Color;
import com.chess.game.Model.Position;

public interface ViewableKingSafetyChecker {

  boolean kingInCheck(Color color);

  boolean isPiecePinned(Position pos);

  List<Position> filterMoves(Position start, List<Position> ends);

}
