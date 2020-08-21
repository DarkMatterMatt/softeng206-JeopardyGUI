package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HeaderView {
    private final VBox _container = new VBox();

    public HeaderView(GameView parent, GameModel model) {
        Label titleLabel = new Label("Jeopardy!");
        titleLabel.getStyleClass().add("title");

        Label scoreLabel = new Label("Winnings: $" + model.getScore());
        scoreLabel.getStyleClass().add("score");

        _container.getStyleClass().add("header");
        _container.getChildren().addAll(titleLabel, scoreLabel);

        model.getScoreProperty().addListener((observable, oldVal, newVal) -> {
            scoreLabel.setText("Winnings: $" + newVal);
        });
    }

    public Pane getView() {
        return _container;
    }
}
