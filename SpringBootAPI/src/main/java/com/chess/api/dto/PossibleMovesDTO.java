package com.chess.api.dto;

import com.chess.game.Model.Position;

import java.util.ArrayList;
import java.util.List;

public class PossibleMovesDTO {
  List<PositionDTO> moves;

  public PossibleMovesDTO(List<Position> moves) {
    this.moves = new ArrayList<>();
    for (Position move : moves) {
      PositionDTO pos = new PositionDTO(move.getRow(), move.getCol());
      this.moves.add(pos);
    }
  }

  public List<PositionDTO> getPossibleMoves() {
    return moves;
  }
}
