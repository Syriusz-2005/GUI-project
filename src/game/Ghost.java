package game;

import utils.MathUtils;
import utils.Vec2i;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Ghost extends Entity {
    private final GhostColor color;
    public boolean isEthereal = false;
    private final TextureController textureController;
    private GameClock powerUpSpawnClock = new GameClock();

    public Ghost(PacmanBoard parent, GhostColor color) throws IOException {
        super(parent);
        this.color = color;
        this.textureController = new TextureController(new String[]{"ghost_scared.png"}, 1f, "ghost_" + color.getColor() + ".png");
        powerUpSpawnClock.on(this::trySpawningPowerup);
    }

    private void trySpawningPowerup(int s) {
        if (s % 5 == 0) {
            if (MathUtils.randInt(0, 5) == 0) {
                var currField = parent.getBoardGrid().get(getGridPos());
                currField.setDynamicPowerUp(PowerUpType.getRandom());
            }
        }
    }

    protected Vec2i getRandDir() {
        return Math.random() > .5
                ? Math.random() > .5 ? new Vec2i(1, 0) : new Vec2i(-1, 0)
                : Math.random() > .5 ? new Vec2i(0, 1) : new Vec2i(0, -1) ;
    }

    @Override
    protected void onGridPosChange() {
        super.onGridPosChange();
        if (isEthereal && parent.getBoardGrid().get(getGridPos()).isGhostSpawn()) {
            isEthereal = false;
        }
    }

    @Override
    public void step(float timeDelta) {
        super.step(timeDelta);
        speedMultiplier = (isEthereal ? 1.5f : 1) + ((float) (parent.getLevel() - 1) * 0.1f);
    }

    protected boolean useGhostBehaviours() {
        boolean playerHasPowerup = parent.getPlayer().hasPowerup();
        if (!playerHasPowerup && !isEthereal) return false;

        var grid = parent.getBoardGrid();
        var neighbours = grid.getNeighboursPos(getGridPos());
        var targetPos = isEthereal ? parent.getGhostSpawn().toFloatCenter() : parent.getPlayer().getPos();
        var dir = isEthereal ? 1 : -1;
        var directions = new ArrayList<>(neighbours
                .stream()
                .filter((Vec2i p) -> !grid.get(p).isWall())
                .sorted((Vec2i a, Vec2i b) -> targetPos.distance(a.toFloatCenter()) > targetPos.distance(b.toFloatCenter()) ? dir : dir * -1)
                .map((Vec2i p) -> p.subtract(getGridPos()))
                .toList());

        if (directions.size() > 2 || goal == null || goal.equals(getGridPos())) {
            if (directions.size() > 1 && currFieldCenter != null) {
                var dirToPrevious = currFieldCenter.clone().subtract(getGridPos());
                directions.removeIf(dirToPrevious::equals);
            }
            findNextGoal(directions.get(0), (Integer stepsCount) -> true);
        }

        return true;
    }

    @Override
    public void draw(Graphics g, int fieldSize) {
        boolean playerHasPowerup = parent.getPlayer().hasPowerup();
        var screenPos = pos.clone().multiply(fieldSize).toInt().subtract(fieldSize / 3);
        textureController.setState(playerHasPowerup);
        if (isEthereal) {
            g.setColor(Color.GRAY);
        }
        var size = fieldSize - fieldSize / 3;
        var currentTexture = textureController.getCurrTexture(new Vec2i(size));
        g.drawImage(currentTexture, screenPos.x, screenPos.y, null);
    }
}
