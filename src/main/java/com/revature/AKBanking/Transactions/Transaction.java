package com.revature.AKBanking.Transactions;

public class Transaction {
    private int transactionID;
    private int accountID;
    private int amount;
    private boolean credit; //true if transaction increases the account amount, false otherwise
    private String description;

    public Transaction(int transactionID, int accountID, int amount, boolean credit, String description) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.amount = amount;
        this.credit = credit;
        this.description = description;
    }

    public Transaction(int transactionID, int accountID, int amount, boolean credit) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.amount = amount;
        this.credit = credit;
        this.description = "";
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
