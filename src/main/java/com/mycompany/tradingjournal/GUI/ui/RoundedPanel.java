
package com.mycompany.tradingjournal.GUI.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {

    private int radius;
    private Color cardBackground;
    private Color lineColor;

    public RoundedPanel(int radius, Color background) {
        this(radius, background, null);
    }

    public RoundedPanel(int radius, Color background, Color lineColor) {
        this.radius = radius;
        this.cardBackground = background;
        this.lineColor = lineColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(cardBackground);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        if (lineColor != null) {
            g2.setColor(lineColor);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
        g2.dispose();
        super.paintComponent(g);
    }

    public void setCardColor(Color color) {
        this.cardBackground = color;
        repaint();
    }

    public void setLineColor(Color color) {
        this.lineColor = color;
        repaint();
    }
}
