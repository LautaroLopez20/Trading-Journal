package com.mycompany.tradingjournal.Logic;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Operation implements Comparable<Operation>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private LocalDate date;
    private String active;
    private String type;
    private String market;
    private String trend;
    private String annotations;
    private double priceIn;
    private double priceOut;
    private double stopLoss;
    private double takeProfit;
    private double resultOp;
    private double percentajeRisk;

    public Operation(){
    }
    
    public Operation(String active, String type, String market, String trend,
            String annotations, double stopLoss, double takeProfit, double resultOp, double percentajeRisk, double priceIn, double priceOut) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(double priceIn) {
        this.priceIn = priceIn;
    }

    public double getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(double priceOut) {
        this.priceOut = priceOut;
    }

    public int getId() {
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

    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(double takePofit) {
        this.takeProfit = takePofit;
    }

    public double getResultOp() {
        return resultOp;
    }

    public void setResultOp(double resultOp) {
        this.resultOp = resultOp;
    }

    public double getPercentajeRisk() {
        return percentajeRisk;
    }

    public void setPercentajeRisk(double percentajeRisk) {
        this.percentajeRisk = percentajeRisk;
    }
    
    @Override
    public int compareTo(Operation o) {
        return this.getDate().compareTo(o.getDate());
    }
}
