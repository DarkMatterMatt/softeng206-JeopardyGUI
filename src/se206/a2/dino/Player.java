package se206.a2.dino;

import javafx.scene.Node;

import java.awt.*;

public class Player extends GameObject {
    private static final double GRAVITY = 4800;
    private static final double JUMP_SPEED = 1200;
    private static final int NOT_JUMPING = -1;
    private static double _jumpingForTime = NOT_JUMPING;
    private static final double GROUND_HEIGHT = 8;

    public Player(Shape bounds, Node view) {
        super(bounds, view);
        setX(100);
        setY(GROUND_HEIGHT);
    }

    public void jumpPress() {
        if (getY() == GROUND_HEIGHT) {
            setSpeedY(JUMP_SPEED);
            _jumpingForTime = 0;
        }
    }

    public void jumpRelease() {
        _jumpingForTime = NOT_JUMPING;
    }

    protected void onTick(double secs, double runningSpeed) {
        double y = getY();
        double speedY = getSpeedY();

        // on the ground
        if (speedY < 0 && y <= GROUND_HEIGHT) {
            setY(GROUND_HEIGHT);
            setSpeedY(0);
        }

        if (_jumpingForTime != NOT_JUMPING) {
            _jumpingForTime += secs;
        }

        // long jump (40% gravity reduction), kicks in after 0.05 secs and lasts a max of 0.3 secs
        if (0.05 < _jumpingForTime && _jumpingForTime < 0.35) {
            setSpeedY(speedY - 0.6 * GRAVITY * secs);
        }
        else {
            setSpeedY(speedY - GRAVITY * secs);
        }
    }

    public void tick(double secs) {
        tick(secs, 0);
    }
}
