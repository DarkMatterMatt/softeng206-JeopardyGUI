package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class HeaderView {
    private final Pane _container = new Pane();
    private final GameModel _model;
    private final Label _scoreLabel = new Label();

    public HeaderView(GameView parent, GameModel model) {
        _model = model;

        _scoreLabel.prefWidthProperty().bind(parent.getView().widthProperty());
        _scoreLabel.getStyleClass().add("score");
        _container.getStyleClass().add("header");
        _container.getChildren().add(_scoreLabel);
        draw();
    }

    public void draw() {
        String s = "$" + _model.getScore();
        _scoreLabel.setText(s);
    }

    public Pane getView() {
        return _container;
    }
}
