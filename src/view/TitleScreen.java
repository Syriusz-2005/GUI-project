package view;

import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JFrame {
    public static Dimension PREFERED_BUTTON_SIZE = new Dimension(180, 30);

    public TitleScreen() {
        super();
        setName("Pacman game");
        setSize(new Dimension(800, 500));
        setLayout(new FlowLayout());

        var mainMenuContainer = new JPanel();
        mainMenuContainer.setLayout(new GridLayout(2, 1));
        var title = new JLabel("Pacman");
        title.setFont(new Font(null, Font.BOLD, 60));
        mainMenuContainer.add(title);

        var menuButtonsContainer = new JPanel();
        var l = new GridLayout(2, 1);
        l.setVgap(10);
        menuButtonsContainer.setLayout(l);

        var playButton = new JButton("Play");
        playButton.setPreferredSize(PREFERED_BUTTON_SIZE);
        menuButtonsContainer.add(playButton);

        var rankingButton = new JButton("View ranking");
        rankingButton.setPreferredSize(PREFERED_BUTTON_SIZE);
        menuButtonsContainer.add(rankingButton);

        mainMenuContainer.add(menuButtonsContainer);


        add(mainMenuContainer);
        setVisible(true);
    }


}
