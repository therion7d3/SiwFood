package it.uniroma3.siw.siwfood.controllers;

public class loginFormat {
    private String username;
    private String password;

    public loginFormat() {}

    public loginFormat(String username, String password) {
        this.username = username;
        this.password = password;
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