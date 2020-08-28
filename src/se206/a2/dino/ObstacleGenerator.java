package se206.a2.dino;

public class ObstacleGenerator {
    private double _lastUpdate = 0;
    private double _nextSpawn = 0;
    private double _spawnRate = 2;

    public ObstacleGenerator() {
    }

    public Obstacle spawnObstacle(double secs) {
        _lastUpdate += secs;
        if (_lastUpdate < _nextSpawn) {
            return null;
        }
        _nextSpawn = _lastUpdate + _spawnRate + Math.random() * _spawnRate;
        Obstacle o = GameObjectFactory.createObstacle(GameObjectFactory.Type.INVERTED_TEE, 0, 40 + Math.random() * 40);
        o.setSpeedX(-150);
        o.setX(1500);
        return o;
    }
}
