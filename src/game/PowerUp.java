package game;

public enum PowerUp {
    SPEED() {
        private GameClock pClock = new GameClock();
        @Override
        public void apply(Entity p) {}

        @Override
        public void step(Entity p) {
            p.speedMultiplier = isActive(p) ? 1.5f : 1f;
        }

        @Override
        public boolean isActive(Entity p) {
            boolean isActive = pClock.getSeconds() < 5;
            if (!isActive) {
                pClock.interrupt();
            }
            return isActive;
        }
    };

    public abstract void apply(Entity p);
    public abstract void step(Entity p);
    public abstract boolean isActive(Entity p);
}
