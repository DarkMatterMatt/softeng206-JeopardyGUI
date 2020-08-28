package se206.a2.dino;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public class DinoHelper {
    private DinoHelper() {
    }

    /* package-private */
    static void invertY(Pane p) {
        Scale scale = new Scale(1, -1);

        scale.pivotYProperty().bind(
                Bindings.createDoubleBinding(() -> p.getBoundsInLocal().getMinY() + p.getBoundsInLocal().getHeight() / 2,
                        p.boundsInLocalProperty())
        );
        p.getTransforms().add(scale);
    }

    /* package-private */
    static double metersPerSecToPixelsPerSec(double meters) {
        return meters * 10;
    }
}
