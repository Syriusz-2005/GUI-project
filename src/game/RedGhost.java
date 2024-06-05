package game;

import java.awt.*;
import java.io.IOException;

public class RedGhost extends Ghost {
    public RedGhost(PacmanBoard parent) throws IOException {
        super(parent, GhostColor.RED);
    }

    @Override
    protected void onGoalReached() {
        super.onGoalReached();
        findNextRandomGoal();
    }

    @Override
    protected void onInFieldCenter(boolean isFirst) {
        super.onInFieldCenter(isFirst);
        if (isFirst) {
            useGhostBehaviours();
        }
    }

    public void findNextRandomGoal() {
        if (parent.getPlayer().hasPowerup()) return;
        var dir = getRandDir();
        findNextGoal(dir, (Integer stepsCount) -> true);
    }
}
