package game;

public class SpeedPowerUp implements PowerUp {
    private GameClock pClock = new GameClock();

    @Override
    public void apply(Entity p) {
    }
    @Override
    public boolean isActive(Entity p) {
        boolean isActive = pClock.getSeconds() < 5;
        if (!isActive) {
            pClock.interrupt();
        }
        return isActive;
    }

    @Override
    public void step(Entity p) {
        p.setSpeedMultiplier(isActive(p) ? 1.5f : 1f);
    }
}
