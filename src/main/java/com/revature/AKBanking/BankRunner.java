package com.revature.AKBanking;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.Users.UserController;
import com.revature.AKBanking.Users.UserRepository;
import com.revature.AKBanking.Users.UserService;
import com.revature.AKBanking.util.auth.AuthController;
import com.revature.AKBanking.util.auth.AuthService;

import static com.revature.AKBanking.util.ScannerLooperImpl.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class BankRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        UserController userController = new UserController(scanner, userService);

        AuthService authService = new AuthService(userService);
        AuthController authController = new AuthController(scanner, authService);

        User loggedInUser = null;

        Integer choice;
        List<String> options;
        do {

            options = new ArrayList<>();
            boolean currentlyLoggedIn = loggedInUser != null;
            if (!currentlyLoggedIn) {
                System.out.println("Welcome to AKBank. How may I help you?");
                options.add("Login");
            } else {
                System.out.printf("Welcome back to AKBank %s%n", loggedInUser.getName());
                options.add("Logout");
                options.add("Users");
            }
            options.add("Exit");
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, options.get(i));
            }
            choice = integerLooper.getNext(scanner, String.format("Please enter an integer 1-%d", options.size()));

            switch (choice) {
                case 1: //login/logout
                    if(currentlyLoggedIn){
                        loggedInUser = authController.logout(loggedInUser);
                    } else {
                        loggedInUser = authController.login(loggedInUser);
                    }
                    break;
                case 2: //users
                    if(!currentlyLoggedIn){
                        System.out.println("Cannot access User info when logged out");
                        break;
                    }
                    userController.showMenu(loggedInUser);
                    break;

            }

        } while (choice != options.size());

        System.out.println("Thanks for using AKBanking. Have a great day!");
    }
}
