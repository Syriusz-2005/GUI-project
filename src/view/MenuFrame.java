package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class MenuFrame extends JFrame {
    public static Dimension PREFERED_BUTTON_SIZE = new Dimension(180, 30);
    private final JPanel titleScreen;
    private final JPanel rankingPanel;
    private final JPanel boardSelectPanel;

    public MenuFrame(Consumer<String> onPlay) {
        super();
        setName("Pacman game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 500));
        setLayout(new FlowLayout());
        titleScreen = new TitlePanel(this::switchToBoardSelect, this::switchToRanking);
        rankingPanel = new RankingPanel();
        boardSelectPanel = new BoardSelectPanel(onPlay);

        add(titleScreen);
        setVisible(true);
    }

    private void switchToRanking(ActionEvent e) {
        remove(titleScreen);
        add(rankingPanel);
        repaint();
        setVisible(true);
    }

    private void switchToBoardSelect(ActionEvent e) {
        remove(titleScreen);
        add(boardSelectPanel);
        repaint();
        setVisible(true);
    }
}
