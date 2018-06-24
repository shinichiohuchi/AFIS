package jp.co.jiro.util;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static final void showAlert(String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
