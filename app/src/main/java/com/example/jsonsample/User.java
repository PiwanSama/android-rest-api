package com.example.jsonsample;

public class User {
    String name;
    String email;
    String photo;

    public User (String name, String email, String photo){
        this.name = name;
        this.email = email;
        this.photo = photo;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
    }
}
