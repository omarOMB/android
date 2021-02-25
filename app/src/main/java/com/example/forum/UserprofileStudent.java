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
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


public class UserprofileStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView title;
    private TextView profileUsername;
    private TextView profileEmail;
    private TextView profileAddress;
    private TextView profilePhone;
    private TextView profileType;
    private ImageView profilephoto;
    private TextView role;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Animation topAnim, bottomAnim, rotateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_userprofile_student);

        //HOOKS
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //EXTRA PASSED VALUES
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String address = getIntent().getStringExtra("filliere");
        String phone = getIntent().getStringExtra("phone");
        String type = getIntent().getStringExtra("annee");
        String photo = getIntent().getStringExtra("photo");
        byte[] decodedImage = Base64.decode(photo, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

        title = findViewById(R.id.profile_title);
        profileUsername = findViewById(R.id.profile_username);
        profileEmail = findViewById(R.id.profile_email);
        profileAddress = findViewById(R.id.profile_adresse);
        profilePhone = findViewById(R.id.profile_phone);
        profileType = findViewById(R.id.profile_type);
        profilephoto = findViewById(R.id.profile_image);
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
        role.setAnimation(bottomAnim);
        profilephoto.setAnimation(topAnim);

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
                Intent intent = new Intent(UserprofileStudent.this,Home.class);
                startActivity(intent);
                break;
            case R.id.nav_user:
                Intent intent2 = new Intent(UserprofileStudent.this,Myprofile.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                SessionManager sessionManager = new SessionManager(UserprofileStudent.this);
                sessionManager.logout();
                Intent intent3 = new Intent(UserprofileStudent.this,Login.class);
                startActivity(intent3);
                break;
        }
        return true;
    }
}