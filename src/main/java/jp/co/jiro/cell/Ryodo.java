package jp.co.jiro.cell;

import jp.co.jiro.player.*;

/**
 * 領土クラス。
 * 駒のすすめる範囲。
 * 王であれば王の周囲8マスが、これに該当する。
 */ 
public class Ryodo extends ShougiCell {
  public Ryodo(int row, int col, Player player) {
    super("", row, col, player);
  }
}

