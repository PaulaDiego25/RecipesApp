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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipesbook.model.Category;
import com.example.recipesbook.model.Comment;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.Step;
import com.example.recipesbook.model.UserRecipeRating;
import com.example.recipesbook.service.RecipesService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private int i = 0;
    private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

    public HomeViewModel() {


        mText = new MutableLiveData<>();


    }

    public void getData(Context context) {
        RecipesService.GetAllRecipes(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                recipeList = Recipe.GetRecipeList(response);
                mText.setValue(recipeList.get(0).Title);
            }

        });
    }

    /*public void getData(Context context) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.8.200:57806/api/recipes";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

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
    }*/

    public LiveData<String> getText() {
        return mText;
    }
}