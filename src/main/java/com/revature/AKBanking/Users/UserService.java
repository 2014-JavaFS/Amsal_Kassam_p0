package com.revature.AKBanking.Users;

import com.revature.AKBanking.util.exceptions.DataNotFoundException;
import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Serviceable;
import com.revature.AKBanking.Users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class UserService implements Serviceable<User> {
    private List<User> users;

    public UserService() {
        users = new ArrayList<User>();
    }

    @Override
    public User findById(String id) throws DataNotFoundException {
        User[] results = users.stream().filter(user -> user.getId() == Integer.parseInt(id))
                .toArray(User[]::new);
        if(results.length == 0) {
            throw new DataNotFoundException(String.format("User with ID: %s not found", id));
        }
        return results[0];
    }

    @Override
    public User create(User newObject) throws InvalidInputException {
        validateUser(newObject);
        users.add(newObject);
        return newObject;
    }

    public User findByLoginInfo(String email, String password) {
        User[] results = users.stream().filter
                (user -> user.getEmail().equals(email) && user.getPassword().equals(password)).toArray(User[]::new);
        return results.length > 0 ? results[0] : null;
    }

    public void validateUser(User user) throws InvalidInputException {
        if (user == null) {
            throw new InvalidInputException("User is null");
        }
        StringBuilder errorMessage = new StringBuilder();
        //keys: error messages, values: boolean expressions of validation checks
        Map<String, Boolean> validationSteps = new LinkedHashMap<String, Boolean>();
        validationSteps.put("First name is empty", user.getFirstName() == null || user.getFirstName().isEmpty());
        validationSteps.put("Last name is empty", user.getLastName() == null || user.getLastName().isEmpty());
        validationSteps.put("Email is empty", user.getEmail() == null || user.getEmail().isEmpty());
        validationSteps.put("Password is empty", user.getPassword() == null || user.getPassword().isEmpty());

        validationSteps.put("ID is out of bounds", user.getId() < 1000000 || user.getId() > 9999999); //any 7-digit number

        String emailRegex = "^[\\w\\.\\-]+@([\\w\\-]+\\.)+\\w{2,}$";
        //email must be of form [a+][@][z+][.zz] where square brackets are required
        validationSteps.put("Email is invalid", user.getEmail() != null && !Pattern.matches(emailRegex, user.getEmail()));

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,24}";
        //password must contain 1 lowercase, 1 uppercase, 1 number, and be between 8 and 24 characters
        validationSteps.put("Password is invalid", user.getPassword() != null && !Pattern.matches(passwordRegex, user.getPassword()));

        for (Map.Entry<String, Boolean> entry : validationSteps.entrySet()) {
            if (entry.getValue()) {
                errorMessage.append(entry.getKey()).append(" ");
            }
        }
        if (!errorMessage.isEmpty()) {
            throw new InvalidInputException(errorMessage.toString().trim());
        }
    }

}
