package se206.a2.dino;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class DinoModel {
    private static final double BASE_RUNNING_SPEED = 600;
    private static final double MAX_RUNNING_SPEED = 2 * BASE_RUNNING_SPEED;
    private final CollectableGenerator _collectableGenerator = new CollectableGenerator(this);
    private final IntegerProperty _deaths = new SimpleIntegerProperty();
    private final BooleanProperty _gameFinishing = new SimpleBooleanProperty();
    private final ObservableList<GameObject> _gameObjects = FXCollections.observableList(new ArrayList<>());
    private final KeyDownTracker _keyDownTracker = KeyDownTracker.getInstance();
    private final ObstacleGenerator _obstacleGenerator = new ObstacleGenerator();
    private final IGameComplete _onComplete;
    private final Player _player = new Player();
    private final Background _background = new Background();
    private double _gameTime = 0;
    private boolean _isRunning = false;
    private double _runningSpeed;
    private final AnimationTimer _gameTimer = new AnimationTimer() {
        private long lastTime;

        @Override
        public void handle(long now) {
            double deltaSecs = (now - lastTime) / 1e9;
            lastTime = now;
            tick(deltaSecs);
        }

        @Override
        public void start() {
            super.start();
            lastTime = System.nanoTime();
        }
    };

    public DinoModel(IGameComplete onComplete) {
        _onComplete = onComplete;
    }

    public void beginFinishGame() {
        _gameFinishing.set(true);
    }

    public void finishGame() {
        _onComplete.gameComplete();
        stopGame();
    }

    public int getDeaths() {
        return _deaths.get();
    }

    public IntegerProperty getDeathsProperty() {
        return _deaths;
    }

    public BooleanProperty getGameFinishingProperty() {
        return _gameFinishing;
    }

    public ObservableList<GameObject> getGameObjects() {
        return _gameObjects;
    }

    public boolean isFinishing() {
        return _gameFinishing.get();
    }

    public boolean isRunning() {
        return _isRunning;
    }

    public void onKeyPress(KeyEvent ev) {
        _keyDownTracker.onKeyPress(ev);
    }

    public void onKeyRelease(KeyEvent ev) {
        _keyDownTracker.onKeyRelease(ev);
    }

    public void startGame() {
        // reset
        _gameObjects.clear();
        _gameObjects.add(_player);
        _gameObjects.add(_background);

        _player.setX(100);
        _gameTime = 0;
        _collectableGenerator.reset();
        _gameFinishing.set(false);

        // start
        _gameTimer.start();
        _isRunning = true;
    }

    public void stopGame() {
        _isRunning = false;
        _gameTimer.stop();
        _gameObjects.clear();
    }

    public void tick(double secs) {
        _gameTime += secs;
        if (_runningSpeed < MAX_RUNNING_SPEED) {
            _runningSpeed = BASE_RUNNING_SPEED * Math.pow(1.1, (int) (_gameTime / 10));
        }

        Iterator<GameObject> iter = _gameObjects.iterator();
        while (iter.hasNext()) {
            GameObject obj = iter.next();

            if (obj instanceof Player) {
                ((Player) obj).tick(secs);
            }
            else {
                obj.tick(secs, _runningSpeed);
            }

            // remove objects off the screen
            if (obj.getX() < -obj.getWidth()) {
                iter.remove();
            }

            if (obj instanceof Player) {
                // do nothing
            }
            else if (obj instanceof Obstacle && !((Obstacle) obj).hasCollided() && _player.collidesWith(obj)) {
                _deaths.set(_deaths.get() + 1);
            }
            else if (obj instanceof Collectable && !((Collectable) obj).hasCollided() && _player.collidesWith(obj)) {
                // collectable will do something here
            }
        }

        GameObject o;

        // spawn new obstacles
        o = _obstacleGenerator.spawnObstacle(secs, _runningSpeed);
        if (o != null) _gameObjects.add(o);

        // spawn new collectables
        o = _collectableGenerator.spawn(secs, _runningSpeed);
        if (o != null) _gameObjects.add(o);
    }
}
