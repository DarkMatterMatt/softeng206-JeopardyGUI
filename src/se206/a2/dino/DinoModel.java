package se206.a2.dino;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class DinoModel {
    private final Background _background = new Background();
    private final ObstacleGenerator _obstacleGenerator = new ObstacleGenerator();
    private final ObservableList<Obstacle> _obstacles = FXCollections.observableList(new ArrayList<>());
    private final IGameComplete _onComplete;
    private final Player _player = GameObjectFactory.createPlayer(GameObjectFactory.Type.PIG, 0, 100);
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

    public void finishGame() {
        _onComplete.gameComplete();
        stopGame();
    }

    public Background getBackground() {
        return _background;
    }

    public ObservableList<Obstacle> getObstacles() {
        return _obstacles;
    }

    public Player getPlayer() {
        return _player;
    }

    public boolean isRunning() {
        return _isRunning;
    }

    public void onKeyPress(KeyEvent ev) {
        _player.jump();
    }

    public void startGame() {
        _gameTimer.start();
        _isRunning = true;
    }

    public void stopGame() {
        _isRunning = false;
        _gameTimer.stop();
        _obstacles.clear();
    }

    public void tick(double secs) {
        _gameTime += secs;
        _runningSpeed = 400 * Math.pow(1.1, (int) (_gameTime / 10));

        _player.tick(secs);
        _background.tick(secs, _runningSpeed);

        Iterator<Obstacle> iter = _obstacles.iterator();
        while (iter.hasNext()) {
            GameObject obj = iter.next();
            obj.tick(secs, _runningSpeed);

            // remove objects off the screen
            if (obj.getX() < obj.getBounds().getBounds().getWidth()) {
                iter.remove();
            }

            if (_player.collidesWith(obj)) {
                System.out.println("collision");
            }
        }

        // spawn new obstacles
        Obstacle o = _obstacleGenerator.spawnObstacle(secs, _runningSpeed);
        if (o != null) {
            _obstacles.add(o);
        }
    }
}
