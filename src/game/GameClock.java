package game;

import utils.TimeCounting;

import java.util.ArrayList;
import java.util.function.Consumer;

public class GameClock implements TimeCounting {
    private final ArrayList<Consumer<Integer>> listeners = new ArrayList<>();
    private volatile int seconds;
    private synchronized void addSecond() {
        seconds++;
        for (var listener : listeners) {
            listener.accept(seconds);
        }
    }

    private final Runnable r = () -> {
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

    public int getSeconds() {
        return seconds;
    }

    public void on(Consumer<Integer> callback) {
        listeners.add(callback);
    }
}
