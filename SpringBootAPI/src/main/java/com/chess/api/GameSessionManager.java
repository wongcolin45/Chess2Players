package com.chess.api;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class GameSessionManager {

  private final Map<String, GameSession> sessions;

  public GameSessionManager() {
    this.sessions = new HashMap<>();
  }

  public String createGameSession() {
    String gameId = UUID.randomUUID().toString();
    sessions.put(gameId, new GameSession());
    return gameId;
  }

  public GameSession getGameSession(String gameId) {
    if (!sessions.containsKey(gameId)) {
      throw new IllegalArgumentException("Invalid gameId: " + gameId + " does not exist.");
    }
    return sessions.get(gameId);
  }


}
