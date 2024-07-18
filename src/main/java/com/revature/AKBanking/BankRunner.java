package com.revature.AKBanking;

import com.revature.AKBanking.Accounts.AccountController;
import com.revature.AKBanking.Accounts.AccountRepository;
import com.revature.AKBanking.Accounts.AccountService;
import com.revature.AKBanking.Transactions.TransactionController;
import com.revature.AKBanking.Transactions.TransactionRepository;
import com.revature.AKBanking.Transactions.TransactionService;
import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.Users.UserController;
import com.revature.AKBanking.Users.UserRepository;
import com.revature.AKBanking.Users.UserService;
import com.revature.AKBanking.util.auth.AuthController;
import com.revature.AKBanking.util.auth.AuthService;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

public class BankRunner {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson());
        });

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        userController.registerPaths(app);

        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService = new AccountService(accountRepository);
        AccountController accountController = new AccountController(accountService);
        accountController.registerPaths(app);

        AuthService authService = new AuthService(userService);
        AuthController authController = new AuthController(authService);
        authController.registerPaths(app);

        TransactionRepository transactionRepository = new TransactionRepository();
        TransactionService transactionService = new TransactionService(transactionRepository);
        transactionService.setAccountService(accountService);
        TransactionController transactionController = new TransactionController(transactionService);
        transactionController.registerPaths(app);

        app.start(8080);
    }
}
