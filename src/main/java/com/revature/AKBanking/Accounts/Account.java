package com.revature.AKBanking.Accounts;

public class Account {
    private int accountNumber;
    private int ownerID;
    private int balance;  //in US cents

    public Account(int accountNumber, int ownerID, int balance) {
        this.accountNumber = accountNumber;
        this.ownerID = ownerID;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int id) {
        this.ownerID = id;
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * @param amount to deposit
     * @return the new balance after deposit
     */
    public Integer deposit(int amount) {
        balance += amount;
        return balance;
    }

    /**
     * @param amount to withdraw
     * @return the new balance if withdrawal is successful, null otherwise
     */
    public Integer withdraw(int amount) {
        if (amount > balance) {
            return null;
        }
        balance -= amount;
        return balance;
    }
}
