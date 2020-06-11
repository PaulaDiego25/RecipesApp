package com.example.recipesbook.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StepIngredient {
    public int Id;
    public String Quantity;

    public Ingredient  Ingredient;
    public Step Step;

    public StepIngredient(){

    }

    public StepIngredient(JSONObject data){
        try {
            int Id = data.getInt("Id");
            String Quantity = data.getString("Quantity");

            this.Id = Id;
            this.Quantity = Quantity;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<StepIngredient> ParseStepIngredientList(String response){
        ArrayList<StepIngredient> list = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(response);

            for (int i = 0; i < obj.length(); i++) {
                StepIngredient c = new StepIngredient(obj.getJSONObject(i));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String toString() {
        if(Ingredient != null){
            return Quantity + " de " + Ingredient.Title;
        }else{
            return Quantity;

        }
        //return Quantity + " de " ;

    }
}
