import game.PacmanBoard;
import view.TerminalView;
import view.View;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing pacman...");

        View view = new TerminalView();

        var board = new PacmanBoard(40, 15);
        board.step(0.01f);
        view.display(board);
    }
}