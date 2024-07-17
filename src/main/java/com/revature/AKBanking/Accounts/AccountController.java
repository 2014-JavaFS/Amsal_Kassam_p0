package com.revature.AKBanking.Accounts;

import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.sql.SQLException;

public class AccountController implements Controller {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/createAccount", this::createNewAccount);
        app.post("/myAccounts", this::myAccounts);
        app.post("/withdraw", this::withdraw);
        app.post("/deposit", this::deposit);
    }

    private void createNewAccount(Context context) {
        int accountNumber, ownerID, balance = 0;
        try{
            accountNumber = Integer.parseInt(context.queryParam("accountNumber"));
            ownerID = Integer.parseInt(context.queryParam("ownerID"));
        } catch (NumberFormatException e) {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            return;
        }

        try{
            Account newAccount = accountService.create(new Account(accountNumber, ownerID, balance));
            context.json(newAccount);
            context.status(HttpStatus.CREATED);
        } catch(InvalidInputException e){
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            context.result(e.getMessage());
        }
    }

    private void myAccounts(Context context) {
        String ownerID = context.header("userID");
        if(ownerID == null || ownerID.isEmpty()){
            context.status(HttpStatus.UNAUTHORIZED);
            context.result("You are not logged in");
            return;
        }
        context.json(accountService.findByOwnerId(ownerID));
    }

    private void deposit(Context context) {
        int accountNumber, amount;
        Account account;
        try {
            accountNumber = Integer.parseInt(context.queryParam("accountNumber"));
            amount = Integer.parseInt(context.queryParam("amount"));

            account = accountService.findById(String.valueOf(accountNumber));
        } catch (NumberFormatException e) {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            return;
        } catch(DataNotFoundException e){
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        accountService.deposit(account, amount);
    }

    private void withdraw(Context context) {
        int accountNumber, amount;
        Account account;
        try {
            accountNumber = Integer.parseInt(context.queryParam("accountNumber"));
            amount = Integer.parseInt(context.queryParam("amount"));

            account = accountService.findById(String.valueOf(accountNumber));
        } catch (NumberFormatException e) {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            return;
        } catch(DataNotFoundException e){
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        accountService.withdraw(account, amount);
    }
}
