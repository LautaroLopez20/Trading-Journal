
package com.mycompany.tradingjournal.GUI;

import com.mycompany.tradingjournal.GUI.ui.RoundedPanel;
import com.mycompany.tradingjournal.GUI.ui.Theme;
import com.mycompany.tradingjournal.Logic.Controller;
import com.mycompany.tradingjournal.Logic.Operation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RecordPanel extends JPanel {

    private static final String[] COLUMNS = {
        "Num Op", "Fecha", "Activo", "Mercado", "Tendencia", "Tipo",
        "Precio Entrada", "Precio Salida", "Stop Loss", "Take Profit",
        "Riesgo %", "Resultado OP", "Anotaciones"
    };

    private static final int[] COLUMN_WIDTHS = {
        60, 90, 100, 90, 100, 80,
        110, 110, 100, 100, 90, 120, 240
    };

    private static final int RESULT_COLUMN = 11;

    private final MainFrame mainFrame;
    private final Controller controller = new Controller();
    private final DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(model);
    private JLabel subtitleLabel;

    public RecordPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        model.setColumnIdentifiers(COLUMNS);

        setLayout(new BorderLayout(0, 18));
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(28, 28, 24, 28));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
    }

    private Component buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Historial de Operaciones");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.TEXT);

        subtitleLabel = new JLabel(" ");
        subtitleLabel.setFont(Theme.FONT_SMALL);
        subtitleLabel.setForeground(Theme.TEXT_DIM);

        titles.add(title);
        titles.add(Box.createVerticalStrut(2));
        titles.add(subtitleLabel);
        header.add(titles, BorderLayout.WEST);

        JButton btnNew = new JButton("+ Nueva Operacion");
        Theme.primaryButton(btnNew);
        btnNew.addActionListener(evt -> mainFrame.navigate(MainFrame.NEW_OPERATION));

        JButton btnEdit = new JButton("Editar");
        Theme.secondaryButton(btnEdit);
        btnEdit.addActionListener(evt -> editSelected());

        JButton btnDelete = new JButton("Borrar");
        Theme.dangerButton(btnDelete);
        btnDelete.addActionListener(evt -> deleteSelected());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        buttons.add(btnNew);
        buttons.add(btnEdit);
        buttons.add(btnDelete);
        header.add(buttons, BorderLayout.EAST);

        return header;
    }

    private Component buildTable() {
        table.setRowHeight(30);
        table.setFont(Theme.FONT_NORMAL);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setDefaultRenderer(Object.class, new ZebraRenderer());
        for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    editSelected();
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Theme.PANEL);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        RoundedPanel card = new RoundedPanel(16, Theme.PANEL);
        card.setLayout(new BorderLayout());
        card.add(scroll, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(card, BorderLayout.CENTER);
        return center;
    }

    public void refresh() {
        model.setRowCount(0);
        List<Operation> operations = controller.getAll();
        if (operations != null) {
            for (Operation op : operations) {
                model.addRow(new Object[]{
                    op.getId(),
                    op.getDate().format(Theme.DATE_FMT),
                    op.getActive(),
                    op.getMarket(),
                    op.getTrend(),
                    op.getType(),
                    op.getPriceIn(),
                    op.getPriceOut(),
                    op.getStopLoss(),
                    op.getTakeProfit(),
                    op.getPercentajeRisk(),
                    Theme.signedMoney(op.getResultOp()),
                    op.getAnnotations()
                });
            }
        }
        int count = operations != null ? operations.size() : 0;
        subtitleLabel.setText(count + (count == 1 ? " operacion registrada"
                : " operaciones registradas"));
    }

    private Operation selectedOperation() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una operacion de la tabla.",
                    "Error de seleccion", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        int id = (int) model.getValueAt(row, 0);
        return controller.getOperation(id);
    }

    private void editSelected() {
        Operation op = selectedOperation();
        if (op == null) {
            return;
        }
        EditOperationDialog dialog = new EditOperationDialog(this, op);
        dialog.setVisible(true);
        refresh();
    }

    private void deleteSelected() {
        Operation op = selectedOperation();
        if (op == null) {
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Se eliminara la operacion #" + op.getId()
                + " (" + op.getActive() + ").\nDeseas continuar?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        controller.delete(op.getId());
        refresh();
    }

    private class ZebraRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable t, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(
                    t, value, isSelected, false, row, column);

            if (!isSelected) {
                component.setBackground(row % 2 == 0
                        ? new Color(34, 38, 47)
                        : new Color(28, 32, 39));
                component.setForeground(Theme.TEXT);
                if (column == RESULT_COLUMN && value != null) {
                    String text = value.toString();
                    if (text.startsWith("+")) {
                        component.setForeground(Theme.GREEN);
                    } else if (text.startsWith("-")) {
                        component.setForeground(Theme.RED);
                    }
                }
            }
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            return component;
        }
    }
}
