package jp.co.afis.model.player

import jp.co.afis.model.cell.KomaType
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class IllegalPlayerExceptionTest{
    @Test
    fun testConstructor() {
        val e = IllegalPlayerException("プレイヤーが不正です。")
        assertAll(
                Executable { assertEquals(true, e is Exception) },
                Executable { assertEquals("プレイヤーが不正です。", e.message) }
        )
    }
}

internal class PlayersTest {
    @Test
    fun testConstructor() {
        val ps = Players()
        val ps2 = Players(player1 = Player1(
                name = "tanaka",
                fuCount = 1,
                kinCount = 2,
                ginCount = 3,
                kyoshaCount = 4,
                keimaCount = 5,
                hishaCount = 6,
                kakuCount = 7,
                ouCount = 8
        ), player2 = Player2(
                name = "yamada",
                fuCount = 21,
                kinCount = 22,
                ginCount = 23,
                kyoshaCount = 24,
                keimaCount = 25,
                hishaCount = 26,
                kakuCount = 27,
                ouCount = 28
        ))
        assertAll(
                // デフォルト駒数
                Executable { assertEquals(9, ps.player1.fuCount) },
                Executable { assertEquals(2, ps.player1.kinCount) },
                Executable { assertEquals(2, ps.player1.ginCount ) },
                Executable { assertEquals(2, ps.player1.kyoshaCount) },
                Executable { assertEquals(2, ps.player1.keimaCount) },
                Executable { assertEquals(1, ps.player1.hishaCount) },
                Executable { assertEquals(1, ps.player1.kakuCount) },
                Executable { assertEquals(1, ps.player1.ouCount) },

                Executable { assertEquals(1, ps2.player1.fuCount) },
                Executable { assertEquals(2, ps2.player1.kinCount) },
                Executable { assertEquals(3, ps2.player1.ginCount) },
                Executable { assertEquals(4, ps2.player1.kyoshaCount) },
                Executable { assertEquals(5, ps2.player1.keimaCount) },
                Executable { assertEquals(6, ps2.player1.hishaCount) },
                Executable { assertEquals(7, ps2.player1.kakuCount) },
                Executable { assertEquals(8, ps2.player1.ouCount) },

                Executable { assertEquals(9, ps.player2.fuCount) },
                Executable { assertEquals(2, ps.player2.kinCount) },
                Executable { assertEquals(2, ps.player2.ginCount ) },
                Executable { assertEquals(2, ps.player2.kyoshaCount) },
                Executable { assertEquals(2, ps.player2.keimaCount) },
                Executable { assertEquals(1, ps.player2.hishaCount) },
                Executable { assertEquals(1, ps.player2.kakuCount) },
                Executable { assertEquals(1, ps.player2.ouCount) },

                Executable { assertEquals(21, ps2.player2.fuCount) },
                Executable { assertEquals(22, ps2.player2.kinCount) },
                Executable { assertEquals(23, ps2.player2.ginCount) },
                Executable { assertEquals(24, ps2.player2.kyoshaCount) },
                Executable { assertEquals(25, ps2.player2.keimaCount) },
                Executable { assertEquals(26, ps2.player2.hishaCount) },
                Executable { assertEquals(27, ps2.player2.kakuCount) },
                Executable { assertEquals(28, ps2.player2.ouCount) }
        )
    }

    @Test
    fun testGetSwitchedPlayer() {
        val p1 = Player1()
        val p2 = Player2()
        assertAll(
                Executable { assertEquals(true, getSwitchedPlayer(p1, p1, p2) is Player2) },
                Executable { assertEquals(true, getSwitchedPlayer(p2, p1, p2) is Player1) },
                Executable { assertEquals(false, getSwitchedPlayer(p1, p1, p2) is Player1) },
                Executable { assertEquals(false, getSwitchedPlayer(p2, p1, p2) is Player2) }
        )
    }

    @Test
    fun testGetSwitchedAttack() {
        // TODO: 無名関数の判定はできない？
//        val p = Player1()
//        assertAll(
//                Executable { assertEquals(getSwitchedAttack(KomaType.FU, p), p::attackWithFu) },
//                Executable { assertEquals(getSwitchedAttack(KomaType.KIN, p), p::attackWithKin) },
//                Executable { assertEquals(getSwitchedAttack(KomaType.GIN, p), p::attackWithGin) },
//                Executable { assertEquals(getSwitchedAttack(KomaType.KEIMA, p), p::attackWithKeima) },
//                Executable { assertEquals(getSwitchedAttack(KomaType.KYOSHA, p), p::attackWithKyosha) },
//                Executable { assertEquals(getSwitchedAttack(KomaType.HISHA, p), p::attackWithHisha) },
//                Executable { assertEquals(getSwitchedAttack(KomaType.KAKU, p), p::attackWithKaku) },
//                Executable { assertEquals(getSwitchedAttack(KomaType.OU, p), p::attackWithOu) }
//        )
    }

    @Test
    fun testSwitchCurrentPlayer() {
        // TODO: private methodなので試験ができてない
        val p = Players()
        p.switchCurrentPlayer()
    }

    @Test
    fun testSwitchAttackStrategy() {
        // TODO: private methodなので試験ができてない
        val p = Players()
        p.switchAttackStrategy(KomaType.FU)
    }

    @Test
    fun testHasEnoughCountOfKomas() {
        assertAll(
                Executable { assertEquals(true, Players().hasEnoughCountOfKomas()) },
                Executable {
                    val p = Players()
                    p.player1.fuCount = 0
                    p.player2.fuCount = 1
                    assertEquals(false, p.hasEnoughCountOfKomas())
                },
                Executable {
                    val p = Players()
                    p.switchCurrentPlayer()
                    p.player1.fuCount = 0
                    p.player2.fuCount = 1
                    assertEquals(true, p.hasEnoughCountOfKomas())
                }
        )
    }
}


