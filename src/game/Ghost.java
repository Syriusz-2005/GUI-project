package game;

import utils.Grid;
import utils.Vec2i;

public class Ghost extends Entity {
    public Ghost(PacmanBoard parent) {
        super(parent);
    }

    private Vec2i getRandDir() {
        return Math.random() > .5
                ? Math.random() > .5 ? new Vec2i(1, 0) : new Vec2i(-1, 0)
                : Math.random() > .5 ? new Vec2i(0, 1) : new Vec2i(0, -1) ;
    }

    @Override
    protected void onGoalReached() {
        super.onGoalReached();
        findNextRandomGoal();
    }

    public void findNextRandomGoal() {
        var dir = getRandDir();
        findNextGoal(dir);
    }

    @Override
    public void step(float timeDelta) {
        super.step(timeDelta);
    }
}
