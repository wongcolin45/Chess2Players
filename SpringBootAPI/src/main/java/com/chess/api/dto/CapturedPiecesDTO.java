package com.chess.api.dto;

import java.util.List;

public class CapturedPiecesDTO {
   private final List<String> whiteCaptures;
   private final List<String> blackCaptures;
   public CapturedPiecesDTO(List<String> whiteCaptures, List<String> blackCaptures) {
       this.whiteCaptures = whiteCaptures;
       this.blackCaptures = blackCaptures;
   }
   public List<String> getWhiteCaptures() {
       return whiteCaptures;
   }
   public List<String> getBlackCaptures() {
       return blackCaptures;
   }
}
