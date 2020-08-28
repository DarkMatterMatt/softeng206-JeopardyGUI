package se206.a2.dino;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public abstract class GameObject {
    private final Shape _bounds;
    private final DoubleProperty _layoutX = new SimpleDoubleProperty();
    private final DoubleProperty _layoutY = new SimpleDoubleProperty();
    private final Node _view;
    private double _containerHeight;
    private double _containerWidth;
    private double _speedX;
    private double _speedY;
    private double _x;
    private double _y;

    protected GameObject(Shape bounds, Node image) {
        _bounds = bounds;
        _view = image;
    }

    public final boolean collidesWith(GameObject obj) {
        if (_bounds == null || obj._bounds == null) {
            return false;
        }
        AffineTransform transform = new AffineTransform();
        transform.translate(getLayoutX() - obj.getLayoutX(), getLayoutY() - obj.getLayoutY());

        Area area = new Area(_bounds);
        area.transform(transform);
        area.intersect(new Area(obj._bounds));
        return !area.isEmpty();
    }

    public Shape getBounds() {
        return _bounds;
    }

    public double getLayoutX() {
        return _layoutX.get();
    }

    public void setLayoutX(double x) {
        _layoutX.set(x);
    }

    public DoubleProperty getLayoutXProperty() {
        return _layoutX;
    }

    public double getLayoutY() {
        return _layoutY.get();
    }

    public void setLayoutY(double y) {
        _layoutY.set(y);
    }

    public DoubleProperty getLayoutYProperty() {
        return _layoutY;
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

    public Node getView() {
        return _view;
    }

    public double getX() {
        return _x;
    }

    public void setX(double x) {
        _x = x;
    }

    public double getY() {
        return _y;
    }

    public void setY(double y) {
        _y = y;
    }

    protected void onTick(double secs) {
    }

    public void setContainerHeight(double height) {
        _containerHeight = height;
        _layoutY.set(_containerHeight - _y - _view.getBoundsInLocal().getHeight());
    }

    public void setContainerWidth(double width) {
        _containerWidth = width;
        _layoutX.set(_x);
    }

    public final void tick(double secs) {
        _x += secs * _speedX;
        _y += secs * _speedY;
        onTick(secs);
        _layoutY.set(_containerHeight - _y - _view.getBoundsInLocal().getHeight());
        _layoutX.set(_x);
    }
}
