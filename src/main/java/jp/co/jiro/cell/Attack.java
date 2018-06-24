package jp.co.jiro.cell;

import jp.co.jiro.player.*;
import java.util.*;

/**
 * 攻撃インタフェース。
 * 駒の攻撃を定義する。
 */
public interface Attack {
  List<ShougiCell> calcRyodo();
}

