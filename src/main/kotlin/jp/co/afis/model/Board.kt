package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.Cell
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.createInitCells
import jp.co.afis.model.player.Player

internal fun calcPlayerScore(player: (Cell) -> CellStatus, cells: Array<Array<Cell>>): Int {
    var score = 0
    cells.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, cell ->
            when (player(cell)) {
                CellStatus.Fu
                    , CellStatus.Kin
                    , CellStatus.Gin
                    , CellStatus.Kyosha
                    , CellStatus.Keima
                    , CellStatus.Hisha
                    , CellStatus.Kaku
                    , CellStatus.Ou
                    , CellStatus.Ryodo -> score++

            }
        }
    }
    return score
}

class Board(row: Int, col: Int, val cells: Array<Array<Cell>> = createInitCells(row, col)) {
    constructor(row: Int, col: Int) : this(row = row, col = col, cells = createInitCells(row, col))

    /**
     * getCell は指定位置のセルを取得する。
     * @param pos 指定位置
     * @return 指定位置のセル
     */
    fun getCell(pos: Position): Cell {
        return cells[pos.row][pos.col]
    }

    fun getCellText(cell: Cell): String {
        val p1s = cell.status.player1
        val p2s = cell.status.player2
        return if (p1s != CellStatus.Empty)
            when (p1s) {
                CellStatus.Empty -> "E"
                CellStatus.Ryodo -> "R"
                CellStatus.Jinchi -> "J"
                else -> p1s.text
            }
        else
            when (p2s) {
                CellStatus.Empty -> "E"
                CellStatus.Ryodo -> "R"
                CellStatus.Jinchi -> "J"
                else -> p2s.text
            }
    }


    /**
     * print は将棋盤文字列を返却しまｓ．
     */
    fun createBoardString(): String {
        val s = cells.joinToString("\n") {
            it.joinToString(separator = "|", prefix = "|", postfix = "|") { getCellText(it) }
        }
        return s
    }


    fun setPlayer1CellStatus(pos: Position, status: CellStatus) {
        cells[pos.row][pos.col].setPlayer1CellStatus(status)
    }

    fun setPlayer2CellStatus(pos: Position, status: CellStatus) {
        cells[pos.row][pos.col].setPlayer2CellStatus(status)
    }

    fun calcPlayer1Score(): Int {
        return calcPlayerScore({ cell -> cell.status.player1 }, cells)
    }

    fun calcPlayer2Score(): Int {
        return calcPlayerScore({ cell -> cell.status.player2 }, cells)
    }

}

