package se206.a2.dino;

public class ObstacleGenerator {
    private double _lastUpdate = 0;
    private double _nextSpawn = 0;
    private double _spawnRate = 3;

    public ObstacleGenerator() {
    }

    public Obstacle spawnObstacle(double secs) {
        _lastUpdate += secs;
        if (_lastUpdate < _nextSpawn) {
            return null;
        }
        _nextSpawn = _lastUpdate + _spawnRate;
        Obstacle o = new Obstacle(Obstacle.ObstacleType.BLOCK, 60, 60, 0);
        o.setSpeedX(-150);
        return o;
    }
}
