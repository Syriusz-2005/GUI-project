package game;

import utils.Vec2f;

public abstract class Entity implements Updatable {
    protected final Vec2f pos = new Vec2f(0, 0);
    protected final Vec2f vel = new Vec2f(0, 0);
    protected float speed = 0.01f;

    public Entity() {}

    public Vec2f getPos() {
        return pos;
    }

    /**
     *
     * @param direction a normalized direction vector. For example {1, 0} points to the right.
     */
    public Entity setMovement(Vec2f direction) {
        var dir = direction.clone().normalize();
        vel.copy(dir.multiply(speed));
        return this;
    }

    public void step(float timeDelta) {
        pos.add(vel);
    }
}
