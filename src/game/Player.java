package game;

import utils.Vec2f;
import utils.Vec2i;

public class Player extends Entity {
    private Vec2f nextMove;
    boolean isInitial = true;

    public Player(PacmanBoard parent) {
        super(parent);
    }

    private void updateMovementDirection() {
        if (nextMove != null) {
            findNextGoal(nextMove.toInt());
            nextMove = null;
        } else {
            vel.copy(Vec2f.ZERO);
        }
    }

    @Override
    protected void onInFieldCenter() {
        updateMovementDirection();
    }

    public Player setNextMove(Vec2f dir) {
        nextMove = dir;
        if (isInitial) {
            updateMovementDirection();
            isInitial = false;
        }
        return this;
    }
}
