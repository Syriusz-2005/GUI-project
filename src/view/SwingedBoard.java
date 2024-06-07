package view;

import game.PacmanBoard;

import javax.swing.*;
import java.awt.*;

public class SwingedBoard extends JPanel {
    private final PacmanBoard board;
    public SwingedBoard(PacmanBoard board) {
        this.board = board;
        var grid = board.getBoardGrid().getGrid();
        for (var row : grid) {
            for (var f : row) {
                var field = new SwingedField(f);
                add(field);
            }
        }
        setLayout(new GridLayout(grid.length, grid[0].length, 0, 0));
    }
}
