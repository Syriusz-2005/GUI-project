package game;

public class GameClock {
    public volatile int seconds;
    private synchronized void addSecond() {
        seconds++;
    }

    private Runnable r = () -> {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
                addSecond();
            }
        } catch (InterruptedException ignored) {}
    };
    private final Thread t1 = new Thread(r);

    public GameClock() {
        t1.start();
    }

    public void interrupt() {
        t1.interrupt();
    }
}
