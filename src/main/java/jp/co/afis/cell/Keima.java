package jp.co.afis.cell;

import jp.co.afis.player.*;
import java.util.*;

/**
 * 桂馬クラス。
 */
public class Keima extends ShougiCell implements Attack {
  public Keima(int row, int col, Player player) {
    super("桂", row, col, player);
  }

  @Override
  public List<ShougiCell> calcRyodo() {
    return player.calcRyodoOfKeima(this);
  }

}



