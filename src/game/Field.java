package game;

public class Field {
    private boolean isWall;
    private boolean isDoor = false;
    private boolean isOpen = false;
    private boolean hasPowerup = false;
    private boolean hasPoint;

    public Field(boolean isWall) {
        this.isWall = isWall;
        this.hasPoint = !isWall;
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

    public boolean hasPoint() {
        return hasPoint;
    }
    public boolean isDoor() {return isDoor;}
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
}
