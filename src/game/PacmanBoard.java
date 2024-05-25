package game;

import utils.Grid;

import java.util.ArrayList;

public class PacmanBoard implements Updatable {
    private final Grid<Field> boardGrid;
    private final ArrayList<Entity> entities = new ArrayList<Entity>();

    public PacmanBoard(int width, int height) {
        boardGrid = new Grid<Field>(width, height);
    }

    public void step(float timeDelta) {
        for (Entity entity : entities) {
            entity.step(timeDelta);
        }
    }
}
