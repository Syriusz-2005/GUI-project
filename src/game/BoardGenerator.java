package game;

import utils.Grid;

import java.io.*;

public class BoardGenerator {
    public static void generate(Grid<Field> g) {
        g.fill((int x, int y) -> new Field(true));
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
                    var field = new Field(c == '█');
                    field.setIsDoor(c == 'd');
                    field.setHasPowerup(c == 'p');
                    field.setIsGhostSpawn(c == 's');
                    g.set(x, y, field);
                }
                y++;
            }
        } catch(IOException e) {
            System.err.println(e);
            System.err.println("Couldn't load a board grid");
        }
    }
}
