package view;

import game.PacmanBoard;

import javax.swing.*;
import java.awt.*;

// Why not the web platform? :(
public class PacmanBoardWindow extends JFrame implements View {
    private int FIELD_SIZE = 30;
    private PacmanBoard board;
    private JPanel boardPanel;

    public PacmanBoardWindow(PacmanBoard board) {
        super();
        this.board = board;
        var size = board.getSize().add(1).multiply(FIELD_SIZE);
        setBackground(new Color(0, 0, 0));
        setSize(size.x, size.y);
        boardPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                var grid = board.getBoardGrid().getGrid();
                g.setColor(new Color(0, 56, 154));
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid[y].length; x++) {
                        if (grid[y][x].isWall()) {
                            g.fillRect(x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                        }
                    }
                }

                var entities = board.getEntities();
                g.setColor(new Color(241, 204, 0));
                for (var entity : entities) {
                    var screenPos = entity.getPos().clone().multiply(FIELD_SIZE).toInt();
                    g.fillArc(screenPos.x - 10, screenPos.y - 10, 20, 20, 0, 350);
                }
            }
        };
        boardPanel.setBackground(new Color(0, 0, 0));
        add(boardPanel);
        setVisible(true);
    }

    public void display(PacmanBoard board) {
       repaint();
    }
}
