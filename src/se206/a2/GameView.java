package se206.a2;

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

    private void createViews() {
        CategoriesListView categoriesListView = new CategoriesListView(_model);
        HeaderView headerView = new HeaderView(_model);

        _container.getChildren().addAll(headerView.getView(), categoriesListView.getView());
        _container.getStyleClass().add("game");
    }

    public VBox getView() {
        return _container;
    }
}
