package view;

import game.Field;
import game.PacmanBoard;
import utils.Vec2i;

public class TerminalView implements View {
    private String getFieldRepresentation(Field f) {
        if (f.isWall()) {
            return "█";
        }
        if (f.hasPoint()) {
            return "●";
        }
        return " ";
    }

    @Override
    public void display(PacmanBoard board) {
        var gridBoard = board.getBoardGrid();
        var grid = gridBoard.getGrid();
        int y = 0;
        for (Field[] row : grid) {
            int x = 0;
            row: for (Field f : row) {
                for (var e : board.getEntities()) {
                    if (e.getGridPos().equals(new Vec2i(x, y))) {
                        System.out.print('*');
                        x++;
                        continue row;
                    }
                }
                System.out.print(getFieldRepresentation(f));
                x++;
            }
            System.out.println();
            y++;
        }
    }
}
