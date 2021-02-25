package com.example.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

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


public class UpdateAccount extends AppCompatActivity {

    private TextView title;
    private TextView profileUsername;
    private TextView profileEmail;
    private TextView profileAddress;
    private TextView profilePhone;
    private TextView profileType;

    Button update;
    Button cancel;

    Animation topAnim, bottomAnim, rotateAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SessionManager sessionManager = new SessionManager(UpdateAccount.this);
        HashMap<String , String> userDetails = sessionManager.getUserDetailsFromSession();
        final String current_token = userDetails.get(SessionManager.KEY_TOKEN);
        final String current_role = userDetails.get(SessionManager.KEY_ROLE);
        final String current_name = userDetails.get(SessionManager.KEY_NAME);
        final String current_email = userDetails.get(SessionManager.KEY_EMAIL);
        final String current_username = userDetails.get(SessionManager.KEY_USERNAME);
        final String current_telephone = userDetails.get(SessionManager.KEY_TELEPHONE);
        final String current_id = userDetails.get(SessionManager.KEY_ID);
        final String current_password = userDetails.get(SessionManager.KEY_PASSWORD);
        final String current_photo = userDetails.get(SessionManager.KEY_PHOTO);

        Log.e( "LOG", "role : " + current_role);

        if (current_role.equals("ROLE_ETUDIANT")){
            setContentView(R.layout.activity_update_account);
        }else{
            setContentView(R.layout.activity_update_account_company);
        }




        profileUsername = findViewById(R.id.user_input_name);
        profileEmail = findViewById(R.id.user_input_email);
        profilePhone = findViewById(R.id.user_input_tel);
        profileType = findViewById(R.id.user_input_year);
        profileAddress = findViewById(R.id.user_input_domain);

        update = findViewById(R.id.update_button);
        cancel = findViewById(R.id.canceling_button);

        //Animations
        rotateAnim = AnimationUtils.loadAnimation(this , R.anim.rotation);
        topAnim = AnimationUtils.loadAnimation(this , R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this , R.anim.bottom_animation);


        if (current_role.equals("ROLE_ETUDIANT")){
            final String current_filliere = userDetails.get(SessionManager.KEY_FILLIERE);
            final String current_annee = userDetails.get(SessionManager.KEY_ANNEE);
            profileUsername.setText(current_name);
            profilePhone.setText(current_telephone);
            profileEmail.setText(current_email);
            profileType.setText(current_annee);
            profileAddress.setText(current_filliere);

            //SET ANIMATIONS
            profileUsername.setAnimation(bottomAnim);
            profilePhone.setAnimation(bottomAnim);
            profileEmail.setAnimation(bottomAnim);
            profileType.setAnimation(bottomAnim);
            profileAddress.setAnimation(bottomAnim);
            update.setAnimation(bottomAnim);
            cancel.setAnimation(bottomAnim);

            update.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final  String name_setting = profileUsername.getText().toString().trim();
                    final  String phone_setting = profilePhone.getText().toString().trim();
                    final  String email_setting = profileEmail.getText().toString().trim();
                    final  String annee_setting = profileType.getText().toString().trim();
                    final  String filliere_setting = profileAddress.getText().toString().trim();

                    //SESSION
                    SessionManager sessionManager = new SessionManager(UpdateAccount.this);
                    sessionManager.createLoginSession(current_id,current_username,name_setting,email_setting,phone_setting,"something",current_token,current_role,"something",annee_setting,filliere_setting,current_password,current_photo);
                    //updating
                    updateStudent(current_id,current_token,current_password,name_setting,phone_setting,email_setting,annee_setting,filliere_setting);
                }
            });


        }else{
            final String current_adresse = userDetails.get(SessionManager.KEY_ADRESSE);
            final String current_type = userDetails.get(SessionManager.KEY_TYPE);
            profileUsername.setText(current_name);
            profilePhone.setText(current_telephone);
            profileEmail.setText(current_email);
            profileType.setText(current_type);
            profileAddress.setText(current_adresse);

            //SET ANIMATIONS
            profileUsername.setAnimation(bottomAnim);
            profilePhone.setAnimation(bottomAnim);
            profileEmail.setAnimation(bottomAnim);
            profileType.setAnimation(bottomAnim);
            profileAddress.setAnimation(bottomAnim);
            update.setAnimation(bottomAnim);
            cancel.setAnimation(bottomAnim);

            update.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final  String name_setting = profileUsername.getText().toString().trim();
                    final  String phone_setting = profilePhone.getText().toString().trim();
                    final  String email_setting = profileEmail.getText().toString().trim();
                    final  String type_setting = profileType.getText().toString().trim();
                    final  String address_setting = profileAddress.getText().toString().trim();

                    //SESSION
                    SessionManager sessionManager = new SessionManager(UpdateAccount.this);
                    sessionManager.createLoginSession(current_id,current_username,name_setting,email_setting,phone_setting,type_setting,current_token,current_role,address_setting,"something","something",current_password,current_photo);
                    //updating
                    updateCompany(current_id,current_token,current_password,name_setting,phone_setting,email_setting,type_setting,address_setting);
                }
            });


        }

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateAccount.this,Myprofile.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
    int isItOk = 0;
        if (isItOk == 1) {
            super.onBackPressed();
        } else {

        }

    }

    private void updateStudent(String student_id,final String student_token, String student_password, String student_name, String student_tel, String student_email, String student_annee , String student_Filliere) {
    String url = "http://192.168.68.1:8080/resources/etudiants/" + student_id ;
    Map<String , String> params = new HashMap<String , String>();
        params.put("name",student_name);
        params.put("password",student_password);
        params.put("telephone",student_tel);
        params.put("email",student_email);
        params.put("annee",student_annee);
        params.put("filliere",student_Filliere);
    JSONObject parameters = new JSONObject(params);
    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url,parameters,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int id_user = response.getInt("id");

                        Toast.makeText(UpdateAccount.this,"Profile is Updated",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateAccount.this,Myprofile.class);
                        startActivity(intent);

                    }catch (JSONException e){
                        Toast.makeText(UpdateAccount.this,"Something is missing !",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(UpdateAccount.this,"Check your connection !",Toast.LENGTH_SHORT).show();
        }
    }){
        @Override
        protected Map<String , String> getParams(){
            Map<String , String> params = new HashMap<String , String>();
            return params;
        }
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "Bearer " + student_token);
            return headers;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(UpdateAccount.this);
        requestQueue.add(jsonRequest);
}

    private void updateCompany(String company_id,final String company_token, String company_password, String company_name, String company_tel, String company_email, String company_type , String company_address) {
        String url = "http://192.168.68.1:8080/resources/companies/" + company_id ;
        Map<String , String> params = new HashMap<String , String>();
        params.put("name",company_name);
        params.put("password",company_password);
        params.put("telephone",company_tel);
        params.put("email",company_email);
        params.put("type",company_type);
        params.put("adresse",company_address);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url,parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id_user = response.getInt("id");

                            Toast.makeText(UpdateAccount.this,"Profile is Updated",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateAccount.this,Myprofile.class);
                            startActivity(intent);

                        }catch (JSONException e){
                            Toast.makeText(UpdateAccount.this,"Something is missing !",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateAccount.this,"Check your connection !",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String , String> getParams(){
                Map<String , String> params = new HashMap<String , String>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "Bearer " + company_token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateAccount.this);
        requestQueue.add(jsonRequest);
    }

}