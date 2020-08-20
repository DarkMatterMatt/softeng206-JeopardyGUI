package se206.a2;

import javafx.scene.control.Button;

public class QuestionView {
    private final CategoryView _categoryView;
    private final Button _elem = new Button();
    private final Question _question;

    public QuestionView(CategoryView categoryView, Question question) {
        _categoryView = categoryView;
        _question = question;

        _elem.setText("$" + question.getValue());
        _elem.prefWidthProperty().bind(categoryView.getView().widthProperty());
        _elem.setOnAction(ev -> categoryView.askQuestion(question));

        _question.getStatusProperty().addListener((observable, oldVal, newVal) -> {
            _elem.setDisable(true);
            System.out.println("Question disabled, " + newVal);
        });
    }

    public Button getView() {
        return _elem;
    }
}
