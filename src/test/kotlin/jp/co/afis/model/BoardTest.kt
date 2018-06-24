package jp.co.afis.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.function.Executable

internal class BoardTest {

    @Test
    fun put() {
    }

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

}