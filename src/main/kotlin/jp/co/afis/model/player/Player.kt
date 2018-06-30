package jp.co.afis.model.player

import jp.co.afis.bean.Position
import jp.co.afis.model.Board
import jp.co.afis.model.cell.Cell
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.KomaType

internal fun isKoma(cell: Cell): Boolean {
    return isPlayer1Koma(cell) || isPlayer2Koma(cell)
}

internal fun isPlayer1Koma(cell: Cell): Boolean {
    val status = cell.status.player1
    return status == CellStatus.Fu ||
            status == CellStatus.Kin ||
            status == CellStatus.Gin ||
            status == CellStatus.Kyosha ||
            status == CellStatus.Keima ||
            status == CellStatus.Hisha ||
            status == CellStatus.Kaku ||
            status == CellStatus.Ou
}

internal fun isPlayer2Koma(cell: Cell): Boolean {
    val status = cell.status.player2
    return status == CellStatus.Fu ||
            status == CellStatus.Kin ||
            status == CellStatus.Gin ||
            status == CellStatus.Kyosha ||
            status == CellStatus.Keima ||
            status == CellStatus.Hisha ||
            status == CellStatus.Kaku ||
            status == CellStatus.Ou
}

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

/**
 * ゲームの勝者
 */
enum class Winner {
    /** 勝者なし */
    NONE,
    /** 先手 */
    PLAYER1,
    /** 後手 */
    PLAYER2,
    /** 同点 */
    SAME_SCORE
}

abstract class Player(
        name: String? = null
        , var fuCount: Int = 9
        , var kinCount: Int = 2
        , var ginCount: Int = 2
        , var keimaCount: Int = 2
        , var kyoshaCount: Int = 2
        , var hishaCount: Int = 1
        , var kakuCount: Int = 1
        , var ouCount: Int = 1
        , var currentAttackType: KomaType = KomaType.FU) {
    abstract fun attack(board: Board, pos: Position)
    abstract fun attackWithFu(board: Board, pos: Position)
    abstract fun attackWithKin(board: Board, pos: Position)
    abstract fun attackWithGin(board: Board, pos: Position)
    abstract fun attackWithKeima(board: Board, pos: Position)
    abstract fun attackWithKyosha(board: Board, pos: Position)
    abstract fun attackWithHisha(board: Board, pos: Position)
    abstract fun attackWithKaku(board: Board, pos: Position)
    abstract fun attackWithOu(board: Board, pos: Position)
    abstract fun getCountOfKomas(): Int
}

class Player1(name: String? = null
              , fuCount: Int = 9
              , kinCount: Int = 2
              , ginCount: Int = 2
              , keimaCount: Int = 2
              , kyoshaCount: Int = 2
              , hishaCount: Int = 1
              , kakuCount: Int = 1
              , ouCount: Int = 1
              , currentAttackType: KomaType = KomaType.FU
) : Player(
        name = name
        , fuCount = fuCount
        , kinCount = kinCount
        , ginCount = ginCount
        , keimaCount = keimaCount
        , kyoshaCount = kyoshaCount
        , hishaCount = hishaCount
        , kakuCount = kakuCount
        , ouCount = ouCount
        , currentAttackType = currentAttackType) {
    override fun attack(board: Board, pos: Position) {
        when (currentAttackType) {
            KomaType.FU -> {
                attackWithFu(board, pos)
                fuCount--
            }
            KomaType.KIN -> {
                attackWithKin(board, pos)
                kinCount--
            }
            KomaType.GIN -> {
                attackWithGin(board, pos)
                ginCount--
            }
            KomaType.KEIMA -> {
                attackWithKeima(board, pos)
                keimaCount--
            }
            KomaType.KYOSHA -> {
                attackWithKyosha(board, pos)
                kyoshaCount--
            }
            KomaType.HISHA -> {
                attackWithHisha(board, pos)
                hishaCount--
            }
            KomaType.KAKU -> {
                attackWithKaku(board, pos)
                kakuCount--
            }
            KomaType.OU -> {
                attackWithOu(board, pos)
                ouCount--
            }
        }
    }

    override fun getCountOfKomas(): Int {
        return when (currentAttackType) {
            KomaType.FU -> fuCount
            KomaType.KIN -> kinCount
            KomaType.GIN -> ginCount
            KomaType.KEIMA -> keimaCount
            KomaType.KYOSHA -> kyoshaCount
            KomaType.HISHA -> hishaCount
            KomaType.KAKU -> kakuCount
            KomaType.OU -> ouCount
        }
    }

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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer2CellStatus(it, CellStatus.Empty)
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer2CellStatus(it, CellStatus.Empty)
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer2CellStatus(it, CellStatus.Empty)
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
                if (p.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(p))) {
                    board.setPlayer1CellStatus(p, CellStatus.Ryodo)
                    board.setPlayer2CellStatus(p, CellStatus.Empty)
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer2CellStatus(it, CellStatus.Empty)
                }
            }
            Position(pos.row - 2, pos.col + 1).let {
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer2CellStatus(it, CellStatus.Empty)
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer2CellStatus(it, CellStatus.Empty)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Kaku)
        }
    }

    override fun attackWithOu(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            calcRyodo3x3(pos, listOf({ _, _ -> false })).forEach {
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer1CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer2CellStatus(it, CellStatus.Empty)
                }
            }
            board.setPlayer1CellStatus(pos, CellStatus.Ou)
        }
    }

}

class Player2(
        name: String? = null
        , fuCount: Int = 9
        , kinCount: Int = 2
        , ginCount: Int = 2
        , keimaCount: Int = 2
        , kyoshaCount: Int = 2
        , hishaCount: Int = 1
        , kakuCount: Int = 1
        , ouCount: Int = 1
        , currentAttackType: KomaType = KomaType.FU
) : Player(
        name = name
        , fuCount = fuCount
        , kinCount = kinCount
        , ginCount = ginCount
        , keimaCount = keimaCount
        , kyoshaCount = kyoshaCount
        , hishaCount = hishaCount
        , kakuCount = kakuCount
        , ouCount = ouCount
        , currentAttackType = currentAttackType) {
    override fun attack(board: Board, pos: Position) {
        when (currentAttackType) {
            KomaType.FU -> attackWithFu(board, pos)
            KomaType.KIN -> attackWithKin(board, pos)
            KomaType.GIN -> attackWithGin(board, pos)
            KomaType.KEIMA -> attackWithKeima(board, pos)
            KomaType.KYOSHA -> attackWithKyosha(board, pos)
            KomaType.HISHA -> attackWithHisha(board, pos)
            KomaType.KAKU -> attackWithKaku(board, pos)
            KomaType.OU -> attackWithOu(board, pos)
        }
    }

    override fun getCountOfKomas(): Int {
        return when (currentAttackType) {
            KomaType.FU -> fuCount
            KomaType.KIN -> kinCount
            KomaType.GIN -> ginCount
            KomaType.KEIMA -> keimaCount
            KomaType.KYOSHA -> kyoshaCount
            KomaType.HISHA -> hishaCount
            KomaType.KAKU -> kakuCount
            KomaType.OU -> ouCount
        }
    }

    override fun attackWithFu(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            Position(pos.row + 1, pos.col).let {
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer1CellStatus(it, CellStatus.Empty)
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer1CellStatus(it, CellStatus.Empty)
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer1CellStatus(it, CellStatus.Empty)
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
                if (p.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(p))) {
                    board.setPlayer2CellStatus(p, CellStatus.Ryodo)
                    board.setPlayer1CellStatus(p, CellStatus.Empty)
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer1CellStatus(it, CellStatus.Empty)
                }
            }
            Position(pos.row + 2, pos.col + 1).let {
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer1CellStatus(it, CellStatus.Empty)
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
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer1CellStatus(it, CellStatus.Empty)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Kaku)
        }
    }

    override fun attackWithOu(board: Board, pos: Position) {
        val rowMax = board.cells.size
        val colMax = board.cells[0].size

        if (pos.isWithinBoardRange(rowMax, colMax)) {
            calcRyodo3x3(pos, listOf({ _, _ -> false })).forEach {
                if (it.isWithinBoardRange(rowMax, colMax) && !isKoma(board.getCell(it))) {
                    board.setPlayer2CellStatus(it, CellStatus.Ryodo)
                    board.setPlayer1CellStatus(it, CellStatus.Empty)
                }
            }
            board.setPlayer2CellStatus(pos, CellStatus.Ou)
        }
    }
}

