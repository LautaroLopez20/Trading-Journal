
package com.mycompany.tradingjournal.GUI.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.UIManager;

public final class Theme {

    public static final Color BG = Color.decode("#16181D");
    public static final Color PANEL = Color.decode("#22262F");
    public static final Color SIDEBAR = Color.decode("#101218");
    public static final Color BORDER = Color.decode("#2E3340");
    public static final Color ACCENT = Color.decode("#4C8DFF");
    public static final Color ACCENT_HOVER = Color.decode("#66A0FF");
    public static final Color NAV_ACTIVE = Color.decode("#253048");
    public static final Color GREEN = Color.decode("#34D398");
    public static final Color RED = Color.decode("#F26D78");
    public static final Color TEXT = Color.decode("#E8EAED");
    public static final Color TEXT_DIM = Color.decode("#8B909A");

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_H2 = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_VALUE = new Font("Segoe UI", Font.BOLD, 22);

    public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DecimalFormat MONEY_FMT =
            new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(new Locale("es", "AR")));

    private Theme() {
    }

    public static void init() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo instalar FlatLaf: " + ex.getMessage());
        }
        UIManager.put("defaultFont", FONT_NORMAL);
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Panel.background", BG);
        UIManager.put("OptionPane.background", BG);
        UIManager.put("ScrollPane.border", javax.swing.BorderFactory.createEmptyBorder());
    }

    public static String money(double value) {
        if (!isFinite(value)) {
            return "-";
        }
        return "$" + MONEY_FMT.format(value);
    }

    public static String signedMoney(double value) {
        if (!isFinite(value)) {
            return "-";
        }
        if (value > 0) {
            return "+$" + MONEY_FMT.format(Math.abs(value));
        }
        if (value < 0) {
            return "-$" + MONEY_FMT.format(Math.abs(value));
        }
        return "$0,00";
    }

    public static String pct(double value) {
        if (!isFinite(value)) {
            return "-";
        }
        return MONEY_FMT.format(value) + "%";
    }

    public static String num2(double value) {
        if (!isFinite(value)) {
            return "-";
        }
        return MONEY_FMT.format(value);
    }

    public static boolean isFinite(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value);
    }

    public static void style(javax.swing.JComponent component, String style) {
        component.putClientProperty("FlatLaf.style", style);
    }

    public static void primaryButton(javax.swing.JButton button) {
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14f));
        button.setFocusPainted(false);
        style(button, "arc: 12; borderWidth: 0; background: #4C8DFF; foreground: #FFFFFF;"
                + " hoverBackground: #66A0FF; pressedBackground: #3E7AE6; focusedBackground: #4C8DFF;");
    }

    public static void secondaryButton(javax.swing.JButton button) {
        button.setFont(FONT_NORMAL);
        button.setFocusPainted(false);
        style(button, "arc: 12; background: #22262F; foreground: #E8EAED;"
                + " hoverBackground: #2A303C; pressedBackground: #262B36;"
                + " borderColor: #333947; focusedBackground: #2A303C;");
    }

    public static void dangerButton(javax.swing.JButton button) {
        button.setFont(FONT_NORMAL);
        button.setFocusPainted(false);
        style(button, "arc: 12; background: #22262F; foreground: #F26D78;"
                + " hoverBackground: #33232A; pressedBackground: #2E2027;"
                + " borderColor: #7A3B41; focusedBackground: #33232A;");
    }
}
