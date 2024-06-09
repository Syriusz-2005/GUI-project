package view;

import game.PacmanBoard;
import utils.ClockDisplay;

import javax.swing.*;
import java.awt.*;

public class SwingedStateDisplay extends JPanel implements ComponentUpdatable {
    private final JLabel pointsLabel;
    private final JLabel timeLabel;
    private final JLabel levelLabel;
    private final PacmanBoard board;
    private int prevScore = -1;
    public SwingedStateDisplay(PacmanBoard board) {
        this.board = board;
        var layout = new FlowLayout(FlowLayout.LEFT);
        layout.setHgap(20);
        setLayout(layout);


        pointsLabel = createLabel();
        add(pointsLabel);

        timeLabel = createLabel();
        add(timeLabel);

        levelLabel = createLabel();
        add(levelLabel);

        setBounds(0, 0, 450, 50);
        setBackground(Color.WHITE);
    }

    private JLabel createLabel() {
        var defaultFont = new Font(null, Font.BOLD, 30);
        var label = new JLabel();
        label.setFont(defaultFont);
        return label;
    }

    @Override
    public void update(double fieldSize) {
        int score = board.getPlayer().getScore();
        var display = new ClockDisplay(board.getClock());
        if (prevScore != score) {
            pointsLabel.setText("Score: " + score);
            prevScore = score;
        }
        timeLabel.setText(display.getFormattedTime());
        levelLabel.setText("Level: " + board.getLevel());
    }
}
