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
}
