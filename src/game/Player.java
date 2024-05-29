package game;

import utils.Vec2f;
import utils.Vec2i;

public class Player extends Entity {
    private Vec2f nextMove;
    private int score = 0;

    public Player(PacmanBoard parent) {
        super(parent);
    }

    private void updateMovementDirection() {
        if (nextMove != null) {
            findNextGoal(nextMove.toInt(), (Integer stepsCount) -> stepsCount > 0);
        } else {
            vel.copy(Vec2f.ZERO);
            goal = null;
        }
    }

    @Override
    protected void onInFieldCenter(boolean isFirst) {
        updateMovementDirection();
    }

    @Override
    protected void onGridPosChange() {
        var gridPos = getGridPos();
        var field = parent.getBoardGrid().get(gridPos);
        if (field.hasPoint()) {
            field.setHasPoint(false);
            score++;
        }
    }

    public Player setNextMove(Vec2f dir) {
        nextMove = dir;
        return this;
    }
}
