package view;

import game.PacmanBoard;

import javax.swing.*;
import java.awt.*;

// Why not the web platform? :(
public class PacmanBoardWindow extends JFrame implements View {
    private int FIELD_SIZE = 30;
    private PacmanBoard board;
    public PacmanBoardWindow(PacmanBoard board) {
        super();
        this.board = board;
        var size = board.getSize().multiply(FIELD_SIZE);
        setSize(size.x, size.y);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var grid = board.getBoardGrid().getGrid();
        g.setColor(new Color(0, 75, 201));
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                g.fillRect(0, 0, 200, 200);
            }
        }

        var entities = board.getEntities();
        g.setColor(new Color(241, 204, 0));
        for (var entity : entities) {
            var screenPos = entity.getPos().clone().multiply(FIELD_SIZE).toInt();
            g.drawArc(screenPos.x, screenPos.y, 20, 20, 0, 350);
        }
    }

    public void display(PacmanBoard board) {
       repaint();
    }
}
