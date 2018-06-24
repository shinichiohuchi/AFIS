package jp.co.afis.player;

import javafx.scene.control.ToggleGroup;
import jp.co.afis.cell.*;
import java.util.*;

public interface Player {
  ToggleGroup getGroup();
  int getPlayerBitFlag();
  int getFuCount();
  int getKinCount();
  int getGinCount();
  int getKeimaCount();
  int getKakuCount();
  int getHishaCount();
  int getKyoshaCount();
  int getOuCount();

  ShougiCellBuilder pollFu();
  ShougiCellBuilder pollKin();
  ShougiCellBuilder pollGin();
  ShougiCellBuilder pollKeima();
  ShougiCellBuilder pollKaku();
  ShougiCellBuilder pollHisha();
  ShougiCellBuilder pollKyosha();
  ShougiCellBuilder pollOu();

  boolean offerFirstFu(ShougiCellBuilder builder);
  boolean offerFirstKin(ShougiCellBuilder builder);
  boolean offerFirstGin(ShougiCellBuilder builder);
  boolean offerFirstKeima(ShougiCellBuilder builder);
  boolean offerFirstKaku(ShougiCellBuilder builder);
  boolean offerFirstHisha(ShougiCellBuilder builder);
  boolean offerFirstKyosha(ShougiCellBuilder builder);
  boolean offerFirstOu(ShougiCellBuilder builder);

  List<ShougiCell> calcRyodoOfFu     ( ShougiCell cell);
  List<ShougiCell> calcRyodoOfKin    ( ShougiCell cell);
  List<ShougiCell> calcRyodoOfGin    ( ShougiCell cell);
  List<ShougiCell> calcRyodoOfKeima  ( ShougiCell cell);
  List<ShougiCell> calcRyodoOfKaku   ( ShougiCell cell, int rowMax, int colMax);
  List<ShougiCell> calcRyodoOfHisha  ( ShougiCell cell, int rowMax, int colMax);
  List<ShougiCell> calcRyodoOfKyosha ( ShougiCell cell, int rowStart);
  List<ShougiCell> calcRyodoOfOu     ( ShougiCell cell);
}
