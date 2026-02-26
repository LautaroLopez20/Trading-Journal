
package com.mycompany.tradingjournal.Persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("TradingJournalPU");
    
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
