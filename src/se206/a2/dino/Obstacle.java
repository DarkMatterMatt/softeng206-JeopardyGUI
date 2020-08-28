package se206.a2.dino;

import java.awt.*;
import java.awt.geom.Point2D;


public class Obstacle extends GameObject {
    private final int _height;
    private final ObstacleType _type;
    private final int _width;

    public Obstacle(ObstacleType type, int width, int height, int distanceOffGround) {
        super(Obstacle.getShapeByType(type, width, height), new Point2D.Double(1500, distanceOffGround));
        _height = height;
        _type = type;
        _width = width;
    }

    public static Shape getShapeByType(ObstacleType type, int width, int height) {
        switch (type) {
            case BLOCK:
                return new Rectangle(width, height);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public int getHeight() {
        return _height;
    }

    public ObstacleType getType() {
        return _type;
    }

    public int getWidth() {
        return _width;
    }

    enum ObstacleType {
        BLOCK,
    }
}
