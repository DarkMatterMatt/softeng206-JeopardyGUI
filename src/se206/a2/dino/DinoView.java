package se206.a2.dino;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;

public class DinoView {
    private final Pane _container = new Pane();
    private final HashMap<GameObject, GameObjectView> _gameObjectViews = new HashMap<>();

    public DinoView(DinoModel model) {
        GameObjectView playerView = new GameObjectView(model, model.getPlayer());
        GameObjectView backgroundView = new GameObjectView(model, model.getBackground());
        DeathCounterView deathCounterView = new DeathCounterView(model);

        model.getGameObjects().addListener((ListChangeListener.Change<? extends GameObject> change) -> {
            while (change.next()) {
                for (GameObject o : change.getRemoved()) {
                    GameObjectView ov = _gameObjectViews.remove(o);
                    _container.getChildren().remove(ov.getView());
                }
                for (GameObject o : change.getAddedSubList()) {
                    GameObjectView ov = new GameObjectView(model, o);
                    o.setContainerWidth(_container.getWidth());
                    o.setContainerHeight(_container.getHeight());
                    _gameObjectViews.put(o, ov);
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
            model.getBackground().setContainerHeight(val);
            _gameObjectViews.forEach((obstacle, view) -> {
                obstacle.setContainerHeight(val);
            });
        });
        _container.widthProperty().addListener((obs, oldVal, newVal) -> {
            double val = newVal.doubleValue();
            // ignore small changes in height, fixes stuttering
            if (Math.abs(oldVal.doubleValue() - val) < 1.5) return;

            clip.setWidth(val);
            model.getPlayer().setContainerWidth(val);
            model.getBackground().setContainerWidth(val);
            _gameObjectViews.forEach((obstacle, view) -> {
                obstacle.setContainerWidth(val);
            });
            deathCounterView.setContainerWidth(val);
        });

        _container.getChildren().addAll(backgroundView.getView(), playerView.getView(), deathCounterView.getView());
        _container.getStyleClass().addAll("dino");
        VBox.setVgrow(_container, Priority.ALWAYS);
    }

    public Pane getView() {
        return _container;
    }

}
