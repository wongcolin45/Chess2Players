package com.chess.api;


import com.chess.api.dto.MoveDTO;
import com.chess.api.dto.PositionDTO;
import com.chess.api.dto.PossibleMovesDTO;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChessWebsocketController {

  private final SimpMessagingTemplate messagingTemplate;
  private final GameSession gameService;

  public ChessWebsocketController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
    gameService = new GameSession();
  }

  @MessageMapping("move-piece")
  public void movePiece(MoveDTO moveDTO) {
    gameService.movePiece(moveDTO);
    messagingTemplate.convertAndSend("/topic/gameState", gameService.getGameStateDTO());
  }

  @MessageMapping("possible-moves")
  @SendTo("/topic/possible-moves")
  public PossibleMovesDTO getPossibleMoves(PositionDTO positionDTO) {
    return gameService.getPossibleMoves(positionDTO);
  }


  @MessageMapping("request-state")
  public void sendFullState() {
    messagingTemplate.convertAndSend("/topic/gameState", gameService.getGameStateDTO());
  }











}