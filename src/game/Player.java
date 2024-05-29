package game;

import utils.Vec2f;
import utils.Vec2i;

public class Player extends Entity {
    private Vec2f nextMove;

    public Player(PacmanBoard parent) {
        super(parent);
    }

    private void updateMovementDirection() {
        if (nextMove != null) {
            System.out.println("New move");
            findNextGoal(nextMove.toInt(), (Integer stepsCount) -> stepsCount > 1);
        } else {
            vel.copy(Vec2f.ZERO);
            goal = null;
        }
    }

    @Override
    protected void onInFieldCenter() {
        updateMovementDirection();
    }

    public Player setNextMove(Vec2f dir) {
        nextMove = dir;
        return this;
    }
}
