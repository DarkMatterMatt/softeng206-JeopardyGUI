package se206.a2.dino;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;

public class Player extends GameObject {
    private static final double GRAVITY = 4800;
    private static final double GROUND_HEIGHT = 8;
    private static final double JUMP_SPEED = 1200;
    private static final int NOT_JUMPING = -1;
    private static double _jumpingForTime = NOT_JUMPING;
    private final KeyDownTracker _keyDownTracker = KeyDownTracker.getInstance();
    private KeyCode _jumpKey = null;

    public Player(Shape bounds, Node view) {
        super(bounds, view);
        setX(100);
        setY(GROUND_HEIGHT);
        _keyDownTracker.addPressListener(this::jumpPress, KeyCode.SPACE, KeyCode.UP, KeyCode.W, KeyCode.NUMPAD8);
        _keyDownTracker.addReleaseListener(this::jumpRelease, KeyCode.SPACE, KeyCode.UP, KeyCode.W, KeyCode.NUMPAD8);
    }

    public void jumpPress(KeyEvent ev) {
        if (ev != null) {
            _jumpKey = ev.getCode();
        }
        if (getY() == GROUND_HEIGHT) {
            setSpeedY(JUMP_SPEED);
            _jumpingForTime = 0;
        }
    }

    public void jumpRelease(KeyEvent ev) {
        if (ev.getCode() == _jumpKey) {
            _jumpingForTime = NOT_JUMPING;
            _jumpKey = null;
        }
    }

    protected void onTick(double secs, double runningSpeed) {
        double y = getY();
        double speedY = getSpeedY();

        // on the ground
        if (speedY < 0 && y <= GROUND_HEIGHT) {
            setY(GROUND_HEIGHT);
            setSpeedY(0);

            // trigger another jump if it's being held down
            if (_jumpKey != null) {
                jumpPress(null);
            }
        }


        // jumping
        if (y > GROUND_HEIGHT) {
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
    }

    public void tick(double secs) {
        tick(secs, 0);
    }
}
