package com.revature.AKBanking.Transactions;

import com.revature.AKBanking.Accounts.Account;
import com.revature.AKBanking.Accounts.AccountRepository;
import com.revature.AKBanking.Accounts.AccountService;
import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Serviceable;

import java.util.List;

public class TransactionService implements Serviceable<Transaction> {
    private final TransactionRepository transactionRepository;
    private AccountService accountService;

    public TransactionService(TransactionRepository repo) {
        transactionRepository = repo;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Transaction findById(String id) throws DataNotFoundException {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction create(Transaction newObject) throws InvalidInputException {
        return transactionRepository.create(newObject);
    }

    public boolean postTransaction(Transaction transaction){
        try {
            Account account = accountService.findById(String.valueOf(transaction.getAccountID()));
            int amount = transaction.getAmount();
            int newBalance = transaction.isCredit() ? accountService.deposit(account, amount)
                    : accountService.withdraw(account, amount);
        } catch (DataNotFoundException | InvalidInputException e) {
            return false;
        }
        this.create(transaction);
        return true;
    }

    public List<Transaction> getTransactionsByAccount(int accountNumber){
        return transactionRepository.findByAccountID(accountNumber);
    }
}
