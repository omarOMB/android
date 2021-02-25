package com.example.forum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {
    public static class ForumViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout containerView;
        public CardView card1;
        public RelativeLayout relativeLayout_image;
        public ImageView image;
        public CardView card2;
        public RelativeLayout relativeLayout_infos;
        public TextView title;
        public Button more_infos;
        public Button profile;

        ForumViewHolder(View view){
            super(view);
            containerView = view.findViewById(R.id.linearLayoutxxx);
            card1 = view.findViewById(R.id.firstcardxxx);
            relativeLayout_image = view.findViewById(R.id.imagelayoutxxx);
            image = view.findViewById(R.id.imageView4xxx);
            card2 = view.findViewById(R.id.secondcardxxx);
            relativeLayout_infos = view.findViewById(R.id.infoslayoutxxx);
            title = view.findViewById(R.id.textView4xxx);
            more_infos = view.findViewById(R.id.button4xxx);
            profile = view.findViewById(R.id.button5xxx);
            SessionManager sessionManager = new SessionManager(view.getContext());
            HashMap<String , String> userDetails = sessionManager.getUserDetailsFromSession();
            final String current_role = userDetails.get(SessionManager.KEY_ROLE);
            if(current_role.equals("ROLE_ETUDIANT")) {
                more_infos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Companies current = (Companies) more_infos.getTag();
                        Intent intent = new Intent(v.getContext(), Userprofile.class);
                        intent.putExtra("username", current.getName());
                        String id = String.valueOf(current.getId());
                        intent.putExtra("id", id);
                        intent.putExtra("email", current.getEmail());
                        intent.putExtra("address", current.getAdresse());
                        intent.putExtra("phone", current.getPhone());
                        intent.putExtra("type", current.getType());
                        //for the photo
                        JSONObject photos = current.getPhoto();
                        try {
                            String stringphototdata = photos.getString("data");
                            intent.putExtra("photo", stringphototdata);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        v.getContext().startActivity(intent);
                    }
                });
            }else{
                more_infos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Students current = (Students) more_infos.getTag();
                        Intent intent = new Intent(v.getContext(), UserprofileStudent.class);
                        intent.putExtra("username", current.getName());
                        String id = String.valueOf(current.getId());
                        intent.putExtra("id", id);
                        intent.putExtra("email", current.getEmail());
                        intent.putExtra("filliere", current.getFilliere());
                        intent.putExtra("phone", current.getPhone());
                        intent.putExtra("annee", current.getAnnee());
                        //for the photo
                        JSONObject photos = current.getPhoto();
                        try {
                            String stringphototdata = photos.getString("data");
                            intent.putExtra("photo", stringphototdata);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }

    }

    private List<User> user = new ArrayList<>();
    RequestQueue requestQueue;

    ForumAdapter(Context context){
        requestQueue = Volley.newRequestQueue(context);
        loadProfiles(context);
    }

    public void loadProfiles(final Context context){
        //GETTING DATA FROM SPRING
        SessionManager sessionManager = new SessionManager(context);
        HashMap<String , String> userDetails = sessionManager.getUserDetailsFromSession();
        final String current_token = userDetails.get(SessionManager.KEY_TOKEN);
        final String current_role = userDetails.get(SessionManager.KEY_ROLE);
        final String current_id = userDetails.get(SessionManager.KEY_ID);
        final String current_username = userDetails.get(SessionManager.KEY_USERNAME);
        final String current_email = userDetails.get(SessionManager.KEY_EMAIL);
        final String current_name = userDetails.get(SessionManager.KEY_NAME);
        final String current_password = userDetails.get(SessionManager.KEY_PASSWORD);
        if(current_role.equals("ROLE_ETUDIANT")) {
            String url = "http://192.168.68.1:8080/resources/companies";
            String url2 = "http://192.168.68.1:8080/resources/etudiants/" + current_id;

            // PROFILES LOADER
            JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject result = response.getJSONObject(i);
                                    user.add(new Companies(
                                            result.getInt("id"),
                                            result.getString("name"),
                                            result.getString("username"),
                                            result.getString("email"),
                                            result.getString("telephone"),
                                            result.getString("adresse"),
                                            result.getString("type"),
                                            result.getJSONObject("photo")
                                    ));

                                }


                            } catch (JSONException e) {
                                Toast.makeText(context, "Timout request problem !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG", error.toString());
                    Toast.makeText(context, "Connection problem !", Toast.LENGTH_SHORT).show();
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

            //SESSION INFORMATIONS
            JsonObjectRequest jsonRequest2 = new JsonObjectRequest(Request.Method.GET, url2,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response2) {
                            try {
                                String id_user = response2.getString("id");
                                String name_user = response2.getString("name");
                                String password_user = response2.getString("password");
                                String email_user = response2.getString("email");
                                String telephone_user = response2.getString("telephone");
                                String username_user = response2.getString("username");
                                String filliere_user = response2.getString("filliere");
                                String annee_user = response2.getString("annee");
                                JSONObject photo_user = response2.getJSONObject("photo");
                                String photo_data_user = photo_user.getString("data");

                                SessionManager sessionManager = new SessionManager(context);
                                sessionManager.createLoginSession(current_id,current_username,current_name,current_email,telephone_user,"something",current_token,current_role,"something",annee_user,filliere_user,current_password,photo_data_user);

                            } catch (JSONException e) {
                                Toast.makeText(context, "Timout request problem !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG","the session error " +  error.toString());
                    Toast.makeText(context, "Cant save personnal infos !", Toast.LENGTH_SHORT).show();
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
            requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonRequest);
            requestQueue.add(jsonRequest2);
        }else{

            // PROFILES LOADER && SESSION INFORMATIONS
            String url = "http://192.168.68.1:8080/resources/companies/" + current_id;
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String id_user = response.getString("id");
                                String name_user = response.getString("name");
                                String password_user = response.getString("password");
                                String email_user = response.getString("email");
                                String telephone_user = response.getString("telephone");
                                String username_user = response.getString("username");
                                String adresse_user = response.getString("adresse");
                                String type_user = response.getString("type");
                                JSONObject photo_user = response.getJSONObject("photo");
                                String photo_data_user = photo_user.getString("data");

                                JSONArray students = response.getJSONArray("etudiants");
                                String role_user = students.getString(0);
                                for (int i = 0; i < students.length(); i++) {
                                    JSONObject result = students.getJSONObject(i);
                                    user.add(new Students(
                                            result.getInt("id"),
                                            result.getString("name"),
                                            result.getString("username"),
                                            result.getString("email"),
                                            result.getString("telephone"),
                                            result.getString("filliere"),
                                            result.getString("annee"),
                                            result.getJSONObject("photo")
                                    ));
                                }

                                SessionManager sessionManager = new SessionManager(context);
                                sessionManager.createLoginSession(current_id,current_username,current_name,current_email,telephone_user,type_user,current_token,current_role,adresse_user,"something","something",current_password,photo_data_user);
                            } catch (JSONException e) {
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG", error.toString());
                    Toast.makeText(context, "Connection problem !", Toast.LENGTH_SHORT).show();
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

            requestQueue.add(jsonRequest);
        }
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        User current = user.get(position);
        holder.title.setText(current.getName());
        holder.more_infos.setTag(current);
        holder.profile.setText(current.getEmail());
        //for the photo
        JSONObject photos = current.getPhoto();
        try {
            String stringphototdata = photos.getString("data");
            byte[] decodedImage = Base64.decode(stringphototdata, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
            holder.image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 150, 150, false));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return user.size();
    }
}
