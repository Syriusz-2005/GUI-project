package view;

import game.PacmanBoard;
import utils.Vec2f;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingedBoard extends JPanel implements ComponentUpdatable {
    private final PacmanBoard board;
    private final ArrayList<SwingedField> fields = new ArrayList<>();

    public SwingedBoard(PacmanBoard board) {
        this.board = board;
        var grid = board.getBoardGrid().getGrid();
        for (var row : grid) {
            for (var f : row) {
                var field = new SwingedField(f);
                add(field);
                fields.add(field);
            }
        }
        setLayout(new GridLayout(grid.length, grid[0].length, 0, 0));
    }

    @Override
    public Dimension getPreferredSize() {
        var boardSize = board.getSize();
        var parentSize = getParent().getSize();
        var fieldSize = Math.min(
                ((double) parentSize.width) / ((double) boardSize.x),
                ((double) parentSize.height) / ((double) boardSize.y)
        );
        var finalPxSize = new Vec2f(boardSize.x, boardSize.y).subtract(1).multiply((float) fieldSize).toInt();
        update();
        return new Dimension(finalPxSize.x, finalPxSize.y);
    }

    @Override
    public void update() {
        for (var field : fields) {
            field.update();
        }
    }
}
