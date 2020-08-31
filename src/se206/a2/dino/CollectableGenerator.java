package se206.a2.dino;

import java.util.ArrayList;

public class CollectableGenerator {
    private double _time = 0;
    private double _nextSpawn = 5;
    private double _obstacleSpeed = 400;
    private static final double SPEED = 1000;
    private static final double SPAWN_SPEED = 0.5;
    public final ArrayList<Collectable> _collectables = new ArrayList<>();
    private static final double CREDITS_SIZE = 50;

    public CollectableGenerator() {
        addCredits();
    }

    private void addCredits() {
        // adds 'Matt M' collectables

        Collectable c;

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_M1, 0, CREDITS_SIZE * 1.24);
        c.setFinalX(10);
        _collectables.add(c);

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_T1, 0, CREDITS_SIZE * 0.77);
        c.setFinalX(100);
        _collectables.add(c);

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_T2, 0, CREDITS_SIZE * 0.77);
        c.setFinalX(133);
        _collectables.add(c);

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_A, 0, CREDITS_SIZE * 0.80);
        c.setFinalX(70);
        _collectables.add(c);

        c = GameObjectFactory.createCollectable(GameObjectFactory.Type.CREDITS_M2, 0, CREDITS_SIZE * 1.18);
        c.setFinalX(180);
        _collectables.add(c);
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

    public Collectable getRandomCollectable() {
        int numRemaining = _collectables.size();
        if (numRemaining == 0) return null;
        return _collectables.remove(0);
    }
}
