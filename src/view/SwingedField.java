package view;

import game.Field;

import javax.swing.*;
import java.awt.*;

public class SwingedField extends JPanel implements ComponentUpdatable {
    private final Field field;
    private final PointComponent point;

    public SwingedField(Field f) {
        field = f;
        var color = field.isDoor() ? Color.GRAY
                : field.isWall() ? Color.BLUE : Color.BLACK;
        setBackground(color);
        setLayout(new BorderLayout());

        point = new PointComponent();
        add(point);
        update();
    }

    public void update() {
        var s = getSize();
        if (point.isVisible() != field.hasPoint()) {
            point.setLocation(s.width / 2, s.height / 2);
            point.setVisible(field.hasPoint());
        }
    }
}
