package jp.co.afis.player;

import jp.co.afis.cell.*;
import java.util.*;

/**
 * 駒を配置したときの、その駒の攻撃範囲のセルのリストを返却するユーティリティクラス。
 */
class PlayerUtil {
  private PlayerUtil() {}

  static List<ShougiCell> calcRyodoOfFu(ShougiCell cell, Player player, int plus) {
    int row = cell.getRow() - plus;
    int col = cell.getCol();
    ShougiCell ryodo = new Ryodo(row, col, player);
    List<ShougiCell> list = new ArrayList<ShougiCell>();
    list.add(ryodo);
    return list;
  }

  static List<ShougiCell> calcRyodoOfKin(ShougiCell cell, Player player, int removeRow) {
    int row = cell.getRow();
    int col = cell.getCol();
    List<ShougiCell> list = new ArrayList<ShougiCell>();
    for (int i=row-1; i<=row+1; i++) {
      for (int j=col-1; j<=col+1; j++) {
        if (!(i == removeRow && (j == col-1 || j == col+1))) {
          list.add(new Ryodo(i, j, player));
        }
      }
    }
    return list;
  }

  static List<ShougiCell> calcRyodoOfGin(ShougiCell cell, Player player, int removeRow) {
    int row = cell.getRow();
    int col = cell.getCol();
    List<ShougiCell> list = new ArrayList<ShougiCell>();
    for (int i=row-1; i<=row+1; i++) {
      for (int j=col-1; j<=col+1; j++) {
        if (!(
              (i == row       && (j == col - 1 || j == col + 1))
              || (i == removeRow &&  j == col)
             )) {
          list.add(new Ryodo(i, j, player));
             }
      }
    }
    return list;
  }

  static List<ShougiCell> calcRyodoOfKeima(ShougiCell cell, Player player, int targetRow) {
    int row = cell.getRow();
    int col = cell.getCol();
    List<ShougiCell> list = new ArrayList<ShougiCell>();
    list.add(new Ryodo(targetRow, col-1, player));
    list.add(new Ryodo(targetRow, col+1, player));
    return list;
  }

  static List<ShougiCell> calcRyodoOfKaku(ShougiCell cell, Player player, int rowMax, int colMax) {
    int row = cell.getRow();
    int col = cell.getCol();
    List<ShougiCell> list = new ArrayList<ShougiCell>();
    list.addAll(calcKaku(row, col,  1,  1, player, rowMax, colMax, list));
    list.addAll(calcKaku(row, col,  1, -1, player, rowMax, colMax, list));
    list.addAll(calcKaku(row, col, -1,  1, player, rowMax, colMax, list));
    list.addAll(calcKaku(row, col, -1, -1, player, rowMax, colMax, list));
    return list;
  }

  static List<ShougiCell> calcRyodoOfHisha(ShougiCell cell, Player player, int rowMax, int colMax) {
    int row = cell.getRow();
    int col = cell.getCol();
    List<ShougiCell> list = new ArrayList<ShougiCell>();
    for (int i=0; i<rowMax; i++) {
      list.add(new Ryodo(i, col, player));
    }
    for (int i=0; i<colMax; i++) {
      list.add(new Ryodo(row, i, player));
    }
    return list;
  }

  static List<ShougiCell> calcRyodoOfKyosha(ShougiCell cell, Player player, int rowStart, int rowEnd) {
    int col = cell.getCol();
    List<ShougiCell> list = new ArrayList<ShougiCell>();
    for (int i=rowStart; i<rowEnd; i++) {
      list.add(new Ryodo(i, col, player));
    }
    return list;
  }

  static List<ShougiCell> calcRyodoOfOu(ShougiCell cell, Player player) {
    int row = cell.getRow();
    int col = cell.getCol();
    List<ShougiCell> list = new ArrayList<ShougiCell>();
    for (int i=row-1; i<=row+1; i++) {
      for (int j=col-1; j<=col+1; j++) {
        list.add(new Ryodo(i, j, player));
      }
    }
    return list;
  }

  private static List<ShougiCell> calcKaku(int currentRow, int currentCol, int rowPlus, int colPlus, Player player, int rowMax, int colMax, List<ShougiCell> list) {
    int newRow = currentRow + rowPlus;
    int newCol = currentCol + colPlus;
    if (   0 <= newRow && newRow < rowMax
        && 0 <= newCol && newCol < colMax)
    {
      list.add(new Ryodo(newRow, newCol, player));
      return calcKaku(newRow, newCol, rowPlus, colPlus, player, rowMax, colMax, list);
    }
    return list;
  }

}
