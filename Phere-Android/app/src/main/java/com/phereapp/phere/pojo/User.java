package com.phereapp.phere.pojo;

/**
 * Created by jorgebaralt on 12/19/17.
 */

public class User {
    private String email;
    private String accountType;
    private String username;
    private String fullName;

    public User(){}

    //Constructor
    public User(String email, String username, String accountType, String fullName){
        this.email = email;
        this.username = username;
        this.accountType = accountType;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() { return fullName; }

}

