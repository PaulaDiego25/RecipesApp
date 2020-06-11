package com.example.recipesbook.ui.stepsList;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.Step;
import com.example.recipesbook.service.RecipesService;

import java.util.ArrayList;

public class StepListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Step>> stepList;


    public StepListViewModel() {
        stepList = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Step>> getText() {
        return stepList;
    }

    public void getStepList(Context context, int recipeId) {
            RecipesService.GetStepsByRecipe(context, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    stepList.setValue(Step.ParseStepList(response));

                }
            },recipeId);
    }
}