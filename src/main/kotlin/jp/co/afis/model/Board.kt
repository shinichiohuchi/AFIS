package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.Cell
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.Koma
import jp.co.afis.model.cell.createInitCells

class Board(row: Int, col: Int, val cells: Array<Array<Cell>> = createInitCells(row, col)) {
    fun put(koma: Koma, pos: Position) {
    }

    fun createBoardString() : String {
        val s = cells.map { rows ->
            rows.map {
                val p1s = it.status.player1
                val p2s = it.status.player2
                if(p1s == CellStatus.Koma|| p2s == CellStatus.Koma) {
                    if (p1s ==CellStatus.Koma)
                        p1s.toString()
                    else
                        p2s.toString()
                } else {
                    if (it.status.player1 != CellStatus.Empty)
                        it.status.player1.toString()
                    else
                        it.status.player2.toString()
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

