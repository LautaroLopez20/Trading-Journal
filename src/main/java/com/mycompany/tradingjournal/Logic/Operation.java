package com.mycompany.tradingjournal.Logic;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    private String active;
    private String type;
    private String market;
    private String trend;
    private String annotations;
    private int priceIn;
    private int priceOut;
    private int stopLoss;
    private int takeProfit;
    private int resultOp;
    private int percentajeRisk;

    public Operation(){
    }
    
    public Operation(String active, String type, String market, String trend,
            String annotations, int stopLoss, int takeProfit, int resultOp, int percentajeRisk, int priceIn, int priceOut) {
        this.active = active;
        this.type = type;
        this.market = market;
        this.trend = trend;
        this.annotations = annotations;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.resultOp = resultOp;
        this.percentajeRisk = percentajeRisk;
        this.priceIn = priceIn;
        this.priceOut = priceOut;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(int priceIn) {
        this.priceIn = priceIn;
    }

    public int getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(int priceOut) {
        this.priceOut = priceOut;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(int stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(int takePofit) {
        this.takeProfit = takePofit;
    }

    public double getResultOp() {
        return resultOp;
    }

    public void setResultOp(int resultOp) {
        this.resultOp = resultOp;
    }

    public int getPercentajeRisk() {
        return percentajeRisk;
    }

    public void setPercentajeRisk(int percentajeRisk) {
        this.percentajeRisk = percentajeRisk;
    }

}
