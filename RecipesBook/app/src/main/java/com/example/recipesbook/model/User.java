package com.example.recipesbook.model;

import java.util.ArrayList;

public class User {
    public int Id;
    public String Name;
    public String Password;
    public String Email;
    public String Alias;

    public ArrayList<UserRecipeRating> UserRecipes;
    public Role Role;
    public ArrayList<Comment> Comments;
}
