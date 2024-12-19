package com.chess.test.Model;

import com.chess.game.Model.Position;

import org.junit.Assert;
import org.junit.Test;


public class PositionTest {

  private final String[] cols = {"a", "b", "c", "d", "e", "f", "g", "h"};

  @Test
  public void testCheckCoordinateEqualsNotation() {
    int row = 8;

    for (int r = 0; r < 8; r++) {
      for (int c = 0; c < 8; c++) {
        Position notation = new Position(cols[c] + row);
        Position coordinate = new Position(r, c);

        Assert.assertEquals(notation, coordinate);
        Assert.assertEquals(notation.toString(), coordinate.toString());
      }
      row--;
    }
  }


}