package game;

import utils.Vec2i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TextureController implements Runnable {
    private final List<BufferedImage> textures;
    private final BufferedImage neutralTexture;
    private Vec2i prevSize = Vec2i.ZERO.clone();
    private List<BufferedImage> resizedTextures;
    private BufferedImage resizedNeutralTexture;
    private final Thread t;
    private volatile boolean isRunning;
    private final float frameTime;
    private volatile int currentTexture = 0;

    public TextureController(String[] textureNames, float frameTime, String neutralTextureName) throws IOException {
        this.frameTime = frameTime;
        textures = Arrays.stream(textureNames)
                .map((name) -> new File("./src/texture/" + name))
                .map((file) -> {
                    try {
                        return ImageIO.read(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        neutralTexture = ImageIO.read(new File("./src/texture/" + neutralTextureName));
        t = new Thread(this);
        t.start();
    }

    public void setState(boolean isRunning) {
        this.isRunning = isRunning;
    }
    private void resize(Vec2i size) {
        Function<BufferedImage, BufferedImage> resize = (t) -> {
            AffineTransform scalingTransform = new AffineTransform();
            var s = size.clone().toFloat().divide(new Vec2i(t.getWidth(), t.getHeight()).toFloat());
            scalingTransform.scale(s.x, s.y);
            AffineTransformOp operation = new AffineTransformOp(scalingTransform, AffineTransformOp.TYPE_BILINEAR);
            var newImg = new BufferedImage(size.x, size.y, t.getType());
            return operation.filter(t, newImg);
        };

        resizedTextures = textures.stream()
                .map(resize)
                .toList();

        resizedNeutralTexture = resize.apply(neutralTexture);
    }

    public Image getCurrTexture(Vec2i size) {
        if (prevSize == null || !prevSize.equals(size)) {
            resize(size);
            prevSize = size;
        }
        if (isRunning) {
            return resizedTextures.get(currentTexture);
        }
        return resizedNeutralTexture;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep((long) (frameTime * 1000));
                    if (isRunning) {
                        currentTexture = ++i % textures.size();
                    }
            }
        } catch (InterruptedException ignored) {}
    }
}
