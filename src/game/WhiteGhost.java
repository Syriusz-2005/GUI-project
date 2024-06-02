package game;

import utils.Vec2i;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class WhiteGhost extends Ghost {

    public WhiteGhost(PacmanBoard parent) {
        super(parent, new Color(255, 255, 255));
    }

    @Override
    protected void onInFieldCenter(boolean isFirst) {
        if (!isFirst) return;
        super.onInFieldCenter(true);
        if (useGhostBehaviours()) return;

        var grid = parent.getBoardGrid();
        var neighbours = grid.getNeighboursPos(getGridPos());
        var directions = new ArrayList<Vec2i>(neighbours
                .stream()
                .filter((Vec2i p) -> !grid.get(p).isWall())
                .map((Vec2i p) -> p.subtract(getGridPos()))
                .toList());

        if (directions.size() > 2 || goal == null || goal.equals(getGridPos())) {
            Collections.shuffle(directions);
            if (directions.size() > 1 && currFieldCenter != null) {
                var dirToPrevious = currFieldCenter.clone().subtract(getGridPos());
                directions.removeIf(dirToPrevious::equals);
            }
            findNextGoal(directions.get(0), (Integer stepsCount) -> true);
        }
    }
}
