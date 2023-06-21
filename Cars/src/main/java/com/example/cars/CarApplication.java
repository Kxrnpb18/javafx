package com.example.cars;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.image.Image;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Stage;

        import java.util.Objects;

public class CarApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("car-view.fxml"));
        AnchorPane root = loader.load();

        CarController controller = loader.getController();
        controller.initialize(null, null); // Optional: Call a method in the controller to perform additional initialization

        // Set the application icon
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/cars/car.jpeg"))));

        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setTitle("Car Lot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
