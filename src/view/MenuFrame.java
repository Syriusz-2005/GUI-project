package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame {
    public static Dimension PREFERED_BUTTON_SIZE = new Dimension(180, 30);
    private final JPanel titleScreen;
    private final JPanel rankingPanel;

    public MenuFrame(ActionListener onPlay) {
        super();
        setName("Pacman game");
        setSize(new Dimension(800, 500));
        setLayout(new FlowLayout());
        titleScreen = new TitlePanel(onPlay, this::switchToRanking);
        rankingPanel = new RankingPanel();

        add(titleScreen);
        setVisible(true);
    }

    private void switchToRanking(ActionEvent e) {
        System.out.println("Switching to ranking...");
        remove(titleScreen);
        add(rankingPanel);
        repaint();
        setVisible(true);
        System.out.println("Switched!");
    }
}
