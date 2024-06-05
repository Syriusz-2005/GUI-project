package game;

public class Field {
    private boolean isWall;
    private boolean isDoor = false;
    private boolean isOpen = false;
    private boolean hasPowerup = false;
    private boolean hasPoint = false;
    private boolean isGhostSpawn = false;
    private boolean isPlayerSpawn = false;
    private PowerUpType dynamicPowerUp = null;

    public Field(boolean isWall) {
        this.isWall = isWall;
    }

    public void setIsGhostSpawn(boolean isGhostSpawn) {
        this.isGhostSpawn = isGhostSpawn;
    }

    public void setPlayerSpawn(boolean playerSpawn) {
        isPlayerSpawn = playerSpawn;
    }

    public boolean isPlayerSpawn() {
        return isPlayerSpawn;
    }

    public boolean isGhostSpawn() {
        return isGhostSpawn;
    }

    public void setHasPoint(boolean hasPoint) {
        this.hasPoint = hasPoint;
    }

    public void setHasPowerup(boolean hasPowerup) {
        this.hasPowerup = hasPowerup;
    }
    public void setIsDoor(boolean isDoor) {this.isDoor = isDoor;}

    public boolean isWall() {
        if (isDoor()) {
            return !isOpen;
        }
        return isWall;
    }

    public boolean hasPowerup() {
        return hasPowerup;
    }

    public PowerUpType getDynamicPowerUp() {
        return dynamicPowerUp;
    }
    public void setDynamicPowerUp(PowerUpType powerUp) {
        dynamicPowerUp = powerUp;
    }

    public boolean hasPoint() {
        return hasPoint;
    }
    public boolean isDoor() {return isDoor;}
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    public boolean isOpen() {
        return isOpen;
    }
}
