package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.player.Player
import jp.co.afis.model.player.Player1
import jp.co.afis.model.player.Player2
import jp.co.afis.model.player.Players

/**
 * AppliableStatus はセルに駒を配置可能かの状態
 */
enum class AppliableStatus {
    /** 正常に配置できた */
    SUCCESS,
    /** すでに駒が存在する */
    KOMA_EXISTS,
    /** すでに領土が存在する */
    RYODO_EXISTS,
    /** そもそも自分の領域ではない */
    NOT_OWN_AREA,
}

internal fun calcAppliable(board: Board, pos: Position, currentPlayer: Player) : AppliableStatus {
    return AppliableStatus.KOMA_EXISTS
}

class Game(val players: Players = Players(), val board: Board = Board(9, 9)) {
    /**
     * click は指定の位置のセルをクリックする。
     * クリックを正常に完了できた場合は、ターンが切り替わり、
     * 次にクリックしたときは相手プレイヤーとしてクリックすることになる。
     * @param pos クリック位置
     */
    fun click(pos: Position) {
//        val cell = board.getCell(pos)
//        when (players.currentPlayer) {
//            is Player1 -> {
//                TODO("hoge")
//            }
//            is Player2 -> TODO("todo")
//        }
    }

    /**
     * print は将棋盤を行列番号と共に標準出力します。
     */
    fun print() {
        val boardString = board.createBoardString()
        println(boardString)
    }
}
