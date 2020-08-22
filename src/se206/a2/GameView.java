package se206.a2;

import javafx.scene.layout.VBox;

public class GameView {
    private final VBox _container = new VBox();
    private final GameModel _model;

    public GameView(GameModel model) {
        _model = model;
        createViews();

        // listen for when the model is reset, we need to recreate our views for the new data
        model.getStateProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal == GameModel.State.RESET) {
                // reset triggered
                _container.getChildren().clear();
                createViews();
            }
        });
    }

    private void createViews() {
        HeaderView headerView = new HeaderView(_model);
        GameContentView contentView = new GameContentView(_model);

        _container.getChildren().addAll(headerView.getView(), contentView.getView());
        _container.getStyleClass().add("game");
    }

    public VBox getView() {
        return _container;
    }
}
