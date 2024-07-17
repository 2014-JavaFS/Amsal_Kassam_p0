package com.revature.AKBanking.Accounts;

import com.revature.AKBanking.util.interfaces.Controller;
import io.javalin.Javalin;

public class AccountController implements Controller {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void registerPaths(Javalin app) {

    }
}
