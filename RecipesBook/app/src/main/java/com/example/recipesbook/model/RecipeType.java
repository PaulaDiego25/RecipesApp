package com.example.recipesbook.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeType {
    public int Id;
    public String Title;
    public String Description;
    public ArrayList<Category> Categories;

    public RecipeType(JSONObject data){
        try {
            int Id = data.getInt("Id");
            String Title = data.getString("Title");
            String Description = data.getString("Description");

            this.Id = Id;
            this.Title = Title;
            this.Description = Description;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<RecipeType> ParseRecipeTypeList(String response){
        ArrayList<RecipeType> list = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(response);

            for (int i = 0; i < obj.length(); i++) {
                RecipeType r = new RecipeType(obj.getJSONObject(i));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String toString() {
        return Title;
    }
}
