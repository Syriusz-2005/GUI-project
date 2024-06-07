package view;

import javax.swing.*;
import java.awt.*;

public class PointComponent extends JPanel {
    private final int size;
    private final Color color;
    private boolean isVisible;
    public PointComponent(int size, Color color) {
        this.size = size;
        this.color = color;
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var s = getSize();
        g.setColor(color);
        g.fillOval((s.width - size) / 2, (s.height - size) / 2, size, size);
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
