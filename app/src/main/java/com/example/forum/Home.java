package com.example.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //HOOKS OF NAVIGATION MENU
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        //DATA AND RECYCLERVIEW
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new  ForumAdapter(getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());

        //TOOL BAR
        setSupportActionBar(toolbar);
        recyclerView.setAdapter(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLayoutManager(layoutManager);
            }
        },2000);


        //NAVIGATION MENU
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout , toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent33 = new Intent(Home.this,Home.class);
                startActivity(intent33);
                break;
            case R.id.nav_user:
                Intent intent = new Intent(Home.this,Myprofile.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                SessionManager sessionManager = new SessionManager(Home.this);
                sessionManager.logout();
                Intent intent2 = new Intent(Home.this,Login.class);
                startActivity(intent2);
                break;
        }
        return true;
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
}
