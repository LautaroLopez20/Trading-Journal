
package com.mycompany.tradingjournal.GUI;

import com.mycompany.tradingjournal.GUI.ui.Theme;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    public static final String DASHBOARD = "dashboard";
    public static final String NEW_OPERATION = "newop";
    public static final String RECORD = "record";

    private final CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel;
    private DashboardPanel dashboardPanel;
    private NewOperationPanel newOperationPanel;
    private RecordPanel recordPanel;

    private final java.util.Map<String, JButton> navButtons = new java.util.HashMap<>();

    public MainFrame() {
        setTitle("Trading Journal");
        setSize(1200, 720);
        setMinimumSize(new Dimension(1080, 660));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildSidebar();
        buildContent();
        navigate(DASHBOARD);
    }

    private void buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Theme.SIDEBAR);
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 14, 20, 14));

        JLabel brand = new JLabel("Trading Journal");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 19));
        brand.setForeground(Theme.TEXT);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        brand.setBorder(BorderFactory.createEmptyBorder(0, 10, 2, 0));

        JLabel brandSub = new JLabel("Registro de operaciones");
        brandSub.setFont(Theme.FONT_SMALL);
        brandSub.setForeground(Theme.TEXT_DIM);
        brandSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        brandSub.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        sidebar.add(brand);
        sidebar.add(brandSub);
        sidebar.add(Box.createVerticalStrut(28));

        ButtonGroup group = new ButtonGroup();
        addNavButton(sidebar, group, "Dashboard", DASHBOARD);
        addNavButton(sidebar, group, "Nueva Operacion", NEW_OPERATION);
        addNavButton(sidebar, group, "Historial", RECORD);

        sidebar.add(Box.createVerticalGlue());

        JLabel version = new JLabel("v1.0");
        version.setFont(Theme.FONT_SMALL);
        version.setForeground(Theme.TEXT_DIM);
        version.setAlignmentX(Component.LEFT_ALIGNMENT);
        version.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        sidebar.add(version);

        getContentPane().add(sidebar, BorderLayout.WEST);
    }

    private void addNavButton(JPanel sidebar, ButtonGroup group, String text, String id) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setFont(Theme.FONT_NORMAL);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        applyNavStyle(button, false);
        button.addActionListener(evt -> navigate(id));
        group.add(button);
        navButtons.put(id, button);
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(6));
    }

    private void applyNavStyle(JButton button, boolean active) {
        if (active) {
            button.setFont(Theme.FONT_NORMAL.deriveFont(Font.BOLD));
            Theme.style(button, "arc: 10; background: #253048; foreground: #FFFFFF;"
                    + " hoverBackground: #253048; pressedBackground: #253048; focusedBackground: #253048;");
        } else {
            button.setFont(Theme.FONT_NORMAL);
            Theme.style(button, "arc: 10; background: #101218; foreground: #8B909A;"
                    + " hoverBackground: #191C24; focusedBackground: #191C24;");
        }
    }

    private void buildContent() {
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Theme.BG);
        contentPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(30, 33, 41)));

        dashboardPanel = new DashboardPanel();
        newOperationPanel = new NewOperationPanel(this);
        recordPanel = new RecordPanel(this);

        contentPanel.add(dashboardPanel, DASHBOARD);
        contentPanel.add(newOperationPanel, NEW_OPERATION);
        contentPanel.add(recordPanel, RECORD);

        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

    public void navigate(String id) {
        JButton selected = navButtons.get(id);
        if (selected != null) {
            navButtons.forEach((key, btn) -> applyNavStyle(btn, key.equals(id)));
        }
        switch (id) {
            case DASHBOARD ->
                dashboardPanel.refresh();
            case RECORD ->
                recordPanel.refresh();
            default -> {
            }
        }
        cardLayout.show(contentPanel, id);
    }

    public void refreshAll() {
        SwingUtilities.invokeLater(() -> {
            dashboardPanel.refresh();
            recordPanel.refresh();
        });
    }
}
