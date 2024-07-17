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

    public String toString(){
        return String.format("acc#: %s owner: %s balance: %d", accountNumber, ownerID, balance);
    }
}
