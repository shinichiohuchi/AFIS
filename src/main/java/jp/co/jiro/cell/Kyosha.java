package jp.co.jiro.cell;

import jp.co.jiro.player.*;
import java.util.*;

/**
 * 香車クラス。
 */
public class Kyosha extends ShougiCell implements Attack {
  private final int rowMax;

  public Kyosha(int row, int col, Player player, int rowMax) {
    super("香", row, col, player);
    this.rowMax = rowMax;
  }

  @Override
  public List<ShougiCell> calcRyodo() {
    return player.calcRyodoOfKyosha(this, rowMax);
  }

}



