package game;

import utils.Vec2f;

import java.awt.*;
import java.io.IOException;
import java.time.Clock;

public class Player extends Entity {
    private Vec2f nextMove;
    private int score = 0;
    public int lives = 3;
    public GameClock powerupClock;
    private final int powerupDuration = 10;
    private int pointsPickedUp = 0;
    private final TextureController textureController = new TextureController(new String[]{
            "player_frame_0.png",
            "player_frame_1.png",
            "player_frame_2.png",
            "player_frame_3.png"
    }, 0.1f, "player_frame_2.png");

    public Player(PacmanBoard parent) throws IOException {
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
            pointsPickedUp++;
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
        textureController.setState(goal != null);
        if (!hasPowerup()) {
            powerupClock = null;
        }
    }

    public boolean hasPowerup() {
        return powerupClock != null && powerupClock.getSeconds() < powerupDuration;
    }
    public int getPointsPickedUp() {
        return pointsPickedUp;
    }
    public void resetPointsPickedUp() {
        pointsPickedUp = 0;
    }

    @Override
    public void draw(Graphics g, int fieldSize) {
        var screenPos = pos.clone().multiply(fieldSize).toInt().subtract(fieldSize / 3);
        var currentTexture = textureController.getCurrTexture();
        var scale = fieldSize - fieldSize / 3;
        g.drawImage(currentTexture, screenPos.x, screenPos.y, scale, scale, null);
    }
}
