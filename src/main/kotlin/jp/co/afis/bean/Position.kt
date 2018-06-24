package jp.co.afis.bean

/**
 * Position は将棋盤における位置を保持します。
 *
 * @constructor
 * 位置を生成します。
 *
 * @param row 行番号
 * @param col 列番号
 */
data class Position(val row: Int, val col: Int) {
    /**
     * isWithinBoardRange は指定の行の範囲未満に位置が存在するかを返します。
     * @param rowMax 行の上限
     * @param colMax 列の上限
     * @return 範囲内であるか否か
     */
    fun isWithinBoardRange(rowMax: Int, colMax: Int): Boolean {
        return row in 0..(rowMax - 1) && col in 0..(colMax - 1)
    }
}