package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.player.Player1
import jp.co.afis.model.player.Player2
import jp.co.afis.model.player.Players
import jp.co.afis.model.player.Winner
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable

internal class GameTest {
    @Test
    fun testCalcAppliable() {
        val row = 6
        val col = 2
        val pos = Position(row, col)

        for (koma in listOf(
                CellStatus.Fu,
                CellStatus.Kin,
                CellStatus.Gin,
                CellStatus.Keima,
                CellStatus.Kyosha,
                CellStatus.Hisha,
                CellStatus.Kaku,
                CellStatus.Ou
        )) {
            val ps = Players()
            assertAll(
                    Executable {
                        assertEquals(AppliableStatus.OUT_OF_RANGE, calcAppliable(Board(9, 9), Position(-1, -1), ps, Player1()))
                    },
                    Executable {
                        assertEquals(AppliableStatus.OUT_OF_RANGE, calcAppliable(Board(9, 9), Position(-1, 0), ps, Player1()))
                    },
                    Executable {
                        assertEquals(AppliableStatus.OUT_OF_RANGE, calcAppliable(Board(9, 9), Position(-1, 65535), ps, Player1()))
                    },
                    Executable {
                        assertEquals(AppliableStatus.OUT_OF_RANGE, calcAppliable(Board(9, 9), Position(0, -1), ps, Player1()))
                    },
                    Executable {
                        assertEquals(AppliableStatus.OUT_OF_RANGE, calcAppliable(Board(9, 9), Position(0, 65535), ps, Player1()))
                    },
                    Executable {
                        assertEquals(AppliableStatus.OUT_OF_RANGE, calcAppliable(Board(9, 9), Position(65535, -1), ps, Player1()))
                    },
                    Executable {
                        assertEquals(AppliableStatus.OUT_OF_RANGE, calcAppliable(Board(9, 9), Position(65535, 0), ps, Player1()))
                    },
                    Executable {
                        assertEquals(AppliableStatus.OUT_OF_RANGE, calcAppliable(Board(9, 9), Position(65535, 65535), ps, Player1()))
                    },
                    // Pattern1 ~ 10
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = koma
                        board.cells[row][col].status.player2 = CellStatus.Ryodo

                        assertAll(
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )

                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = koma
                        board.cells[row][col].status.player2 = CellStatus.Ryochi

                        assertAll(
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )

                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = koma
                        board.cells[row][col].status.player2 = CellStatus.Jinchi

                        assertAll(
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )

                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = koma
                        board.cells[row][col].status.player2 = CellStatus.Empty

                        assertAll(
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )

                    },

                    // Pattern11 ~ 20
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryodo
                        board.cells[row][col].status.player2 = koma
                        assertAll(
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryodo
                        board.cells[row][col].status.player2 = CellStatus.Ryodo
                        assertAll(
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryodo
                        board.cells[row][col].status.player2 = CellStatus.Ryochi
                        assertAll(
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryodo
                        board.cells[row][col].status.player2 = CellStatus.Jinchi
                        assertAll(
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryodo
                        board.cells[row][col].status.player2 = CellStatus.Empty
                        assertAll(
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },

                    // Pattern21 ~ 30
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryochi
                        board.cells[row][col].status.player2 = koma
                        assertAll(
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryochi
                        board.cells[row][col].status.player2 = CellStatus.Ryodo
                        assertAll(
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryochi
                        board.cells[row][col].status.player2 = CellStatus.Ryochi
                        assertAll(
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryochi
                        board.cells[row][col].status.player2 = CellStatus.Jinchi
                        assertAll(
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Ryochi
                        board.cells[row][col].status.player2 = CellStatus.Empty
                        assertAll(
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },


                    // Pattern31 ~ 40
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Jinchi
                        board.cells[row][col].status.player2 = koma
                        assertAll(
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Jinchi
                        board.cells[row][col].status.player2 = CellStatus.Ryodo
                        assertAll(
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Jinchi
                        board.cells[row][col].status.player2 = CellStatus.Ryochi
                        assertAll(
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        // 本来なら発生しない組み合わせ
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Jinchi
                        board.cells[row][col].status.player2 = CellStatus.Jinchi
                        assertAll(
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Jinchi
                        board.cells[row][col].status.player2 = CellStatus.Empty
                        assertAll(
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },

                    // Pattern41 ~ 50
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Empty
                        board.cells[row][col].status.player2 = koma
                        assertAll(
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.KOMA_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Empty
                        board.cells[row][col].status.player2 = CellStatus.Ryodo
                        assertAll(
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.RYODO_EXISTS, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Empty
                        board.cells[row][col].status.player2 = CellStatus.Ryochi
                        assertAll(
                                Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Empty
                        board.cells[row][col].status.player2 = CellStatus.Jinchi
                        assertAll(
                                Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.OK, calcAppliable(board, pos, ps, Player2())) }
                        )
                    },
                    Executable {
                        val board = Board(9, 9)
                        board.cells[row][col].status.player1 = CellStatus.Empty
                        board.cells[row][col].status.player2 = CellStatus.Empty
                        assertAll(
                                Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, ps, Player1())) },
                                Executable { assertEquals(AppliableStatus.NOT_OWN_AREA, calcAppliable(board, pos, ps, Player2())) }
                        )
                    }
            )

        }
    }

    @Test
    fun print() {
        val game = Game(players = Players(), board = Board(9, 9))
        game.print()
    }

    @Test
    fun testClick() {
        assertAll(
                Executable {
                    val game = Game()
                    assertAll(
                            Executable { assertEquals(AppliableStatus.OK, game.click(Position(6, 2))) },
                            Executable { assertTrue(game.players.currentPlayer is Player2) }
                    )

                },
                Executable {
                    val game = Game(vsCPU = true)
                    assertAll(
                            Executable { assertEquals(AppliableStatus.OK, game.click(Position(6, 2))) },
                            Executable { assertTrue(game.players.currentPlayer is Player1) }
                    )
                },
                Executable {
                    val game = Game(vsCPU = true)
                    assertAll(
                            Executable { assertEquals(AppliableStatus.OUT_OF_RANGE, game.click(Position(-1, 2))) },
                            Executable { assertTrue(game.players.currentPlayer is Player1) }
                    )
                }
        )
    }

    @Test
    fun testAttack() {
        assertAll(
                Executable {
                    val game = Game()
                    assertAll(
                            Executable { assertEquals(AppliableStatus.OK, game.attack(Position(6, 2))) },
                            Executable { assertTrue(game.players.currentPlayer is Player2) },
                            Executable { assertEquals(AppliableStatus.OK, game.attack(Position(2, 2))) },
                            Executable { assertTrue(game.players.currentPlayer is Player1) }
                    )
                }
        )
    }

    @Test
    fun testNotExistAppliablePositions() {
        val game = Game()
        val game2 = Game()
        game2.board.cells.forEach { it.forEach { it.status.player1 = CellStatus.Empty } }
        assertAll(
                Executable { assertFalse(game.notExistAppliablePositions()) },
                Executable { assertTrue(game2.notExistAppliablePositions()) }
        )
    }

    @Test
    fun testGetAppliablePositionsOfCurrentPlayer() {
        val game = Game()
        assertAll(
                Executable {
                    assertEquals(
                            listOf(
                                    Position(6, 0),
                                    Position(6, 1),
                                    Position(6, 2),
                                    Position(6, 3),
                                    Position(6, 4),
                                    Position(6, 5),
                                    Position(6, 6),
                                    Position(6, 7),
                                    Position(6, 8),

                                    Position(7, 0),
                                    Position(7, 1),
                                    Position(7, 2),
                                    Position(7, 3),
                                    Position(7, 4),
                                    Position(7, 5),
                                    Position(7, 6),
                                    Position(7, 7),
                                    Position(7, 8),

                                    Position(8, 0),
                                    Position(8, 1),
                                    Position(8, 2),
                                    Position(8, 3),
                                    Position(8, 4),
                                    Position(8, 5),
                                    Position(8, 6),
                                    Position(8, 7),
                                    Position(8, 8)
                            ),
                            game.getAppliablePositionsOfCurrentPlayer()
                    )
                },
                Executable {
                    game.players.switchCurrentPlayer()
                    assertEquals(
                            listOf(
                                    Position(0, 0),
                                    Position(0, 1),
                                    Position(0, 2),
                                    Position(0, 3),
                                    Position(0, 4),
                                    Position(0, 5),
                                    Position(0, 6),
                                    Position(0, 7),
                                    Position(0, 8),

                                    Position(1, 0),
                                    Position(1, 1),
                                    Position(1, 2),
                                    Position(1, 3),
                                    Position(1, 4),
                                    Position(1, 5),
                                    Position(1, 6),
                                    Position(1, 7),
                                    Position(1, 8),

                                    Position(2, 0),
                                    Position(2, 1),
                                    Position(2, 2),
                                    Position(2, 3),
                                    Position(2, 4),
                                    Position(2, 5),
                                    Position(2, 6),
                                    Position(2, 7),
                                    Position(2, 8)
                            ),
                            game.getAppliablePositionsOfCurrentPlayer()
                    )
                }
        )
    }

    @Test
    fun testCalcPlayer1Score() {
        val game = Game()
        val game2 = Game()
        game2.click(Position(6, 2))
        game2.click(Position(0, 2))
        game2.click(Position(6, 2)) // クリック不可のはず
        assertAll(
                Executable { assertEquals(0, game.calcPlayer1Score()) },
                Executable { assertEquals(0, game.calcPlayer2Score()) },
                Executable { assertEquals(2, game2.calcPlayer1Score()) },
                Executable { assertEquals(2, game2.calcPlayer2Score()) }
        )
    }

    @Test
    fun testGetWinner() {
        val game = Game()
        val player1IsWinnerGame = Game()
        val player2IsWinnerGame = Game()
        val sameGame = Game(board = Board(2, 2))
        sameGame.board.cells[0][0].status.player1 = CellStatus.Ryodo
        sameGame.board.cells[0][1].status.player1 = CellStatus.Ryodo
        sameGame.board.cells[1][0].status.player2 = CellStatus.Ryodo
        sameGame.board.cells[1][1].status.player2 = CellStatus.Ryodo

        player1IsWinnerGame.board.cells.forEach { it.forEach { it.status.player1 = CellStatus.Ryodo } }
        player2IsWinnerGame.board.cells.forEach { it.forEach { it.status.player2 = CellStatus.Ryodo } }

        assertAll(
                Executable { assertEquals(Winner.NONE, game.getWinner()) },
                Executable { assertEquals(Winner.PLAYER1, player1IsWinnerGame.getWinner()) },
                Executable { assertEquals(Winner.PLAYER2, player2IsWinnerGame.getWinner()) },
                Executable { assertEquals(Winner.SAME_SCORE, sameGame.getWinner()) }
        )
    }
}
