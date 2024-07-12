package com.revature.AKBanking.Users;

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

    public void printUsers() {
        for (User user : userService.findAll()) {
            System.out.println(user);
        }
    }

    public void createNewUser() {
        System.out.println("Please enter new user info");

        System.out.print("ID: ");
        Integer id = integerLooper.getNext(scanner, "Please enter an integer value or (e)xit to quit\nID: ");
        if (id == null) {
            return;
        }

        System.out.print("First Name: ");
        String firstName = stringLooper.getNext(scanner, "Please enter a first name or (e)xit to quit\nFirst Name: ");
        if (firstName == null) {
            return;
        }

        System.out.print("Last Name: ");
        String lastName = stringLooper.getNext(scanner, "Please enter a last name or (e)xit to quit\nLast Name: ");
        if (lastName == null) {
            return;
        }

        System.out.print("Email: ");
        String email = stringLooper.getNext(scanner, "Please enter a first name or (e)xit to quit\nEmail: ");
        if (email == null) {
            return;
        }


        System.out.print("Password: ");
        String password = stringLooper.getNext(scanner, "Please enter a first name or (e)xit to quit\nPassword: ");
        if (password == null) {
            return;
        }

        System.out.print("User Type (customer or employee): ");
        User.userType type = userTypeLooper.getNext(scanner, "Please enter customer, employee, or (e)xit to quit\nUser Type: ");
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
        Integer id = integerLooper.getNext(scanner, "Please enter an integer value or (e)xit to quit\nID of user to update: ");
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
        String firstName = stringLooper.getNext(scanner, "Please enter a first name, (k)eep to leave unchanged, or (e)xit to wuit\nFirst Name: ");
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
        String lastName = stringLooper.getNext(scanner, "Please enter a last , (k)eep to leave unchanged, or (e)xit to quit\nLast Name: ");
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
        String email = stringLooper.getNext(scanner, "Please enter an email, (k)eep to leave unchanged, or (e)xit to quit\nEmail: ");
        if (email == null) {
            return;
        }
        if (shouldKeep.validate(email)) {
            System.out.println("Keeping Email: " + userToUpdate.getEmail());
        } else {
            userToUpdate.setEmail(email);
        }


        System.out.print("New Password: ");
        String password = stringLooper.getNext(scanner, "Please enter a password, (k)eep to leave unchanged, or (e)xit to quit\nPassword: ");
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
        Integer id = integerLooper.getNext(scanner, "Please enter an integer value or (e)xit to quit\nID of user to delete: ");
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

    public static void main(String[] args) {
        UserController userController = new UserController(new Scanner(System.in), new UserService(new UserRepository()));
        userController.deleteUser();
    }
}
