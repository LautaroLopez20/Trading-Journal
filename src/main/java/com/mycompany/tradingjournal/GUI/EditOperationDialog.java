
package com.mycompany.tradingjournal.GUI;

import com.mycompany.tradingjournal.GUI.ui.RoundedPanel;
import com.mycompany.tradingjournal.GUI.ui.Theme;
import com.mycompany.tradingjournal.Logic.Controller;
import com.mycompany.tradingjournal.Logic.Operation;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class EditOperationDialog extends JDialog {

    private final Controller controller = new Controller();
    private final Operation operation;
    private final OperationForm form = new OperationForm();

    public EditOperationDialog(Component owner, Operation operation) {
        super(JOptionPane.getFrameForComponent(owner),
                "Editar Operacion #" + operation.getId(), ModalityType.APPLICATION_MODAL);
        this.operation = operation;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true);

        RoundedPanel content = new RoundedPanel(16, Theme.PANEL, Theme.BORDER);
        content.setLayout(new BorderLayout(0, 16));
        content.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Editar Operacion #" + operation.getId());
        title.setFont(Theme.FONT_H2);
        title.setForeground(Theme.TEXT);

        JLabel subtitle = new JLabel("Modifica los campos y guarda los cambios");
        subtitle.setFont(Theme.FONT_SMALL);
        subtitle.setForeground(Theme.TEXT_DIM);

        titles.add(title);
        titles.add(Box.createVerticalStrut(2));
        titles.add(subtitle);

        form.fill(operation);

        content.add(titles, BorderLayout.NORTH);
        content.add(form, BorderLayout.CENTER);
        content.add(buildButtons(), BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Theme.BG);
        scroll.setPreferredSize(new Dimension(860, 480));
        setContentPane(scroll);

        pack();
        setLocationRelativeTo(owner);
    }

    private Component buildButtons() {
        JButton btnCancel = new JButton("Cancelar");
        Theme.secondaryButton(btnCancel);
        btnCancel.addActionListener(evt -> dispose());

        JButton btnSave = new JButton("Guardar Cambios");
        Theme.primaryButton(btnSave);
        btnSave.addActionListener(evt -> save());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        buttons.add(btnCancel);
        buttons.add(btnSave);
        return buttons;
    }

    private void save() {
        OperationForm.FormData data = form.collect();
        if (data == null) {
            return;
        }
        controller.update(operation, data.date, data.stopLoss, data.takeProfit,
                data.priceOut, data.priceIn, data.percentajeRisk, data.annotation,
                data.active, data.market, data.trend, data.type);

        JOptionPane.showMessageDialog(this, "La operacion se actualizo correctamente.",
                "Modificacion exitosa", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
