package jp.co.jiro.cell;

import jp.co.jiro.player.*;
import java.util.*;

/**
 * 歩クラス。
 */
public class Kaku extends ShougiCell implements Attack {
  private final int rowMax;
  private final int colMax;

  public Kaku(int row, int col, Player player, int rowMax, int colMax) {
    super("角", row, col, player);
    this.rowMax = rowMax;
    this.colMax = colMax;
  }

  @Override
  public List<ShougiCell> calcRyodo() {
    return player.calcRyodoOfKaku(this, rowMax, colMax);
  }

}


