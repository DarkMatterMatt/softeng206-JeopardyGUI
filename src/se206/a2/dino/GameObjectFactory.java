package se206.a2.dino;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.awt.*;

public class GameObjectFactory {
    private GameObjectFactory() {
    }

    public static Obstacle createObstacle(Type type, double width, double height) {
        Pair<Shape, Node> p = getScaled(type, width, height);
        return new Obstacle(p.getKey(), p.getValue());
    }

    public static Player createPlayer(Type type, double width, double height) {
        Pair<Shape, Node> p = getScaled(type, width, height);
        return new Player(p.getKey(), p.getValue());
    }

    public static Pair<Shape, Node> getScaled(Type type, double width, double height) {
        if (width <= 0 && height <= 0) {
            throw new IllegalArgumentException("One of width and height must be positive. Only one can be automatic at a time");
        }

        int intWidth = (int) Math.round(width);
        int intHeight = (int) Math.round(height);

        double[] polyX;
        double[] polyY;
        String imageFile = null;

        switch (type) {
            case FIRE:
                polyX = new double[]{254, 452, 485, 420, 259, 56, 1, 43};
                polyY = new double[]{1, 317, 543, 661, 693, 636, 514, 336};
                imageFile = "assets/fire.gif";
                break;

            case INVERTED_TEE:
                polyX = new double[]{0, 60, 60, 40, 40, 20, 20, 0};
                polyY = new double[]{80, 80, 60, 60, 0, 0, 60, 60};
                break;

            case PIG:
                polyX = new double[]{10.18, 24.18, 26.18, 37.18, 41.18, 45.18, 55.18, 57.18, 54.18, 40.18, 47.18, 39.18, 33.18, 21.18, 20.18, 10.18, 6.18, 12.18, 6.18, 1.18, 1.18, 8.18, 13.18, 10.18};
                polyY = new double[]{3.42, 0.42, 12.42, 13.42, 6.42, 14.42, 15.42, 20.42, 27.42, 36.42, 43.42, 48.42, 41.42, 44.42, 51.42, 49.42, 45.42, 38.42, 31.42, 30.42, 23.42, 26.42, 20.42, 3.42};
                imageFile = "assets/flying-pig.png";
                break;

            case TEE:
                polyX = new double[]{0, 60, 60, 40, 40, 20, 20, 0};
                polyY = new double[]{0, 0, 20, 20, 80, 80, 20, 20};
                break;

            case RECTANGLE:
                return new Pair<>(
                        new Rectangle(intWidth, intHeight),
                        new javafx.scene.shape.Rectangle(width, height)
                );

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        // scale poly
        double polyWidth = DinoHelper.arrayMax(polyX) - DinoHelper.arrayMin(polyX);
        double polyHeight = DinoHelper.arrayMax(polyY) - DinoHelper.arrayMin(polyY);
        double widthScale = width / polyWidth;
        double heightScale = height / polyHeight;

        double scale;
        if (width <= 0) scale = heightScale;
        else if (height <= 0) scale = widthScale;
        else scale = Math.min(widthScale, heightScale);

        polyX = DinoHelper.arrayScale(polyX, scale);
        polyY = DinoHelper.arrayScale(polyY, scale);
        Polygon poly = new Polygon(DinoHelper.roundArray(polyX), DinoHelper.roundArray(polyY), polyX.length);

        if (imageFile != null) {
            ImageView view = new ImageView(new Image(GameObjectFactory.class.getResourceAsStream(imageFile)));
            view.setPreserveRatio(true);
            view.setSmooth(true);
            view.setCache(true);
            if (width > 0) view.setFitWidth(width);
            if (height > 0) view.setFitHeight(height);

            return new Pair<>(poly, view);
        }

        javafx.scene.shape.Polygon poly2 = new javafx.scene.shape.Polygon(DinoHelper.zipArrays(polyX, polyY));
        return new Pair<>(poly, poly2);
    }

    public enum Type {
        FIRE,
        INVERTED_TEE,
        PIG,
        RECTANGLE,
        TEE,
    }
}
