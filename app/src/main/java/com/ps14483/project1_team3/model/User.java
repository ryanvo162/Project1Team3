package com.ps14483.project1_team3.model;

public class User {
    public String key, name, username, password;

    public User() {
    }

    public User(String name, String username, String password,String key) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.key=key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
