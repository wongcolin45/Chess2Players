package com.chess.api;

import com.chess.api.dto.RoleAssignmentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GameRestController {

  private final GameSessionManager manager;

  @GetMapping("/health")
  public String healthCheck() {
    return "OK";
  }

  @Autowired
  public GameRestController(GameSessionManager manager) {
    this.manager = manager;
  }

  @PostMapping("/create-game")
  public String createGame() {
    return manager.createGameSession();
  }

  @PostMapping("/join-game/{gameId}")
  public RoleAssignmentDTO joinGame(@PathVariable String gameId) {
    GameSession session = manager.getGameSession(gameId);
    return session.assignRole();
  }

  @GetMapping("game/{gameId}/{roleId}")
  public RoleAssignmentDTO getGameRole(@PathVariable String gameId, @PathVariable String roleId) {
    GameSession session = manager.getGameSession(gameId);
    return session.getRole(roleId);
  }

  @DeleteMapping("/reset/games")
  public void resetAllGames() {
    manager.resetAllGameSessions();
  }


}
