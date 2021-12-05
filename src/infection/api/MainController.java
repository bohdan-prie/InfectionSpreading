package infection.api;

import infection.service.InfectionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML private TextField matrixSizeInput;

    private final InfectionService infectionService;

    public MainController() {
        infectionService = InfectionService.getInstance();
    }

    @FXML
    private void initMatrix() {
        String textSize = matrixSizeInput.getText();

        int size;
        try {
            size = Integer.parseInt(textSize);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Number format error");
            alert.setContentText("Number is not valid, try again");

            alert.showAndWait();
            return;
        }

        try {
            infectionService.init(size);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Number error");
            alert.setContentText("Number is not odd or not in given range, try again");

            alert.showAndWait();
            return;
        }

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/InfectionScene.fxml")));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) matrixSizeInput.getScene().getWindow();
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Some error");
            alert.setContentText("Some error happened");

            alert.showAndWait();
        }
    }
}
