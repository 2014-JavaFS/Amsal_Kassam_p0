package com.revature.AKBanking.Accounts;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Controller;
import static com.revature.AKBanking.util.ValidatorImpl.*;

import com.revature.AKBanking.util.interfaces.Validator;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;


public class AccountController implements Controller {
    private AccountService accountService;

    Validator<Context> isEmployeeOrAccountOwner = (Context context) -> {
        return (context.header("userID") != null && context.header("userType") != null)  //null checking
                && (context.header("userType").equals(User.userType.EMPLOYEE.name()) ||  //either user is an employee
                (accountService.findById(context.queryParam("accountNumber")).getOwnerID() == Integer.parseInt(context.header("userID"))));
        //or the owner of the account
    };


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/createAccount", this::createNewAccount);
        app.get("/myAccounts", this::myAccounts);
        app.get("/allAccounts", this::allAccounts);
        app.get("/account", this::getAccountByID);
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
        List<Account> accounts = accountService.findByOwnerId(ownerID);
        if(accounts == null || accounts.isEmpty()){
            context.status(HttpStatus.NOT_FOUND);
            context.result("No accounts for currently logged in user");
            return;
        }

        context.status(HttpStatus.OK);
        context.json(accounts.stream().map(Account::toString).toList());
    }

    private void getAccountByID(Context context) {
        if(!isEmployeeOrAccountOwner.validate(context)){
            context.status(HttpStatus.UNAUTHORIZED);
            context.result("You are not authorized to view this page");
            return;
        }

        Account account;
        try{
            account = accountService.findById(context.queryParam("accountNumber"));
        } catch (DataNotFoundException e) {
            context.status(HttpStatus.NOT_FOUND);
            context.result("Account not found");
            return;
        }
        context.status(HttpStatus.OK);
        context.json(account);
    }

    private void allAccounts(Context context) {
        if(!isEmployee.validate(context)){
            context.status(HttpStatus.UNAUTHORIZED);
            context.result("You are not authorized to view this page");
            return;
        }
        context.json(accountService.findAll());
    }

//DEPRECATED, use transactionController.postTransaction instead
/*
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

        if(!isEmployeeOrAccountOwner.validate(context)){
            context.status(HttpStatus.UNAUTHORIZED);
            context.result("You are not logged in as the owner of this account or an employee");
            return;
        }

        try {
            int newBalance = accountService.deposit(account, amount);
            context.result(String.format("Account: %s%nNew Balance: %d", account.getAccountNumber(), newBalance));
            context.status(HttpStatus.OK);
        } catch (InvalidInputException e) {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            context.result(e.getMessage());
        }
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
        } catch(DataNotFoundException e) {
            context.status(HttpStatus.NOT_FOUND);
            return;
        }

        if(!isEmployeeOrAccountOwner.validate(context)){
            context.status(HttpStatus.UNAUTHORIZED);
            context.result("You are not logged in as the owner of this account or an employee");
            return;
        }

        try {
            int newBalance = accountService.withdraw(account, amount);
            context.result(String.format("Account: %s%nNew Balance: %d", account.getAccountNumber(), newBalance));
            context.status(HttpStatus.OK);
        } catch (InvalidInputException e) {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            context.result(e.getMessage());
        }
    }*/
}
