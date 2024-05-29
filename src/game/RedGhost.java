package game;

import utils.Vec2i;

import java.awt.*;

public class RedGhost extends Ghost {
    public RedGhost(PacmanBoard parent) {
        super(parent, new Color(237, 0, 0));
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
}
