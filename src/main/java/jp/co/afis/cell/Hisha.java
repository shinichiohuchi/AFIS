package jp.co.afis.cell;

import jp.co.afis.player.*;
import java.util.*;

/**
 * 飛車クラス。
 */
public class Hisha extends ShougiCell implements Attack {
  private final int rowMax;
  private final int colMax;

  public Hisha(int row, int col, Player player, int rowMax, int colMax) {
    super("飛", row, col, player);
    this.rowMax = rowMax;
    this.colMax = colMax;
  }

  @Override
  public List<ShougiCell> calcRyodo() {
    return player.calcRyodoOfHisha(this, rowMax, colMax);
  }

}




