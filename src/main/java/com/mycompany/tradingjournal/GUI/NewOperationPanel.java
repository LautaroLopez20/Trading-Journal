
package com.mycompany.tradingjournal.GUI;

import com.mycompany.tradingjournal.GUI.ui.RoundedPanel;
import com.mycompany.tradingjournal.GUI.ui.Theme;
import com.mycompany.tradingjournal.Logic.Controller;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class NewOperationPanel extends JPanel {

    private final Controller controller = new Controller();
    private final MainFrame mainFrame;
    private final OperationForm form = new OperationForm();

    public NewOperationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(0, 18));
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(28, 28, 24, 28));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);
        add(buildButtons(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Nueva Operacion");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT);

        JLabel subtitle = new JLabel("Registra los datos de la operacion");
        subtitle.setFont(Theme.FONT_SMALL);
        subtitle.setForeground(Theme.TEXT_DIM);

        titles.add(title);
        titles.add(Box.createVerticalStrut(2));
        titles.add(subtitle);
        return titles;
    }

    private Component buildCenter() {
        RoundedPanel card = new RoundedPanel(16, Theme.PANEL, Theme.BORDER);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        card.add(form, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(card);
        scroll.getViewport().setBackground(Theme.BG);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private Component buildButtons() {
        JButton btnClear = new JButton("Limpiar");
        Theme.secondaryButton(btnClear);
        btnClear.addActionListener(evt -> form.clear());

        JButton btnSave = new JButton("Guardar Operacion");
        Theme.primaryButton(btnSave);
        btnSave.addActionListener(evt -> save());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        buttons.add(btnClear);
        buttons.add(btnSave);
        return buttons;
    }

    private void save() {
        OperationForm.FormData data = form.collect();
        if (data == null) {
            return;
        }
        controller.save(data.date, data.stopLoss, data.takeProfit, data.priceOut,
                data.priceIn, data.percentajeRisk, data.annotation,
                data.active, data.market, data.trend, data.type);

        JOptionPane.showMessageDialog(this, "La operacion se guardo correctamente.",
                "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE,
                null);
        form.clear();
        mainFrame.navigate(MainFrame.DASHBOARD);
    }
}
