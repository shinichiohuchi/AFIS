package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.KomaType
import jp.co.afis.model.player.*
import java.io.FileInputStream
import java.util.*

fun main(args: Array<String>) {
    val game = Game()
    game.playWithCUI()
}

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
    /** 配列外エラー */
    OUT_OF_RANGE,
    /** 駒の残数なし */
    NO_KOMAS,
}

/**
 * calcAppliable は駒を配置可能か、不可能ならその理由の判定結果を返します。
 * @param board 将棋盤
 * @param pos 判定対象位置
 * @param currentPlayer 操作プレイヤー
 * @return 配置可否、および不可の理由
 */
internal fun calcAppliable(board: Board, pos: Position, players: Players, currentPlayer: Player): AppliableStatus {
    val row = pos.row
    val col = pos.col

    // 範囲外のセルを指定していたらNG
    if (!pos.isWithinBoardRange(board.cells.size, board.cells[0].size))
        return AppliableStatus.OUT_OF_RANGE

    // 駒の残数が0の場合はNG
    if (!players.hasEnoughCountOfKomas())
        return AppliableStatus.NO_KOMAS

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

internal fun createGameWithConfig(configFile: String, row: Int, col:Int): Game {
    val prop = Properties()
    prop.load(FileInputStream(configFile))

    val fu = prop.getProperty("fu").toInt()
    val kin = prop.getProperty("kin").toInt()
    val gin = prop.getProperty("gin").toInt()
    val keima = prop.getProperty("keima").toInt()
    val kyosha = prop.getProperty("kyosha").toInt()
    val hisha = prop.getProperty("hisha").toInt()
    val kaku = prop.getProperty("kaku").toInt()
    val ou = prop.getProperty("ou").toInt()

    val game = Game(players = Players(
            player1 = Player1(
                    fuCount = fu,
                    kinCount = kin,
                    ginCount = gin,
                    keimaCount = keima,
                    kyoshaCount = kyosha,
                    hishaCount = hisha,
                    kakuCount = kaku,
                    ouCount = ou
            ),
            player2 = Player2(
                    fuCount = fu,
                    kinCount = kin,
                    ginCount = gin,
                    keimaCount = keima,
                    kyoshaCount = kyosha,
                    hishaCount = hisha,
                    kakuCount = kaku,
                    ouCount = ou
            )
    ), board = Board(row, col))
    return game
}

/**
 * Game はゲームロジッククラス。
 *
 * @constructor
 * ゲームを生成する。
 *
 * @param players プレイヤー1と2を保持する。
 * @param board 将棋盤
 * @param onCPU CPUによる自動攻撃を有効化する
 */
class Game(val players: Players = Players(), val board: Board = Board(9, 9), val onCPU: Boolean = false) {
    constructor(board: Board) : this(players = Players(), board = board)

    /**
     * click は指定の位置のセルをクリックする。
     * クリックを正常に完了できた場合は、ターンが切り替わり、
     * 次にクリックしたときは相手プレイヤーとしてクリックすることになる。
     * @param pos クリック位置
     */
    fun click(pos: Position): AppliableStatus {
        val appliable = calcAppliable(board, pos, players, players.currentPlayer)
        return when (appliable) {
            AppliableStatus.OK -> {
                players.attack(board, pos)
                board.clearJinchi(players.currentPlayer)
                players.switchCurrentPlayer()
                board.updateRyochi()
                appliable
            }
            else -> {
                appliable
            }
        }
    }

    /**
     * eventWithCPU はCPUによる自動操作です。
     * CPUは配置可能ないずれかのセルに駒の配置を試みます。
     * 配置可能なますが存在しない場合はスキップします。
     */
    fun eventWithCPU() {
        val appliableCount = board.getAppliablePositionsOfPlayer2().size
        TODO("test")
    }

    /**
     * print は将棋盤を行列番号と共に標準出力します。
     */
    fun print() {
        val boardString = board.createBoardString()
        println(boardString)
    }

    fun playWithCUI() {
        while (true) {
            println("Enter Position d,x,y or q > ")
            readLine().let {
                if (it != null) {
                    if (it == "q") {
                        System.exit(0)
                    }
                    val koma = it.split(",")[0].toInt()
                    when (koma) {
                        1 -> players.switchAttackStrategy(KomaType.FU)
                        2 -> players.switchAttackStrategy(KomaType.KIN)
                        3 -> players.switchAttackStrategy(KomaType.GIN)
                        4 -> players.switchAttackStrategy(KomaType.KEIMA)
                        5 -> players.switchAttackStrategy(KomaType.KYOSHA)
                        6 -> players.switchAttackStrategy(KomaType.HISHA)
                        7 -> players.switchAttackStrategy(KomaType.KAKU)
                        8 -> players.switchAttackStrategy(KomaType.OU)
                    }
                    val x = it.split(",")[1].toInt()
                    val y = it.split(",")[2].toInt()
                    click(Position(x, y))
                    print()
                }
            }
        }
    }

    fun calcPlayer1Score(): Int {
        return board.calcPlayer1Score()
    }

    fun calcPlayer2Score(): Int {
        return board.calcPlayer2Score()
    }

    /**
     * getWinner は勝者を返す。
     * @return 勝者
     */
    fun getWinner(): Winner {
        val player1Score = board.calcPlayer1Score()
        val player2Score = board.calcPlayer2Score()
        val sum = player1Score + player2Score
        val row = board.cells.size
        val col = board.cells[0].size
        val cellCount = row * col
        return if (sum < cellCount)
            Winner.NONE
        else {
            when {
                player1Score < player2Score -> Winner.PLAYER2
                player2Score < player1Score -> Winner.PLAYER1
                else -> Winner.SAME_SCORE
            }
        }
    }

    /**
     * getCellDispStatus は画面病が用のセルステータスを返す
     */
    fun getCellDispStatus(pos: Position): CellDispStatus {
        val cell = board.getCell(pos)

        if (cell.status.player1 == CellStatus.Ryochi && cell.status.player2 == CellStatus.Ryochi)
            return CellDispStatus.BOTH_RYOCHI

        when (cell.status.player1) {
            CellStatus.Fu
                , CellStatus.Kin
                , CellStatus.Gin
                , CellStatus.Keima
                , CellStatus.Kyosha
                , CellStatus.Hisha
                , CellStatus.Kaku
                , CellStatus.Ou -> return CellDispStatus.PLAYER1_KOMA
            CellStatus.Ryodo -> return CellDispStatus.PLAYER1_RYODO
            CellStatus.Ryochi -> return CellDispStatus.PLAYER1_RYOCHI
            CellStatus.Jinchi -> return CellDispStatus.PLAYER1_JINCHI
        }
        when (cell.status.player2) {
            CellStatus.Fu
                , CellStatus.Kin
                , CellStatus.Gin
                , CellStatus.Keima
                , CellStatus.Kyosha
                , CellStatus.Hisha
                , CellStatus.Kaku
                , CellStatus.Ou -> return CellDispStatus.PLAYER2_KOMA
            CellStatus.Ryodo -> return CellDispStatus.PLAYER2_RYODO
            CellStatus.Ryochi -> return CellDispStatus.PLAYER2_RYOCHI
            CellStatus.Jinchi -> return CellDispStatus.PLAYER2_JINCHI
        }
        return CellDispStatus.NONE
    }

}

enum class CellDispStatus {
    NONE,
    PLAYER1_KOMA,
    PLAYER1_RYODO,
    PLAYER1_RYOCHI,
    PLAYER1_JINCHI,
    PLAYER2_KOMA,
    PLAYER2_RYODO,
    PLAYER2_RYOCHI,
    PLAYER2_JINCHI,
    BOTH_RYOCHI
}

