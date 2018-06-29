package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.Cell
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.Koma
import jp.co.afis.model.cell.createInitCells

class Board(row: Int, col: Int, val cells: Array<Array<Cell>> = createInitCells(row, col)) {
    /**
     * PutResult はputメソッドの実行結果です。
     */
    enum class PutResult {
        /** 正常に配置できた */
        SUCCESS,
        /** すでに駒が存在する */
        KOMA_EXISTS,
        /** すでに領土が存在する */
        RYODO_EXISTS,
        /** そもそも自分の領域ではない */
        NOT_OWN_AREA,
    }
    fun put(koma: Koma, pos: Position) {
    }

    /**
     * print は将棋盤文字列を返却しまｓ．
     */
    fun createBoardString() : String {
        val s = cells.joinToString("\n") {
            it.joinToString(separator = "|", prefix = "|", postfix = "|") {
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
            }
        }
        return s
    }


    fun setPlayer1CellStatus(pos: Position, status: CellStatus) {
        cells[pos.row][pos.col].setPlayer1CellStatus(status)
    }

    fun setPlayer2CellStatus(pos: Position, status: CellStatus) {
        cells[pos.row][pos.col].setPlayer2CellStatus(status)
    }
}

