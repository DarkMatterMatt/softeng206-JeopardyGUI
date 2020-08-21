package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AnswerView {
    private final VBox _outerContainer = new VBox();

    public AnswerView(GameModel model) {

        Question question = model.getCurrentQuestion();

        Label categoryLabel = new Label("Playing Animals for $" + question.getValue());
        categoryLabel.getStyleClass().add("category");

        Label questionLabel = new Label(question.getQuestion());
        questionLabel.getStyleClass().add("question");

        AnswerInputView answerInputView = new AnswerInputView(model);

        VBox innerContainer = new VBox();
        innerContainer.getStyleClass().add("answer");
        innerContainer.getChildren().addAll(categoryLabel, questionLabel, answerInputView.getView());

        _outerContainer.getStyleClass().add("answer-container");
        _outerContainer.getChildren().add(innerContainer);
    }

    public VBox getView() {
        return _outerContainer;
    }
}
