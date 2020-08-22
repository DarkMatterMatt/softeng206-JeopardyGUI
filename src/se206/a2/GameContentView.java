package se206.a2;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class GameContentView {
    private final AnswerView _answerView;
    private final CategoriesListView _categoriesListView;
    private final StackPane _container = new StackPane();
    private final GameModel _model;

    public GameContentView(GameModel model) {
        _model = model;

        _categoriesListView = new CategoriesListView(_model);
        _answerView = new AnswerView(_model);
        _container.getChildren().addAll(_categoriesListView.getView(), _answerView.getView());

        // listen for when the state changes, we manage which view is shown
        showCorrectView(_model.getState());
        model.getStateProperty().addListener((observable, oldVal, newVal) -> {
            showCorrectView(newVal);
        });
    }

    public StackPane getView() {
        return _container;
    }

    private void showCorrectView(GameModel.State currentState) {
        switch (currentState) {
            case SELECT_QUESTION:
                showView(_categoriesListView.getView());
                break;
            case ANSWER_QUESTION:
                showView(_answerView.getView());
                break;
        }
    }

    private void showView(Parent show) {
        for (Node n : _container.getChildren()) {
            n.setVisible(n == show);
        }
    }
}
