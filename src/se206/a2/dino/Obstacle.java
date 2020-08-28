package se206.a2.dino;

import javafx.scene.Node;

import java.awt.*;

public class Obstacle extends GameObject {
    public Obstacle(Shape bounds, Node view) {
        super(bounds, view);
        setX(1500);
    }
}
