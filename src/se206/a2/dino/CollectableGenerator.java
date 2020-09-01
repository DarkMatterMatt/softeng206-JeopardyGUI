package se206.a2.dino;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;

public class CollectableGenerator {
    private static final double CREDITS_SIZE = 50;
    private static final double SPAWN_SPEED = 0.5;
    private static final double SPEED = 1000;
    public final ArrayList<Collectable> _collectables = new ArrayList<>();
    private final DinoModel _model;
    private final double _obstacleSpeed = 400;
    private int _creditsCollected = 0;
    private double _nextSpawn = 5;
    private double _time = 0;

    public CollectableGenerator(DinoModel model) {
        _model = model;
        addCredits();
    }

    public void addCollectable(Collectable c) {
        _collectables.add(c);
    }

    private void addCredits() {
        // adds 'Matt M' collectables

        Collectable c;

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_M1, 0, CREDITS_SIZE * 1.24);
        c.setFinalX(10);
        c.setOnMissed(this::addCollectable);
        c.setOnCollected(this::onCreditCollected);
        _collectables.add(c);

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_A, 0, CREDITS_SIZE * 0.80);
        c.setFinalX(70);
        c.setOnMissed(this::addCollectable);
        c.setOnCollected(this::onCreditCollected);
        _collectables.add(c);

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_T1, 0, CREDITS_SIZE * 0.77);
        c.setFinalX(100);
        c.setOnMissed(this::addCollectable);
        c.setOnCollected(this::onCreditCollected);
        _collectables.add(c);

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_T2, 0, CREDITS_SIZE * 0.77);
        c.setFinalX(133);
        c.setOnMissed(this::addCollectable);
        c.setOnCollected(this::onCreditCollected);
        _collectables.add(c);

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_M2, 0, CREDITS_SIZE * 1.18);
        c.setFinalX(180);
        c.setOnMissed(this::addCollectable);
        c.setOnCollected(this::onCreditCollected);
        _collectables.add(c);
    }

    public Collectable getRandomCollectable() {
        int numRemaining = _collectables.size();
        if (numRemaining == 0) return null;
        return _collectables.remove(0);
    }

    public void onCreditCollected(Collectable c) {
        if (++_creditsCollected == 5) {
            // we've collected the entire 'Matt M', wait one second for the last collectable to move into place
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> _model.beginFinishGame());
            pause.play();
        }
    }

    public Collectable spawn(double secs, double runningSpeed) {
        _time += secs;
        if (_time < _nextSpawn) {
            return null;
        }
        _nextSpawn = _time + 1 / (SPAWN_SPEED * (1 + 0.5 * Math.random()));
        Collectable o = getRandomCollectable();
        if (o == null) return null;

        o.setX(2500);
        return o;
    }
}
