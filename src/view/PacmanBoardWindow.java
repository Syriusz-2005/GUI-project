package view;

import game.PacmanBoard;
import utils.Vec2f;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class PacmanBoardWindow extends JFrame implements View {
    private final PacmanBoard board;
    private final SwingedBoard boardPanel;
    private GameOverWindow gameOverWindow;

    public PacmanBoardWindow(PacmanBoard board, Thread gameLoopThread) throws IOException {
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
        getContentPane().setBackground(Color.BLACK);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        setMinimumSize(new Dimension(600, 400));
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
        boardPanel = new SwingedBoard(board);
        /*
                boardPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
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

                        var dynamicPowerUp = field.getDynamicPowerUp();
                        if (dynamicPowerUp != null) {
                            g.setColor(dynamicPowerUp.getColor());
                            var r = (int) (0.5 * FIELD_SIZE);
                            var pPos = pos.clone().add(FIELD_SIZE / 2).subtract(r / 2);
                            g.fillOval(pPos.x, pPos.y, r, r);
                        }

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
            }
        };
        */

        getContentPane().add(boardPanel);
        setVisible(true);
    }

    public void display(PacmanBoard board) {
        var fieldSize = boardPanel.getFieldSize();
        boardPanel.update(fieldSize);
    }

    public void displayGameOverWindow() {
        gameOverWindow = new GameOverWindow(board);
        gameOverWindow.setVisible(true);
    }
}
