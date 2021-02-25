package com.example.forum;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PrivateKey;

public class Students extends User{

    private String filliere;
    private String annee;



    Students(int id, String name, String username, String email, String phone, String filliere, String annee, JSONObject photo) {
        super(id, name, username, email, phone , photo);
        this.filliere = filliere;
        this.annee = annee;
    }

    //GETTERS
    public String getFilliere() {
        return filliere;
    }
    public String getAnnee() {
        return annee;
    }



    //SETTERS

    public void setAnne(String annee) {
        this.annee = annee;
    }

    public void setFilliere(String filliere) {
        this.filliere = filliere;
    }
}
