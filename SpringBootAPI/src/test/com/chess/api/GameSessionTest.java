package com.chess.api;

import com.chess.api.dto.GameStateDTO;
import com.chess.api.dto.MoveDTO;
import com.chess.api.dto.PositionDTO;
import com.chess.api.dto.PossibleMovesDTO;
import com.chess.game.Model.Position;
import com.chess.test.Model.AbstractTest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameSessionTest extends AbstractTest {

  private GameSession session;
  private String whitePlayer;
  private String blackPlayer;

  public GameSessionTest() {
    session = new GameSession();
    whitePlayer = session.assignRole().getRoleId();
    blackPlayer = session.assignRole().getRoleId();
  }

  private PositionDTO getPositionDTO(Position position) {
    return new PositionDTO(position.getRow(), position.getCol());
  }

  private MoveDTO getMoveDTO(Position from, Position to) {
    PositionDTO fromDTO = new PositionDTO(from.getRow(), from.getCol());
    PositionDTO toDTO = new PositionDTO(to.getRow(), to.getCol());
    return new MoveDTO(fromDTO, toDTO);
  }



  @Test
  public void testGetFenNotation() {
    Assertions.assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0", session.getFenState());
  }

  @Test
  public void testPossibleMoves() {
    session.movePiece(getMoveDTO(e2, e4), whitePlayer);
    session.movePiece(getMoveDTO(f7, f5), blackPlayer);
    session.movePiece(getMoveDTO(d1, h5), whitePlayer);

    GameStateDTO state = session.getGameStateDTO();
    Assertions.assertTrue(state.isKingInCheck());





  }




}