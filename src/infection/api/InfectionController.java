package infection.api;

import infection.data.Cell;
import infection.data.HealthyCell;
import infection.data.ImmunityCell;
import infection.data.InfectedCell;
import infection.service.InfectionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class InfectionController {

    private final static float GRID_SIZE = 800F;

    @FXML private GridPane gridMatrix;
    @FXML private Button clearMatrix;
    @FXML private TextField stepsSizeInput;

    private Rectangle[][] rectangles;

    private final InfectionService infectionService;

    public InfectionController() {
        infectionService = InfectionService.getInstance();
    }

    @FXML
    public void initialize() {
        while (gridMatrix.getColumnConstraints().size() > 0) {
            gridMatrix.getColumnConstraints().remove(0);
        }
        gridMatrix.setAlignment(Pos.BOTTOM_LEFT);

        Cell[][] matrix = infectionService.getMatrix();
        rectangles = new Rectangle[matrix.length][matrix.length];

        float elementSize = GRID_SIZE / matrix.length;
        gridMatrix.setPrefWidth(GRID_SIZE);
        gridMatrix.setPrefHeight(GRID_SIZE);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Rectangle rectangle = new Rectangle(elementSize, elementSize);
                Cell cell = matrix[i][j];
                rectangles[i][j] = rectangle;
                this.changeRectangleColor(cell, rectangle);
                gridMatrix.add(rectangle, j, i);
            }
        }
    }

    @FXML
    private void clearMatrix() {
        infectionService.clearMatrix();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/scenes/MainScene.fxml")));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) clearMatrix.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadNextStep() {
        this.loadMatrix(1);
    }

    @FXML
    private void loadMatrixInSteps() {
        String textSize = stepsSizeInput.getText();
        int steps;
        try {
            steps = Integer.parseInt(textSize);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Steps size format error");
            alert.setContentText("Steps size is not valid, try again");

            alert.showAndWait();
            return;
        }

        this.loadMatrix(steps);
    }

    private void loadMatrix(int steps) {
        Cell[][] matrix;

        try {
            matrix = infectionService.getMatrixInSomeSteps(steps);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Steps size error");
            alert.setContentText("Steps size is not valid");

            alert.showAndWait();
            return;
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Rectangle rectangle = rectangles[i][j];
                Cell cell = matrix[i][j];
                this.changeRectangleColor(cell, rectangle);
            }
        }
    }

    private void changeRectangleColor(Cell cell, Rectangle rectangle) {
        if (cell instanceof HealthyCell) {
            rectangle.setFill(Color.GREEN);
        } else if (cell instanceof ImmunityCell) {
            rectangle.setFill(Color.BLUE);
        } else if (cell instanceof InfectedCell) {
            rectangle.setFill(Color.RED);
        }
    }
}
