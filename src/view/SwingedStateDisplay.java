package view;

import game.PacmanBoard;

import javax.swing.*;
import java.awt.*;

public class SwingedStateDisplay extends JPanel implements ComponentUpdatable {
    private JLabel pointsLabel;
    private final PacmanBoard board;
    private int prevScore = -1;
    public SwingedStateDisplay(PacmanBoard board) {
        this.board = board;
        setLayout(new FlowLayout(FlowLayout.LEFT));

        pointsLabel = new JLabel();
        pointsLabel.setForeground(Color.WHITE);
        pointsLabel.setFont(new Font(null, Font.BOLD, 30));
        pointsLabel.setBackground(new Color(0, 0, 0, 0));
        add(pointsLabel);

        setBounds(0, 0, 400, 100);
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public void update(double fieldSize) {
        int score = board.getPlayer().getScore();
        if (prevScore != score) {
            pointsLabel.setText("Score: " + score);
            prevScore = score;
        }
    }
}
