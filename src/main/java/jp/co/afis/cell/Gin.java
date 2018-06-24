package jp.co.afis.cell;


import jp.co.afis.player.*;
import java.util.*;

/**
 * 銀クラス。
 */
public class Gin extends ShougiCell implements Attack {
  public Gin(int row, int col, Player player) {
    super("銀", row, col, player);
  }

  @Override
  public List<ShougiCell> calcRyodo() {
    return player.calcRyodoOfGin(this);
  }

}



