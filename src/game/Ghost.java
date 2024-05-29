package game;

import utils.Grid;
import utils.Vec2i;

import java.awt.*;

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
        findNextGoal(dir, (Integer stepsCount) -> true);
    }

    @Override
    public void step(float timeDelta) {
        super.step(timeDelta);
    }

    @Override
    public void draw(Graphics g, int fieldSize) {
        var screenPos = pos.clone().multiply(fieldSize).toInt().subtract(fieldSize / 3);
        g.setColor(new Color(237, 0, 0));
        g.fillArc(screenPos.x, screenPos.y, fieldSize - fieldSize / 3, fieldSize - fieldSize / 3, 0, 350);
    }
}
