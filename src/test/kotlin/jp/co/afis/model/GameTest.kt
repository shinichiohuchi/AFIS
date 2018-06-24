package jp.co.afis.model

import jp.co.afis.model.player.Players
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class GameTest {

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

