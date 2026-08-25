
package com.mycompany.tradingjournal;

import com.mycompany.tradingjournal.GUI.MainFrame;
import com.mycompany.tradingjournal.GUI.ui.Theme;
import javax.swing.SwingUtilities;

public class TradingJournal {

    public static void main(String[] args) {
        Theme.init();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
