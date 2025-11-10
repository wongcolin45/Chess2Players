package com.chess.api.dto;

import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ViewableGame;
import com.chess.game.Model.Log.ViewableGameLog;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameStateDTOConverter {

    public static GameStateDTO getGameStateDTO(ViewableGame game) {
        PositionDTO pawnToPromote = new PositionDTO(-1,-1);
        if (game.promotionAvailable()) {
            Position pos = game.getPromotionPosition();
            pawnToPromote = new PositionDTO(pos.getRow(), pos.getCol());
        }
        return new GameStateDTO(
                game.getTurn(),
                game.kingInCheck(game.getTurn()),
                getLastMoveDTO(game),
                getKingPosition(game),
                game.isGameOver(),
                pawnToPromote,
                getCapturedPiecesDTO(game),
                game.getGameStatus(),
                game.getViewableBoard().getTextGrid());
    }

    private static CapturedPiecesDTO getCapturedPiecesDTO(ViewableGame game) {
        List<PieceType> whiteCaptures = new ArrayList<>();
        List<PieceType> blackCaptures = new ArrayList<>();
        return new CapturedPiecesDTO(
                getPieceSymbols(whiteCaptures, Color.WHITE),
                getPieceSymbols(blackCaptures, Color.BLACK));
    }

    private static Optional<MoveDTO> getLastMoveDTO(ViewableGame game) {
        ViewableGameLog log = game.getLog();
        if (log.getMoveCount() == 0) {
            return Optional.empty();
        }
        Move lastMove = log.getLastMove();
        PositionDTO start = new PositionDTO(lastMove.getStart().getRow(), lastMove.getStart().getCol());
        PositionDTO end = new PositionDTO(lastMove.getEnd().getRow(), lastMove.getEnd().getCol());
        return Optional.of(new MoveDTO(start, end));
    }

    private static PositionDTO getKingPosition(ViewableGame game) {
        Position pos = game.getKingPosition(game.getTurn());
        return new PositionDTO(pos.getRow(), pos.getCol());
    }

    private static List<String> getPieceSymbols(List<PieceType> pieceTypes, Color color) {
        List<String> pieces = new ArrayList<>();
        for (PieceType pieceType : pieceTypes) {
            switch (pieceType) {
                case PieceType.PAWN:
                    pieces.add("P");
                case PieceType.KNIGHT:
                    pieces.add("N");
                case PieceType.BISHOP:
                    pieces.add("B");
                case PieceType.ROOK:
                    pieces.add("R");
                case PieceType.QUEEN:
                    pieces.add("Q");
                default:
                    throw new IllegalArgumentException("Invalid piece type");
            }
        }
        if (color == Color.BLACK) {
            return pieces;
        }
        pieces.replaceAll(String::toLowerCase);
        return pieces;
    }

}
