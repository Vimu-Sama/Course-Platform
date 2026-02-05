package com.vimarsh.Course_Platform.DataTransferObjects;

public class LoginResponseDTO {
    private String token ;
    private String email ;
    private long expiresIn ;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String email, long expiresIn) {
        this.token = token;
        this.email = email;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
