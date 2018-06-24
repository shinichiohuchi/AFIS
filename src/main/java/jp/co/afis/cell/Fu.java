package jp.co.afis.cell;

import jp.co.afis.player.*;
import java.util.*;

/**
 * 歩クラス。
 */
public class Fu extends ShougiCell implements Attack {
  public Fu(int row, int col, Player player) {
    super("歩", row, col, player);
  }

  @Override
  public List<ShougiCell> calcRyodo() {
    return player.calcRyodoOfFu(this);
  }

}

