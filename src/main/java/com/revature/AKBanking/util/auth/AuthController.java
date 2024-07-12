package com.revature.AKBanking.util.auth;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.util.exceptions.DataNotFoundException;

import javax.security.sasl.AuthenticationException;

import static com.revature.AKBanking.util.ScannerLooperImpl.*;

import java.util.Scanner;

public class AuthController {
    private final Scanner scanner;
    private final AuthService authService;

    public AuthController(Scanner scanner, AuthService authService) {
        this.scanner = scanner;
        this.authService = authService;
    }

    public User login(User loggedIn) {
        try {
            if(loggedIn != null){
                throw new RuntimeException("Already logged in");
            }

            System.out.print("Email: ");
            String email = stringLooperExit.getNext(scanner, "Please enter an email or (e)xit to quit\nEmail: ");
            if (email == null) {
                return null;
            }

            System.out.print("Password: ");
            String password = stringLooperExit.getNext(scanner, "Please enter your password: ");
            if (password == null) {
                return null;
            }

            return authService.login(email, password);
        } catch (AuthenticationException | DataNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
