package jp.co.afis.model.cell

import jp.co.afis.bean.Position
import jp.co.afis.model.Board

interface AttackStrategy {
    fun attack(board: Board, pos: Position)
}

class Player1Attack : AttackStrategy {
    override fun attack(board: Board, pos: Position) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class Player2Attack : AttackStrategy {
    override fun attack(board: Board, pos: Position) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
