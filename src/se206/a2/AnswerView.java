package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

public class AnswerView {
    private final VBox _outerContainer = new VBox();
    private final Label _categoryNameLabel = new Label();
    private final Label _questionValueLabel = new Label();
    private final Label _questionLabel = new Label();

    public AnswerView(GameModel model) {
        _categoryNameLabel.getStyleClass().add("bold");
        _questionValueLabel.getStyleClass().add("bold");
        _questionLabel.getStyleClass().add("question");

        TextFlow categoryLabel = new TextFlow(new Label("Playing "), _categoryNameLabel, new Label(" for "), _questionValueLabel);
        categoryLabel.getStyleClass().add("category-value");

        AnswerInputView answerInputView = new AnswerInputView(model);

        VBox innerContainer = new VBox();
        innerContainer.getStyleClass().add("answer");
        innerContainer.getChildren().addAll(categoryLabel, _questionLabel, answerInputView.getView());

        _outerContainer.getStyleClass().add("answer-container");
        _outerContainer.getChildren().add(innerContainer);

        questionUpdate(model.getCurrentQuestion());
        model.getCurrentQuestionProperty().addListener((observable, oldVal, newVal) -> questionUpdate(newVal));
    }

    private void questionUpdate(Question q) {
        if (q != null) {
            _categoryNameLabel.setText(q.getCategory().getName());
            _questionValueLabel.setText("$" + q.getValue());
            _questionLabel.setText(q.getQuestion());
        }
    }

    public VBox getView() {
        return _outerContainer;
    }
}
