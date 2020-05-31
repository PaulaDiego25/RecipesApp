package com.example.recipesbook.model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Recipe {
    public int Id;
    public String Title;
    public String Description;
    public double Time;
    public String Picture;
    public boolean IsPublic;
    public Date CreationDate;

    public ArrayList<Step> Steps;
    public Category Category;
    public ArrayList<UserRecipeRating> UserRecipesRating;
    public ArrayList<Comment> Comments;


    public Recipe(){

    }

    public Recipe(JSONObject data){


        try {
            int Id = data.getInt("Id");
            String Title = data.getString("Title");
            String Description = data.getString("Description");
            double Time = data.getDouble("Time");
            String Picture = data.getString("Picture");
            boolean IsPublic = data.getBoolean("IsPublic");
            Date creationDate = null;
            /*try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            creationDate = sdf.parse(data.getString("CreationDate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

            this.Id = Id;
            this.Title = Title;
            this.Description = Description;
            this.Time = Time;
            this.Picture = Picture;
            this.IsPublic = IsPublic;
            //this.CreationDate = creationDate;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Recipe> GetRecipeList(String response){
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        try {
            JSONArray obj = new JSONArray(response);

            for (int i = 0; i < obj.length(); i++) {
                Recipe r = new Recipe(obj.getJSONObject(i));
                recipeList.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipeList;
    }


}
