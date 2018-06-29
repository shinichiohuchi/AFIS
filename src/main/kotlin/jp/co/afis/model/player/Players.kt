package jp.co.afis.model.player

import jp.co.afis.bean.Position
import jp.co.afis.model.Board
import jp.co.afis.model.cell.KomaType

class IllegalPlayerException(message: String) : Exception(message)

/**
 * getSwitchedPlayer はtargetを判定して引数のプレイヤーを切り換えます。
 * @param target 判定対象
 * @param p1 返す対象1
 * @param p2 返す対象2
 * @return 切り換え後プレイヤー
 */
internal fun getSwitchedPlayer(target: Player, p1: Player1, p2: Player2): Player {
    return when (target) {
        is Player1 -> p2
        is Player2 -> p1
        else -> throw IllegalPlayerException("不正なプレイヤーを受け取りました。")
    }
}

/**
 * getSwitchedAttack は駒タイプに応じた攻撃駒を返します。
 * @param komaType 駒タイプ
 * @param player プレイヤー
 * @return 攻撃方法
 */
internal fun getSwitchedAttack(komaType: KomaType, player: Player): (Board, Position) -> Unit {
    return when (komaType) {
        KomaType.FU -> player::attackWithFu
        KomaType.KIN -> player::attackWithKin
        KomaType.GIN -> player::attackWithGin
        KomaType.KEIMA -> player::attackWithKeima
        KomaType.KYOSHA -> player::attackWithKyosha
        KomaType.HISHA -> player::attackWithHisha
        KomaType.KAKU -> player::attackWithKaku
        KomaType.OU -> player::attackWithOu
    }
}

/**
 * Players は先手、後手プレイヤーを管理します。
 *
 * @constructor
 * プレイやを生成します。
 *
 * @param player1 先手
 * @param player2 後手
 * @param currentPlayer 現在のプレイヤー
 */
class Players(
        val player1: Player1 = Player1()
        , val player2: Player2 = Player2()
        , var currentPlayer: Player = player1
        , private var currentAttackStrategy: (Board, Position) -> Unit = currentPlayer::attackWithFu
) {
    fun attack(board: Board, pos: Position) {
        currentAttackStrategy(board, pos)
    }

    fun hasEnoughCountOfKomas() : Boolean {
        return true
    }

    /**
     * switchCurrentPlayer は現在のターンのプレイヤーを切り換えます。
     */
    fun switchCurrentPlayer() {
        currentPlayer = getSwitchedPlayer(currentPlayer, player1, player2)
    }

    /**
     * switchAttackStrategy は攻撃方法を切り換えます。
     * @param komaType 駒タイプ
     */
    fun switchAttackStrategy(komaType: KomaType) {
        currentAttackStrategy = getSwitchedAttack(komaType, currentPlayer)
    }
}

