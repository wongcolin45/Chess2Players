package com.chess.api;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameServiceTest {

  private ChessGameService service;

  public ChessGameServiceTest() {
      service = new ChessGameService();
  }

  @Test
  public void testGetFenNotation() {
    assertEquals(service.getFenState(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 0");
  }



}