package jp.co.afis.bean

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class PositionTest {

    @Test
    fun testIsWithinBoardRange() {
        val f: (Int, Int) -> Boolean = { r, c -> Position(r, c).isWithinBoardRange(9, 9) }

        assertAll(
                Executable { assertEquals(false, f(0, -1)) },
                Executable { assertEquals(true, f(0, 0)) },
                Executable { assertEquals(true, f(0, 8)) },
                Executable { assertEquals(false, f(0, 9)) },
                Executable { assertEquals(false, f(8, -1)) },
                Executable { assertEquals(true, f(8, 0)) },
                Executable { assertEquals(true, f(8, 8)) },
                Executable { assertEquals(false, f(8, 9)) },

                Executable { assertEquals(false, f(-1, 0)) },
                Executable { assertEquals(true, f(0, 0)) },
                Executable { assertEquals(true, f(8, 0)) },
                Executable { assertEquals(false, f(9, 0)) },
                Executable { assertEquals(false, f(-1, 8)) },
                Executable { assertEquals(true, f(0, 8)) },
                Executable { assertEquals(true, f(8, 8)) },
                Executable { assertEquals(false, f(9, 8)) }
        )
    }
}