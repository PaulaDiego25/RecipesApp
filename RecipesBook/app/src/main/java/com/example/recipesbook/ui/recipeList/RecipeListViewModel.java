package com.example.recipesbook.ui.recipeList;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipesbook.model.Recipe;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.example.recipesbook.model.Recipe.*;

public class RecipeListViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();


    public RecipeListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is recipe List fragment");
    }

    public LiveData<String> getText() {
        return mText;
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
                        recipeList = Recipe.getRecipeList(response);
                        mText.setValue(recipeList.get(0).Title);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mText.setValue("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }
}