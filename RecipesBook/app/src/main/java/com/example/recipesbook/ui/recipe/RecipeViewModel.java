package com.example.recipesbook.ui.recipe;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.example.recipesbook.R;
import com.example.recipesbook.model.Category;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.RecipeType;
import com.example.recipesbook.service.RecipesService;
import com.example.recipesbook.ui.step.StepFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<RecipeType>> recipeTypes;
    private MutableLiveData<ArrayList<Category>> recipeCategories;
    private MutableLiveData<Category> recipeCategory;
    private MutableLiveData<RecipeType> recipeType;



    public RecipeViewModel() {
        recipeTypes = new MutableLiveData<>();
        recipeCategories = new MutableLiveData<>();
        recipeCategory = new MutableLiveData<>();
        recipeType = new MutableLiveData<>();
    }

    public void getTypes(Context context) {
        RecipesService.GetAllRecipeTypes(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                recipeTypes.setValue(RecipeType.ParseRecipeTypeList(response));

            }

        });
    }
    public void getRecipeType(Context context, int categoryId) {
        RecipesService.GetRecipeTypeByCategory(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject json = new JSONObject(response);
                    recipeType.setValue(new RecipeType(json));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, categoryId);
    }

    public void getRecipeCategory(Context context, int recipeId) {
        RecipesService.GetCategoryByRecipe(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject json = new JSONObject(response);
                    recipeCategory.setValue(new Category(json));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, recipeId);
    }

    public void getCategories(Context context, int id) {
        RecipesService.GetCategoriesByRecipeType(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                recipeCategories.setValue(Category.ParseCategoryList(response));
            }

        }, id);
    }

    public void saveRecipe(Context context,Response.Listener<String> callback, Recipe recipe) {
        recipe.CreationDate = new Date();
        if(recipe.Id > 0){
            //Update

        }else{
            //Create
            RecipesService.SaveRecipe(context, callback, recipe);
        }


    }

    public LiveData<ArrayList<RecipeType>> getTextLiveData() {
        return recipeTypes;
    }
    public LiveData<ArrayList<Category>> getCategoriesLiveData() {
        return recipeCategories;
    }
    public LiveData<Category> getRecipeCategoryLiveData() {
        return recipeCategory;
    }
    public LiveData<RecipeType> getRecipeTypeLiveData() {
        return recipeType;
    }



}