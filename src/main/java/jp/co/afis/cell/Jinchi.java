package jp.co.afis.cell;

import jp.co.afis.player.*;

/**
 * 陣地クラス。
 * 開始直後の駒を配置できる領域。
 * 将棋盤の(n x 1/3)列を陣地とする。
 * 割り切れない場合は、小数点第１位を四捨五入する。
 */
public class Jinchi extends ShougiCell {
  public Jinchi(int row, int col, Player player) {
    super("", row, col, player);
  }
}
