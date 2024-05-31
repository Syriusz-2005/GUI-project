import game.PacmanBoard;
import view.PacmanBoardWindow;
import view.TitleScreen;

public class Main {
    private static TitleScreen titleScreen = new TitleScreen();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initializing pacman...");

        var board = new PacmanBoard(40, 15);
        var window = new PacmanBoardWindow(board);

        while (true) {
            board.step(2.7f);
            window.display(board);
            Thread.sleep(7);
        }


    }
}