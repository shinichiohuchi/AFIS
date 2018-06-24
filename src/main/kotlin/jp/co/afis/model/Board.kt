package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.Cell
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.Koma
import jp.co.afis.model.cell.createInitCells

class Board(row: Int, col: Int, val cells: Array<Array<Cell>> = createInitCells(row, col)) {
    fun put(koma: Koma, pos: Position) {
    }

    /**
     * print は将棋盤文字列を返却しまｓ．
     */
    fun createBoardString() : String {
        val s = cells.map { rows ->
            rows.map {
                val p1s = it.status.player1
                val p2s = it.status.player2
                if (p1s != CellStatus.Empty)
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
            }.joinToString(separator = "|", prefix = "|", postfix = "|")
        }.joinToString("\n")
        return s
    }


    fun setPlayer1CellStatus(pos: Position, status: CellStatus) {
        cells[pos.row][pos.col].setPlayer1CellStatus(status)
    }

    fun setPlayer2CellStatus(pos: Position, status: CellStatus) {
        cells[pos.row][pos.col].setPlayer2CellStatus(status)
    }
}

