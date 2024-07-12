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

        User loggedIn = null;

        Integer choice;
        List<String> options;
        do {
            System.out.println("Welcome to AKBank. How may I help you?");

            options = new ArrayList<>();
            if (loggedIn == null) {
                options.add("Login");
            } else {
                options.add("Logout");
                options.add("Users");
            }
            options.add("Exit");
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%d. %s%n", i, options.get(i));
            }
            choice = integerLooper.getNext(scanner, String.format("Please enter an integer 1-%d", options.size()));

            switch (choice) {
                case 1: //login/logout
                    break;
                case 2: //users
                    break;
                case 3: //exit
                    System.out.println("Thanks for using AKBanking. Have a great day!");
                    break;
            }

        } while (choice != options.size() - 1);
    }
}
