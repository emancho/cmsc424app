package com.example.vizva.sns_app;

public class Readers {

    String username,email,password,address, tags;

    public Readers(String username, String email, String password, String address, String tags){
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.tags = tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
