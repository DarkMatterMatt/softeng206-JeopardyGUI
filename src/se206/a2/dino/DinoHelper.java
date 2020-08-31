package se206.a2.dino;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public class DinoHelper {
    private DinoHelper() {
    }

    /* package-private */
    static double arrayMax(double[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Cannot find max of null array or array of length zero");
        }
        double max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    /* package-private */
    static double arrayMin(double[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Cannot find min of array of length zero");
        }
        double min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    /* package-private */
    static double[] arrayScale(double[] arr, double scale) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot scale a null array");
        }
        double[] scaled = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            scaled[i] = arr[i] * scale;
        }
        return scaled;
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

    /* package-private */
    static int[] roundArray(double[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Cannot round a null array");
        }
        int[] rounded = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            rounded[i] = (int) Math.round(arr[i]);
        }
        return rounded;
    }

    /* package-private */
    static double[] zipArrays(double[] arr1, double[] arr2) {
        if (arr1 == null || arr2 == null) {
            throw new IllegalArgumentException("Cannot zip a null array");
        }
        if (arr1.length != arr2.length) {
            throw new IllegalArgumentException("Cannot zip arrays of unequal length");
        }

        double[] zipped = new double[arr1.length * 2];
        for (int i = 0; i < arr1.length; i++) {
            zipped[2 * i] = arr1[i];
            zipped[2 * i + 1] = arr2[i];
        }
        return zipped;
    }

    /* package-private */
    static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
    static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
