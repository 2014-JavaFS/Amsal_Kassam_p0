package com.revature.AKBanking.Currency;

public class Currency {
    private String currencyCode;
    private String currencyName;
    private float ratePerUSD;
    private String currencySymbol;

    public Currency(String currencyCode, String currencyName, float ratePerUSD, String currencySymbol) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.ratePerUSD = ratePerUSD;
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public float getRatePerUSD() {
        return ratePerUSD;
    }

    public void setRatePerUSD(float ratePerUSD) {
        this.ratePerUSD = ratePerUSD;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    }
