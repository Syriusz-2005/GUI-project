package game;

import utils.Grid;
import utils.Vec2i;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class PacmanBoard implements Updatable {
    private final String boardName;
    private final Grid<Field> boardGrid;
    private final ArrayList<Entity> entities = new ArrayList<Entity>();
    private Player player;
    private GameOverListener onGameOver;
    private final GameClock clock = new GameClock();
    private int gameStartSecond = 0;

    private final Vec2i ghostSpawn;
    private final Vec2i playerSpawn;
    private final int totalFieldsWithPoints;
    private int level = 1;
    private Consumer<PacmanBoard> onInit;


    private void initState() throws IOException {
        var whiteGhost = new WhiteGhost(this);
        whiteGhost.setGridPos(ghostSpawn);
        entities.add(whiteGhost);

        var orangeGhost = new OrangeGhost(this);
        orangeGhost.setGridPos(ghostSpawn);
        entities.add(orangeGhost);

        var ghost = new RedGhost(this);
        ghost.setGridPos(ghostSpawn);
        entities.add(ghost);
        ghost.findNextRandomGoal();

        player.setGridPos(playerSpawn);
        if (onInit != null) {
            onInit.accept(this);
        }
    }

    public void setOnGameOver(GameOverListener callback) {
        onGameOver = callback;
    }

    public PacmanBoard(String boardName) throws IOException {
        this.boardName = boardName;
        Vec2i size = BoardGenerator.getSize(boardName);
        boardGrid = new Grid<>(Field.class, size.x, size.y);
        BoardGenerator.loadFromFile(boardGrid, boardName);
        System.out.println(boardName);
        ghostSpawn = boardGrid.findFirst(Field::isGhostSpawn);
        playerSpawn = boardGrid.findFirst(Field::isPlayerSpawn);
        totalFieldsWithPoints = boardGrid.findAll(Field::hasPoint).size();
        player = new Player(this);
        entities.add(player);
        clock.on((seconds) -> {
            if (seconds == gameStartSecond + 5) {
                openDoors();
            }
        });
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

    private void disposeEntities() {
        for (Entity e : entities) {
            e.destruct();
        }
        entities.clear();
    }

    private void onDeath() throws IOException {
        player.lives--;
        disposeEntities();
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

    public Vec2i getGhostSpawn() {
        return ghostSpawn;
    }

    public void step(float timeDelta) throws IOException {
        if (player.getPointsPickedUp() >= totalFieldsWithPoints) {
            disposeEntities();
            gameStartSecond = clock.getSeconds();
            BoardGenerator.loadFromFile(boardGrid, boardName);
            player.reset();
            player.resetPointsPickedUp();
            entities.add(player);
            level++;
            initState();
            return;
        }
        for (Entity entity : entities) {
            entity.step(timeDelta);
            if (entity instanceof Ghost && entity.getPos().distance(player.getPos()) < .7) {
                if (player.isInvisible) continue;
                if (player.hasPowerup()) {
                    if (!((Ghost) entity).isEthereal) {
                        ((Ghost) entity).isEthereal = true;
                        player.addScore(100);
                    }
                    continue;
                }
                onDeath();
                return;
            }
        }
    }

    private void openDoors() {
        var doors = boardGrid.findAll(Field::isDoor);
        for (var door : doors) {
            door.setIsOpen(true);
        }
    }

    public PacmanBoard add(Entity e) {
        entities.add(e);
        return this;
    }

    public GameClock getClock() {
        return clock;
    }

    public int getLevel() {
        return level;
    }

    public void onInit(Consumer<PacmanBoard> callback) {
        this.onInit = callback;
    }
}
