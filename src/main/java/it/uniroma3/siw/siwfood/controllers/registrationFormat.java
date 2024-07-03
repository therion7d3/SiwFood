package it.uniroma3.siw.siwfood.controllers;

public class registrationFormat {
    private final String nome;
    private final String cognome;
    private final String password;
    private final String email;
    private final String dob;

    public registrationFormat(String nome, String cognome, String password, String email, String dob) {
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }
}
