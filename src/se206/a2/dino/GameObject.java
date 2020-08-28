package se206.a2.dino;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

public abstract class GameObject {
    private final DoubleProperty _x = new SimpleDoubleProperty();
    private final DoubleProperty _y = new SimpleDoubleProperty();
    private Shape _bounds;
    private double _speedX;
    private double _speedY;

    protected GameObject(Shape bounds, Point2D.Double position) {
        _bounds = bounds;
        _x.set(position.x);
        _y.set(position.y);
    }

    public final boolean collidesWith(GameObject obj) {
        if (_bounds == null || obj._bounds == null) {
            return false;
        }
        AffineTransform transform = new AffineTransform();
        transform.translate(_x.get() - obj._x.get(), _y.get() - obj._y.get());

        Area area = new Area(_bounds);
        area.transform(transform);
        area.intersect(new Area(obj._bounds));
        return !area.isEmpty();
    }

    public Shape getBounds() {
        return _bounds;
    }

    public void setBounds(Shape bounds) {
        _bounds = bounds;
    }

    public double getSpeedX() {
        return _speedX;
    }

    public void setSpeedX(double speedX) {
        _speedX = speedX;
    }

    public double getSpeedY() {
        return _speedY;
    }

    public void setSpeedY(double speedY) {
        _speedY = speedY;
    }

    public double getX() {
        return _x.get();
    }

    public void setX(double x) {
        _x.set(x);
    }

    public DoubleProperty getXProperty() {
        return _x;
    }

    public double getY() {
        return _y.get();
    }

    public void setY(double y) {
        _y.set(y);
    }

    public DoubleProperty getYProperty() {
        return _y;
    }

    protected void onTick(double secs) {
    }

    public final void tick(double secs) {
        _x.set(_x.get() + secs * _speedX);
        _y.set(_y.get() + secs * _speedY);
        onTick(secs);
    }
}
