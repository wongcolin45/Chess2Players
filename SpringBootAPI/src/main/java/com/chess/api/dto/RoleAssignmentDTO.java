package com.chess.api.dto;

import com.chess.api.PlayerRole;
import com.chess.game.Model.Color;

import java.util.UUID;

public class PlayerAssignmentDTO {

  private final String playerId;
  private final PlayerRole playerRole;

  public PlayerAssignmentDTO(String playerId, PlayerRole playerRole) {
    this.playerId = playerId;
    this.playerRole = playerRole;
  }

  public String getPlayerId() {
    return playerId;
  }

  public PlayerRole getPlayerRole() {
    return playerRole;
  }
}
