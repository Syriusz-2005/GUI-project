package view;

import game.Field;
import game.PacmanBoard;

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
        for (Field[] row : grid) {
            for (Field f : row) {
                System.out.print(getFieldRepresentation(f));
            }
            System.out.println();
        }
    }
}
