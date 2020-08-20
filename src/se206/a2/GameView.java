package se206.a2;

import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class GameView {
    private final VBox _container = new VBox();
    private final HeaderView _headerView;
    private final GameModel _model;
    private final TilePane _questionsContainer = new TilePane();

    public GameView(GameModel model) {
        _model = model;

        _headerView = new HeaderView(this, _model);
        _container.getChildren().addAll(_headerView.getView(), _questionsContainer);

        for (Category category : _model.getCategories()) {
            CategoryView view = new CategoryView(this, _model, category.getName());
            _questionsContainer.getChildren().add(view.getView());
        }
    }

    public void askQuestion(String category, int value) {
        System.out.println("askQuestion: " + category + ": " + value);
    }

    public VBox getView() {
        return _container;
    }
}
