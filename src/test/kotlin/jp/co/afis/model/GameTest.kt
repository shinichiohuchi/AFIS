package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.player.Player1
import jp.co.afis.model.player.Player2
import jp.co.afis.model.player.Players
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable

internal class GameTest {
    @Test
    fun testCalcAppliable() {
        val row = 6
        val col = 2
        val pos = Position(row, col)

        assertAll(
                // Pattern1 ~ 10
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Fu
                    board.cells[row][col].status.player2 = CellStatus.Ryodo

                    assertAll(
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player2())) }
                    )

                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Fu
                    board.cells[row][col].status.player2 = CellStatus.Ryochi

                    assertAll(
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player2())) }
                    )

                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Fu
                    board.cells[row][col].status.player2 = CellStatus.Jinchi

                    assertAll(
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player2())) }
                    )

                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Fu
                    board.cells[row][col].status.player2 = CellStatus.Empty

                    assertAll(
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player2())) }
                    )

                },

                // Pattern11 ~ 20
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryodo
                    board.cells[row][col].status.player2 = CellStatus.Fu
                    assertAll(
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryodo
                    board.cells[row][col].status.player2 = CellStatus.Ryodo
                    assertAll(
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryodo
                    board.cells[row][col].status.player2 = CellStatus.Ryochi
                    assertAll(
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryodo
                    board.cells[row][col].status.player2 = CellStatus.Jinchi
                    assertAll(
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryodo
                    board.cells[row][col].status.player2 = CellStatus.Empty
                    assertAll(
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },

                // Pattern21 ~ 30
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryochi
                    board.cells[row][col].status.player2 = CellStatus.Fu
                    assertAll(
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryochi
                    board.cells[row][col].status.player2 = CellStatus.Ryodo
                    assertAll(
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryochi
                    board.cells[row][col].status.player2 = CellStatus.Ryochi
                    assertAll(
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryochi
                    board.cells[row][col].status.player2 = CellStatus.Jinchi
                    assertAll(
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Ryochi
                    board.cells[row][col].status.player2 = CellStatus.Empty
                    assertAll(
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, Player2())) }
                    )
                },


                // Pattern31 ~ 40
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Jinchi
                    board.cells[row][col].status.player2 = CellStatus.Fu
                    assertAll(
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Jinchi
                    board.cells[row][col].status.player2 = CellStatus.Ryodo
                    assertAll(
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Jinchi
                    board.cells[row][col].status.player2 = CellStatus.Ryochi
                    assertAll(
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable { // 本来なら発生しない組み合わせ
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Jinchi
                    board.cells[row][col].status.player2 = CellStatus.Jinchi
                    assertAll(
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Jinchi
                    board.cells[row][col].status.player2 = CellStatus.Empty
                    assertAll(
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, Player2())) }
                    )
                },

                // Pattern41 ~ 50
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Empty
                    board.cells[row][col].status.player2 = CellStatus.Fu
                    assertAll(
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Empty
                    board.cells[row][col].status.player2 = CellStatus.Ryodo
                    assertAll(
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Empty
                    board.cells[row][col].status.player2 = CellStatus.Ryochi
                    assertAll(
                            Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable { // 本来なら発生しない組み合わせ
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Empty
                    board.cells[row][col].status.player2 = CellStatus.Jinchi
                    assertAll(
                            Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.SUCCESS, calcAppliable(board, pos, Player2())) }
                    )
                },
                Executable {
                    val board = Board(9, 9)
                    board.cells[row][col].status.player1 = CellStatus.Empty
                    board.cells[row][col].status.player2 = CellStatus.Empty
                    assertAll(
                            Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, Player1())) },
                            Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, Player2())) }
                    )
                }
        )
    }

    @Test
    fun click() {
    }

    @Test
    fun print(){
        val game = Game(players = Players(), board = Board(9,9) )
        game.print()
    }

    @Test
    fun getPlayers() {
    }
}

