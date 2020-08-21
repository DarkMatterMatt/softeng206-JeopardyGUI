package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;

public class QuestionView {
    private final StackPane _container = new StackPane();

    public QuestionView(CategoryView categoryView, Question question, int height, int topWidth, int bottomWidth) {
        int bottomLeftX = (topWidth - bottomWidth) / 2;

        Polygon background = new Polygon(0, 0,
                topWidth, 0,
                bottomLeftX + bottomWidth, height,
                bottomLeftX, height);
        background.getStyleClass().add("background");

        Label valueLabel = new Label("$" + question.getValue());
        valueLabel.getStyleClass().add("value");

        _container.getStyleClass().add("question");
        _container.getChildren().addAll(background, valueLabel);
        _container.setOnMouseClicked(ev -> categoryView.askQuestion(question));

        question.getStatusProperty().addListener((observable, oldVal, newVal) -> {
            _container.setDisable(true);
            System.out.println("Question disabled, " + newVal);
        });
    }

    public Pane getView() {
        return _container;
    }
}
