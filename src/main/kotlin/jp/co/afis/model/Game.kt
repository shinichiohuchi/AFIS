package jp.co.afis.model

import jp.co.afis.bean.Position
import jp.co.afis.model.cell.CellStatus
import jp.co.afis.model.cell.KomaType
import jp.co.afis.model.player.*
import java.io.*
import java.text.SimpleDateFormat
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
    /** 配置可能な場所なし */
    NO_CLICKABLE_POSITIONS,
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

/**
 * createGameWithConfig は設定ファイルでGameクラスを生成する。
 * @param playConfigPath ゲームプレイの設定ファイルパス
 * @param komaConfigFile 駒の数を設定ファイルパス
 * @param row 将棋盤の行数
 * @param col 将棋盤の列数
 * @return Gameクラス
 * @throws NumberFormatException
 */
internal fun createGameWithConfig(playConfigPath: String, komaConfigFile: String, row: Int, col: Int): Game {
    val prop = Properties()

    // CPUと対戦モード
    prop.load(FileInputStream(playConfigPath))
    val vsCPUFlag = prop.getProperty("vsCPU").toBoolean()

    // プレイ結果をログに出力
    val useLogging = prop.getProperty("loggin").toBoolean()
    val logDir = prop.getProperty("logDir")
    val dateFormat = prop.getProperty("dateFormat")
    val now = SimpleDateFormat(dateFormat).format(Date())
    val logFilePath = "$logDir/$now.csv"
    val encoding = prop.getProperty("encoding")

    // 駒の個数
    prop.load(FileInputStream(komaConfigFile))
    var fu: Int
    var kin: Int
    var gin: Int
    var keima: Int
    var kyosha: Int
    var hisha: Int
    var kaku: Int
    var ou: Int
    try {
        fu = prop.getProperty("fu").toInt()
        kin = prop.getProperty("kin").toInt()
        gin = prop.getProperty("gin").toInt()
        keima = prop.getProperty("keima").toInt()
        kyosha = prop.getProperty("kyosha").toInt()
        hisha = prop.getProperty("hisha").toInt()
        kaku = prop.getProperty("kaku").toInt()
        ou = prop.getProperty("ou").toInt()
    } catch (e: NumberFormatException) {
        throw NumberFormatException("駒の数の設定値が半角英数字ではありませんでした。\n設定ファイルを見直してください。")
    }

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
    ), board = Board(row, col)
            , vsCPU = vsCPUFlag
            , useLoggin = useLogging
            , logFilePath = logFilePath
            , encoding = encoding
    )

    game.openLogger()
    game.write("Player,Row,Col,AttackType,ClickResult,Player1Score,Player2Score")
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
 * @param vsCPU CPUによる自動攻撃を有効化する
 */
class Game(val players: Players = Players(), val board: Board = Board(9, 9), val vsCPU: Boolean = false, val useLoggin: Boolean = false, val logFilePath: String? = null, val encoding: String = "UTF-8") {
    constructor(board: Board) : this(players = Players(), board = board)

    var writer: PrintWriter? = null

    fun openLogger() {
        if (useLoggin) {
            if (logFilePath != null) {
                val f = File(logFilePath)
                val fos = FileOutputStream(f)
                val osw = OutputStreamWriter(fos, encoding)
                val bw = BufferedWriter(osw)
                writer = PrintWriter(bw)
            }
        }
    }

    fun write(text: String) {
        if (useLoggin) writer?.println(text)
    }

    fun closeLogger() {
        if (useLoggin) {
            writer?.close()
            writer = null
        }
    }

    /**
     * attack は指定の位置のセルをクリックする。
     * クリックを正常に完了できた場合は、ターンが切り替わり、
     * 次にクリックしたときは相手プレイヤーとしてクリックすることになる。
     * @param pos クリック位置
     */
    fun attack(pos: Position): AppliableStatus {
        val appliable = calcAppliable(board, pos, players, players.currentPlayer)
        val cp = players.currentPlayer
        val currentPlayerName = if (cp is Player1) "Player1" else "Player2"
        write("$currentPlayerName,${pos.row},${pos.col},${players.currentPlayer.currentAttackType},$appliable,${calcPlayer1Score()},${calcPlayer2Score()}")

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
     * click は指定の位置のセルをクリックする。
     * クリックを正常に完了できた場合は、ターンが切り替わり、
     * 次にクリックしたときは相手プレイヤーとしてクリックすることになる。
     * @param pos クリック位置
     */
    fun click(pos: Position): AppliableStatus {
        val appliable = attack(pos)
        return when (appliable) {
            AppliableStatus.OK -> {
                // 対戦CPUモードがtrueのときはそのままCPUが攻撃をする
                if (!vsCPU)
                    return appliable

                // クリック可能な位置があれば後続の処理を実施
                if (notExistAppliablePositions()) {
                    players.switchCurrentPlayer()
                    return AppliableStatus.NO_CLICKABLE_POSITIONS
                }
                // クリック可能な位置のリストを取得し、ランダムに選択
                val poses = getAppliablePositionsOfCurrentPlayer()
                val rand = Random()
                val randIndex = rand.nextInt(poses.size)
                val pos = poses[randIndex]
                attack(pos)
            }
            else -> appliable
        }
    }

    /**
     * notExistAppliablePositions は配置可能な駒が存在しないかどうかを判定する。
     * @return 配置可能か否か
     */
    fun notExistAppliablePositions(): Boolean {
        return getAppliablePositionsOfCurrentPlayer().isEmpty()
    }

    /**
     * getAppliablePositionsOfCurrentPlayer は現在のプレイヤーのクリック可能な位置リストを返す。
     * @return クリック可能な位置リスト
     */
    fun getAppliablePositionsOfCurrentPlayer(): List<Position> {
        return if (players.currentPlayer is Player1)
            board.getAppliablePositionsOfPlayer1()
        else
            board.getAppliablePositionsOfPlayer2()
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
        // それぞれの点数を取得
        val player1Score = board.calcPlayer1Score()
        val player2Score = board.calcPlayer2Score()

        // セルの数と、両者の点数を足したときに
        // 一致しなければまだゲームが終了していないものとする
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
            CellStatus.Jinchi -> return CellDispStatus.PLAYER2_JINCHI
        }

        // 領地の判定は優先度が最低なのであえて前のwhen式では判定していない
        when (cell.status.player1) {
            CellStatus.Ryochi -> return CellDispStatus.PLAYER1_RYOCHI
        }
        when (cell.status.player2) {
            CellStatus.Ryochi -> return CellDispStatus.PLAYER2_RYOCHI
        }

        return CellDispStatus.NONE
    }

    /**
     * skip は現在の自分のターンをスキップして、カレントプレイヤーを切り換えます。
     */
    fun skip(): AppliableStatus {
        players.switchCurrentPlayer()

        // TODO: ここから先はコピペなんでなんとかしたい
        // 対戦CPUモードがtrueのときはそのままCPUが攻撃をする
        if (!vsCPU)
            return AppliableStatus.OK

        // クリック可能な位置があれば後続の処理を実施
        if (notExistAppliablePositions()) {
            players.switchCurrentPlayer()
            return AppliableStatus.NO_CLICKABLE_POSITIONS
        }
        // クリック可能な位置のリストを取得し、ランダムに選択
        val poses = getAppliablePositionsOfCurrentPlayer()
        val rand = Random()
        val randIndex = rand.nextInt(poses.size)
        val pos = poses[randIndex]
        return attack(pos)
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

