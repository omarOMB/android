package com.example.forum;

import android.content.Context;
import android.content.SharedPreferences;

import java.security.Key;
import java.util.HashMap;

public class SessionManager{

    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_ADRESSE = "adress";
    public static final String KEY_FILLIERE = "filliere";
    public static final String KEY_ANNEE = "annee";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ROLE = "role";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHOTO = "photo";

    public  SessionManager(Context contextx){
        context = contextx;
        usersSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = usersSession.edit();
    }

    public void createLoginSession(String id,String username,String name, String email, String telephone, String type, String token, String role,String adress , String annee, String filliere, String password, String photo){
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_ID,id);
        editor.putString(KEY_ROLE,role);
        editor.putString(KEY_ADRESSE,adress);
        editor.putString(KEY_ANNEE,annee);
        editor.putString(KEY_FILLIERE,filliere);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_TYPE,type);
        editor.putString(KEY_TOKEN,token);
        editor.putString(KEY_TELEPHONE,telephone);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_PHOTO,photo);

        editor.commit();
    }

    public HashMap<String , String> getUserDetailsFromSession(){
        HashMap<String , String> userData  = new HashMap<String , String>();

        userData.put(KEY_ID,usersSession.getString(KEY_ID,null));
        userData.put(KEY_USERNAME,usersSession.getString(KEY_USERNAME,null));
        userData.put(KEY_NAME,usersSession.getString(KEY_NAME,null));
        userData.put(KEY_ANNEE,usersSession.getString(KEY_ANNEE,null));
        userData.put(KEY_FILLIERE,usersSession.getString(KEY_FILLIERE,null));
        userData.put(KEY_EMAIL,usersSession.getString(KEY_EMAIL,null));
        userData.put(KEY_ADRESSE,usersSession.getString(KEY_ADRESSE,null));
        userData.put(KEY_TYPE,usersSession.getString(KEY_TYPE,null));
        userData.put(KEY_TOKEN,usersSession.getString(KEY_TOKEN,null));
        userData.put(KEY_ROLE,usersSession.getString(KEY_ROLE,null));
        userData.put(KEY_TELEPHONE,usersSession.getString(KEY_TELEPHONE,null));
        userData.put(KEY_PASSWORD,usersSession.getString(KEY_PASSWORD,null));
        userData.put(KEY_PHOTO,usersSession.getString(KEY_PHOTO,null));

        return userData;
    }

    public boolean checkLogin(){
        if(usersSession.getBoolean(IS_LOGIN, true)){
            return true;
        }else{
            return false;
        }
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

}

