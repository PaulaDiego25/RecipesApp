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

    private MutableLiveData<ArrayList<Recipe>> recipeList;


    public HomeViewModel() {
        recipeList = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Recipe>> getText() {
        return recipeList;
    }

    public void getRecipes(Context context) {
        RecipesService.GetAllRecipes(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                recipeList.setValue(Recipe.ParseRecipeList(response));
            }
        });
    }
}