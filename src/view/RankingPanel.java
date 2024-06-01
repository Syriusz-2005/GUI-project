package view;

import game.GameScore;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class RankingPanel extends JPanel {
    public RankingPanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        var title = new JLabel("Ranking:");
        add(title);
        var scores = listSavedScores();
        System.out.println(scores);

        var scoreboardPanel = new JPanel();
        var scoreboardLayout = new GridLayout(scores.size(), 1);
        scoreboardLayout.setVgap(10);
        scoreboardPanel.setLayout(scoreboardLayout);
        var scoreboard = new JScrollPane(scoreboardPanel);
        scoreboard.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scoreboard.setPreferredSize(new Dimension(250, 300));

        for (var score : scores) {
            var panel = new JPanel();
            var name = new JLabel("Name: " + score.name);
            panel.add(name);
            var points = new JLabel("Score: " + score.totalScore);
            panel.add(points);
            scoreboardPanel.add(panel);
        }
        add(scoreboard);
    }

    private List<GameScore> listSavedScores() {
        return Stream.of(Objects.requireNonNull(new File("./").listFiles()))
            .filter((file) -> !file.isDirectory() && file.getName().endsWith(".score.bin"))
            .map((file) -> {
                try {
                    var inputStream = new FileInputStream(file.getName());
                    var objectInputStream = new ObjectInputStream(inputStream);
                    var score = (GameScore) objectInputStream.readObject();
                    objectInputStream.close();
                    return score;
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();
    }
}
