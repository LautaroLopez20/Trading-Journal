package com.mycompany.tradingjournal;

import com.mycompany.tradingjournal.GUI.Main;
import com.mycompany.tradingjournal.Persistence.PersistenceController;

public class TradingJournal {

    public static void main(String[] args) {
        Main mainScreen = new Main();
        mainScreen.setVisible(true);
        mainScreen.setLocationRelativeTo(null);
    }
}
