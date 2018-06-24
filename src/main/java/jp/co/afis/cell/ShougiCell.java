package jp.co.afis.cell;

import jp.co.afis.player.*;

public class ShougiCell {
  public final String name;
  protected final int row;
  protected final int col;
  protected final Player player;
  protected int clickableBitFlag = 0;

  public ShougiCell(String name, int row, int col, Player player) {
    this.name   = name;
    this.row    = row;
    this.col    = col;
    this.player = player;
  }

  public String getName() {
    return name;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Player getPlayer() {
    return player;
  }

  public int getClickableBitFlag() {
    return clickableBitFlag;
  }

  public void setClickableBitFlag(int clickableBitFlag) {
    this.clickableBitFlag = clickableBitFlag;
  }

  @Override
  public String toString() {
    return "ShougiCell ::: name = " + name + ", row = " + row + ", col = " + col + ", player = " + player;
  }
}

