package com.revature.AKBanking.Currency;

public class Currency {
    private String currencyCode;
    private String currencyName;
    private float USDExchangeRate;
    private String currencySymbol;

    public Currency(String currencyCode, String currencyName, float USDExchangeRate, String currencySymbol) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.USDExchangeRate = USDExchangeRate;
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public float getUSDExchangeRate() {
        return USDExchangeRate;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     *
     * @param symbol [s] to be displayed before currency
     * @param amountInCents [...wxyz] total in cents
     * @param prefix if true, s is before the number and after otherwise
     * @return string formatted like [s][...wx{.}yz] if prefix else [..wx{.}yz][s]
     */
    public static String formatAsCurrency(String symbol, int amountInCents, boolean prefix){
        return prefix ? String.format("%s%d.%d", symbol, amountInCents/100, amountInCents % 100) :
                String.format("%d.%d%s", amountInCents, amountInCents % 100, symbol);
    }

    /**
     *
     * @param amountInCents [...wxyz] total in cents
     * @return string formatted like $[...wx{.}yz]. Equivalent to formatAsCurrency("$", amountInCents, true);
     */
    public static String formatAsCurrency(int amountInCents){
        return formatAsCurrency("$", amountInCents, true);
    }
}
