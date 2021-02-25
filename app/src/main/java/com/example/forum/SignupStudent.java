package com.example.forum;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;

public class SignupStudent extends AppCompatActivity {

    private ImageView profileimage;
    private TextView profileusername;
    private TextView profilepassword;
    private TextView profilename;
    private TextView profiletelephone;
    private TextView profileemail;
    private TextView profileannee;
    private TextView profilefilliere;
    private Button uploadimage;

    private static final int pick_image = 1;
    Bitmap bitmap;
    String encodeImageString;
    Uri imageUri;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_student);

        profileimage = findViewById(R.id.profileimage);
        profileusername = findViewById(R.id.user_input_username);
        profilepassword = findViewById(R.id.user_input_password);
        profilename = findViewById(R.id.user_input_name);
        profiletelephone = findViewById(R.id.user_input_tel);
        profileemail = findViewById(R.id.user_input_email);
        profileannee = findViewById(R.id.user_input_year);
        profilefilliere = findViewById(R.id.user_input_domain);
        uploadimage = findViewById(R.id.upload_button);
        uploadimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Select Picture"),pick_image);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == pick_image && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap= BitmapFactory.decodeStream(inputStream);
                Log.e("TAG", "coded image ---->" + inputStream);
                profileimage.setImageBitmap(bitmap);

                String coded_image = encodeBitmapImage(bitmap);
                uploaddatatodb(coded_image);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String encodeBitmapImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        //CODING IMAGE
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
        /*
        //DECODING IMAGE
        byte[] decodedImage = Base64.decode(encodeImageString, Base64.DEFAULT);
        //CODING IMAGE AGAIN
        String encodeImageString2 = Base64.encodeToString(decodedImage, Base64.DEFAULT);
         */
        return  encodeImageString;
    }

    private void uploaddatatodb(String codedimagex)
    {/*
        String url="http://192.168.68.1:8080/api/auth/upload";
        final String finalcodedimagex = codedimagex;
        Map<String , String> params = new HashMap<String , String>();
        params.put("data",finalcodedimagex);
        params.put("name","apple.jpg");
        params.put("type","image/*");
        JSONObject file = new JSONObject(params);
        Log.e("TAG", "file json : " + String.valueOf(file));
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url,file,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject  photo = response.getJSONObject("photo");
                            saveprofile(photo);


                        }catch (JSONException e){
                            Toast.makeText(SignupStudent.this,"Picture can't be uploaded change picture please !",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupStudent.this,"Multipart file prob !",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String , String> getParams(){
                Map<String , String> params = new HashMap<String , String>();
                params.put("data",finalcodedimagex);
                params.put("name","apple.jpg");
                params.put("type","image/*");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "multipart/form-data;boundary=*****");
                return headers;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonRequest);
*/
    }

    private void saveprofile(JSONObject photo_infos){
        String url2="http://192.168.68.1:8080/api/auth/signupStudent";
        final  String username_setting = this.profileusername.getText().toString().trim();
        final  String password_setting = this.profilepassword.getText().toString().trim();
        final  String name_setting = this.profilename.getText().toString().trim();
        final  String telephone_setting = this.profiletelephone.getText().toString().trim();
        final  String email_setting = this.profileemail.getText().toString().trim();
        final  String annee_setting = this.profileannee.getText().toString().trim();
        final  String filliere_setting = this.profilefilliere.getText().toString().trim();
        //CREATE JSON OBJECT FOR STRINGS
        Map<String , String> params = new HashMap<String , String>();
        params.put("username",username_setting);
        params.put("password",password_setting);
        params.put("name",name_setting);
        params.put("telephone",telephone_setting);
        params.put("email",email_setting);
        params.put("annee",annee_setting);
        params.put("filliere",filliere_setting);
        JSONObject parameters = new JSONObject(params);
        //ADD THE PHOTO JSONobject
        try {
            parameters.put("photo", photo_infos);
        } catch (JSONException e) {
            Toast.makeText(SignupStudent.this,"Json object didn't get inside" ,Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest2 = new JsonObjectRequest(Request.Method.POST, url2,parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String  message  = response.getString("message");
                            Toast.makeText(SignupStudent.this,"Your profile has been created : " + message ,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupStudent.this,Login.class);
                            startActivity(intent);


                        }catch (JSONException e){
                            Toast.makeText(SignupStudent.this,"There is a problem with the data inserted !",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupStudent.this,"Error Check your connection !",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String , String> getParams(){
                Map<String , String> params = new HashMap<String , String>();
                params.put("password","test");
                params.put("username","test");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonRequest2);
    }


}
