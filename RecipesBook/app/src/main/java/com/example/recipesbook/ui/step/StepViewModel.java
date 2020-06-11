package com.example.recipesbook.ui.step;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.example.recipesbook.model.Category;
import com.example.recipesbook.model.Ingredient;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.RecipeType;
import com.example.recipesbook.model.Step;
import com.example.recipesbook.model.StepIngredient;
import com.example.recipesbook.service.RecipesService;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class StepViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Ingredient>> ingredients;
    private MutableLiveData<ArrayList<StepIngredient>> stepIngredients;
    private MutableLiveData<ArrayList<StepIngredient>> stepWithIngredients;
    private int counter;

    //TODO
    private int newOrder;


    public StepViewModel() {
        ingredients = new MutableLiveData<>();
        stepIngredients = new MutableLiveData<>();
        stepWithIngredients = new MutableLiveData<>();

    }

    public void getData(Context context) {
        RecipesService.GetIngredients(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                ingredients.setValue(Ingredient.ParseIngredientList(response));

            }

        });
    }

    public void getIngredientsByStep(Context context, int stepId) {
        RecipesService.GetIngredientsByStep(context, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stepIngredients.setValue(StepIngredient.ParseStepIngredientList(response));
            }

        }, stepId);
    }

    public void getAllIngredientByStepIngredient(Context context, final ArrayList<StepIngredient> s) {

        counter =0;
        for(int i = 0; i< s.size(); i++){
            RecipesService.GetIngredientByStepIngredient(context, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject json = new JSONObject(response);
                        //ingredient.setValue(new Ingredient(json));
                        s.get(counter).Ingredient = new Ingredient(json);
                        counter++;
                        if(counter == s.size()){
                            stepWithIngredients.setValue(s);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, s.get(i).Id);
        }
    }


    public LiveData<ArrayList<Ingredient>> getIngredients() {
        return ingredients;
    }
    public LiveData<ArrayList<StepIngredient>> getStepIngredients() {
        return stepIngredients;
    }
    public LiveData<ArrayList<StepIngredient>> getAllIngredientsLiveData() {
        return stepWithIngredients;
    }



    public void saveStep(final Context context,final Response.Listener<String> callback, final Step step, final int recipeId) {

        if(step.Id > 0){
            //Update

        }else{
            //Create
            RecipesService.GetNewStepOrder(context, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    step.Order = Integer.parseInt(response);
                    RecipesService.SaveStep(context, callback, step, recipeId);
                }
            },recipeId);
        }

    }


    public void saveStepIngredient(Context context,Response.Listener<String> callback, StepIngredient stepIngredient, int stepId) {

        if(stepIngredient.Id > 0){
            //Update

        }else{
            //Create
            RecipesService.SaveStepIngredient(context, callback, stepIngredient, stepId);
        }


    }
}