package se206.a2.dino;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class PlayerView {
    private final Pane _container = new Pane();

    public PlayerView(DinoModel model) {
        Player p = model.getPlayer();

        Rectangle rectangle = new Rectangle(50, 100);
        rectangle.getStyleClass().add("fill-red");
        _container.setLayoutX(p.getX());
        _container.setLayoutY(p.getY());

        p.getXProperty().addListener((observe, oldVal, newVal) -> _container.setLayoutX(newVal.doubleValue()));
        p.getYProperty().addListener((observe, oldVal, newVal) -> _container.setLayoutY(newVal.doubleValue()));

        _container.getChildren().add(rectangle);
    }

    public Pane getView() {
        return _container;
    }
}
