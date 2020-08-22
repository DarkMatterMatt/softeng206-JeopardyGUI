package se206.a2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

        String stylesheet = getClass().getResource("styles/styles.css").toExternalForm();
        Scene scene = new Scene(view.getView());
        scene.getStylesheets().add(stylesheet);

        // horrible key-press detection to skip in/correct screen
        scene.setOnKeyPressed(e -> {
            switch (model.getState()) {
                case CORRECT_ANSWER:
                case INCORRECT_ANSWER:
                    model.finishQuestion();
            }
        });

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Jeopardy!");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("assets/icon.png")));
        primaryStage.setScene(scene);
        primaryStage.show();

        DragAndResizeHelper.addResizeListener(primaryStage);
    }
}
