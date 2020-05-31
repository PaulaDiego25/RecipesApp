package com.example.recipesbook.model;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class Step {
    public int Id;
    public String Title;
    public String Explanation;
    public int Order;
    public String Picture;

    public ArrayList<StepIngredient> StepIngredients;
    public Recipe Recipe;

    public Step(JSONObject data){
        try {
            int Id = data.getInt("Id");
            String Title = data.getString("Title");
            String Explanation = data.getString("Explanation");
            int Order = data.getInt("Order");
            String Picture = data.getString("Picture");

            this.Id = Id;
            this.Title = Title;
            this.Explanation = Explanation;
            this.Order = Order;
            this.Picture = Picture;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Step> ParseCategoryList(String response){
        ArrayList<Step> list = new ArrayList<>();

        try {
            JSONArray obj = new JSONArray(response);

            for (int i = 0; i < obj.length(); i++) {
                Step s = new Step(obj.getJSONObject(i));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
