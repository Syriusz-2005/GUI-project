package game;

import utils.Grid;
import utils.Vec2i;

import java.io.*;

public class BoardGenerator {
    public static void generate(Grid<Field> g) {
        g.fill((int x, int y) -> new Field(true));
    }
    public static Vec2i getSize(String boardName) throws IOException {
        var file = new File("./src/board/" + boardName);
        var fileReader = new FileReader(file);
        var reader = new BufferedReader(fileReader);
        var size = new Vec2i(0, 0);
        String l;
        while ((l = reader.readLine()) != null) {
            size.y++;
            size.x = l.length();
        }
        reader.close();
        return size;
    }

    public static void loadFromFile(Grid<Field> g, String boardName) throws IOException {
        var file = new File("./src/board/" + boardName);
        var fileReader = new FileReader(file);
        var reader = new BufferedReader(fileReader);
        String l;
        int y = 0;
        while ((l = reader.readLine()) != null) {
            for (int x = 0; x < l.length(); x++) {
                char c = l.charAt(x);
                var field = new Field(c == '█');
                field.setHasPoint(c == '●');
                field.setIsDoor(c == 'd');
                field.setHasPowerup(c == 'p');
                field.setIsGhostSpawn(c == 's');
                field.setPlayerSpawn(c == 'r');
                g.set(x, y, field);
            }
            y++;
        }
        reader.close();
    }
}
