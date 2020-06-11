package com.example.recipesbook.ui.favouriteRecipes;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.service.RecipesService;

import java.util.ArrayList;

public class FavouriteRecipesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Recipe>> recipeList;


    public FavouriteRecipesViewModel() {
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