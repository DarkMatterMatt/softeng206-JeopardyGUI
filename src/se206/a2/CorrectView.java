package se206.a2;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

/**
 * View for correct answer screen, after a correct answer has been submitted
 */
public class CorrectView {
    private static final int TIMEOUT_SECS = 2;
    private final VBox _container = new VBox();
    private final GameModel _model;
    private final ProgressBar _progressBar = new ProgressBar(0);

    public CorrectView(GameModel model) {
        _model = model;

        Label correctLabel = new Label("Correct!");
        correctLabel.getStyleClass().addAll("bold", "main");

        Label interactToSkipLabel = new Label("Click or press any key to skip...");
        interactToSkipLabel.getStyleClass().add("interact-to-skip");

        _container.getChildren().addAll(correctLabel, interactToSkipLabel, _progressBar);
        _container.getStyleClass().add("correct-view");

        startAnimation();
        model.getStateProperty().addListener((observable, oldVal, newVal) -> startAnimation());

        _container.setOnMouseClicked(e -> model.finishQuestion());
    }

    public VBox getView() {
        return _container;
    }

    /**
     * Progress bar fills linearly over a fixed duration, TIMEOUT_SECS
     */
    private void startAnimation() {
        // only start animation when we are the active view
        if (_model.getState() != GameModel.State.CORRECT_ANSWER) return;

        AnimationTimer timer = new AnimationTimer() {
            private long startTime;

            @Override
            public void handle(long now) {
                if (!_container.isVisible()) {
                    stop();
                    return;
                }

                double elapsedSecs = (now - startTime) / 1e9;
                double progress = elapsedSecs / TIMEOUT_SECS;
                _progressBar.setProgress(progress);

                if (progress >= 1) {
                    // leave this screen when the progress bar is full
                    _model.finishQuestion();
                    stop();
                }
            }

            @Override
            public void start() {
                super.start();
                startTime = System.nanoTime();
            }
        };
        timer.start();
    }
}
