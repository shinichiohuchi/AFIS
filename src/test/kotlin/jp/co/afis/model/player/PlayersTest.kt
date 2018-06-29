package jp.co.afis.model.player

import jp.co.afis.model.cell.KomaType
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

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
                Executable { assertEquals(9, ps.player1.fus.size) },
                Executable { assertEquals(2, ps.player1.kins.size) },
                Executable { assertEquals(2, ps.player1.gins.size) },
                Executable { assertEquals(2, ps.player1.kyoshas.size) },
                Executable { assertEquals(2, ps.player1.keimas.size) },
                Executable { assertEquals(1, ps.player1.hishas.size) },
                Executable { assertEquals(1, ps.player1.kakus.size) },
                Executable { assertEquals(1, ps.player1.ous.size) },

                Executable { assertEquals(1, ps2.player1.fus.size) },
                Executable { assertEquals(2, ps2.player1.kins.size) },
                Executable { assertEquals(3, ps2.player1.gins.size) },
                Executable { assertEquals(4, ps2.player1.kyoshas.size) },
                Executable { assertEquals(5, ps2.player1.keimas.size) },
                Executable { assertEquals(6, ps2.player1.hishas.size) },
                Executable { assertEquals(7, ps2.player1.kakus.size) },
                Executable { assertEquals(8, ps2.player1.ous.size) },

                Executable { assertEquals(9, ps.player2.fus.size) },
                Executable { assertEquals(2, ps.player2.kins.size) },
                Executable { assertEquals(2, ps.player2.gins.size) },
                Executable { assertEquals(2, ps.player2.kyoshas.size) },
                Executable { assertEquals(2, ps.player2.keimas.size) },
                Executable { assertEquals(1, ps.player2.hishas.size) },
                Executable { assertEquals(1, ps.player2.kakus.size) },
                Executable { assertEquals(1, ps.player2.ous.size) },

                Executable { assertEquals(21, ps2.player2.fus.size) },
                Executable { assertEquals(22, ps2.player2.kins.size) },
                Executable { assertEquals(23, ps2.player2.gins.size) },
                Executable { assertEquals(24, ps2.player2.kyoshas.size) },
                Executable { assertEquals(25, ps2.player2.keimas.size) },
                Executable { assertEquals(26, ps2.player2.hishas.size) },
                Executable { assertEquals(27, ps2.player2.kakus.size) },
                Executable { assertEquals(28, ps2.player2.ous.size) },

                // セットされている関数
                Executable { assertEquals(ps.player1.fus.first().attack, ps.player1::attackWithFu) },
                Executable { assertEquals(ps.player1.kins.first().attack, ps.player1::attackWithKin) },
                Executable { assertEquals(ps.player1.gins.first().attack, ps.player1::attackWithGin) },
                Executable { assertEquals(ps.player1.keimas.first().attack, ps.player1::attackWithKeima) },
                Executable { assertEquals(ps.player1.kyoshas.first().attack, ps.player1::attackWithKyosha) },
                Executable { assertEquals(ps.player1.hishas.first().attack, ps.player1::attackWithHisha) },
                Executable { assertEquals(ps.player1.kakus.first().attack, ps.player1::attackWithKaku) },
                Executable { assertEquals(ps.player1.ous.first().attack, ps.player1::attackWithOu) },

                Executable { assertEquals(ps.player2.fus.first().attack, ps.player2::attackWithFu) },
                Executable { assertEquals(ps.player2.kins.first().attack, ps.player2::attackWithKin) },
                Executable { assertEquals(ps.player2.gins.first().attack, ps.player2::attackWithGin) },
                Executable { assertEquals(ps.player2.keimas.first().attack, ps.player2::attackWithKeima) },
                Executable { assertEquals(ps.player2.kyoshas.first().attack, ps.player2::attackWithKyosha) },
                Executable { assertEquals(ps.player2.hishas.first().attack, ps.player2::attackWithHisha) },
                Executable { assertEquals(ps.player2.kakus.first().attack, ps.player2::attackWithKaku) },
                Executable { assertEquals(ps.player2.ous.first().attack, ps.player2::attackWithOu) }
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
}


