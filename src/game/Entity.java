package game;

import utils.Vec2f;
import utils.Vec2i;

public abstract class Entity implements Updatable {
    protected final Vec2f pos = new Vec2f(0, 0);
    protected final Vec2f vel = new Vec2f(0, 0);
    protected float speed = 0.01f;
    protected final PacmanBoard parent;
    protected Vec2i goal;
    private Vec2i prevPos;
    private Vec2i currFieldCenter;

    public Entity(PacmanBoard parent) {
        this.parent = parent;
    }

    public Vec2f getPos() {
        return pos;
    }

    public Vec2i getGridPos() {
        return new Vec2i((int) pos.x, (int) pos.y);
    }

    /**
     * @param direction a normalized direction vector. For example {1, 0} points to the right.
     */
    public Entity setMovement(Vec2f direction) {
        var dir = direction.clone().normalize();
        vel.copy(dir.multiply(speed));
        return this;
    }

    public void step(float timeDelta) {
        if (prevPos == null || !prevPos.equals(getGridPos())) {
            onGridPosChange();
        }
        if (getGridPos().toFloatCenter().subtract(pos).length() < vel.length() * timeDelta
                && (currFieldCenter == null || !currFieldCenter.equals(getGridPos()))
        ) {
            onInFieldCenter();
            currFieldCenter = getGridPos();
        }
        prevPos = getGridPos();
        if (goal != null) {
            if (goal.toFloatCenter().subtract(pos).length() < vel.length() * timeDelta) {
                onGoalReached();
            }
            setMovement(goal.toFloatCenter().subtract(pos));
        }
        pos.add(vel.clone().multiply(timeDelta));
    }

    protected void findNextGoal(Vec2i dir) {
        var normalizedDir = dir.normalize();
        var grid = parent.getBoardGrid();
        var currPos = getGridPos();
        var finalPos = grid.walk(currPos, normalizedDir, (Vec2i p, Field f, int i) -> !f.isWall());
        if (finalPos.equals(getGridPos())) {
            onGoalReached();
            return;
        }
        goal = finalPos;
    }

    protected void onGridPosChange() {
    }

    protected void onGoalReached() {
        vel.copy(Vec2f.ZERO);
    }

    protected void onInFieldCenter() {
    }
}
