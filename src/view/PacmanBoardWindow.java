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
    private final SwingedStateDisplay stateDisplayView;
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
        setMinimumSize(new Dimension(800, 600));
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
        stateDisplayView = new SwingedStateDisplay(board);

        var mainContainer = new JPanel();

        getContentPane().add(stateDisplayView);
        getContentPane().add(mainContainer);

        mainContainer.add(boardPanel);
        setVisible(true);
    }

    public void display(PacmanBoard board) {
        var fieldSize = boardPanel.getFieldSize();
        boardPanel.update(fieldSize);
        stateDisplayView.update(fieldSize);
    }

    public void displayGameOverWindow() {
        gameOverWindow = new GameOverWindow(board);
        gameOverWindow.setVisible(true);
    }
}
