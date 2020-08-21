package se206.a2;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HeaderView {
    private final VBox _container = new VBox();

    public HeaderView(GameView parent, GameModel model) {
        ReadOnlyDoubleProperty parentWidth = parent.getView().widthProperty();

        Label titleLabel = new Label("Jeopardy");
        titleLabel.prefWidthProperty().bind(parentWidth);
        titleLabel.getStyleClass().add("title");

        Label scoreLabel = new Label("Winnings: $" + model.getScore());
        scoreLabel.prefWidthProperty().bind(parentWidth);
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
