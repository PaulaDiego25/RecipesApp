package com.example.recipesbook.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Comment {
    public int Id;
    public String Content;
    public Date Date;

    public User User;
    public Recipe Recipe;
}
