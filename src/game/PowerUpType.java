package game;

public enum PowerUpType {
    SPEED() {
        @Override
        public PowerUp getInstance() {
            return new SpeedPowerUp();
        }
    };

    public abstract PowerUp getInstance();
}
