package se206.a2.dino;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class DinoView {
    private static final int FADE_OUT_MS = 2000;
    private final Pane _container = new Pane();

    public DinoView(DinoModel model) {
        DeathCounter deathCounter = new DeathCounter(model);
        deathCounter.getView().setViewOrder(-10);

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

        // listen for the game finishing, we fade out
        model.getGameFinishingProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                FadeTransition ft = new FadeTransition(Duration.millis(FADE_OUT_MS), _container);
                ft.setFromValue(1.0);
                ft.setToValue(0);

                // after fade out, have one second of black screen, then we're done
                ft.setOnFinished(ev -> {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> model.finishGame());
                    pause.play();
                });
                ft.play();
            }
        });

        _container.getChildren().add(deathCounter.getView());
        _container.getStyleClass().add("dino");
        VBox.setVgrow(_container, Priority.ALWAYS);
    }

    public Pane getView() {
        return _container;
    }

}
