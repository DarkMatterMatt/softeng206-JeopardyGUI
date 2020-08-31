package se206.a2.dino;

import javafx.scene.layout.Pane;

public class GameObjectView {
    private final Pane _container = new Pane();

    public GameObjectView(DinoModel model, GameObject obj) {
        _container.setLayoutX(obj.getLayoutX());
        _container.setLayoutY(obj.getLayoutY());

        obj.getLayoutXProperty().addListener((observe, oldVal, newVal) -> _container.setLayoutX(newVal.doubleValue()));
        obj.getLayoutYProperty().addListener((observe, oldVal, newVal) -> _container.setLayoutY(newVal.doubleValue()));

        _container.getChildren().add(obj.getView());
    }

    public Pane getView() {
        return _container;
    }
}
