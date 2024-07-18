package com.revature.AKBanking.util.auth;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.util.interfaces.Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import javax.security.sasl.AuthenticationException;

public class AuthController implements Controller {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void registerPaths(Javalin app) {
        app.post("/login", this::postLogin);
        app.get("/logout", this::logout);
    }

    private void postLogin(Context context) {
        String email = context.queryParam("email");
        String password = context.queryParam("password");

        try {
            User user = authService.login(email, password);
            context.header("userID", String.valueOf(user.getId()));
            context.header("userType", user.getType().name());
            context.status(200);
        } catch (AuthenticationException e) {
            context.status(HttpStatus.UNAUTHORIZED);
        }
    }

    private void logout(Context context) {
        context.removeHeader("userID");
        context.removeHeader("userType");
        context.status(200);
    }
}
