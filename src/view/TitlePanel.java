package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TitlePanel extends JPanel {

    public TitlePanel(ActionListener onPlay, ActionListener onSwitchToRanking) {
        super();

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
        playButton.addActionListener(onPlay);
        playButton.setPreferredSize(MenuFrame.PREFERED_BUTTON_SIZE);
        menuButtonsContainer.add(playButton);

        var rankingButton = new JButton("View ranking");
        rankingButton.setPreferredSize(MenuFrame.PREFERED_BUTTON_SIZE);
        rankingButton.addActionListener(onSwitchToRanking);
        menuButtonsContainer.add(rankingButton);

        mainMenuContainer.add(menuButtonsContainer);

        add(mainMenuContainer);
    }


}
