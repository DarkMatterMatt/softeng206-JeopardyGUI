package se206.a2.dino;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.awt.*;

public class Background extends GameObject {
    private final Image _image = new Image(GameObjectFactory.class.getResourceAsStream("assets/background.png"));
    private boolean _firstTick = true;

    public Background() {
        super(new Rectangle(4800, 24), new HBox());
    }

    @Override
    protected void onTick(double secs, double runningSpeed) {
        // background loads after first game tick so that the scene doesn't
        //   automatically expand to try and fit the entire width of the background
        if (_firstTick) {
            _firstTick = false;
            ((HBox) getImage()).getChildren().addAll(new ImageView(_image), new ImageView(_image));
        }

        // when one side goes off the screen, shift ourselves right
        if (getX() < -2400) {
            setX(getX() + 2400);
        }
    }
}
