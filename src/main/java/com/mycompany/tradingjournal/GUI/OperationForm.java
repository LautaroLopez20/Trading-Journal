
package com.mycompany.tradingjournal.GUI;

import com.mycompany.tradingjournal.GUI.ui.Theme;
import com.mycompany.tradingjournal.Logic.Operation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public class OperationForm extends JPanel {

    public static class FormData {
        public LocalDate date;
        public double stopLoss;
        public double takeProfit;
        public double priceOut;
        public double priceIn;
        public double percentajeRisk;
        public String annotation;
        public String active;
        public String market;
        public String trend;
        public String type;
    }

    private final JSpinner spinnerDate = new JSpinner(new SpinnerDateModel());
    private final JComboBox<String> cmbActive = new JComboBox<>(new String[]{"BTCUSDT", "EURUSD"});
    private final JComboBox<String> cmbMarket = new JComboBox<>(new String[]{"CRYPTO", "FOREX"});
    private final JComboBox<String> cmbType = new JComboBox<>(new String[]{"LONG", "SHORT"});
    private final JComboBox<String> cmbTrend = new JComboBox<>(new String[]{"ALCISTA", "BAJISTA"});
    private final JTextField txtPriceIn = new JTextField(12);
    private final JTextField txtPriceOut = new JTextField(12);
    private final JTextField txtStopLoss = new JTextField(12);
    private final JTextField txtTakeProfit = new JTextField(12);
    private final JTextField txtPercentajeRisk = new JTextField(12);
    private final JTextArea txtAreaAnnotation = new JTextArea(4, 20);

    public OperationForm() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        cmbActive.setEditable(true);
        spinnerDate.setEditor(new JSpinner.DateEditor(spinnerDate, "dd/MM/yyyy"));
        txtAreaAnnotation.setLineWrap(true);
        txtAreaAnnotation.setWrapStyleWord(true);

        addRow(0, "Fecha:", spinnerDate, "Activo:", cmbActive);
        addRow(1, "Mercado:", cmbMarket, "Tipo:", cmbType);
        addRow(2, "Tendencia:", cmbTrend, "Riesgo %:", txtPercentajeRisk);
        addRow(3, "Precio Entrada:", txtPriceIn, "Precio Salida:", txtPriceOut);
        addRow(4, "Stop Loss:", txtStopLoss, "Take Profit:", txtTakeProfit);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(14, 4, 6, 8);
        JLabel lblAnnotation = new JLabel("Anotaciones:");
        lblAnnotation.setFont(Theme.FONT_NORMAL);
        lblAnnotation.setForeground(Theme.TEXT_DIM);
        add(lblAnnotation, gbc);

        JScrollPane scroll = new JScrollPane(txtAreaAnnotation);
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(14, 0, 4, 4);
        add(scroll, gbc);
    }

    private void addRow(int row, String label1, JComponent field1, String label2, JComponent field2) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = row;
        gbc.insets = new Insets(6, 4, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.weightx = 0;
        JLabel l1 = new JLabel(label1);
        l1.setFont(Theme.FONT_NORMAL);
        l1.setForeground(Theme.TEXT_DIM);
        add(l1, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        field1.setPreferredSize(new java.awt.Dimension(180, 34));
        add(field1, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(6, 24, 6, 8);
        JLabel l2 = new JLabel(label2);
        l2.setFont(Theme.FONT_NORMAL);
        l2.setForeground(Theme.TEXT_DIM);
        add(l2, gbc);

        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 4);
        field2.setPreferredSize(new java.awt.Dimension(180, 34));
        add(field2, gbc);
    }

    public FormData collect() {
        LocalDate date = ((Date) spinnerDate.getValue()).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        Double priceIn = parsePositive(txtPriceIn.getText(), "Precio Entrada");
        Double priceOut = parsePositive(txtPriceOut.getText(), "Precio Salida");
        Double stopLoss = parsePositive(txtStopLoss.getText(), "Stop Loss");
        Double takeProfit = parsePositive(txtTakeProfit.getText(), "Take Profit");
        Double risk = parseNumber(txtPercentajeRisk.getText(), "Riesgo %");

        if (priceIn == null || priceOut == null || stopLoss == null
                || takeProfit == null || risk == null) {
            return null;
        }

        FormData data = new FormData();
        data.date = date;
        data.priceIn = priceIn;
        data.priceOut = priceOut;
        data.stopLoss = stopLoss;
        data.takeProfit = takeProfit;
        data.percentajeRisk = risk;
        data.annotation = txtAreaAnnotation.getText().trim();
        data.active = String.valueOf(cmbActive.getSelectedItem()).trim().toUpperCase();
        data.market = (String) cmbMarket.getSelectedItem();
        data.trend = (String) cmbTrend.getSelectedItem();
        data.type = (String) cmbType.getSelectedItem();
        return data;
    }

    private Double parsePositive(String text, String fieldName) {
        Double value = parseNumber(text, fieldName);
        if (value != null && value <= 0) {
            showError(fieldName + " debe ser mayor que 0.");
            return null;
        }
        return value;
    }

    private Double parseNumber(String text, String fieldName) {
        try {
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException ex) {
            showError("El campo \"" + fieldName + "\" debe ser un numero valido.");
            return null;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Dato invalido",
                JOptionPane.ERROR_MESSAGE);
    }

    public void fill(Operation op) {
        spinnerDate.setValue(Date.from(op.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        selectOrAdd(cmbActive, op.getActive());
        selectOrAdd(cmbMarket, op.getMarket());
        selectOrAdd(cmbType, op.getType());
        selectOrAdd(cmbTrend, op.getTrend());
        txtPriceIn.setText(String.valueOf(op.getPriceIn()));
        txtPriceOut.setText(String.valueOf(op.getPriceOut()));
        txtStopLoss.setText(String.valueOf(op.getStopLoss()));
        txtTakeProfit.setText(String.valueOf(op.getTakeProfit()));
        txtPercentajeRisk.setText(String.valueOf(op.getPercentajeRisk()));
        txtAreaAnnotation.setText(op.getAnnotations());
    }

    private void selectOrAdd(JComboBox<String> combo, String value) {
        boolean found = false;
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equalsIgnoreCase(value)) {
                combo.setSelectedIndex(i);
                found = true;
                break;
            }
        }
        if (!found) {
            combo.setSelectedItem(value);
        }
    }

    public void clear() {
        spinnerDate.setValue(new Date());
        txtPriceIn.setText("");
        txtPriceOut.setText("");
        txtStopLoss.setText("");
        txtTakeProfit.setText("");
        txtPercentajeRisk.setText("");
        txtAreaAnnotation.setText("");
        cmbActive.setSelectedIndex(0);
        cmbMarket.setSelectedIndex(0);
        cmbType.setSelectedIndex(0);
        cmbTrend.setSelectedIndex(0);
    }
}
