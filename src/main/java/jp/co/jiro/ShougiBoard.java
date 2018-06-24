package jp.co.jiro;

import javafx.application.Platform;
import javafx.scene.control.ToggleGroup;
import jp.co.jiro.cell.*;
import jp.co.jiro.player.*;

import java.util.*;

public class ShougiBoard {
    private final int row;
    private final int col;
    private final ShougiCell[][] cells;

    /**
     * 9x9マスの将棋盤の生成。
     */
    ShougiBoard(ToggleGroup group, ToggleGroup group2) {
        this(9, 9, group, group2);
    }

    /**
     * 将棋盤の生成。
     *
     * @param row 行数。デフォルトは9
     * @param col 列数。デフォルトは9
     */
    ShougiBoard(int row, int col, ToggleGroup group, ToggleGroup group2) {
        this.row = row;
        this.col = col;
        this.cells = new ShougiCell[row][row];

        // 空セルで初期化
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j] = new EmptyCell(i, j, new NonPlayer());
            }
        }

        // 陣地をセット。
        // Player1側
        int jinchiRow = calcArea();
        for (int i = 0; i < jinchiRow; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j] = new Jinchi(i, j, new Player1(row, col, group));
            }
        }

        // 陣地セット
        // Player2側
        for (int i = row - jinchiRow; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j] = new Jinchi(i, j, new Player2(row, col, group2));
            }
        }

    }

    /**
     * セルをセットする。
     *
     * @param cell セル
     */
    void setCell(ShougiCell cell) {
        if (!isWithinRange(cell)) {
            return;
        }

        // 駒をセット
        cells[cell.getRow()][cell.getCol()] = cell;

        // 駒の攻撃範囲(領土)をセット
        if (cell instanceof ShougiCell) {
            if (cell instanceof Attack) {
                Attack koma = (Attack) cell;
                // 攻撃範囲
                List<ShougiCell> ryodos = koma.calcRyodo();
                for (ShougiCell ryodo : ryodos) {
                    if (isWithinRange(ryodo)) {
                        int row = ryodo.getRow();
                        int col = ryodo.getCol();
                        ShougiCell srcCell = cells[row][col];
                        if (!(srcCell instanceof Attack)) {
                            cells[row][col] = ryodo;
                        }
                    }
                }
            }
        }

        updateClickable();
    }

    /**
     * 選択可能状態を初期化する。
     */
    private void updateClickable() {
        // クリック可能フラグを初期化
        for (ShougiCell[] cs : cells) {
            for (ShougiCell cell : cs) {
                cell.setClickableBitFlag(0);
            }
        }

        // クリック可能フラグをセット
        for (ShougiCell[] cs : cells) {
            for (ShougiCell cell : cs) {
                if (cell instanceof Attack || cell instanceof Ryodo) {
                    setClickable(cell, 0, 0);
                    setClickable(cell, 0, 1);
                    setClickable(cell, 0, -1);
                    setClickable(cell, 1, 0);
                    setClickable(cell, -1, 0);
                }
            }
        }
    }

    /**
     * 駒、あるいは領土と隣接していて、クリック可能なセルであることを設定する。
     */
    private void setClickable(ShougiCell cell, int rowPlus, int colPlus) {
        int row = cell.getRow();
        int col = cell.getCol();

        if (isWithinRange(row + rowPlus, col + colPlus)) {
            ShougiCell targetCell = cells[row + rowPlus][col + colPlus];

            Player player = cell.getPlayer();
            int playerFlag = player.getPlayerBitFlag();

            int targetFlag = targetCell.getClickableBitFlag();
            targetFlag |= playerFlag;
            targetCell.setClickableBitFlag(targetFlag);
        }
    }

    /**
     * 将棋盤を出力する。
     */
    void display() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                ShougiCell cell = cells[i][j];

                if (cell instanceof Jinchi) {
                    Player p = cell.getPlayer();
                    if (p instanceof Player1) {
                        System.out.print("V ");
                    } else if (p instanceof Player2) {
                        System.out.print("A ");
                    }
                } else if (cell instanceof Ryodo) {
                    System.out.print("- ");
                } else if (cell instanceof Attack) {
                    System.out.print(cell.getName());
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    /**
     * 将棋盤の行数から陣地数を計算する。
     */
    private int calcArea() {
        return (int) Math.round(row / 3);
    }

    /**
     * 行と列の値が将棋盤の範囲内に存在するかを返す。
     */
    private boolean isWithinRange(int row, int col) {
        return
                0 <= row && row < this.row
                        && 0 <= col && col < this.col;
    }

    /**
     * セルが将棋盤の範囲内に存在するかを返す。
     */
    private boolean isWithinRange(ShougiCell cell) {
        return isWithinRange(cell.getRow(), cell.getCol());
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // GETTER
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    ShougiCell[][] getCells() {
        return cells;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

}
