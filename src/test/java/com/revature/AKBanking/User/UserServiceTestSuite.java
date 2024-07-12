package com.revature.AKBanking.User;


import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.Users.UserRepository;
import com.revature.AKBanking.Users.UserService;
import com.revature.AKBanking.util.exceptions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// valid user declaration for copy/pasting
// new User(1234567, "John", "Doe", "john@doe.com", "Password1")

public class UserServiceTestSuite {
    private UserService testUserService;
    private static UserRepository userRepository = new UserRepository();

    @BeforeAll
    public static void setUpBeforeClass() {
        try {
            userRepository.findById("1234568");
        } catch (DataNotFoundException e) {
            userRepository.create(new User(1234568, "John", "Doe", "john@doe.com", "Password1"));
        }
    }

    @BeforeEach
    public void setUp() {
        testUserService = new UserService(userRepository);
    }

    @Test
    public void testIDValidation() {
        User testUser = new User(1, "John", "Doe", "john@doe.com", "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("ID is out of bounds", exception.getMessage());
    }

    @Test
    public void testFirstNameValidation() {
        User testUser = new User(1234568, "", "Doe", "john@doe.com", "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("First name is empty", exception.getMessage());
    }

    @Test
    public void testLastNameValidation() {
        User testUser = new User(1234568, "John", "", "john@doe.com", "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Last name is empty", exception.getMessage());
    }

    @Test
    public void testEmailNotEmptyValidation() {
        User testUser = new User(1234568, "John", "Doe", null, "Password1");
        ;
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Email is empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa.com", "a@b", "a.com@"})
    public void testEmailRegexValidation(String testEmail) {
        User testUser = new User(1234568, "John", "Doe", testEmail, "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Email is invalid", exception.getMessage());
    }

    @Test
    public void testPasswordNotEmptyValidation() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", null);
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Password is empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"paSSwoRD", "password1", "PASSWORD1", "abC3", "abcDEF123456789123456789ABCdef"})
    public void testPasswordRegexValidation(String testPassword) {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", testPassword);
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            testUserService.validateUser(testUser);
        });
        assertEquals("Password is invalid, must contain 1 lowercase character, 1 uppercase character, and 1 number", exception.getMessage());
    }

    @Test
    public void testSuccessfulAdditionAndDeletion() {
        User testUser = new User(9876543, "Jane", "Deer", "jane@deer.com", "TestUs3r");

        assertEquals(testUser, testUserService.create(testUser));
        assertTrue(testUserService.delete(testUser));
    }

    @Test
    public void testFindingUserByID() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", "Password1");

        User searchResult = testUserService.findById(String.valueOf(testUser.getId()));

        assertEquals(testUser, searchResult);
    }

    @Test
    public void testFailFindingUserByID() {
        User testUser = new User(1234567, "John", "Doe", "john@doe.com", "Password1");

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            testUserService.findById(String.valueOf(testUser.getId()));
        });
        assertEquals(String.format("User with ID: %s not found", testUser.getId()), exception.getMessage());
    }

    @Test
    public void testFindingUserByLoginInfo() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", "Password1");

        User searchResult = testUserService.findByLoginInfo(testUser.getEmail(), testUser.getPassword());

        assertNotNull(searchResult);
        assertEquals(testUser, searchResult);
    }

    @Test
    public void testFailFindingUserByLoginInfo() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", "Password1");

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            testUserService.findByLoginInfo(testUser.getEmail(), testUser.getPassword() + "123");
        });
        assertEquals("Email or password is incorrect", exception.getMessage());
    }

    @Test
    public void testUpdatingUser() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", "Password1");

    }
}
