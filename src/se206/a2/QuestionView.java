package se206.a2;

import javafx.scene.control.Button;

public class QuestionView {
    private final String _categoryName;
    private final CategoryView _categoryView;
    private final Button _elem = new Button();
    private final GameModel _model;
    private final int _value;

    public QuestionView(CategoryView categoryView, GameModel model, String categoryName, int value) {
        _categoryName = categoryName;
        _categoryView = categoryView;
        _model = model;
        _value = value;

        Question question = _model.getQuestion(categoryName, value);

        _elem.setText("$" + question.getValue());
        _elem.prefWidthProperty().bind(categoryView.getView().widthProperty());
        _elem.setOnAction(ev -> categoryView.askQuestion(value));
    }

    public Button getView() {
        return _elem;
    }
}
