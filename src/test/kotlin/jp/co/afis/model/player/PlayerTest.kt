package jp.co.afis.model.player

import com.sun.xml.internal.ws.api.model.MEP
import jp.co.afis.bean.Position
import jp.co.afis.model.Board
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.CellStatus.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

// assertチェック用の関数

private val f: (Board, Int, Int) -> CellStatus = { b, r, c -> b.cells[r][c].status.player1 }
private val f2: (Board, Int, Int) -> CellStatus = { b, r, c -> b.cells[r][c].status.player2 }
private val actVec_3x3: (Board) -> List<CellStatus> =
        { b ->
            listOf<CellStatus>(
                    f(b, 5, 2), f(b, 5, 3), f(b, 5, 4),
                    f(b, 6, 2), f(b, 6, 3), f(b, 6, 4),
                    f(b, 7, 2), f(b, 7, 3), f(b, 7, 4),

                    f2(b, 5, 2), f2(b, 5, 3), f2(b, 5, 4),
                    f2(b, 6, 2), f2(b, 6, 3), f2(b, 6, 4),
                    f2(b, 7, 2), f2(b, 7, 3), f2(b, 7, 4))
        }
private val actVec_3x3_p2: (Board) -> List<CellStatus> =
        { b ->
            listOf<CellStatus>(
                    f(b, 3, 5), f(b, 3, 6), f(b, 3, 7),
                    f(b, 4, 5), f(b, 4, 6), f(b, 4, 7),
                    f(b, 5, 5), f(b, 5, 6), f(b, 5, 7),

                    f2(b, 1, 5), f2(b, 1, 6), f2(b, 1, 7),
                    f2(b, 2, 5), f2(b, 2, 6), f2(b, 2, 7),
                    f2(b, 3, 5), f2(b, 3, 6), f2(b, 3, 7) )
        }
private val actVec_5x5: (Board) -> List<CellStatus> =
        { b ->
            listOf<CellStatus>(
                    f(b, 4, 1), f(b, 4, 2), f(b, 4, 3), f(b, 4, 4), f(b, 4, 5),
                    f(b, 5, 1), f(b, 5, 2), f(b, 5, 3), f(b, 5, 4), f(b, 5, 5),
                    f(b, 6, 1), f(b, 6, 2), f(b, 6, 3), f(b, 6, 4), f(b, 6, 5),
                    f(b, 7, 1), f(b, 7, 2), f(b, 7, 3), f(b, 7, 4), f(b, 7, 5),
                    f(b, 8, 1), f(b, 8, 2), f(b, 8, 3), f(b, 8, 4), f(b, 8, 5),

                    f2(b, 4, 1), f2(b, 4, 2), f2(b, 4, 3), f2(b, 4, 4), f2(b, 4, 5),
                    f2(b, 5, 1), f2(b, 5, 2), f2(b, 5, 3), f2(b, 5, 4), f2(b, 5, 5),
                    f2(b, 6, 1), f2(b, 6, 2), f2(b, 6, 3), f2(b, 6, 4), f2(b, 6, 5),
                    f2(b, 7, 1), f2(b, 7, 2), f2(b, 7, 3), f2(b, 7, 4), f2(b, 7, 5),
                    f2(b, 8, 1), f2(b, 8, 2), f2(b, 8, 3), f2(b, 8, 4), f2(b, 8, 5)
            )

        }
private val actVec_5x5_p2: (Board) -> List<CellStatus> =
        { b ->
            listOf<CellStatus>(
                    f(b, 0, 4), f(b, 0, 5), f(b, 0, 6), f(b, 0, 7), f(b, 0, 8),
                    f(b, 1, 4), f(b, 1, 5), f(b, 1, 6), f(b, 1, 7), f(b, 1, 8),
                    f(b, 2, 4), f(b, 2, 5), f(b, 2, 6), f(b, 2, 7), f(b, 2, 8),
                    f(b, 3, 4), f(b, 3, 5), f(b, 3, 6), f(b, 3, 7), f(b, 3, 8),
                    f(b, 4, 4), f(b, 4, 5), f(b, 4, 6), f(b, 4, 7), f(b, 4, 8),

                    f2(b, 0, 4), f2(b, 0, 5), f2(b, 0, 6), f2(b, 0, 7), f2(b, 0, 8),
                    f2(b, 1, 4), f2(b, 1, 5), f2(b, 1, 6), f2(b, 1, 7), f2(b, 1, 8),
                    f2(b, 2, 4), f2(b, 2, 5), f2(b, 2, 6), f2(b, 2, 7), f2(b, 2, 8),
                    f2(b, 3, 4), f2(b, 3, 5), f2(b, 3, 6), f2(b, 3, 7), f2(b, 3, 8),
                    f2(b, 4, 4), f2(b, 4, 5), f2(b, 4, 6), f2(b, 4, 7), f2(b, 4, 8))

        }

// テストコード

internal class PlayerPackageFunctionTest {
    @Test
    fun testCalcRyodoPosition() {
        calcRyodo3x3(Position(6, 3), listOf({ bp, tp -> true })).let {
            assertEquals(emptyList<Position>(), it)
        }

        calcRyodo3x3(Position(6, 3), listOf({ bp, tp -> bp.row + 1 == tp.row })).let {
            assertEquals(listOf(Position(5, 2), Position(5, 3), Position(5, 4), Position(6, 2), Position(6, 3), Position(6, 4)), it)
        }
    }

    @Test
    fun testCalcSlanting() {
        assertEquals(listOf(
                Position(8, 0),
                Position(7, 1),
                Position(6, 2),
                Position(5, 3),
                Position(4, 4),
                Position(3, 5),
                Position(2, 6),
                Position(1, 7),
                Position(0, 8)
        ).sortedBy { it.row }.sortedBy { it.col },
                calcSlanting(row = 8, col = 0, rowMax = 9, colMax = 9, rowf = { it - 1 }, colf = { it + 1 }).sortedBy { it.row }.sortedBy { it.col }
        )
    }

}

internal class Player1Test {
    @Test
    fun testAttackWithFu() {
        val board = Board(9, 9)
        val p1 = Player1("hoge", 9)
        val pos = Position(6, 3)
        p1.attackWithFu(board, pos)

        assertEquals(listOf(
                Empty, Ryodo, Empty,
                Jinchi, Koma, Jinchi,
                Jinchi, Jinchi, Jinchi,
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty
        ), actVec_3x3(board))
    }

    @Test
    fun testAttackWithKin() {
        val board = Board(9, 9)
        val p1 = Player1("hoge", 9)
        val pos = Position(6, 3)
        p1.attackWithKin(board, pos)

        assertEquals(listOf(
                Ryodo, Ryodo, Ryodo,
                Ryodo, Koma, Ryodo,
                Jinchi, Ryodo, Jinchi,
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty
        ), actVec_3x3(board))
    }

    @Test
    fun testAttackWithGin() {
        val board = Board(9, 9)
        val p1 = Player1("hoge", 9)
        val pos = Position(6, 3)
        p1.attackWithGin(board, pos)

        assertEquals(listOf(
                Ryodo, Ryodo, Ryodo,
                Jinchi, Koma, Jinchi,
                Ryodo, Jinchi, Ryodo,
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty
        ), actVec_3x3(board))
    }

    @Test
    fun testAttackWithKeima() {
        val board = Board(9, 9)
        val p1 = Player1("hoge", 9)
        val pos = Position(6, 3)
        p1.attackWithKeima(board, pos)

        assertEquals(listOf(
                Empty, Ryodo, Empty, Ryodo, Empty,
                Empty, Empty, Empty, Empty, Empty,
                Jinchi, Jinchi, Koma, Jinchi, Jinchi,
                Jinchi, Jinchi, Jinchi, Jinchi, Jinchi,
                Jinchi, Jinchi, Jinchi, Jinchi, Jinchi,
                Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty
        ), actVec_5x5(board))
    }

    @Test
    fun testAttackWithKyosha() {
        val board = Board(9, 9)
        val p1 = Player1("hoge", 9)
        val pos = Position(6, 3)
        p1.attackWithKyosha(board, pos)

        assertEquals(CellStatus.Ryodo, board.cells[0][3].status.player1)
        assertEquals(CellStatus.Ryodo, board.cells[1][3].status.player1)
        assertEquals(CellStatus.Ryodo, board.cells[2][3].status.player1)
        assertEquals(CellStatus.Ryodo, board.cells[3][3].status.player1)
        assertEquals(CellStatus.Ryodo, board.cells[4][3].status.player1)
        assertEquals(CellStatus.Ryodo, board.cells[5][3].status.player1)
        assertEquals(CellStatus.Koma, board.cells[6][3].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[7][3].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[8][3].status.player1)

        assertEquals(CellStatus.Jinchi, board.cells[6][0].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[6][1].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[6][2].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[6][4].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[6][5].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[6][6].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[6][7].status.player1)
        assertEquals(CellStatus.Jinchi, board.cells[6][8].status.player1)

        // ここからPlayer2

        assertEquals(CellStatus.Jinchi, board.cells[0][3].status.player2)
        assertEquals(CellStatus.Jinchi, board.cells[1][3].status.player2)
        assertEquals(CellStatus.Jinchi, board.cells[2][3].status.player2)
        assertEquals(CellStatus.Empty, board.cells[3][3].status.player2)
        assertEquals(CellStatus.Empty, board.cells[4][3].status.player2)
        assertEquals(CellStatus.Empty, board.cells[5][3].status.player2)
        assertEquals(CellStatus.Empty, board.cells[6][3].status.player2)
        assertEquals(CellStatus.Empty, board.cells[7][3].status.player2)
        assertEquals(CellStatus.Empty, board.cells[8][3].status.player2)

        assertEquals(CellStatus.Empty, board.cells[6][0].status.player2)
        assertEquals(CellStatus.Empty, board.cells[6][1].status.player2)
        assertEquals(CellStatus.Empty, board.cells[6][2].status.player2)
        assertEquals(CellStatus.Empty, board.cells[6][4].status.player2)
        assertEquals(CellStatus.Empty, board.cells[6][5].status.player2)
        assertEquals(CellStatus.Empty, board.cells[6][6].status.player2)
        assertEquals(CellStatus.Empty, board.cells[6][7].status.player2)
        assertEquals(CellStatus.Empty, board.cells[6][8].status.player2)
    }

    @Test
    fun testAttackWithHisha() {
        val board = Board(9, 9)
        val p1 = Player1("hoge", 9)
        val pos = Position(6, 3)
        p1.attackWithHisha(board, pos)

        assertEquals(listOf(
                Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, Koma, Ryodo, Ryodo, // 行方向
                Ryodo, Ryodo, Ryodo, Koma, Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, // 列方向
                Jinchi, Jinchi, Jinchi, Empty, Empty, Empty, Empty, Empty, Empty, // 行方向
                Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty // 列方向
        ), listOf(
                f(board, 0, 3), f(board, 1, 3), f(board, 2, 3), f(board, 3, 3), f(board, 4, 3), f(board, 5, 3), f(board, 6, 3), f(board, 7, 3), f(board, 8, 3),
                f(board, 6, 0), f(board, 6, 1), f(board, 6, 2), f(board, 6, 3), f(board, 6, 4), f(board, 6, 5), f(board, 6, 6), f(board, 6, 7), f(board, 6, 8),
                f2(board, 0, 3), f2(board, 1, 3), f2(board, 2, 3), f2(board, 3, 3), f2(board, 4, 3), f2(board, 5, 3), f2(board, 6, 3), f2(board, 7, 3), f2(board, 8, 3),
                f2(board, 6, 0), f2(board, 6, 1), f2(board, 6, 2), f2(board, 6, 3), f2(board, 6, 4), f2(board, 6, 5), f2(board, 6, 6), f2(board, 6, 7), f2(board, 6, 8)
        ))
    }

    @Test
    fun testAttackWithKaku() {
        val board = Board(9, 9)
        val p1 = Player1("hoge", 9)
        val pos = Position(6, 3)
        p1.attackWithKaku(board, pos)

        assertEquals(listOf(
                Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, Koma, Ryodo, Ryodo,
                Ryodo, Ryodo, Ryodo, Koma, Ryodo, Ryodo,
                Jinchi, Jinchi, Empty, Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty, Empty
        ), listOf(
                f(board, 1, 8), f(board, 2, 7), f(board, 3, 6), f(board, 4, 5), f(board, 5, 4), f(board, 6, 3), f(board, 7, 2), f(board, 8, 1),
                f(board, 3, 0), f(board, 4, 1), f(board, 5, 2), f(board, 6, 3), f(board, 7, 4), f(board, 8, 5),
                f2(board, 1, 8), f2(board, 2, 7), f2(board, 3, 6), f2(board, 4, 5), f2(board, 5, 4), f2(board, 6, 3), f2(board, 7, 2), f2(board, 8, 1),
                f2(board, 3, 0), f2(board, 4, 1), f2(board, 5, 2), f2(board, 6, 3), f2(board, 7, 4), f2(board, 8, 5)
        ))
    }

    @Test
    fun testAttackWithOu() {
        val board = Board(9, 9)
        val p1 = Player1("hoge", 9)
        val pos = Position(6, 3)
        p1.attackWithOu(board, pos)

        assertEquals(listOf(
                Ryodo, Ryodo, Ryodo,
                Ryodo, Koma, Ryodo,
                Ryodo, Ryodo, Ryodo,
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty
        ), actVec_3x3(board))
    }
}

internal class Player2Test {
    @Test
    fun testAttackWithFu() {
        val board = Board(9, 9)
        val p2 = Player2("hoge", 9)
        val pos = Position(2, 6)
        p2.attackWithFu(board, pos)

        assertEquals(listOf(
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Jinchi, Jinchi, Jinchi,
                Jinchi, Koma, Jinchi,
                Empty, Ryodo, Empty
        ), actVec_3x3_p2(board))
    }

    @Test
    fun testAttackWithKin() {
        val board = Board(9, 9)
        val p1 = Player2("hoge", 9)
        val pos = Position(2, 6)
        p1.attackWithKin(board, pos)

        assertEquals(listOf(
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Jinchi, Ryodo, Jinchi,
                Ryodo, Koma, Ryodo,
                Ryodo, Ryodo, Ryodo
        ), actVec_3x3_p2(board))
    }

    @Test
    fun testAttackWithGin() {
        val board = Board(9, 9)
        val p1 = Player2("hoge", 9)
        val pos = Position(2, 6)
        p1.attackWithGin(board, pos)

        assertEquals(listOf(
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Ryodo, Jinchi, Ryodo,
                Jinchi, Koma, Jinchi,
                Ryodo, Ryodo, Ryodo
        ), actVec_3x3_p2(board))
    }

    @Test
    fun testAttackWithKeima() {
        val board = Board(9, 9)
        val p1 = Player2("hoge", 9)
        val pos = Position(2, 6)
        p1.attackWithKeima(board, pos)

        assertEquals(listOf(
                Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty,
                Empty, Empty, Empty, Empty, Empty,
                Jinchi, Jinchi, Jinchi, Jinchi, Jinchi,
                Jinchi, Jinchi, Jinchi, Jinchi, Jinchi,
                Jinchi, Jinchi, Koma, Jinchi, Jinchi,
                Empty, Empty, Empty, Empty, Empty,
                Empty, Ryodo, Empty, Ryodo, Empty
        ), actVec_5x5_p2(board))
    }

    @Test
    fun testAttackWithKyosha() {
        val board = Board(9, 9)
        val p1 = Player2("hoge", 9)
        val pos = Position(2, 6)
        p1.attackWithKyosha(board, pos)

        assertEquals(listOf(
                Empty,
                Empty,
                Empty,
                Empty,
                Empty,
                Empty,
                Jinchi,
                Jinchi,
                Jinchi,

                Empty,
                Empty,
                Empty,
                Empty,
                Empty,
                Empty,
                Empty,
                Empty,
                Empty,

                Jinchi,
                Jinchi,
                Koma,
                Ryodo,
                Ryodo,
                Ryodo,
                Ryodo,
                Ryodo,
                Ryodo,

                Jinchi,
                Jinchi,
                Jinchi,
                Jinchi,
                Jinchi,
                Jinchi,
                Koma,
                Jinchi,
                Jinchi
        ), listOf(
                f(board, 0, 6),
                f(board, 1, 6),
                f(board, 2, 6),
                f(board, 3, 6),
                f(board, 4, 6),
                f(board, 5, 6),
                f(board, 6, 6),
                f(board, 7, 6),
                f(board, 8, 6),

                f(board, 2, 0),
                f(board, 2, 1),
                f(board, 2, 2),
                f(board, 2, 3),
                f(board, 2, 4),
                f(board, 2, 5),
                f(board, 2, 6),
                f(board, 2, 7),
                f(board, 2, 8),

                f2(board, 0, 6),
                f2(board, 1, 6),
                f2(board, 2, 6),
                f2(board, 3, 6),
                f2(board, 4, 6),
                f2(board, 5, 6),
                f2(board, 6, 6),
                f2(board, 7, 6),
                f2(board, 8, 6),

                f2(board, 2, 0),
                f2(board, 2, 1),
                f2(board, 2, 2),
                f2(board, 2, 3),
                f2(board, 2, 4),
                f2(board, 2, 5),
                f2(board, 2, 6),
                f2(board, 2, 7),
                f2(board, 2, 8)
                ))
    }

    @Test
    fun testAttackWithHisha() {
        val board = Board(9, 9)
        val p1 = Player2("hoge", 9)
        val pos = Position(2, 6)
        p1.attackWithHisha(board, pos)

        assertEquals(listOf(
                Empty, Empty, Empty, Empty, Empty, Empty, Jinchi, Jinchi, Jinchi,
                Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,
                Ryodo, Ryodo, Koma, Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, Ryodo,
                Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, Koma, Ryodo, Ryodo
        ), listOf(
                f(board, 0, 6), f(board, 1, 6), f(board, 2, 6), f(board, 3, 6), f(board, 4, 6), f(board, 5, 6), f(board, 6, 6), f(board, 7, 6), f(board, 8, 6),
                f(board, 2, 0), f(board, 2, 1), f(board, 2, 2), f(board, 2, 3), f(board, 2, 4), f(board, 2, 5), f(board, 2, 6), f(board, 2, 7), f(board, 2, 8),
                f2(board, 0, 6), f2(board, 1, 6), f2(board, 2, 6), f2(board, 3, 6), f2(board, 4, 6), f2(board, 5, 6), f2(board, 6, 6), f2(board, 7, 6), f2(board, 8, 6),
                f2(board, 2, 0), f2(board, 2, 1), f2(board, 2, 2), f2(board, 2, 3), f2(board, 2, 4), f2(board, 2, 5), f2(board, 2, 6), f2(board, 2, 7), f2(board, 2, 8)
        ))
    }

    @Test
    fun testAttackWithKaku() {
        val board = Board(9, 9)
        val p1 = Player2("hoge", 9)
        val pos = Position(2, 6)
        p1.attackWithKaku(board, pos)

        assertEquals(listOf(
                Empty, Empty, Empty, Empty, Empty,
                Jinchi, Jinchi, Jinchi, Empty, Empty, Empty, Empty, Empty, Empty,

                Ryodo, Ryodo, Koma, Ryodo, Ryodo,
                Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, Ryodo, Koma, Ryodo, Ryodo
        ), listOf(
                f(board, 0, 4), f(board, 1, 5), f(board, 2, 6), f(board, 3, 7), f(board, 4, 8),
                f(board, 8, 0), f(board, 7, 1), f(board, 6, 2), f(board, 5, 3), f(board, 4, 4), f(board, 3, 5), f(board, 2, 6), f(board, 1, 7), f(board, 0, 8),
                f2(board, 0, 4), f2(board, 1, 5), f2(board, 2, 6), f2(board, 3, 7), f2(board, 4, 8),
                f2(board, 8, 0), f2(board, 7, 1), f2(board, 6, 2), f2(board, 5, 3), f2(board, 4, 4), f2(board, 3, 5), f2(board, 2, 6), f2(board, 1, 7), f2(board, 0, 8)
        ))
    }

    @Test
    fun testAttackWithOu() {
        val board = Board(9, 9)
        val p1 = Player2("hoge", 9)
        val pos = Position(2, 6)
        p1.attackWithOu(board, pos)

        assertEquals(listOf(
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Empty, Empty, Empty,
                Ryodo, Ryodo, Ryodo,
                Ryodo, Koma, Ryodo,
                Ryodo, Ryodo, Ryodo
        ), actVec_3x3_p2(board))
    }
}
