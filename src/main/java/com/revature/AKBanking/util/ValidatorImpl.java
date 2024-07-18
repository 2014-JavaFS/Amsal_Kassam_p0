package com.revature.AKBanking.util;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.util.interfaces.Validator;
import io.javalin.http.Context;

public class ValidatorImpl {
    public static Validator<Context> isEmployee = (
    Context context) -> {
        String userType = context.header("userType");
        return !(userType == null || userType.isEmpty() || userType.equals(User.userType.CUSTOMER.name()));

    };
}
