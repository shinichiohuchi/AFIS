package jp.co.afis;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jp.co.afis.bean.Position;
import jp.co.afis.cell.*;
import jp.co.afis.control.MyLabel;
import jp.co.afis.cell.Attack;
import jp.co.afis.model.AppliableStatus;
import jp.co.afis.model.Board;
import jp.co.afis.model.Game;
import jp.co.afis.model.cell.Cell;
import jp.co.afis.model.cell.CellStatus;
import jp.co.afis.player.NonPlayer;
import jp.co.afis.player.Player;
import jp.co.afis.player.Player2;
import jp.co.afis.player.Player1;
import jp.co.afis.util.AlertUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML
    private AnchorPane boardGridPaneParent;
    @FXML
    private GridPane boardGridPane;

    @FXML
    private MenuItem newItem;

    @FXML
    private ToggleGroup komaToggleGroup;

    @FXML
    private ToggleGroup komaToggleGroup2;

    @FXML
    private Label player1ScoreLabel;

    @FXML
    private Label player2ScoreLabel;

    @FXML
    private RadioButton fuRadioButton;
    @FXML
    private RadioButton kinRadioButton;
    @FXML
    private RadioButton ginRadioButton;
    @FXML
    private RadioButton keimaRadioButton;
    @FXML
    private RadioButton kakuRadioButton;
    @FXML
    private RadioButton hishaRadioButton;
    @FXML
    private RadioButton kyoshaRadioButton;
    @FXML
    private RadioButton ouRadioButton;

    @FXML
    private RadioButton fuRadioButton2;
    @FXML
    private RadioButton kinRadioButton2;
    @FXML
    private RadioButton ginRadioButton2;
    @FXML
    private RadioButton keimaRadioButton2;
    @FXML
    private RadioButton kakuRadioButton2;
    @FXML
    private RadioButton hishaRadioButton2;
    @FXML
    private RadioButton kyoshaRadioButton2;
    @FXML
    private RadioButton ouRadioButton2;

    @FXML
    private Label fuLabel;
    @FXML
    private Label kinLabel;
    @FXML
    private Label ginLabel;
    @FXML
    private Label keimaLabel;
    @FXML
    private Label kakuLabel;
    @FXML
    private Label hishaLabel;
    @FXML
    private Label kyoshaLabel;
    @FXML
    private Label ouLabel;

    @FXML
    private Label fuLabel2;
    @FXML
    private Label kinLabel2;
    @FXML
    private Label ginLabel2;
    @FXML
    private Label keimaLabel2;
    @FXML
    private Label kakuLabel2;
    @FXML
    private Label hishaLabel2;
    @FXML
    private Label kyoshaLabel2;
    @FXML
    private Label ouLabel2;

    private Game game;

    @FXML
    private void initialize() {

    }

    /**
     * 将棋盤を生成する。
     */
    @FXML
    private void newItemOnAction() {
        TextInputDialog dialog = new TextInputDialog("9 9");
        dialog.setHeaderText("生成する将棋盤の行数を入力してください。");
        Optional<String> opt = dialog.showAndWait();
        if (!opt.isPresent()) {
            return;
        }

        String result = opt.orElse("9 9");
        result = result
                .replaceAll("１", "1")
                .replaceAll("２", "2")
                .replaceAll("３", "3")
                .replaceAll("４", "4")
                .replaceAll("５", "5")
                .replaceAll("６", "6")
                .replaceAll("７", "7")
                .replaceAll("８", "8")
                .replaceAll("９", "9")
                .replaceAll("０", "0")
                .replaceAll("　", " ")
                ;

        String[] arr = result.split(" +");
        if (arr.length < 2) {
            AlertUtil.showAlert("入力が不正でした");
            return;
        }

        final String REG = "^\\d*$";
        if (!arr[0].matches(REG) || !arr[1].matches(REG)) {
            AlertUtil.showAlert("入力が不正でした [" + arr[0] + " " + arr[1] + "]");
            return;
        }

        komaToggleGroup.selectToggle(komaToggleGroup.getToggles().get(0));
        komaToggleGroup2.selectToggle(komaToggleGroup2.getToggles().get(0));

        int row = Integer.parseInt(arr[0]);
        int col = Integer.parseInt(arr[1]);
        createBoard(row, col);
        updateDisplay();
    }

    @FXML
    private void closeOnAction() {
        Platform.exit();
    }

    @FXML
    private void skipMenuItemOnAction() {
        final String CR = System.lineSeparator();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("自分のターンをスキップして、相手のターンになります。" + CR + "本当にスキップしますか？");
        Optional<ButtonType> opt = alert.showAndWait();
        opt.ifPresent(b -> {
            if (b == ButtonType.YES) {
//                if (playerTurnList == null || playerTurnList.size() == 0) {
//                    return;
//                }
//
//                Player p = playerTurnList.poll();
//                playerTurnList.add(p);
//                updateDisplay();
            }
        });
    }

    private void createBoard(int row, int col) {
        game = new Game(new Board(row, col));

        final double W = 50.0;
        final double H = 50.0;
        boardGridPane = new GridPane();
        boardGridPane.setGridLinesVisible(true);
        AnchorPane.setTopAnchor(boardGridPane, 10.0);
        AnchorPane.setLeftAnchor(boardGridPane, 10.0);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                MyLabel label = new MyLabel(i, j);
                label.setPrefWidth(W);
                label.setPrefHeight(H);
                label.setStyle("-fx-font-size: 20px;");
                label.setOnMouseClicked(e -> {
                    int r = label.getRow();
                    int c = label.getCol();

                    AppliableStatus result = game.click(new Position(r, c));
                    switch (result) {
                        case OK:
                            break;
                        case KOMA_EXISTS:
                            AlertUtil.showAlert("駒の上には配置できません。");
                            break;
                        case RYODO_EXISTS:
                            AlertUtil.showAlert("領土の上には配置できません。");
                            break;
                        case NOT_OWN_AREA:
                            AlertUtil.showAlert("駒または領土に隣接しないマスに配置できません");
                            break;
                    }

                    Cell cell = game.getBoard().getCell(new Position(r, c));
                    String text = game.getBoard().getCellText(cell);
                    label.setText(text);
                    updateDisplay();
                });
                boardGridPane.add(label, j, i);
            }
        }

        boardGridPaneParent.getChildren().clear();
        boardGridPaneParent.getChildren().add(boardGridPane);
    }

    /**
     * セルの状態を判定して背景色を変更する。
     */
    void updateDisplay() {
        //String playerName = playerTurnList.getFirst() instanceof Player1 ? "先手" : "後手";

        // ターン表示
//        Stage stage = (Stage) boardGridPane.getScene().getWindow();
//        stage.setTitle(Main.TITLE + " : " + playerName);


//        int colCount = board.getCol();
        Cell[][] cells = game.getBoard().getCells();
        int colCount = cells[0].length;

        // 将棋盤セルの背景色を変更する
//        List<Node> nodeList = boardGridPane.getChildren();
//        ShougiCell[][] cells = board.getCells();
//        int player1Score = 0;
//        int player2Score = 0;

        List<Node> nodeList = boardGridPane.getChildren();
        int player1Score = 0;
        int player2Score = 0;

        // 駒と領土の描画
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Cell cell = cells[i][j];
                int index = calcArrayIndex(i + 1, j + 1, colCount);

                jp.co.afis.model.player.Player player = game.getPlayers().getCurrentPlayer();
                CellStatus status = cell.getStatus().getPlayer1();
                Node node = nodeList.get(index);

                if (status == CellStatus.Fu) {
                    if (player instanceof jp.co.afis.model.player.Player1) {
                        player1Score++;
                        node.setStyle("-fx-background-color: #1e90ff;");
                    } else if (player instanceof jp.co.afis.model.player.Player2) {
                        player2Score++;
                        node.setStyle("-fx-background-color: #cd5c5c;");
                    }
                } else if (status == CellStatus.Ryodo) {
                    if (player instanceof jp.co.afis.model.player.Player1) {
                        player1Score++;
                        node.setStyle("-fx-background-color: #87ceeb;");
                    } else if (player instanceof jp.co.afis.model.player.Player2) {
                        player2Score++;
                        node.setStyle("-fx-background-color: #f08080;");
                    }
                } else if (status == CellStatus.Ryochi) {
                } else if (status == CellStatus.Jinchi) {
                    node.setStyle("-fx-background-color: #a9a9a9;");
                } else {
                    node.setStyle("-fx-background-color: white;");
                }


//                if (!(cell instanceof Attack || cell instanceof Ryodo)) {
//                    int cellFlag = cell.getClickableBitFlag();
//                    if (0 < cellFlag) {
//                        Player nextPlayer = playerTurnList.get(0);
//                        int playerFlag = nextPlayer.getPlayerBitFlag();
//                        if ((cellFlag & playerFlag) == playerFlag) {
//                            node.setStyle("-fx-background-color: #f0e68c;");
//                        }
//                    }
//                }
            }
        }

        fuLabel.setText("" + game.getPlayers().getPlayer1().getFuCount());
        kinLabel.setText("" + game.getPlayers().getPlayer1().getKinCount());
        ginLabel.setText("" + game.getPlayers().getPlayer1().getGinCount());
        keimaLabel.setText("" + game.getPlayers().getPlayer1().getKeimaCount());
        kyoshaLabel.setText("" + game.getPlayers().getPlayer1().getKyoshaCount());
        hishaLabel.setText("" + game.getPlayers().getPlayer1().getHishaCount());
        kakuLabel.setText("" + game.getPlayers().getPlayer1().getKakuCount());
        ouLabel.setText("" + game.getPlayers().getPlayer1().getOuCount());

        fuLabel2.setText("" + game.getPlayers().getPlayer2().getFuCount());
        kinLabel2.setText("" + game.getPlayers().getPlayer2().getKinCount());
        ginLabel2.setText("" + game.getPlayers().getPlayer2().getGinCount());
        keimaLabel2.setText("" + game.getPlayers().getPlayer2().getKeimaCount());
        kyoshaLabel2.setText("" + game.getPlayers().getPlayer2().getKyoshaCount());
        hishaLabel2.setText("" + game.getPlayers().getPlayer2().getHishaCount());
        kakuLabel2.setText("" + game.getPlayers().getPlayer2().getKakuCount());
        ouLabel2.setText("" + game.getPlayers().getPlayer2().getOuCount());

        // 得点を更新する。
        player1ScoreLabel.setText("" + player1Score);
        player2ScoreLabel.setText("" + player2Score);

        // 勝敗の判定
//        int rowLen = board.getCells().length;
//        int colLen = board.getCells()[0].length;
//        int totalCellCount = rowLen * colLen;
//        int totalPlayerScore = player1Score + player2Score;
//        if (totalCellCount <= totalPlayerScore) {
//            String winner = player1Score < player2Score ? "後手" : "先手";
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setHeaderText("ゲーム終了！");
//            final String CR = System.lineSeparator();
//            String text =
//                    "先手の得点 : " + player1Score + CR
//                    + "後手の得点 : " + player2Score + CR + CR
//                    + "勝者は " + winner + " です！"
//                    ;
//            alert.setContentText(text);
//            alert.showAndWait();
//        }
    }

    private int calcArrayIndex(int row, int col, int colMax) {
        return row * colMax - (colMax - col);
    }

    /**
     * 渡された文字と等しい駒ビルダーを返却する。
     * @param komaText
     * @param player
     * @return
     */
    private ShougiCellBuilder pollSelectedShougiCellBuilder(String komaText, Player player) {
        komaText = komaText.substring(0,1);
        switch (komaText) {
            case "歩":
                return player.pollFu();
            case "金":
                return player.pollKin();
            case "銀":
                return player.pollGin();
            case "桂":
                return player.pollKeima();
            case "角":
                return player.pollKaku();
            case "飛":
                return player.pollHisha();
            case "香":
                return player.pollKyosha();
            case "王":
                return player.pollOu();
        }
        return null;
    }

    /**
     * 駒ビルダーを元のリストに返却する。
     * 一度取り出したビルダーをもとに戻すために使う。
     * @param builder
     * @param komaText
     * @param player
     * @return
     */
    private boolean offerFirstSelectedShougiCellBuilder(ShougiCellBuilder builder, String komaText, Player player) {
        komaText = komaText.substring(0,1);
        switch (komaText) {
            case "歩":
                return player.offerFirstFu(builder);
            case "金":
                return player.offerFirstKin(builder);
            case "銀":
                return player.offerFirstGin(builder);
            case "桂":
                return player.offerFirstKeima(builder);
            case "角":
                return player.offerFirstKaku(builder);
            case "飛":
                return player.offerFirstHisha(builder);
            case "香":
                return player.offerFirstKyosha(builder);
            case "王":
                return player.offerFirstOu(builder);
        }
        return false;
    }
}
