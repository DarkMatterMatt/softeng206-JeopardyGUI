package se206.a2;

import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class GameView {
    private final VBox _container = new VBox();
    private final GameModel _model;

    public GameView(GameModel model) {
        _model = model;
        createViews();

        // listen for when the model is reset, we need to recreate our views for the new data
        model.getNeedsResetProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal) {
                // only recreate views when reset triggered becomes true
                _container.getChildren().clear();
                createViews();
            }
        });
    }

    public void askQuestion(Category category, Question question) {
        _model.askQuestion(category.getName(), question.getValue());
        _model.answerQuestion("kiwi");
    }

    private void createViews() {
        TilePane categoriesContainer = new TilePane();
        categoriesContainer.getStyleClass().add("categories");

        HeaderView headerView = new HeaderView(this, _model);

        _container.getChildren().addAll(headerView.getView(), categoriesContainer);
        _container.getStyleClass().add("game");

        for (Category category : _model.getCategories()) {
            CategoryView view = new CategoryView(this, category);
            categoriesContainer.getChildren().add(view.getView());
        }
    }

    public VBox getView() {
        return _container;
    }
}
