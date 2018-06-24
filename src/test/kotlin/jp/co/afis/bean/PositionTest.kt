package jp.co.afis.bean

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class PositionTest {

    @Test
    fun testIsWithinBoardRange() {
        val f: (Int, Int) -> Boolean = { r, c -> Position(r, c).isWithinBoardRange(4, 8) }

        assertAll(
                Executable { assertEquals(false, f(0, -1)) },
                Executable { assertEquals(true, f(0, 0)) },
                Executable { assertEquals(true, f(0, 7)) },
                Executable { assertEquals(false, f(0, 8)) },
                Executable { assertEquals(false, f(3, -1)) },
                Executable { assertEquals(true, f(3, 0)) },
                Executable { assertEquals(true, f(3, 7)) },
                Executable { assertEquals(false, f(3, 8)) },

                Executable { assertEquals(false, f(-1, 0)) },
                Executable { assertEquals(true, f(0, 0)) },
                Executable { assertEquals(true, f(3, 0)) },
                Executable { assertEquals(false, f(4, 0)) },
                Executable { assertEquals(false, f(-1, 7)) },
                Executable { assertEquals(true, f(0, 7)) },
                Executable { assertEquals(true, f(3, 7)) },
                Executable { assertEquals(false, f(4, 7)) }
        )
    }
}