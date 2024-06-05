package game;

public abstract class PowerUp {
    protected GameClock pClock = new GameClock();

    public abstract void apply(Entity p);
    public abstract void step(Entity p);

    public boolean isActive(Entity p) {
        boolean isActive = pClock.getSeconds() < 5;
        if (!isActive) {
            pClock.interrupt();
        }
        return isActive;
    }
}

