package se206.a2.dino;

/**
 * Generates obstacles to be added to the game
 */
public class ObstacleGenerator {
    private double _lastUpdate = 0;
    private double _nextSpawn = 10;
    private double _spawnSpeed = 0.5;
    private double _obstacleSpeed = 400;
    private static final double MAX_SPEED = 1000;
    private static final double MAX_SPAWN_SPEED = 1.5;

    public ObstacleGenerator() {
    }

    /**
     * @return a new obstacle if one is due to spawn, or null
     */
    public Obstacle spawnObstacle(double secs, double runningSpeed) {
        _lastUpdate += secs;
        if (_lastUpdate < _nextSpawn) {
            return null;
        }
        _nextSpawn = _lastUpdate + 1 / (_spawnSpeed * (1 + 0.5 * Math.random()));
        Obstacle o = getRandomObstacle(75 * (1 + 0.5 * Math.random()));
        o.setY(5);
        if (_obstacleSpeed < MAX_SPEED) _obstacleSpeed += 20;
        if (_spawnSpeed < MAX_SPAWN_SPEED) _spawnSpeed += 1.0 / 80;
        return o;
    }

    private Obstacle getRandomObstacle(double height) {
        switch ((int) (Math.random() * 2)) {
            case 0:
                return GameObjectFactory.createObstacle(GameObjectFactory.Type.FIRE, 0, height * 1.2);
            case 1:
                return GameObjectFactory.createObstacle(GameObjectFactory.Type.SPIKES, 0, height);
            default:
                throw new IllegalStateException("Random int out of bounds");
        }
    }
}
