package com.vimarsh.Course_Platform.DataTransferObjects;

public class UserResponseDTO {
    int id ;
    String email ;
    String message ;

    public UserResponseDTO() {
    }

    public UserResponseDTO(int id, String email, String message) {
        this.id = id;
        this.email = email;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
