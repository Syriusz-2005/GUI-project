package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TextureController implements Runnable {
    private final Image[] textures;
    private Thread t;
    private volatile boolean isRunning;
    private final float frameTime;
    private volatile int currentTexture = 0;

    public TextureController(String[] textureNames, float frameTime) throws IOException {
        this.frameTime = frameTime;
        textures = (Image[]) Arrays.stream(textureNames)
                .map((name) -> new File("./src/texture/" + name))
                .toArray();
        t = new Thread(this);
        t.start();
    }

    public void setState(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public Image getCurrTexture() {
        return textures[currentTexture];
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep((long) (frameTime * 1000));
                    if (isRunning) {
                        currentTexture = ++i % textures.length;
                    }
            }
        } catch (InterruptedException ignored) {}
    }
}
