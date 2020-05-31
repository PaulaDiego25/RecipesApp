package com.example.recipesbook.model;

import java.util.Date;

public class Comment {
    public int Id;
    public String Content;
    public Date Date;

    public User User;
    public Recipe Recipe;
}
