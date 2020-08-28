package se206.a2.dino;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class DinoView {
    private final Pane _container = new Pane();
    private final HashMap<Obstacle, ObstacleView> _obstacleViews = new HashMap<>();

    public DinoView(DinoModel model) {
        DinoHelper.invertY(_container);

        PlayerView playerView = new PlayerView(model);

        model.getObstacles().forEach(obstacle -> _obstacleViews.put(obstacle, new ObstacleView(model, obstacle)));
        model.getObstacles().addListener((ListChangeListener.Change<? extends Obstacle> change) -> {
            while (change.next()) {
                for (Obstacle obstacle : change.getRemoved()) {
                    ObstacleView ov = _obstacleViews.remove(obstacle);
                    _container.getChildren().remove(ov.getView());
                }
                for (Obstacle o : change.getAddedSubList()) {
                    ObstacleView ov = new ObstacleView(model, o);
                    _obstacleViews.put(o, ov);
                    _container.getChildren().add(ov.getView());
                }
            }
        });

        _container.getChildren().add(playerView.getView());
        _container.getStyleClass().addAll("dino", "border-blue");
        VBox.setVgrow(_container, Priority.ALWAYS);
    }

    public Pane getView() {
        return _container;
    }

}
