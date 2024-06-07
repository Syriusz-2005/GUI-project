package game;

import utils.MathUtils;

import java.awt.*;

public enum PowerUpType {
    SPEED() {
        @Override
        public PowerUp getInstance() {
            return new SpeedPowerUp();
        }

        @Override
        public Color getColor() {
            return Color.GREEN;
        }

        @Override
        public void apply(Entity e) {
            e.applySpeedPowerUp();
        }
    },
    FREEZE() {
        @Override
        public PowerUp getInstance() {
            return new FreezePowerUp();
        }

        @Override
        public Color getColor() {
            return Color.CYAN;
        }

        @Override
        public void apply(Entity e) {
            e.applyFreezePowerUp();
        }
    },
    INVISIBLE() {
        @Override
        public PowerUp getInstance() {
            return null;
        }

        @Override
        public Color getColor() {
            return Color.PINK;
        }

        @Override
        public void apply(Entity e) {
            e.applyInvisibilityPowerUp();
        }
    }

    public abstract PowerUp getInstance();
    public abstract Color getColor();
    public abstract void apply(Entity e);
    public static PowerUpType getRandom() {
        var r = MathUtils.randInt(0, PowerUpType.values().length);
        return switch (r) {
            case 1 -> FREEZE;
            default -> SPEED;
        };
    }
}
