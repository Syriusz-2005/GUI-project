package view;

import game.Field;

import javax.swing.*;
import java.awt.*;

public class SwingedField extends JPanel implements ComponentUpdatable {
    private final Field field;
    private final PointComponent point;
    private final PointComponent staticPowerup;

    public SwingedField(Field f) {
        field = f;
        var color = field.isDoor() ? Color.GRAY
                : field.isWall() ? Color.BLUE : Color.BLACK;
        setBackground(color);
        setLayout(new BorderLayout());

        point = new PointComponent(10, Color.LIGHT_GRAY);
        staticPowerup = new PointComponent(20, Color.LIGHT_GRAY);
        add(staticPowerup);
        add(point);
        update(1);
    }

    private void set(PointComponent p, boolean isVisible) {
        p.setVisible(isVisible);
        if (isVisible) {
            removeAll();
            add(p);
        } else {
            remove(p);
        }
    }

    public void update(double fieldSize) {
        if (staticPowerup.isVisible() != field.hasPowerup()) {
            set(staticPowerup, field.hasPowerup());
            repaint();
            return;
        }

        if (point.isVisible() != field.hasPoint()) {
            set(point, field.hasPoint());
            repaint();
        }

    }
}
