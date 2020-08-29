package se206.a2.dino;

import javafx.scene.Node;

import java.awt.*;

public class Player extends GameObject {
    private static final double GRAVITY = 4000;
    private static final double JUMP_SPEED = 1200;

    public Player(Shape bounds, Node view) {
        super(bounds, view);
        setX(100);
    }

    public void jump() {
        setSpeedY(JUMP_SPEED);
    }

    public void tick(double secs) {
        tick(secs, 0);
    }

    protected void onTick(double secs, double runningSpeed) {
        double y = getY();
        double speedY = getSpeedY();

        // on the ground
        if (speedY < 0 && y <= 0) {
            setY(0);
            setSpeedY(0);
        }

        setSpeedY(speedY - GRAVITY * secs);
    }
}
