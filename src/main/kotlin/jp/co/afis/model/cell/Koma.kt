package jp.co.afis.model.cell

import javafx.geometry.Pos
import jp.co.afis.bean.Position
import jp.co.afis.model.Board

enum class CellStatus(val text: String) {
    Empty(""), Fu("歩"), Kin("金"), Gin("銀"), Kyosha("香"), Keima("桂"), Hisha("飛"), Kaku("角"), Ou("王"), Ryodo(""), Jinchi("")
}

class CellStatuses(var player1: CellStatus = CellStatus.Empty, var player2: CellStatus = CellStatus.Empty)

open class Cell(open val pos: Position, val status: CellStatuses = CellStatuses()) {
    fun setPlayer1CellStatus(status: CellStatus) {
        this.status.player1 = status
    }

    fun setPlayer2CellStatus(status: CellStatus) {
        this.status.player2 = status
    }
}

/**
 * calcArea は陣地の行数を計算します。
 * @param n 将棋盤の行数
 * @return 陣地の行数
 */
internal fun calcArea(n: Int) = Math.round(n.toDouble() / 3).toInt()

/**
 * createInitCells は将棋盤のセルの二次元配列を生成します。
 * @param row 生成する将棋盤の行数
 * @param col 生成する将棋盤の列数
 * @return 将棋盤セル二次元配列
 */
internal fun createInitCells(row: Int, col: Int): Array<Array<Cell>> {
    var arr: Array<Array<Cell>> = emptyArray()
    val jinchiCount = calcArea(row)
    (0 until jinchiCount).forEach { r ->
        arr += arrayOf(Array(col, { c -> Cell(pos = Position(row = r, col = c), status = CellStatuses(player2 = CellStatus.Jinchi)) }))
    }
    (jinchiCount until row - jinchiCount).forEach { r ->
        arr += arrayOf(Array(col, { c -> Cell(pos = Position(row = r, col = c), status = CellStatuses()) }))
    }
    (row - jinchiCount until row).forEach { r ->
        arr += arrayOf(Array(col, { c -> Cell(pos = Position(row = r, col = c), status = CellStatuses(player1 = CellStatus.Jinchi)) }))
    }
    return arr
}

class Koma(val text: String, val attack: (Board, Position) -> Unit)

enum class KomaType {
    FU, KIN, GIN, KEIMA, KYOSHA, HISHA, KAKU, OU
}
