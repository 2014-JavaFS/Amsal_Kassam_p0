package com.revature.AKBanking.Transactions;

import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Serviceable;

import java.util.List;

public class TransactionService implements Serviceable<Transaction> {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository repo) {
        transactionRepository = repo;
    }


    @Override
    public Transaction findById(String id) throws DataNotFoundException {
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public Transaction create(Transaction newObject) throws InvalidInputException {
        return null;
    }
}
