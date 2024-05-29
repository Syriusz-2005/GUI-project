package game;

public class Field {
    private boolean isWall;
    private boolean hasPowerup = false;
    private boolean hasPoint = true;

    public Field(boolean isWall) {
        this.isWall = isWall;
    }

    public void setHasPoint(boolean hasPoint) {
        this.hasPoint = hasPoint;
    }

    public void setHasPowerup(boolean hasPowerup) {
        this.hasPowerup = hasPowerup;
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean hasPowerup() {
        return hasPowerup;
    }

    public boolean hasPoint() {
        return hasPoint;
    }
}
