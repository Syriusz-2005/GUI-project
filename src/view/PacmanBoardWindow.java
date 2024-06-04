package view;

import game.PacmanBoard;
import utils.ClockDisplay;
import utils.Vec2f;
import utils.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PacmanBoardWindow extends JFrame implements View {
    private int FIELD_SIZE = 30;
    private PacmanBoard board;
    private JPanel boardPanel;
    private GameOverWindow gameOverWindow;

    public PacmanBoardWindow(PacmanBoard board, Thread gameLoopThread) {
        super();
        this.board = board;
        board.setOnGameOver(this::displayGameOverWindow);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Interrupting the game loop...");
                gameLoopThread.interrupt();
            }
        });
        setTitle("Game window");
        setBackground(new Color(0, 0, 0));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                var wSize = getSize();
                var boardSize = board.getSize();

                FIELD_SIZE = Math.min(wSize.width / boardSize.x, (wSize.height - 180) / boardSize.y);
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
                var s = System.currentTimeMillis();
                super.paintComponent(g);
                var grid = board.getBoardGrid().getGrid();
                g.translate(0, 150);

                g.setColor(Color.WHITE);
                g.setFont(new Font(null, Font.PLAIN, 40));

                var scoreTitle = "Score: " + board.getPlayer().getScore();
                g.drawString(scoreTitle, 30, -70);

                var livesCounter = "Lives: " + board.getPlayer().lives;
                g.drawString(livesCounter, 250, -70);

                var levelCounter = "Level: " + board.getLevel();
                g.drawString(levelCounter, 650, -70);

                var clockDisplay = new ClockDisplay(board.getClock());
                var time = clockDisplay.getFormattedTime();
                g.drawString(time, 450, -70);

                for (int y = 0; y < grid.length; y++) {
                    for (int x = 0; x < grid[y].length; x++) {
                        var field = grid[y][x];
                        var pos = new Vec2i(x, y).multiply(FIELD_SIZE);
                        if (field.isDoor()) {
                            g.setColor(field.isOpen() ? Color.GRAY : Color.WHITE);
                            g.fillRect(pos.x, pos.y, FIELD_SIZE, FIELD_SIZE);
                        } else if (field.isWall()) {
                            g.setColor(new Color(0, 56, 154));
                            g.fillRect(pos.x, pos.y, FIELD_SIZE, FIELD_SIZE);
                        } else if (field.hasPowerup()) {
                            g.setColor(Color.WHITE);
                            var r = (int) (0.5 * FIELD_SIZE);
                            pos.add(FIELD_SIZE / 2).subtract(r / 2);
                            g.fillOval(pos.x, pos.y, r, r);
                        } else if (field.hasPoint()) {
                            g.setColor(Color.WHITE);
                            var r = (int) (0.2 * FIELD_SIZE);
                            pos.add(FIELD_SIZE / 2).subtract(r / 2);
                            g.fillOval(pos.x, pos.y, r, r);
                        }
                    }
                }

                if (board.isGameOver()) {
                    var gameOverTitle = "Game over!";
                    g.setColor(Color.RED);
                    g.setFont(new Font(null, Font.PLAIN, 80));
                    g.drawString(gameOverTitle, getWidth() / 2, getHeight() / 2);
                }

                var entities = board.getEntities();
                for (var entity : entities) {
                    entity.draw(g, FIELD_SIZE);
                }
                var d = System.currentTimeMillis() - s;
                System.out.println("Delta: " + d);
            }
        };
        boardPanel.setBackground(new Color(0, 0, 0));
        add(boardPanel);
        setVisible(true);
    }

    public void display(PacmanBoard board) {
       repaint();
    }

    public void displayGameOverWindow() {
        gameOverWindow = new GameOverWindow(board);
        gameOverWindow.setVisible(true);
    }
}
