package com.chess.api.dto;

import com.chess.api.PlayerRole;

public class RoleAssignmentDTO {

  private final String roleId;
  private final PlayerRole role;

  public RoleAssignmentDTO(String roleId, PlayerRole role) {
    this.roleId = roleId;
    this.role = role;
  }

  public String getRoleId() {
    return roleId;
  }

  public PlayerRole getRole() {
    return role;
  }
}
