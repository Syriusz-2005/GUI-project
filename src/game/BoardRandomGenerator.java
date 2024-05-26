package game;

import utils.Grid;

import java.io.*;
import java.util.logging.FileHandler;

public class BoardRandomGenerator {
    public static void generate(Grid<Field> g) {
        g.fill((int x, int y) -> {
           return new Field(true);
        });
    }

    public static void loadFromFile(Grid<Field> g) {
        var file = new File("./src/board/testBoard.txt");
        try {
            var fileReader = new FileReader(file);
            var reader = new BufferedReader(fileReader);
            String l;
            int y = 0;
            while ((l = reader.readLine()) != null) {
                for (int x = 0; x < l.length(); x++) {
                    char c = l.charAt(x);
                    g.set(x, y, new Field(c == '█'));
                }
                y++;
            }
        } catch(IOException e) {
            System.err.println(e);
            System.err.println("Couldn't load a board grid");
        }
    }
}
