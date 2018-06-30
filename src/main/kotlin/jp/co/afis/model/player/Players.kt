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
) {
    fun attack(board: Board, pos: Position) {
        currentPlayer.attack(board, pos)
    }

    /**
     * hasEnoughCountOfKomas は現在のプレイヤーの現在の駒の残数を判定し、十分な数が存在するかを返します。
     * @return 十分か、否か
     */
    fun hasEnoughCountOfKomas() : Boolean {
        val count = currentPlayer.getCountOfKomas()
        return 0 < count
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
        currentPlayer.currentAttackType = komaType
    }
}

