package game;

import utils.Vec2i;

import java.awt.*;
import java.util.ArrayList;

public abstract class Ghost extends Entity {
    private final Color color;
    public Ghost(PacmanBoard parent, Color color) {
        super(parent);
        this.color = color;
    }

    protected Vec2i getRandDir() {
        return Math.random() > .5
                ? Math.random() > .5 ? new Vec2i(1, 0) : new Vec2i(-1, 0)
                : Math.random() > .5 ? new Vec2i(0, 1) : new Vec2i(0, -1) ;
    }

    protected boolean useEscapeMode() {
        boolean playerHasPowerup = parent.getPlayer().hasPowerup();
        if (!playerHasPowerup) return false;

        var grid = parent.getBoardGrid();
        var neighbours = grid.getNeighboursPos(getGridPos());
        var playerPos = parent.getPlayer().getPos();

        var directions = new ArrayList<Vec2i>(neighbours
                .stream()
                .filter((Vec2i p) -> !grid.get(p).isWall())
                .sorted((Vec2i a, Vec2i b) -> playerPos.distance(a.toFloatCenter()) > playerPos.distance(b.toFloatCenter()) ? -1 : 1)
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
        g.setColor(playerHasPowerup ? Color.GREEN : color);
        g.fillArc(screenPos.x, screenPos.y, fieldSize - fieldSize / 3, fieldSize - fieldSize / 3, 0, 350);
    }
}
