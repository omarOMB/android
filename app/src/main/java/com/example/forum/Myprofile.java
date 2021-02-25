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


public class Myprofile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView title;
    private TextView profileUsername;
    private TextView profileEmail;
    private TextView profileAddress;
    private TextView profilePhone;
    private TextView profileType;
    private ImageView profileImage;
    private TextView profileRole;
    private Button update_the_profile_button;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Animation topAnim, bottomAnim, rotateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SessionManager sessionManager = new SessionManager(Myprofile.this);
        HashMap<String , String> userDetails = sessionManager.getUserDetailsFromSession();
        final String current_token = userDetails.get(SessionManager.KEY_TOKEN);
        final String current_role = userDetails.get(SessionManager.KEY_ROLE);
        final String current_name = userDetails.get(SessionManager.KEY_NAME);
        final String current_email = userDetails.get(SessionManager.KEY_EMAIL);
        final String current_username = userDetails.get(SessionManager.KEY_USERNAME);
        final String current_telephone = userDetails.get(SessionManager.KEY_TELEPHONE);
        final String current_id = userDetails.get(SessionManager.KEY_ID);
        final String current_photo_data = userDetails.get(SessionManager.KEY_PHOTO);

        Log.e( "LOG", "role : " + current_role);

        if (current_role.equals("ROLE_ETUDIANT")){
            setContentView(R.layout.activity_myprofile);
        }else{
            setContentView(R.layout.activity_myprofile_company);
        }


        //HOOKS
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        title = findViewById(R.id.profile_title);
        profileUsername = findViewById(R.id.profile_username);
        profileEmail = findViewById(R.id.profile_email);
        profileAddress = findViewById(R.id.profile_adresse);
        profilePhone = findViewById(R.id.profile_phone);
        profileType = findViewById(R.id.profile_type);
        profileImage = findViewById(R.id.profile_image);
        profileRole = findViewById(R.id.profile_role);
        update_the_profile_button = findViewById(R.id.update_the_profile);

        //Animations
        rotateAnim = AnimationUtils.loadAnimation(this , R.anim.rotation);
        topAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);

        //decryptage photo
        byte[] decodedImage = Base64.decode(current_photo_data, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

        if (current_role.equals("ROLE_ETUDIANT")){
            final String current_filliere = userDetails.get(SessionManager.KEY_FILLIERE);
            final String current_annee = userDetails.get(SessionManager.KEY_ANNEE);
            title.setText(current_username);
            profileUsername.setText(current_name);
            profileEmail.setText(current_email);
            profileAddress.setText(current_filliere);
            profilePhone.setText(current_telephone);
            profileType.setText(current_annee);
            profileRole.setText(current_role);
            profileImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 150, 150, false));

            //SET ANIMATIONS
            title.setAnimation(bottomAnim);
            profileUsername.setAnimation(bottomAnim);
            profileEmail.setAnimation(bottomAnim);
            profileAddress.setAnimation(bottomAnim);
            profilePhone.setAnimation(bottomAnim);
            profileType.setAnimation(bottomAnim);
            profileRole.setAnimation(bottomAnim);
            profileImage.setAnimation(topAnim);

        }else{
            final String current_adresse = userDetails.get(SessionManager.KEY_ADRESSE);
            final String current_type = userDetails.get(SessionManager.KEY_TYPE);
            title.setText(current_username);
            profileUsername.setText(current_name);
            profileEmail.setText(current_email);
            profileAddress.setText(current_adresse);
            profilePhone.setText(current_telephone);
            profileType.setText(current_type);
            profileRole.setText(current_role);
            profileImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 150, 150, false));

            //SET ANIMATIONS
            title.setAnimation(bottomAnim);
            profileUsername.setAnimation(bottomAnim);
            profileEmail.setAnimation(bottomAnim);
            profileAddress.setAnimation(bottomAnim);
            profilePhone.setAnimation(bottomAnim);
            profileType.setAnimation(bottomAnim);
            profileRole.setAnimation(bottomAnim);
            profileImage.setAnimation(topAnim);
        }


        //TOOL BAR
        setSupportActionBar(toolbar);

        update_the_profile_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Myprofile.this,UpdateAccount.class);
                startActivity(intent);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(Myprofile.this,Home.class);
                startActivity(intent);
                break;
            case R.id.nav_user:
                break;
            case R.id.nav_logout:
                SessionManager sessionManager = new SessionManager(Myprofile.this);
                sessionManager.logout();
                Intent intent2 = new Intent(Myprofile.this,Login.class);
                startActivity(intent2);
                break;
        }
        return true;
    }
}