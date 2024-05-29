package game;

import utils.Vec2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class OrangeGhost extends Ghost {
    public OrangeGhost(PacmanBoard parent) {
        super(parent, Color.ORANGE);
        speed = 0.006f;
    }

    @Override
    protected void onInFieldCenter(boolean isFirst) {
        super.onInFieldCenter(isFirst);
        if (!isFirst) return;
        var grid = parent.getBoardGrid();
        var neighbours = grid.getNeighboursPos(getGridPos());
        var playerPos = parent.getPlayer().getPos();
        var directions = new ArrayList<Vec2i>(neighbours
                .stream()
                .filter((Vec2i p) -> !grid.get(p).isWall())
                .sorted((Vec2i a, Vec2i b) -> playerPos.distance(a.toFloatCenter()) < playerPos.distance(b.toFloatCenter()) ? -1 : 1)
                .map((Vec2i p) -> p.subtract(getGridPos()))
                .toList());

        if (directions.size() > 2 || goal == null || goal.equals(getGridPos())) {
            if (directions.size() > 1 && currFieldCenter != null) {
                var dirToPrevious = currFieldCenter.clone().subtract(getGridPos());
                directions.removeIf(dirToPrevious::equals);
            }
            findNextGoal(directions.get(0), (Integer stepsCount) -> true);
        }
    }
}
