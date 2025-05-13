package com.chess.game.Model.Game;

import com.chess.game.Model.Position;

public interface MutableChessGame extends ViewableChessGame {

  void movePiece(Position start, Position end);


}
