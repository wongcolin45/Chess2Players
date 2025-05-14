package com.chess.test.Model.Pieces;

import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.ChessPieceFactory;

import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;
import com.chess.test.Model.AbstractTest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class KnightTest extends AbstractTest {

  private final Piece whiteKnight;
  private final Piece blackKnight;

  private final Piece whitePawn;
  private final Piece blackPawn;

  public KnightTest() {
    whiteKnight = ChessPieceFactory.buildPiece(Color.WHITE, PieceType.KNIGHT);
    blackKnight = ChessPieceFactory.buildPiece(Color.BLACK, PieceType.KNIGHT);
    whitePawn = ChessPieceFactory.buildPiece(Color.WHITE, PieceType.PAWN);
    blackPawn = ChessPieceFactory.buildPiece(Color.BLACK, PieceType.PAWN);
  }

  @Test
  public void testWhiteKnight() {
    game.clearBoard();
    game.placePiece(whiteKnight, e5);
    List<Position> moves = game.getPossibleMoves(e5);
    List<Position> expectedMoves = List.of(d7, f7, c6, g6, c4, g4, f3, d3);
    checkMoves(moves, expectedMoves);
  }

  @Test
  public void testBlackKnight() {
    game.clearBoard();
    game.placePiece(blackKnight, e4);
    List<Position> moves = game.getPossibleMoves(e5);
    List<Position> expectedMoves = List.of(d6, f6, c5, g5, c3, g3, f2, d2);
  }

  @Test
  public void testWhiteKnightValidCaptures() {
    game.clearBoard();
    game.placePiece(whiteKnight, e5);
    List<Position> expectedMoves = List.of(d7, f7, c6, g6, c4, g4, f3, d3);
    for (Position pos : expectedMoves) {
      game.placePiece(blackPawn, pos);
    }
    List<Position> moves = game.getPossibleMoves(e5);
    checkMoves(moves, expectedMoves);
  }

  @Test
  public void testWhiteKnightInvalidCaptures() {
    game.clearBoard();
    game.placePiece(whiteKnight, e5);
    List<Position> expectedMoves = List.of(d7, f7, c6, g6, c4, g4, f3, d3);
    for (Position pos : expectedMoves) {
      game.placePiece(whitePawn, pos);
    }
    List<Position> moves = game.getPossibleMoves(e5);
    Assertions.assertTrue(moves.isEmpty());
  }

  @Test
  public void testBlackKnightValidCaptures() {
    game.clearBoard();
    game.placePiece(blackKnight, e4);
    List<Position> expectedMoves = List.of(d6, f6, c5, g5, c3, g3, f2, d2);
    for (Position pos : expectedMoves) {
      game.placePiece(whitePawn, pos);
    }
    List<Position> moves = game.getPossibleMoves(e4);
    checkMoves(moves, expectedMoves);
  }

  @Test
  public void testBlackKnightInvalidCaptures() {
    game.clearBoard();
    game.placePiece(blackKnight, e4);
    List<Position> expectedMoves = List.of(d6, f6, c5, g5, c3, g3, f2, d2);
    for (Position pos : expectedMoves) {
      game.placePiece(blackPawn, pos);
    }
    List<Position> moves = game.getPossibleMoves(e5);
    Assertions.assertTrue(moves.isEmpty());
  }
}
