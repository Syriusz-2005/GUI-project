import game.PacmanBoard;
import view.TerminalView;
import view.View;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initializing pacman...");

        View view = new TerminalView();

        var board = new PacmanBoard(40, 15);

        while (true) {
            board.step(40f);
            view.display(board);
            Thread.sleep(40);
        }

    }
}