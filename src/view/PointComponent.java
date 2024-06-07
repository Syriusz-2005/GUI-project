package view;

import javax.swing.*;
import java.awt.*;

public class PointComponent extends JPanel {
    public PointComponent() {
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var s = getSize();
        g.fillOval(s.width / 2 - 5, s.height / 2 - 5, 10, 10);
    }
}
