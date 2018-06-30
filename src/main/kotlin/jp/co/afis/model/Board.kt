package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.Cell
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.createInitCells
import jp.co.afis.model.player.Player
import jp.co.afis.model.player.Player1

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

internal fun calcRyochiCells(player: (Cell) -> CellStatus, cells: Array<Array<Cell>>): List<Position> {
    var poses = mutableListOf<Position>()
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
                    , CellStatus.Ryodo -> {
                    poses.add(Position(rowIndex, colIndex - 1))
                    poses.add(Position(rowIndex, colIndex + 1))
                    poses.add(Position(rowIndex - 1, colIndex))
                    poses.add(Position(rowIndex + 1, colIndex))
                }
            }
        }
    }
    return poses.distinct()
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

    /**
     * calcPlayer1Score は先手の得点を計算して返します。
     * @return 先手の得点
     */
    fun calcPlayer1Score(): Int {
        return calcPlayerScore({ cell -> cell.status.player1 }, cells)
    }

    /**
     * calcPlayer1Score は後手の得点を計算して返します。
     * @return 後手の得点
     */
    fun calcPlayer2Score(): Int {
        return calcPlayerScore({ cell -> cell.status.player2 }, cells)
    }

    /**
     * 領地をセットします。
     */
    fun updateRyochi() {
        val rowMax = cells.size
        val colMax = cells[0].size
        calcRyochiCells({ cell -> cell.status.player1 }, cells).forEach {
            if (it.isWithinBoardRange(rowMax, colMax)) {
                val cell = cells[it.row][it.col]
                val status = cell.status.player1
                when (status) {
                    CellStatus.Fu
                        , CellStatus.Kin
                        , CellStatus.Gin
                        , CellStatus.Keima
                        , CellStatus.Kyosha
                        , CellStatus.Hisha
                        , CellStatus.Kaku
                        , CellStatus.Ou
                        , CellStatus.Ryodo -> Unit
                    CellStatus.Ryochi, CellStatus.Empty -> {
                        when (cell.status.player2) {
                            CellStatus.Fu
                                , CellStatus.Kin
                                , CellStatus.Gin
                                , CellStatus.Keima
                                , CellStatus.Kyosha
                                , CellStatus.Hisha
                                , CellStatus.Kaku
                                , CellStatus.Ou
                                , CellStatus.Ryodo -> Unit
                            CellStatus.Ryochi, CellStatus.Empty -> {
                                cell.status.player1 = CellStatus.Ryochi
                            }
                        }
                    }
                }
            }
        }

        calcRyochiCells({ cell -> cell.status.player2 }, cells).forEach {
            if (it.isWithinBoardRange(rowMax, colMax)) {
                val cell = cells[it.row][it.col]
                val status = cell.status.player2
                when (status) {
                    CellStatus.Fu
                        , CellStatus.Kin
                        , CellStatus.Gin
                        , CellStatus.Keima
                        , CellStatus.Kyosha
                        , CellStatus.Hisha
                        , CellStatus.Kaku
                        , CellStatus.Ou
                        , CellStatus.Ryodo -> Unit
                    CellStatus.Ryochi, CellStatus.Empty -> {
                        when (cell.status.player1) {
                            CellStatus.Fu
                                , CellStatus.Kin
                                , CellStatus.Gin
                                , CellStatus.Keima
                                , CellStatus.Kyosha
                                , CellStatus.Hisha
                                , CellStatus.Kaku
                                , CellStatus.Ou
                                , CellStatus.Ryodo -> Unit
                            CellStatus.Ryochi, CellStatus.Empty -> {
                                cell.status.player2 = CellStatus.Ryochi
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * clearJinchi は陣地を消す
     */
    fun clearJinchi(player: Player) {
        if (player is Player1) {
            cells.forEach {
                it.forEach {
                    val cell = it
                    if (cell.status.player1 == CellStatus.Jinchi) {
                        cell.status.player1 = CellStatus.Empty
                    }
                }
            }
        } else {
            cells.forEach {
                it.forEach {
                    val cell = it
                    if (cell.status.player2 == CellStatus.Jinchi) {
                        cell.status.player2 = CellStatus.Empty
                    }
                }
            }
        }
    }

}

