package com.example.recipesbook.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ingredient {
    public int Id;
    public String Title;

    public ArrayList<StepIngredient> StepIngredients;

    public Ingredient(JSONObject data){
        try {
            int Id = data.getInt("Id");
            String Title = data.getString("Title");

            this.Id = Id;
            this.Title = Title;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Ingredient> ParseIngredientList(String response){
        ArrayList<Ingredient> list = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(response);

            for (int i = 0; i < obj.length(); i++) {
                Ingredient c = new Ingredient(obj.getJSONObject(i));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
