package com.chess.game.Model.Log;

import com.chess.game.Model.Board.ViewableChessBoard;
import com.chess.game.Model.Move;

public interface MutableChessGameGameLog extends ViewableChessGameLog {

  void setPieceLocations(ViewableChessBoard board);

  void addMove(Move move);

  void removeLastMove();


}
