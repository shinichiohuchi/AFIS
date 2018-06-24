package jp.co.jiro.player;

import javafx.scene.control.ToggleGroup;
import jp.co.jiro.cell.*;

import java.util.*;

public class Player1 implements Player {

    private final ToggleGroup group;
    private LinkedList<ShougiCellBuilder> fuList;
    private LinkedList<ShougiCellBuilder> kinList;
    private LinkedList<ShougiCellBuilder> ginList;
    private LinkedList<ShougiCellBuilder> keimaList;
    private LinkedList<ShougiCellBuilder> kakuList;
    private LinkedList<ShougiCellBuilder> hishaList;
    private LinkedList<ShougiCellBuilder> kyoshaList;
    private LinkedList<ShougiCellBuilder> ouList;

    public static final int BIT_FLAG = 1;

    public Player1(int rowMax, int colMax, ToggleGroup group) {
        this.group = group;

        fuList = new LinkedList<ShougiCellBuilder>() {{
            for (int i = 0; i < 9; i++) {
                add((row, col) -> new Fu(row, col, new Player1(rowMax, colMax, group)));
            }
        }};

        kinList = new LinkedList<ShougiCellBuilder>() {{
            for (int i = 0; i < 2; i++) {
                add((row, col) -> new Kin(row, col, new Player1(rowMax, colMax, group)));
            }
        }};

        ginList = new LinkedList<ShougiCellBuilder>() {{
            for (int i = 0; i < 2; i++) {
                add((row, col) -> new Gin(row, col, new Player1(rowMax, colMax, group)));
            }
        }};

        keimaList = new LinkedList<ShougiCellBuilder>() {{
            for (int i = 0; i < 2; i++) {
                add((row, col) -> new Keima(row, col, new Player1(rowMax, colMax, group)));
            }
        }};

        kakuList = new LinkedList<ShougiCellBuilder>() {{
            for (int i = 0; i < 1; i++) {
                add((row, col) -> new Kaku(row, col, new Player1(rowMax, colMax, group), rowMax, colMax));
            }
        }};

        hishaList = new LinkedList<ShougiCellBuilder>() {{
            for (int i = 0; i < 1; i++) {
                add((row, col) -> new Hisha(row, col, new Player1(rowMax, colMax, group), rowMax, colMax));
            }
        }};

        kyoshaList = new LinkedList<ShougiCellBuilder>() {{
            for (int i = 0; i < 2; i++) {
                add((row, col) -> new Kyosha(row, col, new Player1(rowMax, colMax, group), rowMax));
            }
        }};

        ouList = new LinkedList<ShougiCellBuilder>() {{
            for (int i = 0; i < 1; i++) {
                add((row, col) -> new Ou(row, col, new Player1(rowMax, colMax, group)));
            }
        }};

    }

    @Override
    public ToggleGroup getGroup() {
        return group;
    }

    @Override
    public int getPlayerBitFlag() {
        return BIT_FLAG;
    }

    @Override
    public int getFuCount() {
        return fuList.size();
    }

    @Override
    public int getKinCount() {
        return kinList.size();
    }

    @Override
    public int getGinCount() {
        return ginList.size();
    }

    @Override
    public int getKeimaCount() {
        return keimaList.size();
    }

    @Override
    public int getKakuCount() {
        return kakuList.size();
    }

    @Override
    public int getHishaCount() {
        return hishaList.size();
    }

    @Override
    public int getKyoshaCount() {
        return kyoshaList.size();
    }

    @Override
    public int getOuCount() {
        return ouList.size();
    }

    @Override
    public ShougiCellBuilder pollFu() {
        return fuList.poll();
    }

    @Override
    public ShougiCellBuilder pollKin() {
        return kinList.poll();
    }

    @Override
    public ShougiCellBuilder pollGin() {
        return ginList.poll();
    }

    @Override
    public ShougiCellBuilder pollKeima() {
        return keimaList.poll();
    }

    @Override
    public ShougiCellBuilder pollKaku() {
        return kakuList.poll();
    }

    @Override
    public ShougiCellBuilder pollHisha() {
        return hishaList.poll();
    }

    @Override
    public ShougiCellBuilder pollKyosha() {
        return kyoshaList.poll();
    }

    @Override
    public ShougiCellBuilder pollOu() {
        return ouList.poll();
    }

    @Override
    public boolean offerFirstFu(ShougiCellBuilder builder) {
        return fuList.offerFirst(builder);
    }

    @Override
    public boolean offerFirstKin(ShougiCellBuilder builder) {
        return kinList.offerFirst(builder);
    }

    @Override
    public boolean offerFirstGin(ShougiCellBuilder builder) {
        return ginList.offerFirst(builder);
    }

    @Override
    public boolean offerFirstKeima(ShougiCellBuilder builder) {
        return keimaList.offerFirst(builder);
    }

    @Override
    public boolean offerFirstKaku(ShougiCellBuilder builder) {
        return kakuList.offerFirst(builder);
    }

    @Override
    public boolean offerFirstHisha(ShougiCellBuilder builder) {
        return hishaList.offerFirst(builder);
    }

    @Override
    public boolean offerFirstKyosha(ShougiCellBuilder builder) {
        return kyoshaList.offerFirst(builder);
    }

    @Override
    public boolean offerFirstOu(ShougiCellBuilder builder) {
        return ouList.offerFirst(builder);
    }

    @Override
    public List<ShougiCell> calcRyodoOfFu(ShougiCell cell) {
        return PlayerUtil.calcRyodoOfFu(cell, this, 1);
    }

    @Override
    public List<ShougiCell> calcRyodoOfKin(ShougiCell cell) {
        return PlayerUtil.calcRyodoOfKin(cell, this, cell.getRow() + 1);
    }

    @Override
    public List<ShougiCell> calcRyodoOfGin(ShougiCell cell) {
        return PlayerUtil.calcRyodoOfGin(cell, this, cell.getRow() + 1);
    }

    @Override
    public List<ShougiCell> calcRyodoOfKeima(ShougiCell cell) {
        return PlayerUtil.calcRyodoOfKeima(cell, this, cell.getRow() - 2);
    }

    @Override
    public List<ShougiCell> calcRyodoOfKaku(ShougiCell cell, int rowMax, int colMax) {
        return PlayerUtil.calcRyodoOfKaku(cell, this, rowMax, colMax);
    }

    @Override
    public List<ShougiCell> calcRyodoOfHisha(ShougiCell cell, int rowMax, int colMax) {
        return PlayerUtil.calcRyodoOfHisha(cell, this, rowMax, colMax);
    }

    @Override
    public List<ShougiCell> calcRyodoOfKyosha(ShougiCell cell, int rowMax) {
        return PlayerUtil.calcRyodoOfKyosha(cell, this, 0, cell.getRow());
    }

    @Override
    public List<ShougiCell> calcRyodoOfOu(ShougiCell cell) {
        return PlayerUtil.calcRyodoOfOu(cell, this);
    }


}

