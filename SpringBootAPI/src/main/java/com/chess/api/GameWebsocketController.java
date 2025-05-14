package com.chess.api;


import com.chess.api.dto.MoveDTO;
import com.chess.api.dto.PieceSelectionDTO;
import com.chess.api.dto.PositionDTO;
import com.chess.api.dto.PossibleMovesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebsocketController {

  private final SimpMessagingTemplate messagingTemplate;

  private final GameSessionManager manager;

  @Autowired
  public GameWebsocketController(SimpMessagingTemplate messagingTemplate, GameSessionManager manager) {
    this.messagingTemplate = messagingTemplate;
    this.manager = manager;
  }

  @MessageMapping("move-piece/{gameId}/{roleId}")
  public void movePiece(@DestinationVariable String gameId, @DestinationVariable String roleId, MoveDTO moveDTO) {
    GameSession session = manager.getGameSession(gameId);
    session.movePiece(moveDTO, roleId);
    messagingTemplate.convertAndSend("/topic/gameState/"+gameId, session.getGameStateDTO());
  }

  @MessageMapping("possible-moves/{gameId}/{roleId}")
  public void getPossibleMoves(@DestinationVariable String gameId, @DestinationVariable String roleId,PositionDTO positionDTO) {
    GameSession session = manager.getGameSession(gameId);
    messagingTemplate.convertAndSend("/topic/possible-moves/"+gameId+"/"+roleId, session.getPossibleMoves(positionDTO, roleId));

  }

  @MessageMapping("promote-pawn/{gameId}")
  public void promotePawn(@DestinationVariable String gameId, PieceSelectionDTO pieceSelectionDTO) {
    GameSession session = manager.getGameSession(gameId);
    if (!session.canPromotePawn()) {
      throw new IllegalStateException("Can't promote pawn");
    }
    session.promotePawn(pieceSelectionDTO);
    messagingTemplate.convertAndSend("/topic/gameState/"+gameId, session.getGameStateDTO());
  }

  @MessageMapping("request-state/{gameId}")
  public void sendFullState(@DestinationVariable String gameId) {
    GameSession session = manager.getGameSession(gameId);
    messagingTemplate.convertAndSend("/topic/gameState/"+gameId, session.getGameStateDTO());
  }











}