package game;

import utils.Vec2f;
import utils.Vec2i;

import java.awt.*;
import java.util.function.Predicate;

public abstract class Entity implements Updatable {
    protected final Vec2f pos = new Vec2f(0, 0);
    protected final Vec2f vel = new Vec2f(0, 0);
    protected float speed = 0.01f;
    // Entity effects system would be more flexible than hardcoding values
    protected float speedMultiplier = 1;
    protected final PacmanBoard parent;
    protected Vec2i goal;
    protected Vec2i prevPos;
    protected Vec2i currFieldCenter;
    private boolean isInFieldCenter = true;
    private PowerUp speedPowerUp;
    private PowerUp freezePowerUp;
    private PowerUp invisibilityPowerUp;
    public boolean isFrozen = false;


    public Entity(PacmanBoard parent) {
        this.parent = parent;
    }


    public void applySpeedPowerUp() {
        speedPowerUp = PowerUpType.SPEED.getInstance();
        speedPowerUp.apply(this);
    }

    public void applyFreezePowerUp() {
        freezePowerUp = PowerUpType.FREEZE.getInstance();
        freezePowerUp.apply(this);
    }

    public void applyInvisibilityPowerUp() {
        invisibilityPowerUp = PowerUpType.INVISIBLE.getInstance();
        invisibilityPowerUp.apply(this);
    }

    public Vec2f getPos() {
        return pos;
    }

    public Vec2i getGridPos() {
        return new Vec2i((int) pos.x, (int) pos.y);
    }
    public Entity setGridPos(Vec2i p) {
        pos.x = p.x + .5f;
        pos.y = p.y + .5f;
        return this;
    }

    public float calcSpeedMultiplier() {
        return speed * speedMultiplier;
    }

    private void stepPowerUps() {
        if (speedPowerUp != null) speedPowerUp.step(this);
        if (freezePowerUp != null) freezePowerUp.step(this);
    }

    /**
     * @param direction a normalized direction vector. For example {1, 0} points to the right.
     */
    public Entity setMovement(Vec2f direction) {
        var dir = direction.clone().normalize();
        stepPowerUps();
        vel.copy(dir.multiply(calcSpeedMultiplier()));
        return this;
    }

    protected boolean isInFieldCenter() {
        return isInFieldCenter;
    }

    public void step(float timeDelta) {
        if (prevPos == null || !prevPos.equals(getGridPos())) {
            onGridPosChange();
        }
        isInFieldCenter = getGridPos().toFloatCenter().subtract(pos).length() <= calcSpeedMultiplier() * timeDelta;
        if (isInFieldCenter) {
            onInFieldCenter(currFieldCenter == null || !getGridPos().equals(currFieldCenter));
            currFieldCenter = getGridPos();
        }
        prevPos = getGridPos();
        if (goal != null) {
            if (goal.toFloatCenter().subtract(pos).length() < vel.length() * timeDelta) {
                onGoalReached();
            } else {
                setMovement(goal.toFloatCenter().subtract(pos));
            }
        }
        if (isFrozen) return;
        pos.add(vel.clone().multiply(timeDelta));
    }

    protected void findNextGoal(Vec2i dir, Predicate<Integer> isValid) {
        var normalizedDir = dir.normalize();
        var grid = parent.getBoardGrid();
        var currPos = getGridPos();
        var finalPos = grid.walk(currPos, normalizedDir, (Vec2i p, Field f, int i) -> !f.isWall());
        if (!isValid.test(finalPos.distance(currPos))) {
            return;
        }
        if (finalPos.equals(currPos)) {
            onGoalReached();
            return;
        }
        goal = finalPos;
    }

    protected void onGridPosChange() {}

    protected void onGoalReached() {
        goal = null;
        vel.copy(Vec2f.ZERO);
    }

    protected void onInFieldCenter(boolean isFirst) {}

    public void draw(Graphics g, int fieldSize) {
        var screenPos = pos.clone().multiply(fieldSize).toInt().subtract(fieldSize / 3);
        g.setColor(new Color(241, 204, 0));
        g.fillArc(screenPos.x, screenPos.y, fieldSize - fieldSize / 3, fieldSize - fieldSize / 3, 0, 350);
    }

    public void setSpeedMultiplier(float v) {
        speedMultiplier = v;
    }

    public PacmanBoard getParent() {
        return parent;
    }
    public abstract TextureController getTextureController();
}
