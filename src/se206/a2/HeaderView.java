package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * View for the header. Contains taskbar, title, score
 */
public class HeaderView {
    private final VBox _container = new VBox();

    public HeaderView(GameModel model) {
        // title
        Label titleLabel = new Label("Jeopardy!");
        titleLabel.getStyleClass().add("title");

        // score
        Label scoreLabel = new Label("Winnings: $" + model.getScore());
        scoreLabel.getStyleClass().add("score");

        // taskbar
        TaskbarView taskbar = new TaskbarView(model);

        _container.getStyleClass().add("header");
        _container.getChildren().addAll(taskbar.getView(), titleLabel, scoreLabel);

        // update score automatically
        model.getScoreProperty().addListener((observable, oldVal, newVal) -> scoreLabel.setText("Winnings: $" + newVal));
    }

    public Pane getView() {
        return _container;
    }
}
