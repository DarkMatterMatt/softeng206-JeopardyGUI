package se206.a2.dino;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class DinoView {
    private final Pane _container = new Pane();

    public DinoView(DinoModel model) {
        DeathCounter deathCounter = new DeathCounter(model);

        // add existing game objects
        model.getGameObjects().forEach(o -> {
            o.setContainerWidth(_container.getWidth());
            o.setContainerHeight(_container.getHeight());
            _container.getChildren().add(o.getView());
        });

        // listen for future changes so we can add those game objects too
        model.getGameObjects().addListener((ListChangeListener.Change<? extends GameObject> change) -> {
            while (change.next()) {
                for (GameObject o : change.getRemoved()) {
                    _container.getChildren().remove(o.getView());
                }
                for (GameObject o : change.getAddedSubList()) {
                    o.setContainerWidth(_container.getWidth());
                    o.setContainerHeight(_container.getHeight());
                    _container.getChildren().add(o.getView());
                }
            }
        });

        // hide overflow
        Rectangle clip = new Rectangle(_container.getWidth(), _container.getHeight());
        _container.setClip(clip);

        // listen for change in screen height
        _container.heightProperty().addListener((obs, oldVal, newVal) -> {
            double val = newVal.doubleValue();
            // ignore small changes in height, fixes stuttering
            if (Math.abs(oldVal.doubleValue() - val) < 1.5) return;

            clip.setHeight(val);
            model.getGameObjects().forEach((o) -> o.setContainerHeight(val));
        });

        // listen for change in screen width
        _container.widthProperty().addListener((obs, oldVal, newVal) -> {
            double val = newVal.doubleValue();
            // ignore small changes in height, fixes stuttering
            if (Math.abs(oldVal.doubleValue() - val) < 1.5) return;

            clip.setWidth(val);
            model.getGameObjects().forEach((o) -> o.setContainerWidth(val));
            deathCounter.setContainerWidth(val);
        });

        _container.getChildren().add(deathCounter.getView());
        _container.getStyleClass().add("dino");
        VBox.setVgrow(_container, Priority.ALWAYS);
    }

    public Pane getView() {
        return _container;
    }

}
