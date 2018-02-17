package com.phereapp.phere;

/**
 * Created by jorgebaralt on 12/19/17.
 */

public class User {
    private String email;
    private String accountType;
    private String username;

    public User(String email, String username, String accountType){
        this.email = email;
        this.username = username;
        this.accountType = accountType;
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
}
