package com.chess.game.Model.Log;

import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Move;

public interface MutableGameLog extends ViewableGameLog {

  void setPieceLocations(ViewableBoard board);

  void addMove(Move move);

  void removeLastMove();


}
