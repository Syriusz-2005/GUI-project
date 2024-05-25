import game.PacmanBoard;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing pacman...");

        var board = new PacmanBoard(30, 30);
        board.step(0.01f);
    }
}