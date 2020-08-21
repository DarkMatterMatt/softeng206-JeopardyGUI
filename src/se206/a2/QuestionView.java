package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;

public class QuestionView {
    private final StackPane _container = new StackPane();

    public QuestionView(GameModel model, Question question, int height, int topWidth, int bottomWidth) {
        int bottomLeftX = (topWidth - bottomWidth) / 2;

        Polygon background = new Polygon(0, 0,
                topWidth, 0,
                bottomLeftX + bottomWidth, height,
                bottomLeftX, height);
        background.getStyleClass().add("background");

        Label valueLabel = new Label("$" + question.getValue());
        valueLabel.getStyleClass().add("value");

        _container.getStyleClass().addAll("question", "btn");
        _container.getChildren().addAll(background, valueLabel);
        _container.setOnMouseClicked(ev -> model.askQuestion(question));

        updateStatus(question.getStatus());
        question.getStatusProperty().addListener((observable, oldVal, newVal) -> updateStatus(newVal));
    }

    public void updateStatus(Question.Status status) {
        switch (status) {
            case UNATTEMPTED:
                _container.getStyleClass().removeAll("correct", "incorrect");
                _container.setDisable(false);
                break;
            case CORRECT:
                _container.getStyleClass().add("correct");
                _container.setDisable(true);
                break;
            case INCORRECT:
                _container.getStyleClass().add("incorrect");
                _container.setDisable(true);
                break;
        }
    }

    public Pane getView() {
        return _container;
    }
}
