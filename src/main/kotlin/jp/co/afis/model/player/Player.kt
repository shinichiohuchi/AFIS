package jp.co.afis.model.player

import javafx.geometry.Pos
import jp.co.afis.bean.Position
import jp.co.afis.model.Board
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.Koma
import sun.jvm.hotspot.runtime.posix.POSIXSignals

/**
 * calcRyodo3x3 は起点位置から3x3マスの領土の位置リストを返却します。
 * @param pos 起点位置
 * @param excludes 除外フィルタ設定。一つでも条件に一致したものは除外する
 * @return 位置リスト
 */
internal fun calcRyodo3x3(pos: Position, excludes: List<(Position, Position) -> Boolean>): List<Position> {
    val row = pos.row
    val col = pos.col
    var list = mutableListOf<Position>()
    (row - 1..row + 1).map { r ->
        (col - 1..col + 1)
                .map { Position(r, it) }
                .filter { p -> excludes.all { !it(pos, p) } }
                .forEach {
                    list.add(it)
                }
    }
    return list
}

/**
 * calcSlanting は斜め方向のポジションリストを返します。
 * @param poses 位置リスト
 * @param row posesに追加するポジションの行番号
 * @param col psesに追加するポジjションの列番号
 * @param rowMax 将棋盤の行の最大数
 * @param colMax 将棋盤の列の最大数
 * @param rowf 行番号の計算関数
 * @param colf 列番号の計算関数
 * @return 斜め方向の位置リスト
 */
internal tailrec fun calcSlanting(poses: List<Position> = listOf(), row: Int, col: Int, rowMax: Int, colMax: Int, rowf: (Int) -> Int, colf: (Int) -> Int): List<Position> {
    if (row < 0 || rowMax <= row || col < 0 || colMax <= col) return poses
    val p = Position(row, col)
    return calcSlanting(poses = (poses + p), row = rowf(row), col = colf(col), rowMax = rowMax, colMax = colMax, rowf = rowf, colf = colf)
}

/**
 * calcRyodoOfHisha は飛車の攻撃範囲の位置を返します。
 * @param pos 起点の位置
 * @param rowMax 将棋盤の行の最大数
 * @param colMax 将棋盤の列の最大数
 * @return 飛車の攻撃範囲位置リスト
 */
internal fun calcRyodoOfHisha(pos: Position, rowMax: Int, colMax: Int): List<Position> {
    return listOf(
            (0 until rowMax).map { Position(it, pos.col) },
            (0 until colMax).map { Position(pos.row, it) }
    ).flatMap { it }
}

/**
 * calcRyodoOfKaku は角の攻撃範囲の位置を返します。
 * @param pos 起点の位置
 * @param rowMax 将棋盤の行の最大数
 * @param colMax 将棋盤の列の最大数
 * @return 角の攻撃範囲位置リスト
 */
internal fun calcRyodoOfKaku(pos: Position, rowMax: Int, colMax: Int): List<Position> {
    return listOf(
            calcSlanting(row = pos.row, col = pos.col, rowMax = rowMax, colMax = colMax, rowf = { it + 1 }, colf = { it + 1 }),
            calcSlanting(row = pos.row, col = pos.col, rowMax = rowMax, colMax = colMax, rowf = { it + 1 }, colf = { it - 1 }),
            calcSlanting(row = pos.row, col = pos.col, rowMax = rowMax, colMax = colMax, rowf = { it - 1 }, colf = { it + 1 }),
            calcSlanting(row = pos.row, col = pos.col, rowMax = rowMax, colMax = colMax, rowf = { it - 1 }, colf = { it - 1 })
    ).flatMap { it }
}

class Players
abstract class Player(name: String? = null, fuCount: Int) {
    abstract fun attackWithFu(board: Board, pos: Position)
    abstract fun attackWithKin(board: Board, pos: Position)
    abstract fun attackWithGin(board: Board, pos: Position)
    abstract fun attackWithKeima(board: Board, pos: Position)
    abstract fun attackWithKyosha(board: Board, pos: Position)
    abstract fun attackWithHisha(board: Board, pos: Position)
    abstract fun attackWithKaku(board: Board, pos: Position)
    abstract fun attackWithOu(board: Board, pos: Position)
}

class Player1(name: String, fuCount: Int) : Player(name = name, fuCount = fuCount) {
    /** 歩 */
    var fus = mutableListOf(Array(fuCount, { Koma("歩", ::attackWithFu) }))

    /** 金 */
    var kins = mutableListOf(Array(fuCount, { Koma("金", ::attackWithKin) }))

    /** 銀 */
    var gins = mutableListOf(Array(fuCount, { Koma("銀", ::attackWithGin) }))

    /** 香車 */
    var kyoshas = mutableListOf(Array(fuCount, { Koma("香", ::attackWithKyosha) }))

    /** 桂馬 */
    var keimas = mutableListOf(Array(fuCount, { Koma("桂", ::attackWithKeima) }))

    /** 飛車 */
    var hishas = mutableListOf(Array(fuCount, { Koma("飛", ::attackWithHisha) }))

    /** 角 */
    var kakus = mutableListOf(Array(fuCount, { Koma("角", ::attackWithKaku) }))

    /** 王 */
    var ous = mutableListOf(Array(fuCount, { Koma("王", ::attackWithOu) }))

    /**
     * attackWithFu は歩の攻撃範囲の領土を返す
     * @param board 将棋盤
     * @param pos 駒の配置位置
     */
    override fun attackWithFu(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            Position(pos.row - 1, pos.col).let {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Fu)
        }
    }

    override fun attackWithKin(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            calcRyodo3x3(pos, listOf(
                    { bp, tp -> bp.row + 1 == tp.row && bp.col - 1 == tp.col },
                    { bp, tp -> bp.row + 1 == tp.row && bp.col + 1 == tp.col })
            ).forEach {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Kin)
        }
    }

    override fun attackWithGin(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            calcRyodo3x3(pos, listOf(
                    { bp, tp -> bp.row == tp.row && bp.col - 1 == tp.col },
                    { bp, tp -> bp.row == tp.row && bp.col + 1 == tp.col },
                    { bp, tp -> bp.row + 1 == tp.row && bp.col == tp.col })
            ).forEach {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Gin)
        }
    }

    override fun attackWithKyosha(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            (0..pos.row).forEach {
                val p = Position(it, pos.col)
                if (p.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer1CellStatus(p, CellStatus.Ryodo)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Kyosha)
        }
    }

    override fun attackWithKeima(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            Position(pos.row - 2, pos.col - 1).let {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                }
            }
            Position(pos.row - 2, pos.col + 1).let {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Keima)
        }
    }

    override fun attackWithHisha(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            val poses = calcRyodoOfHisha(pos, board.cells.size, board.cells[0].size)
            poses.forEach {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Hisha)
        }
    }

    override fun attackWithKaku(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            val poses = calcRyodoOfKaku(pos, board.cells.size, board.cells[0].size)
            poses.forEach {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Kaku)
        }
    }

    override fun attackWithOu(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)){
            calcRyodo3x3(pos, listOf({ _, _ -> false })).forEach {
                if (it.isWithinBoardRange(rowMax, colMax)){
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Ou)
        }
    }

}

class Player2(name: String, fuCount: Int) : Player(name = name, fuCount = fuCount) {
    /** 歩 */
    var fus = mutableListOf(Array(fuCount, { Koma("歩", ::attackWithFu) }))

    /** 金 */
    var kins = mutableListOf(Array(fuCount, { Koma("金", ::attackWithKin) }))

    /** 銀 */
    var gins = mutableListOf(Array(fuCount, { Koma("銀", ::attackWithGin) }))

    /** 香車 */
    var kyoshas = mutableListOf(Array(fuCount, { Koma("香", ::attackWithKyosha) }))

    /** 桂馬 */
    var keimas = mutableListOf(Array(fuCount, { Koma("桂", ::attackWithKeima) }))

    /** 飛車 */
    var hishas = mutableListOf(Array(fuCount, { Koma("飛", ::attackWithHisha) }))

    /** 角 */
    var kakus = mutableListOf(Array(fuCount, { Koma("角", ::attackWithKaku) }))

    /** 王 */
    var ous = mutableListOf(Array(fuCount, { Koma("王", ::attackWithOu) }))

    override fun attackWithFu(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            Position(pos.row + 1, pos.col).let {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Fu)
        }
    }

    override fun attackWithKin(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            calcRyodo3x3(pos, listOf(
                    { bp, tp -> bp.row - 1 == tp.row && bp.col - 1 == tp.col },
                    { bp, tp -> bp.row - 1 == tp.row && bp.col + 1 == tp.col })
            ).forEach {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Kin)
        }
    }

    override fun attackWithGin(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            calcRyodo3x3(pos, listOf(
                    { bp, tp -> bp.row == tp.row && bp.col - 1 == tp.col },
                    { bp, tp -> bp.row == tp.row && bp.col + 1 == tp.col },
                    { bp, tp -> bp.row - 1 == tp.row && bp.col == tp.col })
            ).forEach {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Gin)
        }
    }

    override fun attackWithKyosha(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            (pos.row until board.cells.size).forEach {
                val p = Position(it, pos.col)
                if (p.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer2CellStatus(p, CellStatus.Ryodo)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Kyosha)
        }

    }

    override fun attackWithKeima(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            Position(pos.row + 2, pos.col - 1).let {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                }
            }
            Position(pos.row + 2, pos.col + 1).let {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Keima)
        }
    }

    override fun attackWithHisha(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            val poses = calcRyodoOfHisha(pos, board.cells.size, board.cells[0].size)
            poses.forEach {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Hisha)
        }
    }

    override fun attackWithKaku(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            val poses = calcRyodoOfKaku(pos, board.cells.size, board.cells[0].size)
            poses.forEach {
                if (it.isWithinBoardRange(rowMax, colMax)) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Kaku)
        }
    }

    override fun attackWithOu(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)){
            calcRyodo3x3(pos, listOf({ _, _ -> false })).forEach {
                if (it.isWithinBoardRange(rowMax, colMax)){
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Ou)
        }
    }
}

