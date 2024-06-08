package view;

import game.PacmanBoard;
import utils.Vec2f;
import utils.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SwingedBoard extends JPanel implements ComponentUpdatable {
    private final PacmanBoard board;
    private final ArrayList<SwingedField> fields = new ArrayList<>();
    private double fieldSize;
    private final SwingedPlayer playerComponent;


    public SwingedBoard(PacmanBoard board) throws IOException {
        this.board = board;
        playerComponent = new SwingedPlayer(board, this);

        var grid = board.getBoardGrid().getGrid();
        //Documentation said that: https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html

//        var gridPanel = new JPanel();
        setLayout(new GridLayout(grid.length, grid[0].length, 0, 0));

        for (var row : grid) {
            for (var f : row) {
                var field = new SwingedField(f);
                add(field);
                fields.add(field);
            }
        }
//        gridPanel.setBackground(Color.CYAN);
//        add(gridPanel, JLayeredPane.DEFAULT_LAYER);
        setBackground(Color.DARK_GRAY);
//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                gridPanel.setBounds(getBounds());
//            }
//        });
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
        System.out.println(new Dimension(finalPxSize.x, finalPxSize.y));
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
