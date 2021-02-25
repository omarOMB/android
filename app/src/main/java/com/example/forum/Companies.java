package com.example.forum;

import org.json.JSONObject;

public class Companies extends User{

    private String adresse;
    private String type;


    Companies(int id, String name, String username, String email, String phone, String adresse, String type , JSONObject photo) {
        super(id, name, username, email, phone,photo);
        this.adresse = adresse;
        this.type = type;
    }

    //GETTERS
    public String getAdresse() {
        return adresse;
    }
    public String getType() {
        return type;
    }

    //SETTERS

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setType(String type) {
        this.type = type;
    }
}
