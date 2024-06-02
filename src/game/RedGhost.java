package game;

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
