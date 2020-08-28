package se206.a2.dino;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class GameObjectFactory {
    private GameObjectFactory() {
    }

    public static Obstacle createObstacle(ObstacleType type, double width, double height) {
        int intWidth = (int) Math.round(width);
        int intHeight = (int) Math.round(height);

        switch (type) {
            case BLOCK:
                return new Obstacle(
                        new Rectangle(intWidth, intHeight),
                        new javafx.scene.shape.Rectangle(width, height)
                );

            case TEE:
                return new Obstacle(
                        new Polygon(
                                new int[]{0, 60, 60, 40, 40, 20, 20, 0,},
                                new int[]{0, 0, 20, 20, 80, 80, 20, 20,},
                                6
                        ),
                        new javafx.scene.shape.Polygon(0, 0, 60, 0, 60, 20, 40, 20, 40, 80, 20, 80, 20, 20, 0, 20)
                );

            case INVERTED_TEE:
                return new Obstacle(
                        new Polygon(
                                new int[]{0, 60, 60, 40, 40, 20, 20, 0,},
                                new int[]{80, 80, 60, 60, 0, 0, 60, 60},
                                6
                        ),
                        new javafx.scene.shape.Polygon(0, 80, 60, 80, 60, 60, 40, 60, 40, 0, 20, 0, 20, 60, 0, 60)
                );

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public static Player createPlayer(PlayerType type, double width, double height) {
        int intWidth = (int) Math.round(width);
        int intHeight = (int) Math.round(height);

        switch (type) {
            case BLOCK:
                return new Player(
                        new Rectangle(intWidth, intHeight),
                        new javafx.scene.shape.Rectangle(width, height)
                );

            case PIG:
                return new Player(
                        new Polygon(
                                new int[]{42, 101, 109, 155, 172, 188, 230, 238, 226, 167, 197, 163, 138, 88, 84, 42, 26, 51, 26, 5, 5, 34, 55},
                                new int[]{14, 2, 52, 56, 27, 60, 64, 85, 114, 152, 181, 202, 173, 185, 214, 206, 189, 160, 131, 127, 98, 110, 85},
                                23
                        ),
                        new ImageView(new Image(GameObjectFactory.class.getResourceAsStream("assets/flying-pig.png")))
                );

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    enum ObstacleType {
        BLOCK,
        FIRE,
        TEE,
        INVERTED_TEE,
    }

    enum PlayerType {
        BLOCK,
        PIG,
    }
}
