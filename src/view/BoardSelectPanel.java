package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BoardSelectPanel extends JPanel {
    private final List<String> boardNames;
    public BoardSelectPanel(Consumer<String> onMapSelected) {
        super();

        boardNames = Stream.of(Objects.requireNonNull(new File("./src/board").listFiles()))
                .filter((file) -> !file.isDirectory() && file.getName().endsWith(".txt"))
                .map(File::getName)
                .toList();
        var label = new JLabel("Choose the board:");
        add(label);

        for (var name : boardNames) {
            var element = new JButton(name);
            element.addActionListener((e) -> {
                onMapSelected.accept(name);
            });
            add(element);
        }

        setLayout(new GridLayout(boardNames.size() + 1, 1));
    }
}
