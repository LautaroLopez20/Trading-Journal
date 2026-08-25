
package com.mycompany.tradingjournal.GUI;

import com.mycompany.tradingjournal.GUI.ui.RoundedPanel;
import com.mycompany.tradingjournal.GUI.ui.Theme;
import com.mycompany.tradingjournal.Logic.Controller;
import com.mycompany.tradingjournal.Logic.Operation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DashboardPanel extends JPanel {

    private final Controller controller = new Controller();

    private static final String[] STAT_TITLES = {
        "Operaciones Totales", "Ganadas", "Perdidas",
        "Win Rate", "Ganancia Total", "Perdida Total",
        "Profit Factor", "Resultado Mensual", "Resultado Semanal"
    };

    private final JLabel[] statValues = new JLabel[STAT_TITLES.length];

    private JLabel bestValueLabel;
    private JLabel bestSubLabel;
    private JLabel worstValueLabel;
    private JLabel worstSubLabel;

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(28, 28, 24, 28));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Dashboard");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT);

        JLabel subtitle = new JLabel("Resumen general de tu rendimiento");
        subtitle.setFont(Theme.FONT_SMALL);
        subtitle.setForeground(Theme.TEXT_DIM);

        titles.add(title);
        titles.add(Box.createVerticalStrut(2));
        titles.add(subtitle);

        header.add(titles, BorderLayout.WEST);
        return header;
    }

    private JScrollPane buildCenter() {
        JPanel center = new JPanel(new BorderLayout(0, 20));
        center.setOpaque(false);

        JPanel statsGrid = new JPanel(new java.awt.GridLayout(3, 3, 16, 16));
        statsGrid.setOpaque(false);
        for (int i = 0; i < STAT_TITLES.length; i++) {
            statValues[i] = new JLabel("-", JLabel.LEFT);
            statValues[i].setFont(Theme.FONT_VALUE);
            statValues[i].setForeground(Theme.TEXT);
            statsGrid.add(buildStatCard(STAT_TITLES[i], statValues[i]));
        }

        JPanel summaryRow = new JPanel(new java.awt.GridLayout(1, 2, 16, 0));
        summaryRow.setOpaque(false);
        bestValueLabel = new JLabel("-");
        bestSubLabel = new JLabel("Sin datos");
        worstValueLabel = new JLabel("-");
        worstSubLabel = new JLabel("Sin datos");
        summaryRow.add(buildSummaryCard("Mejor Operacion", Theme.GREEN, bestValueLabel, bestSubLabel));
        summaryRow.add(buildSummaryCard("Peor Operacion", Theme.RED, worstValueLabel, worstSubLabel));

        center.add(statsGrid, BorderLayout.CENTER);
        center.add(summaryRow, BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(center);
        scroll.getViewport().setBackground(Theme.BG);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private RoundedPanel buildStatCard(String title, JLabel value) {
        RoundedPanel card = new RoundedPanel(16, Theme.PANEL, Theme.BORDER);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(Theme.TEXT_DIM);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        return card;
    }

    private RoundedPanel buildSummaryCard(String title, Color accentColor, JLabel value, JLabel sub) {
        RoundedPanel card = new RoundedPanel(16, Theme.PANEL, Theme.BORDER);
        card.setLayout(new BorderLayout(0, 6));
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        card.setPreferredSize(new Dimension(10, 120));

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        titleLabel.setForeground(accentColor);

        value.setFont(Theme.FONT_VALUE);
        value.setForeground(Theme.TEXT);
        sub.setFont(Theme.FONT_SMALL);
        sub.setForeground(Theme.TEXT_DIM);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(value);
        info.add(Box.createVerticalStrut(4));
        info.add(sub);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        return card;
    }

    public void refresh() {
        int totalOps = (int) controller.getTotalOps();

        statValues[0].setText(String.valueOf(totalOps));
        statValues[1].setText(String.valueOf((int) controller.getWonTrades()));
        statValues[2].setText(String.valueOf((int) controller.getLossTrades()));

        double winRate = totalOps > 0 ? controller.getWinRate() : Double.NaN;
        statValues[3].setText(Theme.pct(winRate));

        statValues[4].setText(Theme.signedMoney(controller.getTotalProfit()));
        statValues[5].setText(Theme.signedMoney(controller.getTotalLoss()));
        statValues[6].setText(Theme.num2(controller.getProfitFactor()));

        double monthly = controller.getMonthlyResult();
        double weekly = controller.getWeeklyResult();
        statValues[7].setText(Theme.signedMoney(monthly));
        statValues[8].setText(Theme.signedMoney(weekly));
        statValues[7].setForeground(resultColor(monthly));
        statValues[8].setForeground(resultColor(weekly));

        fillSummary(controller.getBestOp(), bestValueLabel, bestSubLabel);
        fillSummary(controller.getWorstOp(), worstValueLabel, worstSubLabel);
    }

    private Color resultColor(double value) {
        if (!Theme.isFinite(value)) {
            return Theme.TEXT;
        }
        if (value > 0) {
            return Theme.GREEN;
        }
        if (value < 0) {
            return Theme.RED;
        }
        return Theme.TEXT;
    }

    private void fillSummary(Operation op, JLabel value, JLabel sub) {
        if (op == null) {
            value.setText("-");
            value.setForeground(Theme.TEXT_DIM);
            sub.setText("Sin datos");
            return;
        }
        value.setText(Theme.signedMoney(op.getResultOp()));
        value.setForeground(resultColor(op.getResultOp()));
        sub.setText(op.getActive() + "  ·  " + op.getType() + "  ·  "
                + op.getDate().format(Theme.DATE_FMT));
    }
}
