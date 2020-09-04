package se206.a2.dino;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public abstract class GameObject {
    private final Pane _container = new Pane();
    private Shape _bounds;
    private double _containerHeight;
    private double _containerWidth;
    private double _height;
    private boolean _movesWithGround = true;
    private double _speedX;
    private double _speedY;
    private double _width;
    private double _x;
    private double _y;

    protected GameObject(Shape bounds, Node image) {
        _bounds = bounds;
        _container.getChildren().add(image);
        _width = bounds.getBounds().getWidth();
        _height = bounds.getBounds().getHeight();
    }

    public final boolean collidesWith(GameObject obj) {
        if (_bounds == null || obj._bounds == null) {
            return false;
        }

        // X or Y doesn't overlap
        if (_x + _width < obj._x || obj._x + obj._width < _x) return false;
        if (_y + _height < obj._y || obj._y + obj._height < _y) return false;

        AffineTransform transform = new AffineTransform();
        transform.translate(_x - obj._x, _y - obj._y);

        Area area = new Area(_bounds);
        area.transform(transform);
        area.intersect(new Area(obj._bounds));
        if (area.isEmpty()) return false;

        onCollision(obj);
        obj.onCollision(this);
        return true;
    }

    public Shape getBounds() {
        return _bounds;
    }

    public void setBounds(Shape bounds) {
        _bounds = bounds;
        _width = bounds.getBounds().getWidth();
        _height = bounds.getBounds().getHeight();
    }

    public double getContainerHeight() {
        return _containerHeight;
    }

    public void setContainerHeight(double height) {
        _containerHeight = height;
        _container.setLayoutY(_containerHeight - _y - _height);
    }

    public double getContainerWidth() {
        return _containerWidth;
    }

    public void setContainerWidth(double width) {
        _containerWidth = width;
        _container.setLayoutX(_x);
    }

    public double getHeight() {
        return _height;
    }

    public Node getImage() {
        return _container.getChildren().get(0);
    }

    public void setImage(Node node) {
        _container.getChildren().clear();
        _container.getChildren().add(node);
    }

    public double getLayoutX() {
        return _container.getLayoutX();
    }

    public void setLayoutX(double x) {
        _container.setLayoutX(x);
    }

    public double getLayoutY() {
        return _container.getLayoutY();
    }

    public void setLayoutY(double y) {
        _container.setLayoutY(y);
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

    public Pane getView() {
        return _container;
    }

    public double getWidth() {
        return _width;
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

    public boolean movesWithGround() {
        return _movesWithGround;
    }

    protected void onCollision(GameObject other) {
    }

    protected void onTick(double secs, double runningSpeed) {
    }

    public void setMovesGround(boolean movesWithGround) {
        _movesWithGround = movesWithGround;
    }

    public final void tick(double secs, double runningSpeed) {
        _x += secs * _speedX;
        _y += secs * _speedY;
        if (_movesWithGround) _x -= secs * runningSpeed;
        onTick(secs, runningSpeed);
        _container.setLayoutX(_x);
        _container.setLayoutY(_containerHeight - _y - _height);
    }
}
