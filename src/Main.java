import game.PacmanBoard;
import view.MenuFrame;
import view.PacmanBoardWindow;

public class Main {
    private static final Runnable gameLoop = () -> {
        var board = new PacmanBoard(40, 15);
        var window = new PacmanBoardWindow(board, Thread.currentThread());

        try {
            while (!board.isGameOver() && !Thread.currentThread().isInterrupted() ) {
                board.step(2.7f);
                window.display(board);
                    Thread.sleep(7);
            }
        } catch (InterruptedException ignored) {}
        finally {
            System.out.println("Thread ended");
        }
    };
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initializing pacman...");

        var menu = new MenuFrame((e) -> {
            var gameLoopThread = new Thread(gameLoop);
            gameLoopThread.start();
        });
    }
}