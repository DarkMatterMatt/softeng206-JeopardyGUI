package se206.a2.dino;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;

import java.awt.*;

public class Player extends GameObject {
    private static final double GRAVITY = 4800;
    private static final double GROUND_HEIGHT = 8;
    private static final double JUMP_SPEED = 1200;
    private static final double MOVE_SPEED = 400;
    private static final int NOT_JUMPING = -1;
    private static double _jumpingForTime = NOT_JUMPING;
    private final KeyDownTracker _keyDownTracker = KeyDownTracker.getInstance();
    private final Pair<Shape, Node> duckModel = GameObjectFactory.getViewAndBounds(GameObjectFactory.Type.DUCK, 120, 100);
    private final Pair<Shape, Node> pigModel;
    private KeyCode _jumpKey = null;
    private KeyCode _movingLeftKey = null;
    private KeyCode _movingRightKey = null;

    public Player(Shape bounds, Node view) {
        super(bounds, view);
        pigModel = new Pair<>(bounds, view);
        setY(GROUND_HEIGHT);

        // jump
        _keyDownTracker.addPressListener(this::jumpPress, KeyCode.SPACE, KeyCode.UP, KeyCode.W, KeyCode.NUMPAD8);
        _keyDownTracker.addReleaseListener(this::jumpRelease, KeyCode.SPACE, KeyCode.UP, KeyCode.W, KeyCode.NUMPAD8);

        // duck
        _keyDownTracker.addPressListener(ev -> {
            if (getBounds() == pigModel.getKey()) {
                setBounds(duckModel.getKey());
                setImage(duckModel.getValue());
            }
            else {
                setBounds(pigModel.getKey());
                setImage(pigModel.getValue());
            }
        }, KeyCode.DOWN, KeyCode.S, KeyCode.NUMPAD2);

        // move left
        _keyDownTracker.addPressListener(ev -> _movingLeftKey = ev.getCode(), KeyCode.LEFT, KeyCode.A, KeyCode.NUMPAD4);
        _keyDownTracker.addReleaseListener(ev -> {
            if (_movingLeftKey == ev.getCode()) _movingLeftKey = null;
        }, KeyCode.LEFT, KeyCode.A, KeyCode.NUMPAD4);

        // move right
        _keyDownTracker.addPressListener(ev -> _movingRightKey = ev.getCode(), KeyCode.RIGHT, KeyCode.D, KeyCode.NUMPAD6);
        _keyDownTracker.addReleaseListener(ev -> {
            if (_movingRightKey == ev.getCode()) _movingRightKey = null;
        }, KeyCode.RIGHT, KeyCode.D, KeyCode.NUMPAD6);
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
        double x = getX();
        double y = getY();
        double width = getWidth();
        double containerWidth = getContainerWidth();
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

        // left/right movement
        if (_movingLeftKey != null && _movingRightKey == null && x > 0) {
            setX(x - MOVE_SPEED * secs);
        }
        else if (_movingLeftKey == null && _movingRightKey != null && x + width < containerWidth) {
            setX(x + MOVE_SPEED * secs);
        }
    }

    public void tick(double secs) {
        tick(secs, 0);
    }
}
