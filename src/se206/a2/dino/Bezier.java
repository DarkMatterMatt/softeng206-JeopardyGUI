package se206.a2.dino;

import java.awt.geom.Point2D;

public class Bezier {
    public static class Cubic {
        private static final int ARR_SIZE = 1000; // array of -1 <= t <= 2
        private final double[] _cacheXforT = new double[3 * ARR_SIZE];
        private final double _p1X, _p1Y, _p2X, _p2Y, _aX, _bX, _cX, _aY, _bY, _cY;

        /**
         * Create a new Cubic Bezier curve.
         *
         * @param p1X x-coordinate for the first point on the curve
         * @param p1Y y-coordinate for the first point on the curve
         * @param p2X x-coordinate for the second point on the curve
         * @param p2Y y-coordinate for the second point on the curve
         */
        public Cubic(double p1X, double p1Y, double p2X, double p2Y) {
            _p1X = p1X;
            _p1Y = p1Y;
            _p2X = p2X;
            _p2Y = p2Y;

            _cX = 3 * _p1X;
            _bX = 3 * (_p2X - _p1X) - _cX;
            _aX = 1 - _cX - _bX;

            _cY = 3 * _p1Y;
            _bY = 3 * (_p2Y - _p1Y) - _cY;
            _aY = 1 - _cY - _bY;

            for (int i = 0; i < 3 * ARR_SIZE; i++) {
                _cacheXforT[i] = getX((double) (i - ARR_SIZE) / ARR_SIZE);
            }
        }

        /**
         * @param t time 0 <= t <= 1
         * @return progress at time `t`
         */
        public double get(double t) {
            return getY(getTAtX(t));
        }

        public double getP1X() {
            return _p1X;
        }

        public double getP1Y() {
            return _p1Y;
        }

        public double getP2X() {
            return _p2X;
        }

        public double getP2Y() {
            return _p2Y;
        }

        /**
         * @param t time 0 <= t <= 1
         * @return point on curve at time `t`
         */
        public Point2D getPoint(double t) {
            return new Point2D.Double(getX(t), getY(t));
        }

        /**
         * Terrible implementation to find the time which produces `x`. Doesn't even work half the time...
         */
        public double getTAtX(double x) {
            double best_diff = Math.abs(_cacheXforT[ARR_SIZE] - x);
            int best_i = ARR_SIZE;

            // search range for t <= 0
            if (x < 0) {
                for (int i = ARR_SIZE - 1; i >= 0; i--) {
                    double diff = Math.abs(_cacheXforT[i] - x);
                    if (diff < best_diff) {
                        best_i = i;
                        best_diff = diff;
                    }
                }
            }
            // search range for 0 <= t < 2
            else {
                for (int i = ARR_SIZE + 1; i < _cacheXforT.length; i++) {
                    double diff = Math.abs(_cacheXforT[i] - x);
                    if (diff < best_diff) {
                        best_i = i;
                        best_diff = diff;
                    }
                }
            }

            return (double) (best_i - ARR_SIZE) / ARR_SIZE;
        }

        /**
         * @param t time 0 <= t <= 1
         * @return x-value on curve at time `t`
         */
        public double getX(double t) {
            return (_aX * Math.pow(t, 3)) + (_bX * Math.pow(t, 2)) + (_cX * t);
        }

        /**
         * @param t time 0 <= t <= 1
         * @return y-value on curve at time `t`
         */
        public double getY(double t) {
            return (_aY * Math.pow(t, 3)) + (_bY * Math.pow(t, 2)) + (_cY * t);
        }
    }
}
