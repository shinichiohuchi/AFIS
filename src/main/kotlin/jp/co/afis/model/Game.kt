package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.player.Players

class Game(val players: Players, val board: Board) {
    fun click(pos: Position) {

    }

    /**
     * print は将棋盤を行列番号と共に標準出力します。
     */
    fun print() {
        val boardString = board.createBoardString()
        println(boardString)
    }

    private fun updateCurrentPlayer() {

    }
}
