package jp.co.afis.cell;

import java.util.*;

/**
 * 攻撃インタフェース。
 * 駒の攻撃を定義する。
 */
public interface Attack {
  List<ShougiCell> calcRyodo();
}

