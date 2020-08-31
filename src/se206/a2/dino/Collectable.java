package se206.a2.dino;

import javafx.scene.Node;

import java.awt.*;

public class Collectable extends GameObject {
    private static final double WAVE_AMPLITUDE = 100;
    private static final double WAVE_FREQ = 3;
    private static final double POS_Y = 100;
    private double _finalX;
    private double _finalY;
    private boolean _hasCollided = false;
    private double _time = 0;

    public Collectable(Shape bounds, Node view) {
        super(bounds, view);
        setX(1500);
    }

    public boolean hasCollided() {
        return _hasCollided;
    }

    @Override
    protected void onCollision(GameObject other) {
        super.onCollision(other);

        if (_hasCollided) return;
        _hasCollided = true;

        setSpeedY(300);
        setSpeedX(-100);
    }

    @Override
    protected void onTick(double secs, double runningSpeed) {
        super.onTick(secs, runningSpeed);

        _time += secs;

        // fly in a sine wave pattern
        if (_hasCollided) {
            _finalY = getContainerHeight() - 70;

            if (getY() > _finalY) {
                setY(_finalY);
                setSpeedY(0);
            }
            if (getX() < _finalX) {
                setX(_finalX);
                setSpeedX(0);
            }
        }
        else {
            setY(POS_Y + WAVE_AMPLITUDE * Math.sin(WAVE_FREQ * _time));
        }
    }

    public void setFinalX(double finalX) {
        _finalX = finalX;
    }

    public void setFinalY(double finalY) {
        _finalY = finalY;
    }
}
