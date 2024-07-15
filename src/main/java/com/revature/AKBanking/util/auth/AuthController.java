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
    }

    private void postLogin(Context ctx) {
        String email = ctx.queryParam("email");
        String password = ctx.queryParam("password");

        try {
            User user = authService.login(email, password);
            ctx.header("userID", String.valueOf(user.getId()));
            ctx.header("userType", user.getType().name());
            ctx.status(200);
        } catch (AuthenticationException e) {
            ctx.status(HttpStatus.UNAUTHORIZED);
        }
    }
}
