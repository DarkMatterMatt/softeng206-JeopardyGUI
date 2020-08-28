package se206.a2.dino;

import java.awt.*;
import java.awt.geom.Point2D;

public class Player extends GameObject {
    private static final double GRAVITY = 800;
    private static final double JUMP_SPEED = 400;

    public Player() {
        super(new Rectangle(50, 100), new Point2D.Double(100, 0));
    }

    public void jump() {
        setSpeedY(JUMP_SPEED);
    }

    @Override
    protected void onTick(double secs) {
        double y = getY();
        double speedY = getSpeedY();

        // on the ground
        if (speedY < 0 && y <= 0) {
            setY(0);
            setSpeedY(0);
        }

        setSpeedY(speedY - GRAVITY * secs);
    }
}
