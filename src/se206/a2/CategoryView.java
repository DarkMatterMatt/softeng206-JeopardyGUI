package se206.a2;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CategoryView {
    private final Category _category;
    private final VBox _container = new VBox();
    private final GameView _gameView;

    public CategoryView(GameView gameView, Category category) {
        _gameView = gameView;
        _category = category;

        String capitalizedName = _category.getName().substring(0, 1).toUpperCase() + _category.getName().substring(1);
        Label nameLabel = new Label(capitalizedName);
        nameLabel.minWidthProperty().bind(_container.widthProperty());
        nameLabel.getStyleClass().add("name");

        _container.getStyleClass().add("category");
        _container.getChildren().add(nameLabel);

        for (Question question : category.getQuestions()) {
            QuestionView view = new QuestionView(this, question);
            _container.getChildren().add(view.getView());
        }
    }

    public void askQuestion(Question question) {
        _gameView.askQuestion(_category, question);
    }

    public VBox getView() {
        return _container;
    }
}
