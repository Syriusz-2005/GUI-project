package game;

public class InvisibilityPowerup extends PowerUp {
    private void setInvisible(Player p, boolean isInvisible) {
        p.isInvisible = isInvisible;
    }

    @Override
    public void apply(Entity p) {}

    @Override
    public void step(Entity p) {
        setInvisible((Player) p, isActive(p));
    }
}
