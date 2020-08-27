package se206.a2;

public class LadderCalc {
    public final int _bottomWidth;
    public final int _height;
    public final int _topWidth;

    /**
     * LadderCalc is used to calculate width/heights on a quadrilateral
     * where the topWidth != bottomWidth and the top and bottom edges are parallel
     *
     * @param height      vertical height of the quadrilateral
     * @param bottomWidth length of the bottom edge
     * @param topWidth    length of the top edge
     */
    public LadderCalc(int height, int bottomWidth, int topWidth) {
        _bottomWidth = bottomWidth;
        _height = height;
        _topWidth = topWidth;
    }

    public int getBottomWidth() {
        return _bottomWidth;
    }

    public int getHeight() {
        return _height;
    }

    public int getHeightAtWidth(int width) {
        float percent = (float) (width - _topWidth) / (_bottomWidth - _topWidth);
        return Math.round((1 - percent) * _height);
    }

    public int getTopWidth() {
        return _topWidth;
    }

    public int getWidthAtHeight(int height) {
        float slope = (float) (_bottomWidth - _topWidth) / _height;
        return Math.round(_bottomWidth - height * slope);
    }

    public int getXAtY(int y) {
        float slope = (float) (_bottomWidth - _topWidth) / _height;
        return Math.round(_topWidth + y * slope);
    }

    public int getYAtX(int x) {
        float percent = (float) (x - _topWidth) / (_bottomWidth - _topWidth);
        return Math.round(percent * _height);
    }
}
