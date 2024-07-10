package com.revature.AKBanking.Users;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public enum userType {
        USER, EMPLOYEE
    }

    public User(int id, String firstName,String lastName, String email, String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return String.format("%s, %s", lastName, firstName);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
