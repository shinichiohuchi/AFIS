package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.player.Players

class Game(val players: Players, val board: Board) {
    /**
     * click は指定の位置のセルをクリックする。
     * クリックを正常に完了できた場合は、ターンが切り替わり、
     * 次にクリックしたときは相手プレイヤーとしてクリックすることになる。
     * @param pos クリック位置
     */
    fun click(pos: Position) {
//        val koma = players.pop()
//        val result = board.put(koma)
//        if (result == Board.PutResult.SUCCESS) {
//            players.switchCurrentPlayer()
//        } else {
//            players.push(koma)
//        }
//        return result
    }

    /**
     * print は将棋盤を行列番号と共に標準出力します。
     */
    fun print() {
        val boardString = board.createBoardString()
        println(boardString)
    }
}
