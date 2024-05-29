package game;

import utils.Vec2i;

import java.awt.*;

public abstract class Ghost extends Entity {
    private final Color color;
    public Ghost(PacmanBoard parent, Color color) {
        super(parent);
        this.color = color;
    }

    protected Vec2i getRandDir() {
        return Math.random() > .5
                ? Math.random() > .5 ? new Vec2i(1, 0) : new Vec2i(-1, 0)
                : Math.random() > .5 ? new Vec2i(0, 1) : new Vec2i(0, -1) ;
    }

    @Override
    public void draw(Graphics g, int fieldSize) {
        var screenPos = pos.clone().multiply(fieldSize).toInt().subtract(fieldSize / 3);
        g.setColor(color);
        g.fillArc(screenPos.x, screenPos.y, fieldSize - fieldSize / 3, fieldSize - fieldSize / 3, 0, 350);
    }
}
