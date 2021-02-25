package com.example.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Userprofile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView title;
    private TextView profileUsername;
    private TextView profileEmail;
    private TextView profileAddress;
    private TextView profilePhone;
    private TextView profileType;
    private ImageView profilephoto;
    private TextView role;
    private Button subscribe;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Animation topAnim, bottomAnim, rotateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_userprofile);

        //HOOKS
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //EXTRA PASSED VALUES
        String username = getIntent().getStringExtra("username");
        final String id_company = getIntent().getStringExtra("id");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("address");
        String phone = getIntent().getStringExtra("phone");
        String type = getIntent().getStringExtra("type");
        String photo = getIntent().getStringExtra("photo");
        byte[] decodedImage = Base64.decode(photo, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

        title = findViewById(R.id.profile_title);
        profileUsername = findViewById(R.id.profile_username);
        profileEmail = findViewById(R.id.profile_email);
        profileAddress = findViewById(R.id.profile_adresse);
        profilePhone = findViewById(R.id.profile_phone);
        profileType = findViewById(R.id.profile_type);
        profilephoto = findViewById(R.id.profile_image123);
        subscribe = findViewById(R.id.subbutton);
        role = findViewById(R.id.profile_role);

        //Animations
        rotateAnim = AnimationUtils.loadAnimation(this , R.anim.rotation);
        topAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

        title.setText(username);
        profileUsername.setText(username);
        profileEmail.setText(email);
        profileAddress.setText(address);
        profilePhone.setText(phone);
        profileType.setText(type);
        profilephoto.setImageBitmap(Bitmap.createScaledBitmap(bmp, 150, 150, false));
        //TOOL BAR
        setSupportActionBar(toolbar);

        //SET ANIMATIONS
        title.setAnimation(bottomAnim);
        profileUsername.setAnimation(bottomAnim);
        profileEmail.setAnimation(bottomAnim);
        profileAddress.setAnimation(bottomAnim);
        profilePhone.setAnimation(bottomAnim);
        profileType.setAnimation(bottomAnim);
        profilephoto.setAnimation(topAnim);
        role.setAnimation(bottomAnim);
        subscribe.setAnimation(bottomAnim);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String , String> userDetails = sessionManager.getUserDetailsFromSession();
        final String current_id = userDetails.get(SessionManager.KEY_ID);
        final String current_token = userDetails.get(SessionManager.KEY_TOKEN);

        // PROFILES LOADER && SESSION INFORMATIONS
        String url99 = "http://192.168.68.1:8080/resources/companies/" + id_company;
        JsonObjectRequest jsonRequest2 = new JsonObjectRequest(Request.Method.GET, url99, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray students = response.getJSONArray("etudiants");
                            for (int i = 0; i < students.length(); i++) {
                                JSONObject result = students.getJSONObject(i);
                                if (String.valueOf(result.getInt("id")).equals(current_id)){
                                    Drawable background = subscribe.getBackground();
                                    subscribe.setText("UNAPPLY");
                                    subscribe.setBackgroundResource(R.color.RoundedRedButton);
                                }
                            }
                        } catch (JSONException e) {
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG", error.toString());
                Toast.makeText(Userprofile.this, "Connection problem !", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + current_token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Userprofile.this);
        requestQueue.add(jsonRequest2);


        subscribe.setOnClickListener(new View.OnClickListener() {
            Drawable background = subscribe.getBackground();
            @Override
            public void onClick(View view) {
                if (subscribe.getText().equals("APPLY")){
                    subscribeRequest(id_company);
                    subscribe.setText("UNAPPLY");
                    subscribe.setBackgroundResource(R.color.RoundedRedButton);
                }
                else{
                    unsubscribeRequest(id_company);
                    subscribe.setText("APPLY");
                    subscribe.setBackgroundResource(R.color.RoundedGreenButton);
                }
            }
        });



        //NAVIGATION MENU
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout , toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        int isItOk = 0;
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            if (isItOk == 1) {
                super.onBackPressed();
            } else {

            }
        }
    }

    private void subscribeRequest(String companyid) {
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String , String> userDetails = sessionManager.getUserDetailsFromSession();
        final String user_id = userDetails.get(SessionManager.KEY_ID);
        final String user_token = userDetails.get(SessionManager.KEY_TOKEN);
        String url = "http://192.168.68.1:8080/resources/postuler/"+ user_id +"/company/" + companyid ;
        Log.e("TAG", "url : "+ url );
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id_user = response.getInt("id");
                            String id_user_string = String.valueOf(id_user);


                        }catch (JSONException e){
                            Toast.makeText(Userprofile.this,"Exeption made",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Userprofile.this,"Check your connection !",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + user_token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Userprofile.this);
        requestQueue.add(jsonRequest);
    }

    private void unsubscribeRequest(String companyid) {
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String , String> userDetails = sessionManager.getUserDetailsFromSession();
        final String user_id = userDetails.get(SessionManager.KEY_ID);
        final String user_token = userDetails.get(SessionManager.KEY_TOKEN);
        String url = "http://192.168.68.1:8080/resources/depostuler/"+ user_id +"/company/" + companyid ;
        Log.e("TAG", "url : "+ url );
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id_user = response.getInt("id");
                            String id_user_string = String.valueOf(id_user);


                        }catch (JSONException e){
                            Toast.makeText(Userprofile.this,"Exeption made",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Userprofile.this,"Check your connection !",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + user_token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Userprofile.this);
        requestQueue.add(jsonRequest);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(Userprofile.this,Home.class);
                startActivity(intent);
                break;
            case R.id.nav_user:
                Intent intent2 = new Intent(Userprofile.this,Myprofile.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                SessionManager sessionManager = new SessionManager(Userprofile.this);
                sessionManager.logout();
                Intent intent3 = new Intent(Userprofile.this,Login.class);
                startActivity(intent3);
                break;
        }
        return true;
    }
}