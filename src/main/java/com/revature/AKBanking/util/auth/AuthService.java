package com.revature.AKBanking.util.auth;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.Users.UserService;
import com.revature.AKBanking.util.exceptions.DataNotFoundException;

import javax.security.sasl.AuthenticationException;

public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String email, String password) throws AuthenticationException {
        try{
            return userService.findByLoginInfo(email, password);
        } catch(DataNotFoundException e){
            throw new AuthenticationException("Invalid email or password");
        }
    }
}
