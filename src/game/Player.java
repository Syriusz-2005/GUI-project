package game;

import utils.Vec2f;

import java.time.Clock;

public class Player extends Entity {
    private Vec2f nextMove;
    private int score = 0;
    public int lives = 3;
    public GameClock powerupClock;
    private final int powerupDuration = 10;

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

    public void reset() {
        nextMove = null;
        vel.copy(Vec2f.ZERO);
        goal = null;
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

        if (field.hasPowerup()) {
            powerupClock = new GameClock();
            field.setHasPowerup(false);
        }
    }

    public int getScore() {
        return score;
    }
    public void addScore(int points) {
        score += points;
    }

    public Player setNextMove(Vec2f dir) {
        nextMove = dir;
        return this;
    }

    @Override
    public void step(float timeDelta) {
        super.step(timeDelta);
        if (!hasPowerup()) {
            powerupClock = null;
        }
    }

    public boolean hasPowerup() {
        return powerupClock != null && powerupClock.getSeconds() < powerupDuration;
    }
}
