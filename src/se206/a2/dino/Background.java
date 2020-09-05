package se206.a2.dino;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.*;

/**
 * Infinite scrolling background
 */
public class Background extends GameObject {
    private static final Image _image = new Image(GameObjectFactory.class.getResourceAsStream("assets/background.png"));

    public Background() {
        super(new Rectangle(4800, 24), new HBox());
        getView().setViewOrder(10);
        ((HBox) getImage()).getChildren().addAll(new ImageView(_image), new ImageView(_image));
    }

    @Override
    protected void onTick(double secs, double runningSpeed) {
        // when one side goes off the screen, shift ourselves right
        if (getX() < -2400) {
            setX(getX() + 2400);
        }
    }
}
