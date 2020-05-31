package com.example.recipesbook.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Category {
    public int Id;
    public String Title;
    public String Description;

    public ArrayList<Recipe> Recipes;
    public RecipeType RecipeType;

    public Category(JSONObject data){
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

    public static ArrayList<Category> ParseCategoryList(String response){
        ArrayList<Category> list = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(response);

            for (int i = 0; i < obj.length(); i++) {
                Category c = new Category(obj.getJSONObject(i));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
