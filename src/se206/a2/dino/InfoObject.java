package se206.a2.dino;

import javafx.scene.Node;

import java.awt.*;

public class InfoObject extends GameObject {
    public static Bezier.Cubic WHIZZ_PAUSE_WHIZZ = new Bezier.Cubic(0.6, 0.95, 0.8, 0.5);
    private Bezier.Cubic _curveX = WHIZZ_PAUSE_WHIZZ;
    private Bezier.Cubic _curveY = WHIZZ_PAUSE_WHIZZ;
    private double _entryDuration = 2;
    private double _entryStart = 0;
    private double _entryXEndPercent = 0;
    private double _entryXStartPercent = 1;
    private double _entryYEndPercent = 0.6;
    private double _entryYStartPercent = 0.4;
    private double _gameTime = 0;

    protected InfoObject(Shape bounds, Node image) {
        super(bounds, image);
        getView().setViewOrder(-40);
        setX(1500);
        setY(140);
        setMovesWithGround(false);
    }

    @Override
    protected void onTick(double secs, double runningSpeed) {
        super.onTick(secs, runningSpeed);
        _gameTime += secs;

        // animation completion percentage (0 <= completion <= 1)
        double completion = (_gameTime - _entryStart) / _entryDuration;
        if (completion <= 0) {
            return;
        }

        double containerWidth = getContainerWidth();
        double containerHeight = getContainerHeight();

        setX(((_entryXEndPercent - _entryXStartPercent) * _curveX.get(completion) + _entryXStartPercent) * containerWidth);
        setY(((_entryYEndPercent - _entryYStartPercent) * _curveY.get(completion) + _entryYStartPercent) * containerHeight);
    }

    public InfoObject setCurve(Bezier.Cubic curve) {
        _curveX = curve;
        _curveY = curve;
        return this;
    }

    public InfoObject setCurveX(Bezier.Cubic curveX) {
        _curveX = curveX;
        return this;
    }

    public InfoObject setCurveY(Bezier.Cubic curveY) {
        _curveY = curveY;
        return this;
    }

    public InfoObject setEntryDuration(double secs) {
        _entryDuration = secs;
        return this;
    }

    public InfoObject setEntryStart(double secs) {
        _entryStart = secs;
        return this;
    }

    public InfoObject setEntryXEndPercent(double entryXEndPercent) {
        _entryXEndPercent = entryXEndPercent;
        return this;
    }

    public InfoObject setEntryXStartPercent(double entryXStartPercent) {
        _entryXStartPercent = entryXStartPercent;
        return this;
    }

    public InfoObject setEntryYEndPercent(double entryYEndPercent) {
        _entryYEndPercent = entryYEndPercent;
        return this;
    }

    public InfoObject setEntryYStartPercent(double entryYStartPercent) {
        _entryYStartPercent = entryYStartPercent;
        return this;
    }
}
