package it.uniroma3.siw.siwfood.responses;

import it.uniroma3.siw.siwfood.entities.ruolo;

import java.util.List;

public class authResponse {
    private String token;
    private String message;
    private List<ruolo> ruolos;
    private boolean success;
    private String username;
    private Long userId;

    public authResponse(String token, String message, boolean success, List<ruolo> ruolos, String username, Long userId) {
        this.token = token;
        this.message = message;
        this.success = success;
        this.ruolos = ruolos;
        this.username = username;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ruolo> getRole(){
        return ruolos;
    }

    public void setRole(List<ruolo> ruolos){
        this.ruolos = ruolos;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

