package game;

public class SpeedPowerUp extends PowerUp {

    @Override
    public void apply(Entity p) {}

    @Override
    public void step(Entity p) {
        p.setSpeedMultiplier(isActive(p) ? 1.5f : 1f);
    }
}
