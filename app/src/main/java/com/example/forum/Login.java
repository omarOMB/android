package com.example.forum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.security.Key;
import java.sql.Array;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Login extends AppCompatActivity {

    //VARIABLES
    Animation topAnim, bottomAnim, rotateAnim;
    ImageView forum_logo, ensak_logo;
    TextView username_input, password_input;
    Button login, create_account_student , create_account_company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Animations
        rotateAnim = AnimationUtils.loadAnimation(this , R.anim.rotation);
        topAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

        //Hooks
        username_input = findViewById(R.id.editText);
        password_input = findViewById(R.id.editText2);
        ensak_logo = findViewById(R.id.imageView);
        login = findViewById(R.id.loginAccount);
        create_account_student = findViewById(R.id.createAccount);
        create_account_company = findViewById(R.id.createAccountcompany);


        username_input.setAnimation(bottomAnim);
        password_input.setAnimation(bottomAnim);
        ensak_logo.setAnimation(bottomAnim);
        login.setAnimation(bottomAnim);
        create_account_student.setAnimation(bottomAnim);
        create_account_company.setAnimation(bottomAnim);




        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loginRequest();
            }
        });

        create_account_student.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignupStudent.class);
                startActivity(intent);
            }
        });

        create_account_company.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignupCompany.class);
                startActivity(intent);
            }
        });

    }



    private void loginRequest() {
        String url = "http://192.168.68.1:8080/api/auth/signin";
        final  String username_setting = this.username_input.getText().toString().trim();
        final  String password_setting = this.password_input.getText().toString().trim();
        Map<String , String> params = new HashMap<String , String>();
        params.put("password",password_setting);
        params.put("username",username_setting);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id_user = response.getInt("id");
                            String id_user_string = String.valueOf(id_user);
                            String username_user = response.getString("username");
                            String name_user = response.getString("name");
                            String email_user = response.getString("email");
                            String token_user = response.getString("token");
                            JSONArray roles = response.getJSONArray("roles");
                            String role_user = roles.getString(0);
                            //GETTING USER ROLE FOR ROUTING


                            //SESSION
                            SessionManager sessionManager = new SessionManager(Login.this);
                            sessionManager.createLoginSession(id_user_string,username_user,name_user,email_user,"something","something",token_user,role_user,"something","something","something",password_setting,"something");

                            Intent intent = new Intent(Login.this,Home.class);
                            startActivity(intent);


                        }catch (JSONException e){
                            Toast.makeText(Login.this,"wrong Login or Password here !",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"Check your connection wrong login or password !",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String , String> getParams(){
                Map<String , String> params = new HashMap<String , String>();
                params.put("password",password_setting);
                params.put("username",username_setting);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(jsonRequest);
    }

    @Override
    public void onBackPressed() {
        int isItOk = 0;
        if (isItOk == 1) {
            super.onBackPressed();
        } else {
            Toast.makeText(Login.this,"You must enter password and login",Toast.LENGTH_SHORT).show();
        }

    }
}
