package game;

import utils.Grid;
import utils.Vec2i;

import java.util.ArrayList;
import java.util.function.Function;

public class PacmanBoard implements Updatable {
    private final Grid<Field> boardGrid;
    private final ArrayList<Entity> entities = new ArrayList<Entity>();
    private Player player;
    private GameOverListener onGameOver;
    private final GameClock clock = new GameClock();

    private void initState() {
        var whiteGhost = new WhiteGhost(this);
        whiteGhost.setGridPos(new Vec2i(35, 2));
        entities.add(whiteGhost);

        var orangeGhost = new OrangeGhost(this);
        orangeGhost.setGridPos(new Vec2i(26, 1));
        entities.add(orangeGhost);

        var ghost = new RedGhost(this);
        ghost.setGridPos(new Vec2i(1, 1));
        entities.add(ghost);
        ghost.findNextRandomGoal();

        player.pos.x = 14.5f;
        player.pos.y = 13.5f;
    }

    public void setOnGameOver(GameOverListener callback) {
        onGameOver = callback;
    }

    public PacmanBoard(int width, int height) {
        boardGrid = new Grid<Field>(Field.class,  width, height);
        BoardRandomGenerator.loadFromFile(boardGrid);
        player = new Player(this);
        entities.add(player);
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
        player.lives--;
        entities.clear();
        player.reset();
        if (isGameOver()) {
            onGameOver.call();
            clock.interrupt();
            System.out.println("Game over!");
            return;
        }
        entities.add(player);
        initState();
    }

    public boolean isGameOver() {
        return player.lives <= 0;
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
