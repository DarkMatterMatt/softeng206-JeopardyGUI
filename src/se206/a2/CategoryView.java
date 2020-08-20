package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CategoryView {
    private final VBox _container = new VBox();
    private final GameView _gameView;
    private final GameModel _model;
    private final String _name;

    public CategoryView(GameView gameView, GameModel model, String name) {
        _gameView = gameView;
        _model = model;
        _name = name;

        String capitalizedName = _name.substring(0, 1).toUpperCase() + _name.substring(1);
        Label nameLabel = new Label(capitalizedName);
        nameLabel.minWidthProperty().bind(_container.widthProperty());
        _container.getChildren().add(nameLabel);

        Category category = _model.getCategory(_name);
        for (Question question : category.getQuestions()) {
            QuestionView view = new QuestionView(this, _model, category.getName(), question.getValue());
            _container.getChildren().add(view.getView());
        }
    }

    public void askQuestion(int value) {
        _gameView.askQuestion(_name, value);
    }

    public VBox getView() {
        return _container;
    }
}
