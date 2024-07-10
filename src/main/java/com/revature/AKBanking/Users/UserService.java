package com.revature.AKBanking.Users;

import com.revature.AKBanking.util.exceptions.InvalidInputException;
import com.revature.AKBanking.util.interfaces.Serviceable;
import com.revature.AKBanking.Users.User;

import java.util.ArrayList;
import java.util.List;

public class UserService implements Serviceable<User>{
    private List<User> users;

    public UserService(){
        users = new ArrayList<User>();
    }

    @Override
    public User[] findAllWithID(String id) {
        User[] results = users.stream().filter
            (user -> user.getId() == Integer.parseInt(id))
                .toArray(User[]::new);
        return results.length > 0 ? results : null;
    }

    @Override
    public User create(User newObject) throws InvalidInputException {
        users.add(newObject);
        return newObject;
    }

    public User findByLoginInfo(String email, String password){
        User[] results = users.stream().filter
            (user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .toArray(User[]::new);
        return results.length > 0 ? results[0] : null;
    }

}
