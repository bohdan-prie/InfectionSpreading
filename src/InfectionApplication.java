import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import infection.data.Cell;
import infection.data.HealthyCell;
import infection.data.InfectedCell;
import infection.data.ImmunityCell;
import infection.service.InfectionService;

import java.util.ResourceBundle;

public class InfectionApplication extends Application {

    @FXML private Button initButton;
    @FXML private GridPane cellsMatrix;
    @FXML private ResourceBundle resources;

    private final InfectionService infectionService = new InfectionService();

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void start(Stage primaryStage) {

        initButton.setOnAction(event -> initButton.setText("test"));

        // print to enter size between 0 and 1002
        try {
            infectionService.init(1001);
        } catch (IllegalArgumentException e) {
            // print to user to enter another size
            System.out.println("Something went wrong");
            return;
        }
        // print to enter step between 0 and 100
        Cell[][] matrix = infectionService.getMatrixInSomeSteps(99);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                Cell cell = matrix[i][j];

                String color = null;

                if (cell instanceof InfectedCell) {
                    color = "r";
                }
                if (cell instanceof ImmunityCell) {
                    color = "b";
                }
                if (cell instanceof HealthyCell) {
                    color = "g";
                }

                System.out.print(color + "  ");
            }
            System.out.println();
        }


        Label label = new Label("test");
        label.setPrefWidth(100);

        FlowPane root = new FlowPane(label);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        //primaryStage.setWidth(250);
        //primaryStage.setHeight(200);

        primaryStage.show();
    }
}
