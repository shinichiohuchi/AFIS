package jp.co.afis.cell;

import jp.co.afis.player.*;

public class EmptyCell extends ShougiCell {
  public EmptyCell(int row, int col, Player player) {
    super("", row, col, player);
  }
}
