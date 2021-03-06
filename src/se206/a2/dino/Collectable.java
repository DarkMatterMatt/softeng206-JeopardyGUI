package se206.a2.dino;

import javafx.scene.Node;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Collectables fly to a fixed position when hit
 */
public class Collectable extends GameObject {
    private static final double POS_Y = 140;
    private static final double WAVE_AMPLITUDE = 100;
    private static final double WAVE_FREQ = 3;
    private double _finalX;
    private double _finalY;
    private boolean _hasCollided = false;
    private Consumer<Collectable> _onCollected = null;
    private Consumer<Collectable> _onMissed = null;
    private boolean _posXLocked = false;
    private boolean _posYLocked = false;
    private double _time = 0;

    public Collectable(Shape bounds, Node view) {
        super(bounds, view);
        getView().setViewOrder(-30);
        setMovesWithGround(false);
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

        if (_onCollected != null) _onCollected.accept(this);
    }

    @Override
    protected void onTick(double secs, double runningSpeed) {
        super.onTick(secs, runningSpeed);
        double x = getX();
        double y = getY();
        double width = getWidth();
        double containerWidth = getContainerWidth();
        double speedX = getSpeedX();
        double speedY = getSpeedY();

        _time += secs;

        if (_hasCollided) {
            // fly towards final position
            _finalY = getContainerHeight() - 70;

            // fly towards finalX & snap into place
            if (_posXLocked || (speedX < 0 && x < _finalX) || (speedX > 0 && x > _finalX)) {
                _posXLocked = true;
                setX(_finalX);
                setSpeedX(runningSpeed);
            }
            else {
                setSpeedX(DinoHelper.clamp(2 * (_finalX - x), -1000, 1000));
            }

            // fly towards finalY & snap into place
            if (_posYLocked || (speedY < 0 && y < _finalY) || (speedY > 0 && y > _finalY)) {
                _posYLocked = true;
                setY(_finalY);
                setSpeedY(0);
            }
            else {
                setSpeedY(DinoHelper.clamp(5 * (_finalY - y), -1000, 1000));
            }
        }
        else {
            // fly in a sine wave pattern
            setY(POS_Y + WAVE_AMPLITUDE * Math.sin(WAVE_FREQ * _time));

            if (x > containerWidth) {
                setX(containerWidth);
            }
            setSpeedX(-0.9 * runningSpeed);
        }

        // trigger event when we leave the screen
        if (x < -width && _onMissed != null) {
            _onMissed.accept(this);
        }
    }

    public void setFinalX(double finalX) {
        _finalX = finalX;
    }

    /**
     * Set function to be called when the collectable is collided with the first time
     */
    public void setOnCollected(Consumer<Collectable> onCollectableCollected) {
        _onCollected = onCollectableCollected;
    }

    /**
     * Set function to be called when the collectable leaves the screen without being collected
     */
    public void setOnMissed(Consumer<Collectable> onCollectableMissed) {
        _onMissed = onCollectableMissed;
    }
}
