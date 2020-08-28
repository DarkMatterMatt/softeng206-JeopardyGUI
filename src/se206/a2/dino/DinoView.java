package se206.a2.dino;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

public class DinoView {
    private final Pane _container = new Pane();
    private final HashMap<Obstacle, ObstacleView> _obstacleViews = new HashMap<>();

    public DinoView(DinoModel model) {
        PlayerView playerView = new PlayerView(model);

        model.getObstacles().addListener((ListChangeListener.Change<? extends Obstacle> change) -> {
            while (change.next()) {
                for (Obstacle obstacle : change.getRemoved()) {
                    ObstacleView ov = _obstacleViews.remove(obstacle);
                    _container.getChildren().remove(ov.getView());
                }
                for (Obstacle o : change.getAddedSubList()) {
                    ObstacleView ov = new ObstacleView(model, o);
                    o.setContainerWidth(_container.getWidth());
                    o.setContainerHeight(_container.getHeight());
                    _obstacleViews.put(o, ov);
                    _container.getChildren().add(ov.getView());
                }
            }
        });

        Rectangle clip = new Rectangle(_container.getWidth(), _container.getHeight());
        _container.setClip(clip);

        _container.heightProperty().addListener((obs, oldVal, newVal) -> {
            double val = newVal.doubleValue();
            // ignore small changes in height, fixes stuttering
            if (Math.abs(oldVal.doubleValue() - val) < 1.5) return;

            clip.setHeight(val);
            model.getPlayer().setContainerHeight(val);
            _obstacleViews.forEach((obstacle, view) -> {
                obstacle.setContainerHeight(val);
            });
        });
        _container.widthProperty().addListener((obs, oldVal, newVal) -> {
            double val = newVal.doubleValue();
            // ignore small changes in height, fixes stuttering
            if (Math.abs(oldVal.doubleValue() - val) < 1.5) return;

            clip.setWidth(val);
            model.getPlayer().setContainerWidth(val);
            _obstacleViews.forEach((obstacle, view) -> {
                obstacle.setContainerWidth(val);
            });
        });

        _container.getChildren().add(playerView.getView());
        _container.getStyleClass().addAll("dino", "border-blue");
        VBox.setVgrow(_container, Priority.ALWAYS);
    }

    public Pane getView() {
        return _container;
    }

}
