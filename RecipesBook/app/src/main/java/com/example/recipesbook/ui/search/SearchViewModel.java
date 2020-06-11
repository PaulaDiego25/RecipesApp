package com.example.recipesbook.ui.search;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.example.recipesbook.model.Category;
import com.example.recipesbook.model.Ingredient;
import com.example.recipesbook.model.RecipeType;
import com.example.recipesbook.service.RecipesService;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {


    private MutableLiveData<ArrayList<Ingredient>> ingredients;
    private MutableLiveData<ArrayList<RecipeType>> recipeTypes;
    private MutableLiveData<ArrayList<Category>> recipeCategories;


    public SearchViewModel() {
        ingredients = new MutableLiveData<>();
        recipeTypes = new MutableLiveData<>();
        recipeCategories = new MutableLiveData<>();
    }

    public void getIngredients(Context context) {
        RecipesService.GetIngredients(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                ArrayList<Ingredient> temp = Ingredient.ParseIngredientList(response);
                //TODO añadir valor a los spinner de vacio
                //temp.add(new Ingredient());
                ingredients.setValue(Ingredient.ParseIngredientList(response));

            }

        });
    }
    public void getTypes(Context context) {
        RecipesService.GetAllRecipeTypes(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                recipeTypes.setValue(RecipeType.ParseRecipeTypeList(response));

            }

        });
    }

    public void getCategories(Context context, int id) {
        RecipesService.GetCategoriesByRecipeType(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                recipeCategories.setValue(Category.ParseCategoryList(response));
            }

        }, id);
    }

    public LiveData<ArrayList<Ingredient>> getIngredientsLiveData() {
        return ingredients;
    }
    public LiveData<ArrayList<RecipeType>> getTypesLiveData() {
        return recipeTypes;
    }
    public LiveData<ArrayList<Category>> getCategoriesLiveData() {
        return recipeCategories;
    }

}