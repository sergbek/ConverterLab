package com.example.sergbek.converterlab.model;


public class CurrencyModel {
    private String abbr;
    private String fullTitle;
    private String ask;
    private String bid;
    private int changeAsk;
    private int changeBit;

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public int getChangeAsk() {
        return changeAsk;
    }

    public int getChangeBit() {
        return changeBit;
    }

    public void setChangeBit(int changeBit) {
        this.changeBit = changeBit;
    }

    public void setChangeAsk(int changeAsk) {
        this.changeAsk = changeAsk;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    @Override
    public String toString() {
        return "CurrencyModel{" +
                "abbr='" + abbr + '\'' +
                ", fullTitle='" + fullTitle + '\'' +
                ", ask='" + ask + '\'' +
                ", bid='" + bid + '\'' +
                ", changeAsk=" + changeAsk +
                ", changeBit=" + changeBit +
                '}';
    }
}
