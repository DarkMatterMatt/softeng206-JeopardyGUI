package se206.a2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameModelDataSource dataSource = new GameModelDataSource("./categories");
        GameModelPersistence modelPersistence = new GameModelPersistence("./.save");
        GameModel model = new GameModel(dataSource, modelPersistence);
        GameView view = new GameView(model);

        String stylesheet = getClass().getResource("styles.css").toExternalForm();
        Scene scene = new Scene(view.getView(), 800, 700);
        scene.getStylesheets().add(stylesheet);

        primaryStage.setTitle("Jeopardy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
