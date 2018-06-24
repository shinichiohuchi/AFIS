package jp.co.jiro.cell;

import jp.co.jiro.player.*;
import java.util.*;

/**
 * 金クラス。
 */
public class Kin extends ShougiCell implements Attack {
  public Kin(int row, int col, Player player) {
    super("金", row, col, player);
  }

  @Override
  public List<ShougiCell> calcRyodo() {
    return player.calcRyodoOfKin(this);
  }

}


