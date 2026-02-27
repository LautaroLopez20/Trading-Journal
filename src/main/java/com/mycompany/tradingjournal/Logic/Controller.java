
package com.mycompany.tradingjournal.Logic;

import com.mycompany.tradingjournal.Persistence.PersistenceController;
import java.util.Date;
import java.util.List;

public class Controller {
    private PersistenceController persistenceController = null;
    
    public Controller() {
        persistenceController = new PersistenceController();
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
        
        persistenceController.save(op);
    }
    
    public List<Operation> getAll() {
        return persistenceController.getAll();
    }
    
    public void delete(int id) {
        persistenceController.delete(id);
    }
    
    public Operation getOperation(int numOp) {
        return persistenceController.getOperation(numOp);
    }
    
    public void update(Operation op,Date date,int stopLoss,int takeProfit,int result,int priceOut,int priceIn,
                           int percentajeRisk,String annotation,String active,String market,String trend,String type){
        op.setDate(date);
        op.setStopLoss(stopLoss);
        op.setTakeProfit(takeProfit);
        op.setResultOp(result);
        op.setPriceIn(priceIn);
        op.setPriceOut(priceOut);
        op.setAnnotations(annotation);
        op.setActive(active);
        op.setMarket(market);
        op.setTrend(trend);
        op.setType(type);
        
        persistenceController.update(op);
    }
    
    public int getActualCapital() {
        int actualCapital = 0;
        List<Operation> operations = persistenceController.getAll();
        for(Operation op : operations) {
            actualCapital += op.getResultOp();
        }
        return actualCapital;
    }
    
    public Operation getBestOp() {
        List<Operation> operations = persistenceController.getAll();
        Operation bestOp = operations.get(0);
        for(Operation op : operations) {
            if(bestOp.getResultOp() < op.getResultOp()) {
                bestOp = op;
            }
        }
        return bestOp;
    }
    
    public Operation getWorstOp() {
        List<Operation> operations = persistenceController.getAll();
        Operation worstOp = operations.get(0);
        for(Operation op : operations) {
            if(worstOp.getResultOp() > op.getResultOp()) {
                worstOp = op;
            }
        }
        return worstOp;
    }
}
