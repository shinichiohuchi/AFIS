package jp.co.afis.control;

import javafx.scene.control.Label;

public class MyLabel extends Label {
    private final int row;
    private final int col;

    public MyLabel(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
