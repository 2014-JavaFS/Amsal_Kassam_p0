package com.revature.AKBanking.Users;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private userType type;

    public enum userType {
        CUSTOMER, EMPLOYEE
    }

    public User(int id, String firstName,String lastName, String email, String password, userType type){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public User(int id, String firstName,String lastName, String email, String password){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.type = userType.CUSTOMER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return String.format("%s, %s", lastName, firstName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public userType getType(){
        return type;
    }

    public void setType(userType type){
        this.type = type;
    }
}
