package se206.a2;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import se206.a2.dino.DinoModel;
import se206.a2.dino.DinoView;

/**
 * View for all game content, controls which child-view is displayed
 */
public class GameContentView {
    private final AnswerView _answerView;
    private final CategoriesListView _categoriesListView;
    private final StackPane _container = new StackPane();
    private final CorrectView _correctView;
    private final DinoView _dinoView;
    private final IncorrectView _incorrectView;
    private final GameModel _model;

    public GameContentView(GameModel model) {
        _model = model;

        // create views for each game state
        _categoriesListView = new CategoriesListView(_model);
        _answerView = new AnswerView(_model);
        _correctView = new CorrectView(_model);
        _incorrectView = new IncorrectView(_model);
        _dinoView = new DinoView(_model.getDinoModel());
        _container.getChildren().addAll(
                _categoriesListView.getView(),
                _answerView.getView(),
                _correctView.getView(),
                _incorrectView.getView(),
                _dinoView.getView()
        );
        _container.getStyleClass().add("content");

        // listen for when the state changes, we manage which view is shown
        showCorrectView(_model.getState());
        model.getStateProperty().addListener((observable, oldVal, newVal) -> showCorrectView(newVal));
    }

    public StackPane getView() {
        return _container;
    }

    /**
     * Show the right view to match the game state
     */
    private void showCorrectView(GameModel.State currentState) {
        // stop dino game if it isn't being displayed
        DinoModel dinoModel = _model.getDinoModel();
        if (currentState != GameModel.State.GAME_OVER && dinoModel.isRunning()) {
            dinoModel.stopGame();
        }

        switch (currentState) {
            case SELECT_QUESTION:
                showView(_categoriesListView.getView());
                break;
            case ANSWER_QUESTION:
                showView(_answerView.getView());
                break;
            case CORRECT_ANSWER:
                showView(_correctView.getView());
                break;
            case INCORRECT_ANSWER:
                showView(_incorrectView.getView());
                break;
            case GAME_OVER:
                showView(_dinoView.getView());
                if (!dinoModel.isRunning()) {
                    dinoModel.startGame();
                }
                break;
        }
    }

    /**
     * Show a single view and hide all others
     */
    private void showView(Parent show) {
        for (Node n : _container.getChildren()) {
            n.setVisible(n == show);
        }
    }
}
