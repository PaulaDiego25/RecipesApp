package com.example.recipesbook.model;
import java.util.ArrayList;
public class Step {
    public int Id;
    public String Title;
    public String Explanation;
    public int Order;
    public String Picture;

    public ArrayList<StepIngredient> StepIngredients;
    public Recipe Recipe;
}
