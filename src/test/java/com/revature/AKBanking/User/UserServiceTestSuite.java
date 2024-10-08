package com.revature.AKBanking.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import com.revature.AKBanking.Users.User;
import com.revature.AKBanking.Users.UserRepository;
import com.revature.AKBanking.Users.UserService;
import com.revature.AKBanking.util.exceptions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

// valid user declaration for copy/pasting
// new User(1234567, "John", "Doe", "john@doe.com", "Password1")
//TODO refactor all tests to use mockito and mock repo
@ExtendWith(MockitoExtension.class)
public class UserServiceTestSuite {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService testUserService;


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

        Exception exception = assertThrows(InvalidInputException.class, () -> testUserService.validateUser(testUser));
        assertEquals("Email is empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa.com", "a@b", "a.com@"})
    public void testEmailRegexValidation(String testEmail) {
        User testUser = new User(1234568, "John", "Doe", testEmail, "Password1");
        Exception exception = assertThrows(InvalidInputException.class, () -> testUserService.validateUser(testUser));
        assertEquals("Email is invalid", exception.getMessage());
    }

    @Test
    public void testPasswordNotEmptyValidation() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", null);
        Exception exception = assertThrows(InvalidInputException.class, () -> testUserService.validateUser(testUser));
        assertEquals("Password is empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"paSSwoRD", "password1", "PASSWORD1", "abC3", "abcDEF123456789123456789ABCdef"})
    public void testPasswordRegexValidation(String testPassword) {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", testPassword);
        Exception exception = assertThrows(InvalidInputException.class, () -> testUserService.validateUser(testUser));
        assertEquals("Password is invalid, must be between 8-24 characters, contain 1 lowercase, 1 uppercase, and 1 number", exception.getMessage());
    }

    @Test
    public void testSuccessfulAdditionAndDeletion() {
        User testUser = new User(9876543, "Jane", "Deer", "jane@deer.com", "TestUs3r");
        Mockito.when(userRepository.create(testUser)).thenReturn(testUser);
        Mockito.when(userRepository.delete(testUser)).thenReturn(true);

        assertEquals(testUser, testUserService.create(testUser));
        assertTrue(testUserService.delete(testUser));
    }

    @Test
    public void testFindingUserByID() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", "Password1");
        Mockito.when(userRepository.findById(String.valueOf(testUser.getId()))).thenReturn(testUser);

        User searchResult = testUserService.findById(String.valueOf(testUser.getId()));

        assertEquals(testUser, searchResult);
    }

    @Test
    public void testFailFindingUserByID() {
        User testUser = new User(1234567, "John", "Doe", "john@doe.com", "Password1");
        String expError = String.format("User with ID: %s not found", testUser.getId());
        Mockito.when(userRepository.findById(String.valueOf(testUser.getId()))).thenThrow(new DataNotFoundException(expError));

        Exception exception = assertThrows(DataNotFoundException.class, () -> testUserService.findById(String.valueOf(testUser.getId())));
        assertEquals(expError, exception.getMessage());
    }

    @Test
    public void testFindingUserByLoginInfo() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", "Password1");
        Mockito.when(userRepository.findByLoginInfo(testUser.getEmail(), testUser.getPassword())).thenReturn(testUser);

        User searchResult = testUserService.findByLoginInfo(testUser.getEmail(), testUser.getPassword());

        assertNotNull(searchResult);
        assertEquals(testUser, searchResult);
    }

    @Test
    public void testFailFindingUserByLoginInfo() {
        User testUser = new User(1234568, "John", "Doe", "john@doe.com", "Password1");
        String expError = "Email or password is incorrect";
        Mockito.when(userRepository.findByLoginInfo(testUser.getEmail(), "incorrect")).thenThrow(new DataNotFoundException(expError));


        Exception exception = assertThrows(DataNotFoundException.class, () -> testUserService.findByLoginInfo(testUser.getEmail(), "incorrect"));
        assertEquals(expError, exception.getMessage());
    }
}
