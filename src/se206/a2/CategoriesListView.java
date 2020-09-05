package se206.a2;

import javafx.scene.layout.TilePane;

/**
 * View that displays all categories in tiles
 */
public class CategoriesListView {
    private final TilePane _container = new TilePane();

    public CategoriesListView(GameModel model) {
        _container.getStyleClass().add("categories");

        for (Category category : model.getCategories()) {
            CategoryView view = new CategoryView(model, category);
            _container.getChildren().add(view.getView());
        }
    }

    public TilePane getView() {
        return _container;
    }
}
