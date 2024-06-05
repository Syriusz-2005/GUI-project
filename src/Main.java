import game.PacmanBoard;
import view.MenuFrame;
import view.PacmanBoardWindow;

import java.io.IOException;

public class Main {
    private static String boardName;
    private static final Runnable gameLoop = () -> {

        try {
            var board = new PacmanBoard(boardName);
            var window = new PacmanBoardWindow(board, Thread.currentThread());
            while (!board.isGameOver() && !Thread.currentThread().isInterrupted() ) {
                board.step(2.7f);
                window.display(board);
                Thread.sleep(7);
            }
        } catch(IOException err) {
            System.err.println("IO exception, couldn't load the game " + boardName);
            System.err.println(err);
            err.printStackTrace();
        } catch (RuntimeException err) {
            System.err.println("Pacman crashed: ");
            System.err.println(err);
            err.printStackTrace();
            System.exit(1);
        } catch (InterruptedException ignored) {} finally {
            System.out.println("Thread ended");
        }
    };
    public static void main(String[] args) {
        System.out.println("Initializing pacman...");

        new MenuFrame((mapName) -> {
            boardName = mapName;
            var gameLoopThread = new Thread(gameLoop);
            gameLoopThread.start();
        });
    }
}