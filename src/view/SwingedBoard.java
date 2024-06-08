package view;

import game.Entity;
import game.PacmanBoard;
import utils.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SwingedBoard extends JLayeredPane implements ComponentUpdatable {
    private final PacmanBoard board;
    private final ArrayList<SwingedField> fieldViews = new ArrayList<>();
    private final ArrayList<SwingedEntity> entityViews = new ArrayList<>();
    private double fieldSize;
    private final SwingedPlayer playerComponent;
    private final JPanel gridPanel;

    public SwingedBoard(PacmanBoard board) throws IOException {
        this.board = board;
        playerComponent = new SwingedPlayer(board, this);

        var grid = board.getBoardGrid().getGrid();
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(grid.length, grid[0].length, 0, 0));

        for (var row : grid) {
            for (var f : row) {
                var field = new SwingedField(f);
                gridPanel.add(field);
                fieldViews.add(field);
            }
        }
        gridPanel.setBackground(Color.BLACK);

        Consumer<PacmanBoard> reInit = (b) -> {
            removeAll();
            entityViews.clear();
            add(gridPanel, JLayeredPane.DEFAULT_LAYER);
            add(playerComponent, JLayeredPane.POPUP_LAYER);
            for (Entity e : board.getEntities()) {
                var entityView = new SwingedEntity(board, this, e);
                entityViews.add(entityView);
                add(entityView, JLayeredPane.POPUP_LAYER);
            }
        };

        reInit.accept(board);
        board.onInit(reInit);
    }

    @Override
    public Dimension getPreferredSize() {
        var boardSize = board.getSize();
        var parentSize = new Vec2i(getParent().getSize());
        fieldSize = Math.min(
                (Math.floor((double) parentSize.x / (double) boardSize.x) * boardSize.x) / ((double) boardSize.x),
                (Math.floor((double) parentSize.y / (double) boardSize.y) * boardSize.y) / ((double) boardSize.y)
        );
        var finalPxSize = boardSize
                .toFloat()
                .multiply((int) fieldSize)
                .toInt();

        gridPanel.setBounds(0, 0, finalPxSize.x, finalPxSize.y);
        return new Dimension(finalPxSize.x, finalPxSize.y);
    }

    @Override
    public void update(double fieldsSize) {
        for (var field : fieldViews) {
            field.update(fieldSize);
        }
        playerComponent.update(fieldSize);
        for (SwingedEntity e : entityViews) {
            e.update(fieldSize);
        }
    }

    public double getFieldSize() {
        return fieldSize;
    }
}
