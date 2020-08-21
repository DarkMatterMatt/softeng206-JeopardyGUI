package se206.a2;

import javafx.scene.control.Button;

public class QuestionView {
    private final Button _elem = new Button();

    public QuestionView(CategoryView categoryView, Question question) {
        _elem.setText("$" + question.getValue());
        _elem.prefWidthProperty().bind(categoryView.getView().widthProperty());
        _elem.getStyleClass().add("question");
        _elem.setOnAction(ev -> categoryView.askQuestion(question));

        question.getStatusProperty().addListener((observable, oldVal, newVal) -> {
            _elem.setDisable(true);
            System.out.println("Question disabled, " + newVal);
        });
    }

    public Button getView() {
        return _elem;
    }
}
