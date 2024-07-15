package com.revature.AKBanking.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.AKBanking.util.interfaces.Validator;
import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;

import static com.revature.AKBanking.util.ScannerLooperImpl.*;

public class UserController {
    private Scanner scanner;
    private final UserService userService;

    public UserController(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    public void showMenu(User user) {
        List<String> options = new ArrayList<>();

        if (user.getType() == User.userType.CUSTOMER) {
            showCustomerMenu(user);
        }
        if (user.getType() == User.userType.EMPLOYEE) {
            showEmployeeMenu(user);
        }
    }

    public void showCustomerMenu(User user) {
        List<String> options = new ArrayList<>();

        options.add("View customer details");
        options.add("Exit");
        for(int i = 0; i < options.size(); i++){
            System.out.printf("%d. %s%n", i + 1, options.get(i));
        }
        Integer choice = integerLooper.getNext(scanner, String.format("Please enter an integer 1-%d", options.size()));
    }

    public void showEmployeeMenu(User user) {
        List<String> options = new ArrayList<>();

        options.add("View employee details");
        options.add("View all users");
        options.add("Create new user");
        options.add("Update existing user");
        options.add("Delete existing user");
        options.add("Exit");
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, options.get(i));
        }
        Integer choice = integerLooper.getNext(scanner, String.format("Please enter an integer 1-%d", options.size()));

        switch (choice) {
            case 1: //view current employee
                System.out.println(user);
                break;
            case 2:
                this.printUsers();
                break;
            case 3:
                this.createNewUserByID();
                break;
            case 4:
                this.updateUser();
                break;
            case 5:
                this.deleteUser();
                break;
        }
    }

    public void printUsers() {
        for (User user : userService.findAll()) {
            System.out.println(user);
        }
    }

    public void createNewUserByID() {
        System.out.println("Please enter new user info");

        System.out.print("ID: ");
        Integer id = integerLooperExit.getNext(scanner, "Please enter an integer value or (e)xit to quit\nID: ");
        if (id == null) {
            return;
        }

        System.out.print("First Name: ");
        String firstName = stringLooperExit.getNext(scanner, "Please enter a first name or (e)xit to quit\nFirst Name: ");
        if (firstName == null) {
            return;
        }

        System.out.print("Last Name: ");
        String lastName = stringLooperExit.getNext(scanner, "Please enter a last name or (e)xit to quit\nLast Name: ");
        if (lastName == null) {
            return;
        }

        System.out.print("Email: ");
        String email = stringLooperExit.getNext(scanner, "Please enter a first name or (e)xit to quit\nEmail: ");
        if (email == null) {
            return;
        }


        System.out.print("Password: ");
        String password = stringLooperExit.getNext(scanner, "Please enter a first name or (e)xit to quit\nPassword: ");
        if (password == null) {
            return;
        }

        System.out.print("User Type (customer or employee): ");
        User.userType type = userTypeLooperExit.getNext(scanner, "Please enter customer, employee, or (e)xit to quit\nUser Type: ");
        if (type == null) {
            return;
        }

        try {
            userService.create(new User(id, firstName, lastName, email, password, type));
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUser() {
        System.out.print("ID of user to update: ");
        Integer id = integerLooperExit.getNext(scanner, "Please enter an integer value or (e)xit to quit\nID of user to update: ");
        if (id == null) {
            return;
        }

        User userToUpdate;
        try {
            userToUpdate = userService.findById(String.valueOf(id));
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        Validator<String> shouldKeep = (String input) -> {
            return input.equalsIgnoreCase("keep") || input.equalsIgnoreCase("k");
        };

        System.out.println("Current First Name: " + userToUpdate.getFirstName());
        System.out.print("New First Name: ");
        String firstName = stringLooperExit.getNext(scanner, "Please enter a first name, (k)eep to leave unchanged, or (e)xit to wuit\nFirst Name: ");
        if (firstName == null) {
            return;
        }
        if (shouldKeep.validate(firstName)) {
            System.out.println("Keeping First Name: " + userToUpdate.getFirstName());
        } else {
            userToUpdate.setFirstName(firstName);
        }

        System.out.println("Current Last Name: " + userToUpdate.getLastName());
        System.out.print("New Last Name: ");
        String lastName = stringLooperExit.getNext(scanner, "Please enter a last , (k)eep to leave unchanged, or (e)xit to quit\nLast Name: ");
        if (lastName == null) {
            return;
        }
        if (shouldKeep.validate(lastName)) {
            System.out.println("Keeping Last Name: " + userToUpdate.getLastName());
        } else {
            userToUpdate.setLastName(lastName);
        }

        System.out.println("Current Email: " + userToUpdate.getEmail());
        System.out.print("New Email: ");
        String email = stringLooperExit.getNext(scanner, "Please enter an email, (k)eep to leave unchanged, or (e)xit to quit\nEmail: ");
        if (email == null) {
            return;
        }
        if (shouldKeep.validate(email)) {
            System.out.println("Keeping Email: " + userToUpdate.getEmail());
        } else {
            userToUpdate.setEmail(email);
        }


        System.out.print("New Password: ");
        String password = stringLooperExit.getNext(scanner, "Please enter a password, (k)eep to leave unchanged, or (e)xit to quit\nPassword: ");
        if (password == null) {
            return;
        }
        if (shouldKeep.validate(password)) {
            System.out.println("Keeping Password");
        } else {
            userToUpdate.setPassword(password);
        }

        try {
            userService.update(userToUpdate);
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser() {
        System.out.print("ID of user to delete: ");
        Integer id = integerLooperExit.getNext(scanner, "Please enter an integer value or (e)xit to quit\nID of user to delete: ");
        if (id == null) {
            return;
        }

        try {
            User userToDelete = userService.findById(String.valueOf(id));
            userService.delete(userToDelete);
        } catch (DataNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
