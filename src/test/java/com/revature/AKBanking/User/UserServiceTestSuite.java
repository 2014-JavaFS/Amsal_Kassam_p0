package com.revature.AKBanking.User;


import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.Users.UserService;
import com.revature.AKBanking.util.exceptions.InvalidInputException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// valid user declaration for copy/pasting
// new User(1234567, "John", "Doe", "john@doe.com", "Password1")

public class UserServiceTestSuite {
    private UserService testUserService;

    @BeforeEach
    public void setUp() {
        testUserService = new UserService();
    }

    @Test
    public void testIDValidation(){
        User testUser = new User(1, "John", "Doe", "john@doe.com", "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("ID is out of bounds", exception.getMessage());
    }

    @Test
    public void testFirstNameValidation(){
        User testUser = new User(1234567, "", "Doe", "john@doe.com", "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> {
           testUserService.validateUser(testUser);
        });
        assertEquals("First name is empty", exception.getMessage());
    }

    @Test
    public void testLastNameValidation(){
        User testUser = new User(1234567, "John", "", "john@doe.com", "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Last name is empty", exception.getMessage());
    }

    @Test
    public void testEmailNotEmptyValidation(){
        User testUser = new User(1234567, "John", "Doe", null, "Password1");;
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Email is empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource (strings = {"aaa.com", "a@b", "a.com@"})
    public void testEmailRegexValidation(String testEmail){
        User testUser = new User(1234567, "John", "Doe", testEmail, "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Email is invalid", exception.getMessage());
    }

    @Test
    public void testPasswordNotEmptyValidation(){
        User testUser = new User(1234567, "John", "Doe", "john@doe.com", null);
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Password is empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource (strings = {"paSSwoRD", "password1", "PASSWORD1", "abC3", "abcDEF123456789123456789ABCdef"})
    public void testPasswordRegexValidation(String testPassword){
        User testUser = new User(1234567, "John", "Doe", "john@doe.com", testPassword);
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Password is invalid", exception.getMessage());
    }

    @Test
    public void testSuccessfulAddition(){
        User testUser = new User(1234567, "John", "Doe", "john@doe.com", "Password1");
        assertDoesNotThrow(() -> assertEquals(testUser, testUserService.create(testUser)));
    }

    
}
