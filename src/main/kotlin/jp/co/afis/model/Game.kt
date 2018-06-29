package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.player.Player
import jp.co.afis.model.player.Player1
import jp.co.afis.model.player.Player2
import jp.co.afis.model.player.Players

/**
 * AppliableStatus はセルに駒を配置可能かの状態
 */
enum class AppliableStatus {
    /** 配置可能 */
    OK,
    /** すでに駒が存在する */
    KOMA_EXISTS,
    /** すでに領土が存在する */
    RYODO_EXISTS,
    /** そもそも自分の領域ではない */
    NOT_OWN_AREA,
}

/**
 * calcAppliable は駒を配置可能か、不可能ならその理由の判定結果を返します。
 * @param board 将棋盤
 * @param pos 判定対象位置
 * @param currentPlayer 操作プレイヤー
 * @return 配置可否、および不可の理由
 */
internal fun calcAppliable(board: Board, pos: Position, currentPlayer: Player) : AppliableStatus {
    val row = pos.row
    val col = pos.col
    val statusPlayer1 = board.cells[row][col].status.player1
    val statusPlayer2 = board.cells[row][col].status.player2

    for (p in listOf(statusPlayer1, statusPlayer2)) {
        when (p) {
            CellStatus.Fu -> return AppliableStatus.KOMA_EXISTS
            CellStatus.Kin -> return AppliableStatus.KOMA_EXISTS
            CellStatus.Gin -> return AppliableStatus.KOMA_EXISTS
            CellStatus.Keima -> return AppliableStatus.KOMA_EXISTS
            CellStatus.Kyosha -> return AppliableStatus.KOMA_EXISTS
            CellStatus.Hisha -> return AppliableStatus.KOMA_EXISTS
            CellStatus.Kaku -> return AppliableStatus.KOMA_EXISTS
            CellStatus.Ou -> return AppliableStatus.KOMA_EXISTS
        }

    }

    if (statusPlayer1 == CellStatus.Ryodo) return AppliableStatus.RYODO_EXISTS
    if (statusPlayer2 == CellStatus.Ryodo) return AppliableStatus.RYODO_EXISTS

    if ((statusPlayer1 == CellStatus.Ryochi || statusPlayer1 == CellStatus.Jinchi)
            && statusPlayer2 == CellStatus.Empty) {
        when (currentPlayer) {
            is Player1 -> return AppliableStatus.OK
            is Player2 -> return AppliableStatus.NOT_OWN_AREA
        }
    }

    if ((statusPlayer2 == CellStatus.Ryochi || statusPlayer2 == CellStatus.Jinchi)
            && statusPlayer1 == CellStatus.Empty) {
        when (currentPlayer) {
            is Player1 -> return AppliableStatus.NOT_OWN_AREA
            is Player2 -> return AppliableStatus.OK
        }
    }

    if (statusPlayer1 == CellStatus.Empty) return AppliableStatus.NOT_OWN_AREA
    if (statusPlayer2 == CellStatus.Empty) return AppliableStatus.NOT_OWN_AREA

    return AppliableStatus.OK
}

class Game(val players: Players = Players(), val board: Board = Board(9, 9)) {
    /**
     * click は指定の位置のセルをクリックする。
     * クリックを正常に完了できた場合は、ターンが切り替わり、
     * 次にクリックしたときは相手プレイヤーとしてクリックすることになる。
     * @param pos クリック位置
     */
    fun click(pos: Position) {
        val appliable = calcAppliable(board, pos, players.currentPlayer)
        when (appliable) {
            AppliableStatus.OK -> {
                players.attack(board, pos)
            }
            else -> {

            }
        }
    }

    /**
     * print は将棋盤を行列番号と共に標準出力します。
     */
    fun print() {
        val boardString = board.createBoardString()
        println(boardString)
    }
}
