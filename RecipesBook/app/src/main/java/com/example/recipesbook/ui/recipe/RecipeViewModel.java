package com.example.recipesbook.ui.recipe;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.RecipeType;
import com.example.recipesbook.service.RecipesService;

import java.util.ArrayList;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<RecipeType>> recipeTypes;


    public RecipeViewModel() {
        recipeTypes = new MutableLiveData<ArrayList<RecipeType>>();

    }

    public void getData(Context context) {
        RecipesService.GetAllRecipeTypes(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                recipeTypes.setValue(RecipeType.ParseRecipeTypeList(response));

            }

        });
    }

    public LiveData<ArrayList<RecipeType>> getText() {
        return recipeTypes;
    }
}