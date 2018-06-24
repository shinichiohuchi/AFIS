package jp.co.jiro.player;

import javafx.scene.control.ToggleGroup;
import jp.co.jiro.cell.*;
import java.util.*;

public class NonPlayer implements Player {
  @Override
  public ToggleGroup getGroup() {
    return null;
  }

  @Override
  public int getPlayerBitFlag() {
    return 0;
  }

  @Override
  public int getFuCount() {
    return 0;
  }

  @Override
  public int getKinCount() {
    return 0;
  }

  @Override
  public int getGinCount() {
    return 0;
  }

  @Override
  public int getKeimaCount() {
    return 0;
  }

  @Override
  public int getKakuCount() {
    return 0;
  }

  @Override
  public int getHishaCount() {
    return 0;
  }

  @Override
  public int getKyoshaCount() {
    return 0;
  }

  @Override
  public int getOuCount() {
    return 0;
  }

  @Override
  public ShougiCellBuilder pollFu() {
    return null;
  }

  @Override
  public ShougiCellBuilder pollKin() {
    return null;
  }

  @Override
  public ShougiCellBuilder pollGin() {
    return null;
  }

  @Override
  public ShougiCellBuilder pollKeima() {
    return null;
  }

  @Override
  public ShougiCellBuilder pollKaku() {
    return null;
  }

  @Override
  public ShougiCellBuilder pollHisha() {
    return null;
  }

  @Override
  public ShougiCellBuilder pollKyosha() {
    return null;
  }

  @Override
  public ShougiCellBuilder pollOu() {
    return null;
  }

  @Override
  public boolean offerFirstFu(ShougiCellBuilder builder) {
    return false;
  }

  @Override
  public boolean offerFirstKin(ShougiCellBuilder builder) {
    return false;
  }

  @Override
  public boolean offerFirstGin(ShougiCellBuilder builder) {
    return false;
  }

  @Override
  public boolean offerFirstKeima(ShougiCellBuilder builder) {
    return false;
  }

  @Override
  public boolean offerFirstKaku(ShougiCellBuilder builder) {
    return false;
  }

  @Override
  public boolean offerFirstHisha(ShougiCellBuilder builder) {
    return false;
  }

  @Override
  public boolean offerFirstKyosha(ShougiCellBuilder builder) {
    return false;
  }

  @Override
  public boolean offerFirstOu(ShougiCellBuilder builder) {
    return false;
  }

  @Override public List<ShougiCell> calcRyodoOfFu     (ShougiCell cell) { return null; };
  @Override public List<ShougiCell> calcRyodoOfKin    ( ShougiCell cell) { return null; };
  @Override public List<ShougiCell> calcRyodoOfGin    ( ShougiCell cell) { return null; };
  @Override public List<ShougiCell> calcRyodoOfKeima  ( ShougiCell cell) { return null; };
  @Override public List<ShougiCell> calcRyodoOfKaku   ( ShougiCell cell, int rowMax, int colMax) { return null; };
  @Override public List<ShougiCell> calcRyodoOfHisha  ( ShougiCell cell, int rowMax, int colMax) { return null; };
  @Override public List<ShougiCell> calcRyodoOfKyosha ( ShougiCell cell, int rowStart) { return null; };
  @Override public List<ShougiCell> calcRyodoOfOu     ( ShougiCell cell) { return null; };
}

