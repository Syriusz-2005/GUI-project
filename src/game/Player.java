package game;

import utils.Vec2f;
import utils.Vec2i;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class Player extends Entity {
    private static final int POWERUP_DURATION = 10;
    private Vec2f nextMove;
    private int score = 0;
    public int lives = 3;
    public GameClock powerupClock;

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

        var dynamicPowerUp = field.getDynamicPowerUp();
        if (dynamicPowerUp != null) {
            dynamicPowerUp.apply(this);
            field.setDynamicPowerUp(null);
        }
    }

    public int getScore() {
        return score;
    }
    public void addScore(int points) {
        score += points;
    }

    public void setNextMove(Vec2f dir) {
        nextMove = dir;
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
        return powerupClock != null && powerupClock.getSeconds() < POWERUP_DURATION;
    }

    public int getPointsPickedUp() {
        return pointsPickedUp;
    }
    public void resetPointsPickedUp() {
        pointsPickedUp = 0;
    }

    @Override
    public void draw(Graphics g, int fieldSize) {
        var size = fieldSize - fieldSize / 3;
        var screenPos = Vec2i.ZERO;
        var currentTexture = textureController.getCurrTexture(new Vec2i(size));
        var g2d = (Graphics2D) g;
        var currTransform = g2d.getTransform();

        AffineTransform trans = new AffineTransform();
        var anchor = screenPos.clone().add(size / 2);
        if (vel.length() > 0) {
            var rotationAngle = vel.clone().normalize().angle();
            trans.rotate(rotationAngle, anchor.x, anchor.y);
        }

        g2d.transform(trans);
        g2d.drawImage(currentTexture, 0, 0,null);
        g2d.setTransform(currTransform);
    }
}
