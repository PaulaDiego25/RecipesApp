package com.example.recipesbook.ui.versionList;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.Step;
import com.example.recipesbook.service.RecipesService;

import java.util.ArrayList;

public class VersionListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Recipe>> versionsList;


    public VersionListViewModel() {
        versionsList = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Recipe>> getVersionsLiveData() {
        return versionsList;
    }

    public void getVersionsList(Context context, int recipeId) {
            RecipesService.GetRecipeVersions(context, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    versionsList.setValue(Recipe.ParseRecipeList(response));

                }
            },recipeId);
    }
}