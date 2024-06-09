package view;

import game.Field;

import javax.swing.*;
import java.awt.*;

public class SwingedField extends JPanel implements ComponentUpdatable {
    private final Field field;
    private final SwingedPoint pointView;
    private final SwingedPoint staticPowerupView;
    private final SwingedPoint dynamicPowerupView;

    public SwingedField(Field f) {
        field = f;
        var color = field.isDoor() ? Color.GRAY
                : field.isWall() ? Color.BLUE : Color.BLACK;
        setBackground(color);
        setLayout(new BorderLayout());

        pointView = new SwingedPoint(10, Color.LIGHT_GRAY);
        staticPowerupView = new SwingedPoint(25, Color.LIGHT_GRAY);
        dynamicPowerupView = new SwingedPoint(25, Color.CYAN);
        reset();
    }

    public void setIsVisible(SwingedPoint component, boolean isVisible) {
        removeAll();
        if (isVisible) {
            add(component);
        }
        component.setVisible(isVisible);
        update(1);
    }

    public void update(double fieldSize) {
        var dynamicPowerup = field.getDynamicPowerUp();
        var isDynamicPowerupVisible = dynamicPowerup != null;
        if (dynamicPowerupView.isVisible() != isDynamicPowerupVisible) {
            if (isDynamicPowerupVisible) {
                dynamicPowerupView.setColor(dynamicPowerup.getColor());
            }
            setIsVisible(dynamicPowerupView, isDynamicPowerupVisible);
        }
        if (dynamicPowerupView.isVisible()) return;

        if (staticPowerupView.isVisible() != field.hasPowerup()) {
            setIsVisible(staticPowerupView, field.hasPowerup());
        }
        if (staticPowerupView.isVisible()) return;

        if (pointView.isVisible() != field.hasPoint()) {
            setIsVisible(pointView, field.hasPoint());
        }
    }

    public void reset() {
        pointView.setVisible(false);
        staticPowerupView.setVisible(false);
        dynamicPowerupView.setVisible(false);
        update(1);
    }
}
