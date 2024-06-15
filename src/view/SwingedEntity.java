package view;

import game.Entity;
import game.PacmanBoard;
import game.TextureController;
import utils.Vec2i;

import javax.swing.*;
import java.awt.*;

public class SwingedEntity extends JPanel implements ComponentUpdatable {
    private final PacmanBoard board;
    private final SwingedBoard boardView;
    private final Entity entity;

    public SwingedEntity(
            PacmanBoard board,
            SwingedBoard boardView,
            Entity entity
    ) {
        this.board = board;
        this.boardView = boardView;
        this.entity = entity;
        setOpaque(false);
    }

    @Override
    public void update(double fieldSize) {
        double size = fieldSize - fieldSize / 3;
        var txt = entity.getTextureController().getCurrTexture(new Vec2i((int) size));
        var txtSize = new Vec2i(txt);

        var pPos = entity.getPos()
                .clone()
                .multiply((float) fieldSize)
                .toInt()
                .subtract(txtSize.clone().divide(2));

        setBounds(pPos.x, pPos.y, txtSize.x, txtSize.y);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        entity.draw(g, (int) boardView.getFieldSize());
    }
}
