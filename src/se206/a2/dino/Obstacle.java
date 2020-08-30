package se206.a2.dino;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

import java.awt.*;

public class Obstacle extends GameObject {
    private static final int FLASH_DURATION_MS = 70;
    private static final int NUM_FLASHES = 3;
    private boolean hasCollided = false;

    public Obstacle(Shape bounds, Node view) {
        super(bounds, view);
        setX(1500);
    }

    @Override
    protected void onCollision(GameObject other) {
        super.onCollision(other);

        if (hasCollided) return;
        hasCollided = true;

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
}
