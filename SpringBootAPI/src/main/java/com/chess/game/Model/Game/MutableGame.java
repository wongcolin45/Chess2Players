package com.chess.game.Model.Game;

import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;

public interface MutableGame extends ViewableGame {

  void movePiece(Position start, Position end);

  void promotePawn(PieceType pieceType);
}
