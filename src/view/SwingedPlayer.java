package view;

import game.PacmanBoard;
import game.TextureController;
import utils.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SwingedPlayer extends JPanel implements ComponentUpdatable {
    private final PacmanBoard board;
    private final SwingedBoard boardView;

    private final TextureController textureController = new TextureController(new String[]{
            "player_frame_0.png",
            "player_frame_1.png",
            "player_frame_2.png",
            "player_frame_3.png"
    }, 0.1f, "player_frame_2.png");
    public SwingedPlayer(PacmanBoard board, SwingedBoard boardView) throws IOException {
        this.boardView = boardView;
        this.board = board;
    }

    @Override
    public void update(double fieldSize) {
        var p = board.getPlayer();
        double size = fieldSize - fieldSize / 3;
        var txt = textureController.getCurrTexture(new Vec2i((int) size));
        var txtSize = new Vec2i(txt);

        var pPos = p.getPos()
                .clone()
                .multiply((float) fieldSize)
                .toInt()
                .subtract(txtSize.clone().divide(2));

        setBounds(pPos.x, pPos.y, txtSize.x, txtSize.y);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        board.getPlayer().draw(g, (int) boardView.getFieldSize());
    }
}
