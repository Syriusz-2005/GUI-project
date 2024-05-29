package game;

import utils.Grid;
import utils.Vec2i;

import java.util.ArrayList;

public class PacmanBoard implements Updatable {
    private final Grid<Field> boardGrid;
    private final ArrayList<Entity> entities = new ArrayList<Entity>();
    private Player player;

    private void initState() {
        entities.clear();
        player = new Player(this);
        var ghost = new Ghost(this);
        ghost.pos.x = 1.5f;
        ghost.pos.y = 1.5f;
        entities.add(ghost);
        player.pos.x = 14.5f;
        player.pos.y = 13.5f;
        entities.add(player);
        ghost.findNextRandomGoal();
    }

    public PacmanBoard(int width, int height) {
        boardGrid = new Grid<Field>(Field.class,  width, height);
        BoardRandomGenerator.loadFromFile(boardGrid);
        initState();
    }
    public Grid<Field> getBoardGrid() {
        return boardGrid;
    }

    public Vec2i getSize() {
        return boardGrid.getSize();
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    private void onDeath() {
        initState();
    }

    public void step(float timeDelta) {
        for (Entity entity : entities) {
            if (entity != player && entity.getPos().distance(player.getPos()) < .7) {
                onDeath();
                return;
            }
            entity.step(timeDelta);
        }
    }
    public PacmanBoard add(Entity e) {
        entities.add(e);
        return this;
    }
}
