package se206.a2.dino;

import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.awt.*;

public class GameObjectFactory {
    private GameObjectFactory() {
    }

    public static Collectable createCollectable(Type type, double width, double height) {
        Pair<Shape, Node> p = getViewAndBounds(type, width, height);
        return new Collectable(p.getKey(), p.getValue());
    }

    public static Obstacle createObstacle(Type type, double width, double height) {
        Pair<Shape, Node> p = getViewAndBounds(type, width, height);
        return new Obstacle(p.getKey(), p.getValue());
    }

    public static Pair<Shape, Node> getViewAndBounds(Type type, double width, double height) {
        if (width <= 0 && height <= 0) {
            throw new IllegalArgumentException("One of width and height must be positive. Only one can be automatic at a time");
        }

        int intWidth = (int) Math.round(width);
        int intHeight = (int) Math.round(height);

        double[] polyX;
        double[] polyY;
        String imageFile = null;
        Rectangle2D viewport = null;

        switch (type) {
            case CREDITS:
                polyX = new double[]{0, 457, 457, 0};
                polyY = new double[]{0, 0, 124, 124};
                imageFile = "assets/credits.gif";
                break;

            case CREDITS_A:
                polyX = new double[]{0, 59, 59, 0};
                polyY = new double[]{0, 0, 87, 87};
                imageFile = "assets/credits.gif";
                viewport = new Rectangle2D(123, 30, 59, 87);
                break;

            case CREDITS_M1:
                polyX = new double[]{0, 113, 113, 0};
                polyY = new double[]{0, 0, 118, 118};
                imageFile = "assets/credits.gif";
                viewport = new Rectangle2D(3, 3, 113, 118);
                break;

            case CREDITS_M2:
                polyX = new double[]{0, 109, 109, 0};
                polyY = new double[]{0, 0, 118, 118};
                imageFile = "assets/credits.gif";
                viewport = new Rectangle2D(345, 3, 109, 118);
                break;

            case CREDITS_T1:
                polyX = new double[]{0, 61, 61, 0};
                polyY = new double[]{0, 0, 77, 77};
                imageFile = "assets/credits.gif";
                viewport = new Rectangle2D(183, 37, 61, 77);
                break;

            case CREDITS_T2:
                polyX = new double[]{0, 63, 63, 0};
                polyY = new double[]{0, 0, 77, 77};
                imageFile = "assets/credits.gif";
                viewport = new Rectangle2D(246, 37, 63, 77);
                break;

            case DUCK:
                polyX = new double[]{143.6, 168.833, 257.859, 380.3, 436, 383.025, 308, 259.333, 206.4, 252.738, 284.4, 273.875, 189.112, 108.75, 45.886, 48.5, 28.875, 0, 22.625, 74.189, 15.553, 10.4, 57.335, 100.181, 123};
                polyY = new double[]{2.55, 32.633, 30.909, 2.13, 11.675, 48.462, 50.6, 72.9, 120.8, 130.858, 121.7, 164.3, 133.611, 148.05, 187.9, 203.3, 213.8, 207.5, 177.9, 149.3, 130.905, 93.5, 93.311, 75.953, 13.6};
                imageFile = "assets/duck.png";
                break;

            case FIRE:
                polyX = new double[]{254, 452, 485, 420, 259, 56, 1, 43};
                polyY = new double[]{1, 317, 543, 661, 693, 636, 514, 336};
                imageFile = "assets/fire.gif";
                break;

            case INVERTED_TEE:
                polyX = new double[]{0, 60, 60, 40, 40, 20, 20, 0};
                polyY = new double[]{80, 80, 60, 60, 0, 0, 60, 60};
                break;

            case INFO_AVOID_OBSTACLES:
                polyX = new double[]{0, 335.86, 335.86, 0};
                polyY = new double[]{0, 0, 28.05, 28.05};
                imageFile = "assets/avoid-obstacles.png";
                break;

            case INFO_COLLECT_ITEMS:
                polyX = new double[]{0, 285.87, 285.87, 0};
                polyY = new double[]{0, 0, 28.05, 28.05};
                imageFile = "assets/collect-items.png";
                break;

            case INFO_CONTROLS:
                polyX = new double[]{380, 380, 392, 392, 536.362, 536.362, 661, 661, 536, 536, 377, 377, 279.556, 279.556, 120, 120, 0, 0, 120, 120, 264, 264, 277, 277};
                polyY = new double[]{0, 43.9, 43.9, 187.9, 187.9, 214.9, 214.9, 288.74, 288.74, 315.675, 315.675, 358.9, 358.9, 315.9, 315.9, 288.9, 288.9, 214.797, 214.797, 187.9, 187.9, 43.9, 43.9, 0};
                imageFile = "assets/controls.png";
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
            if (viewport != null) view.setViewport(viewport);

            return new Pair<>(poly, view);
        }

        javafx.scene.shape.Polygon poly2 = new javafx.scene.shape.Polygon(DinoHelper.zipArrays(polyX, polyY));
        return new Pair<>(poly, poly2);
    }

    public enum Type {
        CREDITS,
        CREDITS_A,
        CREDITS_M1,
        CREDITS_M2,
        CREDITS_T1,
        CREDITS_T2,
        DUCK,
        FIRE,
        INFO_AVOID_OBSTACLES,
        INFO_COLLECT_ITEMS,
        INFO_CONTROLS,
        INVERTED_TEE,
        PIG,
        RECTANGLE,
        TEE,
    }
}
