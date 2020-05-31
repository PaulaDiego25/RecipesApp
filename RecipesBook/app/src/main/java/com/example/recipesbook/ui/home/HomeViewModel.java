package com.example.recipesbook.ui.home;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipesbook.model.Category;
import com.example.recipesbook.model.Comment;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.Step;
import com.example.recipesbook.model.UserRecipeRating;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private int i = 0;

    public HomeViewModel() {


        mText = new MutableLiveData<>();


    }

    public void getData(Context context) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.8.200:57806/api/recipes";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mText.setValue("Response is: " + response);

                        try {
                            JSONArray obj = new JSONArray(response);

                            for (int i = 0; i < obj.length(); i++) {
                                int Id = obj.getJSONObject(i).getInt("Id");
                                String Title = obj.getJSONObject(i).getString("Title");
                                String Description = obj.getJSONObject(i).getString("Description");
                                double Time = obj.getJSONObject(i).getDouble("Time");
                                String Picture = obj.getJSONObject(i).getString("Picture");
                                boolean IsPublic = obj.getJSONObject(i).getBoolean("IsPublic");

                                Recipe r = new Recipe();
                                r.Id = Id;
                                r.Title = Title;
                                r.Description = Description;
                                r.Time = Time;
                                r.Picture = Picture;
                                r.IsPublic = IsPublic;

                                mText.setValue(r.toString());
                            }

                        } catch (Exception e) {
                            mText.setValue("Failed to parse");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mText.setValue("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        // Do something after 5s = 5000ms
        //mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}