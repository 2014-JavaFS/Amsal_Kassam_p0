package com.revature.AKBanking.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.javalin.Javalin;

import com.revature.AKBanking.util.interfaces.Controller;
import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class UserController implements Controller {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/addUser", this::createNewUserByID);
        app.post("/updateUser", this::updateUser);
        app.post("/deleteUser", this::deleteUser);
    }

    public void createNewUserByID(Context context) {

        int id;
        User.userType type;
        try {
            id = Integer.parseInt(context.queryParam("ID"));
            type = Enum.valueOf(User.userType.class, context.queryParam("type"));
        } catch (NumberFormatException | NullPointerException e) {
            context.status(HttpStatus.PARTIAL_CONTENT);
            return;
        }

        String firstName = context.queryParam("firstName");
        String lastName = context.queryParam("lastName");
        String email = context.queryParam("email");
        String password = context.queryParam("password");

        try {
            User newUser = userService.create(new User(id, firstName, lastName, email, password, type));
            context.header("newUser", newUser.toString());
            context.status(200);
        } catch (InvalidInputException e) {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(Context context) {
        String id = context.queryParam("ID");
        User userToUpdate;
        try {
            userToUpdate = userService.findById(id);
        } catch (DataNotFoundException e) {
            context.status(HttpStatus.NOT_FOUND);
            System.out.println(e.getMessage());
            return;
        }


        String firstName = context.queryParam("firstName");
        firstName = firstName == null ? userToUpdate.getFirstName() : firstName;
        String lastName = context.queryParam("lastName");
        lastName = lastName == null ? userToUpdate.getLastName() : lastName;
        String email = context.queryParam("email");
        email = email == null ? userToUpdate.getEmail() : email;
        String password = context.queryParam("password");
        password = password == null ? userToUpdate.getPassword() : password;
        String userType = context.queryParam("type");
        userType = userType == null ? userToUpdate.getType().name() : userType;

        try {
            User.userType type = Enum.valueOf(User.userType.class, userType);
            userToUpdate = new User(Integer.parseInt(id), firstName, lastName, email, password, type);
            userService.update(userToUpdate);
            context.header("updatedUser", userToUpdate.toString());
            context.status(200);
        } catch (NumberFormatException | NullPointerException e) {
            context.status(HttpStatus.PARTIAL_CONTENT);
            System.out.println(e.getMessage());
        } catch (InvalidInputException e) {
            context.status(HttpStatus.UNPROCESSABLE_CONTENT);
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(Context context) {
        try {
            String id = context.queryParam("ID");
            User userToDelete = userService.findById(id);
            userService.delete(userToDelete);
            context.header("deletedUser", userToDelete.toString());
            context.status(200);
        } catch (DataNotFoundException | NumberFormatException e) {
            context.status(HttpStatus.NOT_FOUND);
            System.out.println(e.getMessage());
        }
    }
}
