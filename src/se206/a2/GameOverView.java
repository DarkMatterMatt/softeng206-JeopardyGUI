package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View for game over screen, after all questions have been attempted
 */
public class GameOverView {
    private final VBox _container = new VBox();
    private final GameModel _model;
    private final Label _subtitleLabel = new Label();
    private final Label _titleLabel = new Label();

    public GameOverView(GameModel model) {
        _model = model;

        _titleLabel.getStyleClass().addAll("bold", "title");
        _subtitleLabel.getStyleClass().addAll("subtitle");

        Label dinoLabel = new Label("Play Minigame");
        dinoLabel.getStyleClass().addAll("play-dino", "btn", "bold");
        dinoLabel.setOnMouseClicked(ev -> _model.startDinoGame());

        Label resetLabel = new Label("Reset");
        resetLabel.getStyleClass().addAll("reset", "btn", "bold");
        resetLabel.setOnMouseClicked(ev -> _model.reset());

        HBox btnContainer = new HBox(resetLabel, dinoLabel);
        btnContainer.getStyleClass().add("btn-container");

        _container.getChildren().addAll(_titleLabel, _subtitleLabel, btnContainer);
        _container.getStyleClass().add("game-over-view");

        update();
        model.getStateProperty().addListener((observable, oldVal, newVal) -> update());
    }

    public VBox getView() {
        return _container;
    }

    /**
     * Progress bar fills linearly over a fixed duration, TIMEOUT_SECS
     */
    private void update() {
        // only update data when we are the active view
        if (_model.getState() != GameModel.State.GAME_OVER) return;

        int score = _model.getScore();
        if (score > 0) {
            _titleLabel.setText("Congratulations!");
            _subtitleLabel.setText("You won $" + score + "!");
        }
        else {
            _titleLabel.setText("Welp.");
            _subtitleLabel.setText("That was pretty terrible. Your score was " + score + ".");
        }
    }
}
