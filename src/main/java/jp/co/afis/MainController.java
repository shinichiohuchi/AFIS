package jp.co.afis;

import com.sun.prism.impl.ps.CachingEllipseRep;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jp.co.afis.bean.Position;
import jp.co.afis.control.MyLabel;
import jp.co.afis.model.*;
import jp.co.afis.model.cell.Cell;
import jp.co.afis.model.cell.KomaType;
import jp.co.afis.model.player.Player1;
import jp.co.afis.model.player.Winner;
import jp.co.afis.util.AlertUtil;

import java.io.File;
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
        komaToggleGroup.selectedToggleProperty().addListener(e -> {
            RadioButton radio = (RadioButton) komaToggleGroup.getSelectedToggle();
            String text = radio.getText().substring(0, 1);

            switch (text) {
                case "歩":
                    game.getPlayers().getPlayer1().setCurrentAttackType(KomaType.FU);
                    break;
                case "金":
                    game.getPlayers().getPlayer1().setCurrentAttackType(KomaType.KIN);
                    break;
                case "銀":
                    game.getPlayers().getPlayer1().setCurrentAttackType(KomaType.GIN);
                    break;
                case "桂":
                    game.getPlayers().getPlayer1().setCurrentAttackType(KomaType.KEIMA);
                    break;
                case "角":
                    game.getPlayers().getPlayer1().setCurrentAttackType(KomaType.KAKU);
                    break;
                case "飛":
                    game.getPlayers().getPlayer1().setCurrentAttackType(KomaType.HISHA);
                    break;
                case "香":
                    game.getPlayers().getPlayer1().setCurrentAttackType(KomaType.KYOSHA);
                    break;
                case "王":
                    game.getPlayers().getPlayer1().setCurrentAttackType(KomaType.OU);
                    break;
            }
        });

        komaToggleGroup2.selectedToggleProperty().addListener(e -> {
            RadioButton radio = (RadioButton) komaToggleGroup2.getSelectedToggle();
            String text = radio.getText().substring(0, 1);

            switch (text) {
                case "歩":
                    game.getPlayers().getPlayer2().setCurrentAttackType(KomaType.FU);
                    break;
                case "金":
                    game.getPlayers().getPlayer2().setCurrentAttackType(KomaType.KIN);
                    break;
                case "銀":
                    game.getPlayers().getPlayer2().setCurrentAttackType(KomaType.GIN);
                    break;
                case "桂":
                    game.getPlayers().getPlayer2().setCurrentAttackType(KomaType.KEIMA);
                    break;
                case "角":
                    game.getPlayers().getPlayer2().setCurrentAttackType(KomaType.KAKU);
                    break;
                case "飛":
                    game.getPlayers().getPlayer2().setCurrentAttackType(KomaType.HISHA);
                    break;
                case "香":
                    game.getPlayers().getPlayer2().setCurrentAttackType(KomaType.KYOSHA);
                    break;
                case "王":
                    game.getPlayers().getPlayer2().setCurrentAttackType(KomaType.OU);
                    break;
            }
        });
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
                AppliableStatus result = game.skip();
                if (result == AppliableStatus.NO_CLICKABLE_POSITIONS)
                    AlertUtil.showAlert("プレイヤー2にクリック可能な位置がないのでスキップしました"); // TODO
                updateDisplay();
            }
        });
    }

    private void createBoard(int row, int col) {
        String playPropPath = "./config/play.properties";
        String komaPropPath = "./config/koma.properties";
        boolean playPropExists = new File(playPropPath).exists();
        boolean komaPropExists = new File(komaPropPath).exists();
        if (playPropExists && komaPropExists) {
            game = GameKt.createGameWithConfig(playPropPath, komaPropPath, row, col);
        } else {
            game = new Game(new Board(row, col));
        }

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
                            Cell cell = game.getBoard().getCell(new Position(r, c));
                            String text = game.getBoard().getCellText(cell);
                            label.setText(text);
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
                        case NO_KOMAS:
                            AlertUtil.showAlert("駒の数が不足しています。");
                            break;
                        case NO_CLICKABLE_POSITIONS:
                            AlertUtil.showAlert("プレイヤー2に配置可能な場所がなかったためスキップしました。");
                            break;
                        default:
                            AlertUtil.showAlert("不明なステータスです。" + result);

                    }
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
        String playerName = game.getPlayers().getCurrentPlayer() instanceof Player1 ? "先手" : "後手";

        // ターン表示
        Stage stage = (Stage) boardGridPane.getScene().getWindow();
        stage.setTitle(Main.TITLE + " : " + playerName);

        Cell[][] cells = game.getBoard().getCells();
        int colCount = cells[0].length;

        List<Node> nodeList = boardGridPane.getChildren();

        // セルの色の初期化
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                int index = calcArrayIndex(i + 1, j + 1, colCount);
                Node node = nodeList.get(index);
                node.setStyle("-fx-background-color: white;");
            }
        }

        // 駒と領土の描画
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                int index = calcArrayIndex(i + 1, j + 1, colCount);

                Node node = nodeList.get(index);
                jp.co.afis.model.player.Player currentPlayer = game.getPlayers().getCurrentPlayer();

                CellDispStatus dispStatus = game.getCellDispStatus(new Position(i, j));
                switch (dispStatus) {
                    case PLAYER1_KOMA:
                        node.setStyle("-fx-background-color: #1e90ff;");
                        break;
                    case PLAYER1_RYODO:
                        node.setStyle("-fx-background-color: #87ceeb;");
                        break;
                    case PLAYER1_RYOCHI:
                        if (currentPlayer instanceof jp.co.afis.model.player.Player1)
                            node.setStyle("-fx-background-color: yellow;");
                        break;
                    case PLAYER1_JINCHI:
                        if (currentPlayer instanceof jp.co.afis.model.player.Player1)
                            node.setStyle("-fx-background-color: #a9a9a9;");
                        break;
                    case PLAYER2_KOMA:
                        node.setStyle("-fx-background-color: #cd5c5c;");
                        break;
                    case PLAYER2_RYODO:
                        node.setStyle("-fx-background-color: #f08080;");
                        break;
                    case PLAYER2_RYOCHI:
                        if (currentPlayer instanceof jp.co.afis.model.player.Player2)
                            node.setStyle("-fx-background-color: yellow;");
                        break;
                    case PLAYER2_JINCHI:
                        if (currentPlayer instanceof jp.co.afis.model.player.Player2)
                            node.setStyle("-fx-background-color: #a9a9a9;");
                        break;
                    case BOTH_RYOCHI:
                        node.setStyle("-fx-background-color: yellow;");
                        break;
                }
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
        int player1Score = game.calcPlayer1Score();
        int player2Score = game.calcPlayer2Score();
        player1ScoreLabel.setText("" + player1Score);
        player2ScoreLabel.setText("" + player2Score);

        // セルのテキストを更新する。
        int r = 0;
        for (Cell[] cs : game.getBoard().getCells()) {
            int c = 0;
            for (Cell cell:cs) {
                int index = calcArrayIndex(r + 1, c + 1, colCount);
                MyLabel lbl = (MyLabel) boardGridPane.getChildren().get(index);
                Cell boardCell = game.getBoard().getCells()[r][c];
                lbl.setText(boardCell.getStatus().getPlayer1().getText());
                //lbl.setText(boardCell.getStatus().getPlayer2().getText());
                c++;
            }
            r++;
        }


        // 勝敗の判定
        showWinner(player1Score, player2Score);
    }

    private void showWinner(int player1Score, int player2Score) {
        Winner winner = game.getWinner();
        if (winner == Winner.NONE)
            return;

        game.write("Winner," + winner);
        game.closeLogger();

        if (winner == Winner.PLAYER1 || winner == Winner.PLAYER2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ゲーム終了！");
            final String CR = System.lineSeparator();
            String text =
                    "先手の得点 : " + player1Score + CR
                            + "後手の得点 : " + player2Score + CR + CR
                            + "勝者は " + winner + " です！";
            alert.setContentText(text);
            alert.showAndWait();
            return;
        }

        if (winner == Winner.SAME_SCORE) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ゲーム終了！");
            final String CR = System.lineSeparator();
            String text =
                    "先手の得点 : " + player1Score + CR
                            + "後手の得点 : " + player2Score + CR + CR
                            + "同点です！";
            alert.setContentText(text);
            alert.showAndWait();
        }
    }

    private int calcArrayIndex(int row, int col, int colMax) {
        return row * colMax - (colMax - col);
    }

}
