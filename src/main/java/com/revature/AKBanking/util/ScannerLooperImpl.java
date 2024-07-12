package com.revature.AKBanking.util;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.util.interfaces.ScannerLooper;

public class ScannerLooperImpl {
    public static ScannerLooper<Integer> integerLooperExit = (scanner, repeatMessage) -> {
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("exit") && !input.equalsIgnoreCase("e")) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print(repeatMessage);
                input = scanner.nextLine();
            }
        }
        return null;
    };

    public static ScannerLooper<Integer> integerLooper = (scanner, repeatMessage) -> {
        String input = scanner.nextLine();
        while (true) {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print(repeatMessage);
                input = scanner.nextLine();
            }
        }
    };

    public static ScannerLooper<String> stringLooperExit = (scanner, repeatMessage) -> {
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("exit") && !input.equalsIgnoreCase("e")) {
            if (!input.trim().isEmpty()) {
                return input;
            }
            System.out.print(repeatMessage);
            input = scanner.nextLine();
        }
        return null;
    };

    public static ScannerLooper<User.userType> userTypeLooperExit = (scanner, repeatMessage) -> {
        String input = scanner.nextLine();
        while (!input.equalsIgnoreCase("exit") && !input.equalsIgnoreCase("e")) {
            try {
                return User.userType.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print(repeatMessage);
                input = scanner.nextLine();
            }
        }
        return null;
    };
}
