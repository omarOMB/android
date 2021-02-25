package com.example.forum;

import org.json.JSONObject;

public class User {
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private JSONObject photo;

    User(int id,String name,String username,String email,String phone,JSONObject photo){
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.photo = photo;


    }

    //GETTERS
    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public JSONObject getPhoto() {
        return photo;
    }

    //SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhoto(JSONObject photo) {
        this.photo = photo;
    }
}
