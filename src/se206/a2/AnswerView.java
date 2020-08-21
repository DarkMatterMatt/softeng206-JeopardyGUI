package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class AnswerView {
    private final VBox _outerContainer = new VBox();

    public AnswerView(GameModel model) {
        Question question = model.getCurrentQuestion();

        Label categoryNameLabel = new Label(question.getCategory().getName());
        categoryNameLabel.getStyleClass().add("bold");
        Label questionValueLabel = new Label("$" + question.getValue());
        questionValueLabel.getStyleClass().add("bold");

        TextFlow categoryLabel = new TextFlow(new Label("Playing "), categoryNameLabel, new Label(" for "), questionValueLabel);
        categoryLabel.getStyleClass().add("category-value");

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
