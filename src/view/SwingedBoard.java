package view;

import game.PacmanBoard;
import utils.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SwingedBoard extends JLayeredPane implements ComponentUpdatable {
    private final PacmanBoard board;
    private final ArrayList<SwingedField> fields = new ArrayList<>();
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
                fields.add(field);
            }
        }
        gridPanel.setBackground(Color.BLACK);
        add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        add(playerComponent, JLayeredPane.POPUP_LAYER);
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
        for (var field : fields) {
            field.update(fieldSize);
        }
        playerComponent.update(fieldSize);
    }

    public double getFieldSize() {
        return fieldSize;
    }
}
