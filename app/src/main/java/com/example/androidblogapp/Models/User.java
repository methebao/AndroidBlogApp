package com.example.androidblogapp.Models;

public class User {

    private String fullName;


    private String bio;
    private String email;

    public User(String fullName, String bio, String email ) {
        this.fullName = fullName;
        this.bio = bio;
        this.email = email;
    }
    public User () {

    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
