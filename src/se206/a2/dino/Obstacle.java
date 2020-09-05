package se206.a2.dino;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

import java.awt.*;

public class Obstacle extends GameObject {
    private static final int FLASH_DURATION_MS = 70;
    private static final int NUM_FLASHES = 3;
    private boolean _hasCollided = false;

    public Obstacle(Shape bounds, Node view) {
        super(bounds, view);
        getView().setViewOrder(-20);
        setX(5000);
    }

    public boolean hasCollided() {
        return _hasCollided;
    }

    @Override
    protected void onCollision(GameObject other) {
        super.onCollision(other);

        if (_hasCollided) return;
        _hasCollided = true;

        // flashing animation
        Timeline collisionAnimation = new Timeline(new KeyFrame(
                Duration.millis(FLASH_DURATION_MS),
                event -> getView().setVisible(!getView().isVisible())
        ));
        // teleport off screen after flashing has finished
        collisionAnimation.setOnFinished(event -> setX(-1500));
        collisionAnimation.setCycleCount(NUM_FLASHES * 2);
        collisionAnimation.play();
    }

    @Override
    protected void onTick(double secs, double runningSpeed) {
        super.onTick(secs, runningSpeed);

        // move to edge of container screen
        if (getX() > getContainerWidth()) {
            setX(getContainerWidth());
        }
    }
}
