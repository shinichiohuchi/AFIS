package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.KomaType
import jp.co.afis.model.player.Player1
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable

internal class BoardTest {
    @Test
    fun createBoardString() {
        assertAll(
                Executable {
                    val expected = """
            |J|J|J|J|J|J|J|J|J|
            |J|J|J|J|J|J|J|J|J|
            |J|J|J|J|J|J|J|J|J|
            |E|E|E|E|E|E|E|E|E|
            |E|E|E|E|E|E|E|E|E|
            |E|E|E|E|E|E|E|E|E|
            |J|J|J|J|J|J|J|J|J|
            |J|J|J|J|J|J|J|J|J|
            |J|J|J|J|J|J|J|J|J|""".trimIndent()
                    val actual = Board(9, 9).createBoardString()
                    assertEquals(expected, actual)
                },
                Executable {
                    val expected = """
            |J|J|J|J|J|J|J|J|J|
            |E|E|E|E|E|E|E|E|E|
            |E|E|E|E|E|E|E|E|E|
            |J|J|J|J|J|J|J|J|J|""".trimIndent()
                    val actual = Board(4, 9).createBoardString()
                    assertEquals(expected, actual)
                },
                Executable {
                    val expected = """
            |J|J|J|J|
            |E|E|E|E|
            |E|E|E|E|
            |J|J|J|J|""".trimIndent()
                    val actual = Board(4, 4).createBoardString()
                    assertEquals(expected, actual)
                }
        )
    }

    @Test
    fun testCalcPlayerScore() {
        assertAll(
                Executable {
                    val board = Board(9, 9)
                    assertEquals(0, calcPlayerScore({ cell -> cell.status.player1 }, board.cells))
                },
                Executable {
                    val game = Game()
                    game.click(Position(6, 2))
                    assertEquals(2, calcPlayerScore({ cell -> cell.status.player1 }, game.board.cells))
                },
                Executable {
                    val game = Game()
                    game.players.switchAttackStrategy(KomaType.KYOSHA)
                    game.click(Position(8, 2))
                    game.players.switchAttackStrategy(KomaType.KYOSHA)
                    game.click(Position(0, 2))
                    assertAll(
                            Executable { assertEquals(9, calcPlayerScore({ cell -> cell.status.player1 }, game.board.cells)) },
                            Executable { assertEquals(0, calcPlayerScore({ cell -> cell.status.player2 }, game.board.cells)) }
                    )
                },
                Executable {
                    val game = Game()
                    game.players.switchAttackStrategy(KomaType.KYOSHA)
                    game.click(Position(8, 2))
                    game.players.switchAttackStrategy(KomaType.KYOSHA)
                    game.click(Position(0, 3))
                    assertAll(
                            Executable { assertEquals(9, calcPlayerScore({ cell -> cell.status.player1 }, game.board.cells)) },
                            Executable { assertEquals(9, calcPlayerScore({ cell -> cell.status.player2 }, game.board.cells)) }
                    )
                },
                Executable {
                    val game = Game()

                    game.board.cells[4][3].status.player1 = CellStatus.Jinchi
                    game.players.switchAttackStrategy(KomaType.KIN)
                    game.click(Position(4, 3))

                    game.board.cells[3][5].status.player2 = CellStatus.Jinchi
                    game.players.switchAttackStrategy(KomaType.KIN)
                    game.click(Position(3, 5))

                    assertAll(
                            Executable { assertEquals(5, calcPlayerScore({ cell -> cell.status.player1 }, game.board.cells)) },
                            Executable { assertEquals(7, calcPlayerScore({ cell -> cell.status.player2 }, game.board.cells)) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    assertEquals(0, calcPlayerScore({ cell -> cell.status.player2 }, board.cells))
                },
                Executable {
                    val game = Game()
                    game.click(Position(6, 2))
                    game.click(Position(2, 2))
                    assertEquals(2, calcPlayerScore({ cell -> cell.status.player2 }, game.board.cells))
                }
        )
    }

    @Test
    fun testCalcPlayer1Score() {
        assertAll(
                Executable { assertEquals(0, Board(9, 9).calcPlayer1Score()) },
                Executable {
                    val game = Game()
                    game.click(Position(6, 2))
                    assertEquals(2, game.board.calcPlayer1Score())
                }
        )
    }

    @Test
    fun testCalcPlayer2Score() {
        assertAll(
                Executable { assertEquals(0, Board(9, 9).calcPlayer2Score()) },
                Executable {
                    val game = Game()
                    game.click(Position(6, 2))
                    game.click(Position(2, 2))
                    assertEquals(2, game.board.calcPlayer2Score())
                }
        )
    }

    @Test
    fun testUpdateRyochi() {
        val board = Board(9, 9)
        board.cells[6][2].status.player1 = CellStatus.Fu
        board.cells[5][2].status.player1 = CellStatus.Ryodo

        board.cells[1][2].status.player2 = CellStatus.Fu
        board.cells[2][2].status.player2 = CellStatus.Ryodo
        board.updateRyochi()
        assertAll(
                Executable { assertEquals(CellStatus.Empty, board.cells[4][1].status.player1) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[4][2].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[4][3].status.player1) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[5][1].status.player1) },
                Executable { assertEquals(CellStatus.Ryodo, board.cells[5][2].status.player1) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[5][3].status.player1) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[6][1].status.player1) },
                Executable { assertEquals(CellStatus.Fu, board.cells[6][2].status.player1) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[6][3].status.player1) },
                Executable { assertEquals(CellStatus.Jinchi, board.cells[7][1].status.player1) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[7][2].status.player1) },
                Executable { assertEquals(CellStatus.Jinchi, board.cells[7][3].status.player1) },

                Executable { assertEquals(CellStatus.Empty, board.cells[4][1].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[4][2].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[4][3].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[5][1].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[5][2].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[5][3].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[6][1].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[6][2].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[6][3].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[7][1].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[7][2].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[7][3].status.player2) },

                Executable { assertEquals(CellStatus.Empty, board.cells[0][1].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[0][2].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[0][3].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[1][1].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[1][2].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[1][3].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[2][1].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[2][2].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[2][3].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[3][1].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[3][2].status.player1) },
                Executable { assertEquals(CellStatus.Empty, board.cells[3][3].status.player1) },

                Executable { assertEquals(CellStatus.Jinchi, board.cells[0][1].status.player2) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[0][2].status.player2) },
                Executable { assertEquals(CellStatus.Jinchi, board.cells[0][3].status.player2) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[1][1].status.player2) },
                Executable { assertEquals(CellStatus.Fu, board.cells[1][2].status.player2) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[1][3].status.player2) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[2][1].status.player2) },
                Executable { assertEquals(CellStatus.Ryodo, board.cells[2][2].status.player2) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[2][3].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[3][1].status.player2) },
                Executable { assertEquals(CellStatus.Ryochi, board.cells[3][2].status.player2) },
                Executable { assertEquals(CellStatus.Empty, board.cells[3][3].status.player2) }
        )
    }

}