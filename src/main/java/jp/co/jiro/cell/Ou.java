package jp.co.jiro.cell;

import jp.co.jiro.player.*;
import java.util.*;

/**
 * 王クラス。
 */
public class Ou extends ShougiCell implements Attack {
  public Ou(int row, int col, Player player) {
    super("王", row, col, player);
  }

  @Override
  public List<ShougiCell> calcRyodo() {
    return player.calcRyodoOfOu(this);
  }

}


