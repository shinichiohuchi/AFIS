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
data class Position(val row: Int, val col: Int)