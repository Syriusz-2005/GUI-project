package view;

import game.GameScore;
import game.PacmanBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameOverWindow extends JFrame {
    private final JTextField nameInput;
    private final PacmanBoard board;

    public GameOverWindow(PacmanBoard board) {
        super();
        this.board = board;
        setTitle("Save game score");
        setSize(400, 250);
        setLayout(new FlowLayout());

        var parent = new JPanel();

        parent.setLayout(new GridLayout(3, 1));

        var label = new JLabel("Name this save");
        nameInput = new JTextField();

        var button = new JButton();
        button.setSize(200, 50);
        button.setText("Save");
        button.addActionListener(this::clickedSave);

        parent.add(label);
        parent.add(nameInput);
        parent.add(button);

        add(parent);
    }

    private void clickedSave(ActionEvent e) {
        try {
            var name = nameInput.getText();
            System.out.println(name);

            var gameScore = new GameScore(board.getPlayer().getScore(), name, board.getClock().getSeconds());
            var scoreTitle = name.replaceAll("[^a-z0-9A-Z]", "_");
            var stream = new FileOutputStream("./" + scoreTitle + ".score.bin");

            var objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(gameScore);
            objectStream.flush();
            objectStream.close();

            stream.close();
            setVisible(false);
        } catch (IOException err) {
            System.err.println("Couldn't save the game score!");
        }
    }
}
