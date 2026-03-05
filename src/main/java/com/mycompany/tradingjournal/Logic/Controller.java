
package com.mycompany.tradingjournal.Logic;

import com.mycompany.tradingjournal.Persistence.PersistenceController;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Controller{
    private PersistenceController persistenceController = null;
    
    public Controller() {
        persistenceController = new PersistenceController();
    }
    
    public void save(LocalDate date,double stopLoss, double takeProfit, double priceOut,
                    double priceIn, double percentajeRisk, String annotation, String active, String market,
                    String trend, String type) {
        Operation op = new Operation();
        op.setDate(date);
        op.setStopLoss(stopLoss);
        op.setTakeProfit(takeProfit);
        op.setResultOp(priceOut - priceIn);
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
    
    public void update(Operation op,LocalDate date,double stopLoss,double takeProfit,double priceOut,double priceIn,
                           double percentajeRisk,String annotation,String active,String market,String trend,String type){
        op.setDate(date);
        op.setStopLoss(stopLoss);
        op.setTakeProfit(takeProfit);
        op.setResultOp(priceOut - priceIn);
        op.setPriceIn(priceIn);
        op.setPriceOut(priceOut);
        op.setAnnotations(annotation);
        op.setActive(active);
        op.setMarket(market);
        op.setTrend(trend);
        op.setType(type);
        
        persistenceController.update(op);
    }

    public double getTotalOps() {
        double totalOps = persistenceController.getAll().size();
        return totalOps;
    }
    
    public double getWonTrades() {
        double wonTrades = 0;
        List<Operation> operations = persistenceController.getAll();
        for(Operation op : operations) {
            if(op.getResultOp() > 0) {
                wonTrades++;
            }
        }
        return wonTrades;
    }
    
    public double getLossTrades() {
        double lossTrades = 0;
        List<Operation> operations = persistenceController.getAll();
        for(Operation op : operations) {
            if(op.getResultOp() < 0) {
                lossTrades++;
            }
        }
        return lossTrades;
    }
    
    public double getWinRate() {
        double winRate = (getWonTrades() / getTotalOps()) * 100;
        return winRate;
    }
    
    public double getTotalProfit() {
        double totalProfit = 0;
        List<Operation> operations = persistenceController.getAll();
        for(Operation op : operations) {
            if(op.getResultOp() > 0) {
                totalProfit += op.getResultOp();
            }
        }
        return totalProfit;
    }
    
    public double getTotalLoss() {
        double totalProfit = 0;
        List<Operation> operations = persistenceController.getAll();
        for(Operation op : operations) {
            if(op.getResultOp() < 0) {
                totalProfit += op.getResultOp();
            }
        }
        return totalProfit;
    }
    
    public double getProfitFactor() {
        double profitFactor = (getTotalProfit() / getTotalLoss());
        return profitFactor;
    }
    
    public double getMonthlyResult() {
        double monthlyResult = 0;
        int month = LocalDate.now().getMonthValue();
        List<Operation> operations = persistenceController.getAll();
        Collections.sort(operations);
        for(Operation op : operations) {
            if(op.getDate().getMonthValue() == month) {
                monthlyResult += op.getResultOp();
            }
        }
        return monthlyResult;
    }
    
    public double getWeeklyResult() {
        double weeklyResult = 0;
        List<Operation> operations = persistenceController.getAll();
        if(!operations.isEmpty()) {
            Collections.sort(operations,Collections.reverseOrder());
            LocalDate mostRecentOp = operations.get(0).getDate();
            LocalDate weekStart = null;
            int opDay = mostRecentOp.getDayOfWeek().getValue() - 1;
            
            //The week start on Monday
            if(opDay != 0) {
                weekStart = mostRecentOp.minusDays(opDay);
            } else {
                weekStart = mostRecentOp;
            }
            
            for(Operation op : operations) {
                if((op.getDate().isAfter(weekStart)) || (op.getDate().isEqual(weekStart))) {
                    weeklyResult += op.getResultOp();
                }
            }
        }
        return weeklyResult;
    }
    
    public Operation getBestOp() {
        List<Operation> operations = persistenceController.getAll();
        if(!operations.isEmpty()) {
            Operation bestOp = operations.get(0);
            for(Operation op : operations) {
                if(bestOp.getResultOp() < op.getResultOp()) {
                    bestOp = op;
                }
            }
            return bestOp;
        }
        return null;
    }
    
    public Operation getWorstOp() {
        List<Operation> operations = persistenceController.getAll();
        if(!operations.isEmpty()) {
            Operation worstOp = operations.get(0);
            for(Operation op : operations) {
                if(worstOp.getResultOp() > op.getResultOp()) {
                    worstOp = op;
                }
            }
            return worstOp;
        }
        return null;
    }
}
