package view;

import game.PacmanBoard;
import utils.Vec2f;
import utils.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setSize(size.x, size.y);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var wSize = getSize();
                var boardSize = board.getSize();

                FIELD_SIZE = Math.min(wSize.width / boardSize.x, wSize.height / boardSize.y);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                var player = board.getPlayer();
                switch (e.getKeyChar()) {
                    case 'w': {
                        player.setNextMove(new Vec2f(0, -1));
                        break;
                    }
                    case 's': {
                        player.setNextMove(new Vec2f(0, 1));
                        break;
                    }
                    case 'a': {
                        player.setNextMove(new Vec2f(-1, 0));
                        break;
                    }
                    case 'd': {
                        player.setNextMove(new Vec2f(1, 0));
                        break;
                    }
                }
            }
        });
        boardPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                var grid = board.getBoardGrid().getGrid();
                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid[y].length; x++) {
                        var field = grid[y][x];
                        var pos = new Vec2i(x, y).multiply(FIELD_SIZE);
                        if (field.isWall()) {
                            g.setColor(new Color(0, 56, 154));
                            g.fillRect(pos.x, pos.y, FIELD_SIZE, FIELD_SIZE);
                        } else if (field.hasPoint()) {
                            g.setColor(Color.WHITE);
                            var r = (int) (0.2 * FIELD_SIZE);
                            pos.add(FIELD_SIZE / 2).subtract(r / 2);
                            g.fillOval(pos.x, pos.y, r, r);
                        }
                    }
                }

                var entities = board.getEntities();
                for (var entity : entities) {
                    entity.draw(g, FIELD_SIZE);
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
