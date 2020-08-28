package se206.a2.dino;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class ObstacleView {
    private final Pane _container = new Pane();

    public ObstacleView(DinoModel model, Obstacle obstacle) {
        Rectangle rectangle = new Rectangle(obstacle.getWidth(), obstacle.getHeight());
        rectangle.getStyleClass().add("fill-red");
        _container.setLayoutX(obstacle.getX());
        _container.setLayoutY(obstacle.getY());

        obstacle.getXProperty().addListener((observe, oldVal, newVal) -> _container.setLayoutX(newVal.doubleValue()));
        obstacle.getYProperty().addListener((observe, oldVal, newVal) -> _container.setLayoutY(newVal.doubleValue()));

        _container.getChildren().add(rectangle);
    }

    public Pane getView() {
        return _container;
    }
}
