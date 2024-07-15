package com.revature.AKBanking;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.Users.UserController;
import com.revature.AKBanking.Users.UserRepository;
import com.revature.AKBanking.Users.UserService;
import com.revature.AKBanking.util.auth.AuthController;
import com.revature.AKBanking.util.auth.AuthService;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import static com.revature.AKBanking.util.ScannerLooperImpl.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class BankRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Javalin app = Javalin.create(config -> {
            config.jsonMapper(new JavalinJackson());
        });

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(userService);
        userController.registerPaths(app);

        AuthService authService = new AuthService(userService);
        AuthController authController = new AuthController(authService);
        authController.registerPaths(app);

        User loggedInUser = null;

        app.start(8080);
    }
}
