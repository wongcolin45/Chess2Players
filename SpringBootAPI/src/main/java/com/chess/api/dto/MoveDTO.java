package com.chess.api.dto;

public class MoveDTO {
  private final PositionDTO from;
  private final PositionDTO to;

  public MoveDTO(PositionDTO from, PositionDTO to) {
    this.from = from;
    this.to = to;
  }

  public PositionDTO getFrom() {
    return from;
  }
  public PositionDTO getTo() {
    return to;
  }
}
