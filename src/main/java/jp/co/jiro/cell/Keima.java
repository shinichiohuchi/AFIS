package jp.co.jiro.cell;

import jp.co.jiro.player.*;
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



