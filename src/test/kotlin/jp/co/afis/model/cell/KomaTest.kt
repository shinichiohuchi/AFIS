package jp.co.afis.model.cell

import jp.co.afis.bean.Position
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PackageFunctionTest {
    @Test
    fun testCalcArea() {
        assertEquals(1, calcArea(3), "計算ミスってます。")
        assertEquals(2, calcArea(5), "計算ミスってます。")
        assertEquals(2, calcArea(6), "計算ミスってます。")
        assertEquals(3, calcArea(9), "計算ミスってます。")
        assertEquals(3, calcArea(10), "計算ミスってます。")
    }

    @Test
    fun testCreateInitCells() {
        val cells = createInitCells(9, 9)
        assertEquals(CellStatus.Empty, cells[0][0].status.player1)
        assertEquals(CellStatus.Jinchi, cells[0][0].status.player2)
        assertEquals(CellStatus.Empty, cells[0][8].status.player1)
        assertEquals(CellStatus.Jinchi, cells[0][8].status.player2)

        assertEquals(CellStatus.Empty, cells[2][0].status.player1)
        assertEquals(CellStatus.Jinchi, cells[2][0].status.player2)
        assertEquals(CellStatus.Empty, cells[2][8].status.player1)
        assertEquals(CellStatus.Jinchi, cells[2][8].status.player2)

        assertEquals(CellStatus.Empty, cells[3][0].status.player1)
        assertEquals(CellStatus.Empty, cells[3][0].status.player2)
        assertEquals(CellStatus.Empty, cells[3][8].status.player1)
        assertEquals(CellStatus.Empty, cells[3][8].status.player2)

        assertEquals(CellStatus.Empty, cells[5][0].status.player1)
        assertEquals(CellStatus.Empty, cells[5][0].status.player2)
        assertEquals(CellStatus.Empty, cells[5][8].status.player1)
        assertEquals(CellStatus.Empty, cells[5][8].status.player2)

        assertEquals(CellStatus.Jinchi, cells[6][0].status.player1)
        assertEquals(CellStatus.Empty, cells[6][0].status.player2)
        assertEquals(CellStatus.Jinchi, cells[6][8].status.player1)
        assertEquals(CellStatus.Empty, cells[6][8].status.player2)

        assertEquals(CellStatus.Jinchi, cells[8][0].status.player1)
        assertEquals(CellStatus.Empty, cells[8][0].status.player2)
        assertEquals(CellStatus.Jinchi, cells[8][8].status.player1)
        assertEquals(CellStatus.Empty, cells[8][8].status.player2)

        (0..8).forEach { r ->
            (0..8).forEach { c ->
                val cell = cells[r][c]
                assertEquals(r, cell.pos.row)
                assertEquals(c, cell.pos.col)
            }
        }
    }
}

internal class CellTest {
    @Test
    fun testConstructor() {
        val cell = Cell(pos = Position(row = 1, col = 2))
        assertEquals(1, cell.pos.row, "値が一致しませんでした。")
        assertEquals(2, cell.pos.col, "値が一致しませんでした。")
        assertNotEquals(cell.pos.row, cell.pos.col, "値が一致したらおかしい")
    }
}
