package com.revature.AKBanking.Transactions;

import com.revature.AKBanking.Accounts.Account;
import com.revature.AKBanking.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class TransactionController implements Controller{
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/postTransaction", this::postTransaction);
        app.get("/history", this::transactionHistory);
    }

    private void postTransaction(Context context) {
        int id, accountNumber, amount;
        boolean credit;
        String description;

        try{
            id = Integer.parseInt(context.formParam("id"));
            accountNumber = Integer.parseInt(context.formParam("accountNumber"));
            amount = Integer.parseInt(context.formParam("amount"));
            credit = Boolean.parseBoolean(context.formParam("credit"));
        } catch (NullPointerException | NumberFormatException e){
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            context.result("Cannot process Data");
            return;
        }

        description = context.formParam("description");
        description = description == null ? "" : description;

        boolean success = transactionService.postTransaction(new Transaction(id, accountNumber, amount, credit, description));
        if(success){
            context.status(HttpStatus.OK);
            context.result("Transaction successfully posted");
        } else {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            context.result("Transaction could not be posted");
        }
    }

    private void transactionHistory(Context context) {
        int accountNumber;
        try {
            accountNumber = Integer.parseInt(context.queryParam("accountNumber"));
        } catch (NumberFormatException | NullPointerException e) {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            context.result("Cannot process Data");
            return;
        }

        context.json(transactionService.getTransactionsByAccount(accountNumber));
        context.status(HttpStatus.OK);
    }
}
