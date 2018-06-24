package jp.co.jiro.cell;

import jp.co.jiro.player.*;

public class EmptyCell extends ShougiCell {
  public EmptyCell(int row, int col, Player player) {
    super("", row, col, player);
  }
}
