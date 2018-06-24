package jp.co.jiro.cell;

import jp.co.jiro.player.Player;

public interface ShougiCellBuilder {
    ShougiCell create(int row, int col);
}
