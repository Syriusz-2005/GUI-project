package game;

import utils.Vec2f;

public class Player extends Entity {
    private Vec2f nextMove;

    public Player(PacmanBoard parent) {
        super(parent);
    }

    private void updateMovementDirection() {
        if (nextMove != null) {
            findNextGoal(nextMove.toInt());
        }
    }

    @Override
    protected void onGridPosChange() {
        super.onGridPosChange();
        updateMovementDirection();
    }

    public Player setNextMove(Vec2f dir) {
        nextMove = dir;
        updateMovementDirection();
        return this;
    }
}
