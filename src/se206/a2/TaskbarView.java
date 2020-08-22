package se206.a2;

import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class TaskbarView {
    private final HBox _container = new HBox();

    public TaskbarView(GameModel model) {
        ImageView exit = new ImageView(new Image(getClass().getResourceAsStream("assets/exit.png")));
        exit.setFitWidth(32);
        exit.setPreserveRatio(true);
        exit.setSmooth(true);
        exit.setCache(true);

        ImageView reset = new ImageView(new Image(getClass().getResourceAsStream("assets/reset.png")));
        reset.setFitWidth(32);
        reset.setPreserveRatio(true);
        reset.setSmooth(true);
        reset.setCache(true);

        Pane exitContainer = new Pane(exit);
        exitContainer.setOnMouseClicked(e -> Platform.exit());
        exitContainer.getStyleClass().add("btn");
        Tooltip.install(exitContainer, new Tooltip("Quit"));

        Pane resetContainer = new Pane(reset);
        resetContainer.setOnMouseClicked(e -> model.reset());
        resetContainer.getStyleClass().add("btn");
        Tooltip.install(resetContainer, new Tooltip("Reset game"));

        _container.getStyleClass().add("taskbar");
        _container.getChildren().addAll(resetContainer, exitContainer);
    }

    public Pane getView() {
        return _container;
    }
}
