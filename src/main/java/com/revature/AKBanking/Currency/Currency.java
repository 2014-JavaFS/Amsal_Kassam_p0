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

    /**
     * @param symbol        [s] to be displayed before currency
     * @param amountInCents [...wxyz] total in cents
     * @param prefix        if true, s is before the number and after otherwise
     * @return string formatted like [s][...wx{.}yz] if prefix else [..wx{.}yz][s]
     */
    public static String formatAsCurrency(String symbol, int amountInCents, boolean prefix) {
        return prefix ? String.format("%s%d.%d", symbol, amountInCents / 100, amountInCents % 100) :
                String.format("%d.%d%s", amountInCents, amountInCents % 100, symbol);
    }

    /**
     * @param amountInCents [...wxyz] total in cents
     * @return string formatted like $[...wx{.}yz]. Equivalent to formatAsCurrency("$", amountInCents, true);
     */
    public static String formatAsCurrency(int amountInCents) {
        return formatAsCurrency("$", amountInCents, true);
    }
}
