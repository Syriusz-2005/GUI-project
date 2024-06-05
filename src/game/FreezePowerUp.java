package game;

public class FreezePowerUp extends PowerUp {

    private void setFrozen(PacmanBoard b, boolean isFrozen) {
        b.getEntities()
            .forEach((e) -> {
                if (e instanceof Ghost) {
                    e.isFrozen = isFrozen;
                }
            });
    }

    @Override
    public void apply(Entity p) {
        setFrozen(p.getParent(), true);
    }

    @Override
    public void step(Entity p) {
        if (!isActive(p)) {
            setFrozen(p.getParent(), false);
        }
    }
}
