package view;

import game.Field;

import javax.swing.*;
import java.awt.*;

public class SwingedField extends JPanel {
    private final Field field;
    public SwingedField(Field f) {
        field = f;
        var color = field.isDoor() ? Color.GRAY
                : field.isWall() ? Color.BLUE : Color.BLACK;
        setBackground(color);
    }

}
