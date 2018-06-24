package jp.co.afis.cell;

import jp.co.afis.player.*;
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


