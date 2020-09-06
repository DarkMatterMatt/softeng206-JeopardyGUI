package se206.a2.dino;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertEquals;

/**
 * Test suite for Bezier. Currently not comprehensive
 */
public class BezierTest {
    Bezier.Cubic cubic1 = new Bezier.Cubic(0.17, 0.67, 0.83, 0.67);

    @Test
    public void cubic_hasCorrectValueAt000() {
        pointEquals(cubic1.getPoint(0.00), 0, 0);
    }

    @Test
    public void cubic_hasCorrectValueAt024() {
        pointEquals(cubic1.getPoint(0.24), 0.19352, 0.38045);
    }

    @Test
    public void cubic_hasCorrectValueAt072() {
        pointEquals(cubic1.getPoint(0.72), 0.76346, 0.77846);
    }

    @Test
    public void cubic_hasCorrectValueAt098() {
        pointEquals(cubic1.getPoint(0.98), 0.98922, 0.98059);
    }

    @Test
    public void cubic_hasCorrectValueAt100() {
        pointEquals(cubic1.getPoint(1.00), 1, 1);
    }

    private void pointEquals(Point2D p, double x, double y) {
        assertEquals(x, p.getX(), 1e-5);
        assertEquals(y, p.getY(), 1e-5);
    }
}
