package com.revature.AKBanking.Accounts;

import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Crudable;
import com.revature.AKBanking.util.interfaces.Serviceable;
import com.revature.AKBanking.util.interfaces.Validator;

import java.util.List;

public class AccountService implements Crudable<Account> {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository repo) {
        this.accountRepository = repo;
    }

    @Override
    public Account findById(String id) throws DataNotFoundException {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account create(Account newObject) throws InvalidInputException {
        validateAccount(newObject);
        return accountRepository.create(newObject);
    }

    @Override
    public boolean update(Account updatedModel) {
        validateAccount(updatedModel);
        return accountRepository.update(updatedModel);
    }

    @Override
    public boolean delete(Account accountToDelete){
        return accountRepository.delete(accountToDelete);
    }

    public List<Account> findByOwnerId(String ownerId) {
        return accountRepository.findByOwnerId(ownerId);
    }

    public void validateAccount(Account newObject) throws InvalidInputException {
        if (newObject == null) {
            throw new InvalidInputException("Account is null");
        }
        if (newObject.getBalance() < 0) {
            throw new InvalidInputException("Negative balance");
        }

    }

    public int deposit(Account account, int amount) throws InvalidInputException {
        validateAccount(account);
        if(amount <= 0){
            throw new InvalidInputException("Amount must be greater than 0");
        }
        account.setBalance(account.getBalance() + amount);
        this.update(account);
        return account.getBalance();
    }

    public int withdraw(Account account, int amount) throws InvalidInputException {
        validateAccount(account);
        if(amount <= 0){
            throw new InvalidInputException("Amount must be greater than 0");
        }
        if(account.getBalance() < amount){
            throw new InvalidInputException("Withdrawal amount is greater than balance");
        }

        account.setBalance(account.getBalance() - amount);
        this.update(account);
        return account.getBalance();
    }
}
