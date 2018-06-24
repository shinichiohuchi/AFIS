package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.Cell
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.Koma
import jp.co.afis.model.cell.createInitCells

class Board(row: Int, col: Int, val cells: Array<Array<Cell>> = createInitCells(row, col)) {
    fun put(koma: Koma, pos: Position) {
    }

    fun setPlayer1CellStatus(pos: Position, status: CellStatus) {
        cells[pos.row][pos.col].setPlayer1CellStatus(status)
    }

    fun setPlayer2CellStatus(pos: Position, status: CellStatus) {
        cells[pos.row][pos.col].setPlayer2CellStatus(status)
    }
}

