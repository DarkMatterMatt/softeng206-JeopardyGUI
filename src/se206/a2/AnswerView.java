package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * View for answer screen, when a question has been selected
 */
public class AnswerView {
    private final Label _categoryNameLabel = new Label();
    private final VBox _container = new VBox();
    private final Label _questionLabel = new Label();
    private final Label _questionValueLabel = new Label();

    public AnswerView(GameModel model) {
        _categoryNameLabel.getStyleClass().add("bold");
        _questionValueLabel.getStyleClass().add("bold");
        _questionLabel.getStyleClass().add("question");

        TextFlow categoryLabel = new TextFlow(new Label("Playing "), _categoryNameLabel, new Label(" for "), _questionValueLabel);
        categoryLabel.getStyleClass().add("category-value");

        AnswerInputView answerInputView = new AnswerInputView(model);

        _container.getStyleClass().add("answer");
        _container.getChildren().addAll(categoryLabel, _questionLabel, answerInputView.getView());

        questionUpdate(model.getCurrentQuestion());
        model.getCurrentQuestionProperty().addListener((observable, oldVal, newVal) -> questionUpdate(newVal));
    }

    public VBox getView() {
        return _container;
    }

    /**
     * Update display to show question details
     */
    private void questionUpdate(Question q) {
        if (q != null) {
            _categoryNameLabel.setText(q.getCategory().getName());
            _questionValueLabel.setText("$" + q.getValue());
            _questionLabel.setText(q.getQuestion());
        }
    }
}
