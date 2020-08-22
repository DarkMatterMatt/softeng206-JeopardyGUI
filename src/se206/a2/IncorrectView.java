package se206.a2;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class IncorrectView {
    private static final int TIMEOUT_SECS = 5;
    private final Label _answerLabel = new Label();
    private final VBox _container = new VBox();
    private final GameModel _model;
    private final ProgressBar _progressBar = new ProgressBar(0);

    public IncorrectView(GameModel model) {
        _model = model;

        Label incorrectLabel = new Label("Incorrect!");
        Label answerPrefixLabel = new Label("The correct answer was ");
        Label answerSuffixLabel = new Label(".");

        TextFlow answerText = new TextFlow(answerPrefixLabel, _answerLabel, answerSuffixLabel);

        answerText.getStyleClass().add("text-flow");
        incorrectLabel.getStyleClass().addAll("bold", "main");
        _answerLabel.getStyleClass().add("bold");

        Label interactToSkipLabel = new Label("Click or press any key to skip...");
        interactToSkipLabel.getStyleClass().add("interact-to-skip");

        _container.getChildren().addAll(incorrectLabel, answerText, interactToSkipLabel, _progressBar);
        _container.getStyleClass().add("incorrect-view");

        startAnimation();
        model.getStateProperty().addListener((observable, oldVal, newVal) -> startAnimation());

        questionUpdate(model.getCurrentQuestion());
        model.getCurrentQuestionProperty().addListener((observable, oldVal, newVal) -> questionUpdate(newVal));

        _container.setOnMouseClicked(e -> model.finishQuestion());
    }

    public VBox getView() {
        return _container;
    }

    private void questionUpdate(Question q) {
        if (q != null) {
            _answerLabel.setText(q.getAnswer());
        }
    }

    private void startAnimation() {
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
