
package com.mycompany.tradingjournal.Logic;

import com.mycompany.tradingjournal.Persistence.PersistenceController;
import java.util.Date;
import java.util.List;

public class Controller {
    private PersistenceController persistanceController = null;
    
    public Controller() {
        persistanceController = new PersistenceController();
    }
    
    public void save(Date date,int stopLoss, int takeProfit, int result, int priceIn,
                    int priceOut, int percentajeRisk, String annotation, String active, String market,
                    String trend, String type) {
        Operation op = new Operation();
        op.setDate(date);
        op.setStopLoss(stopLoss);
        op.setTakeProfit(takeProfit);
        op.setResultOp(result);
        op.setPriceIn(priceIn);
        op.setPriceOut(priceOut);
        op.setPercentajeRisk(percentajeRisk);
        op.setAnnotations(annotation);
        op.setActive(active);
        op.setMarket(market);
        op.setTrend(trend);
        op.setType(type);
        
        persistanceController.save(op);
    }
    
    public List<Operation> getAll() {
        return persistanceController.getAll();
    }
}
