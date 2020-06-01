package com.example.recipesbook.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipesbook.model.Recipe;

import org.json.JSONObject;
import org.json.JSONStringer;

public class RecipesService {
    //private static String ApiUrl = "http://192.168.1.146:57806/api/";
    private static String ApiUrl = "http://192.168.8.200:57806/api/";

    private static String RecipesUrl = "recipes";
    private static String RatingsUrl = "ratings";
    private static String UsersUrl = "users";
    private static String RolesUrl = "roles";
    private static String CategoriesUrl = "categories";
    private static String StepsUrl = "steps";
    private static String UserRecipesUrl = "userRecipes";
    private static String IngredientsUrl = "ingredients";
    private static String RecipeTypesUrl = "recipeTypes";
    private static String StepIngredientsUrl = "stepIngredients";

    // region Recipes
    public static void GetAllRecipes(Context context, Response.Listener<String> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipesUrl;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetAllRecipes", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void GetRecipe(Context context, Response.Listener<String> callback, int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipesUrl + "/" + id;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { 
                        Log.println(Log.ERROR, "GetRecipe", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void SaveRecipe(Context context, Response.Listener<JSONObject> callback, Recipe recipe) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipesUrl;

        // Request a string response from the provided URL.
        try {
            JSONObject payload = new JSONObject(new JSONStringer().value(recipe).toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, payload, callback,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.println(Log.ERROR, "SaveRecipe", error.toString());
                        }
                    });
            // Add the request to the RequestQueue.
            queue.add(request);
        } catch (Exception e) {
            Log.println(Log.ERROR, "Service", e.toString());
        }
    }

    public static void UpdateRecipe(Context context, Response.Listener<JSONObject> callback, Recipe recipe) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipesUrl + "/" + recipe.Id;

        // Request a string response from the provided URL.
        try {
            JSONObject payload = new JSONObject(new JSONStringer().value(recipe).toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, payload, callback,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.println(Log.ERROR, "UpdateRecipe", error.toString());
                        }
                    });
            // Add the request to the RequestQueue.
            queue.add(request);
        } catch (Exception e) {
            Log.println(Log.ERROR, "Service", e.toString());
        }
    }

    public static void DeleteRecipe(Context context, Response.Listener<String> callback, int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipesUrl + "/" + id;

        // Request a string response from the provided URL.
        try {
            StringRequest request = new StringRequest(Request.Method.DELETE, url, callback,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.println(Log.ERROR, "DeleteRecipe", error.toString());
                        }
                    });
            // Add the request to the RequestQueue.
            queue.add(request);
        } catch (Exception e) {
            Log.println(Log.ERROR, "Service", e.toString());
        }
    }
    // endregion

    // region Steps
    public static void GetAllSteps(Context context, Response.Listener<String> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + StepsUrl;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetAllSteps", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void GetStep(Context context, Response.Listener<String> callback, int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + StepsUrl + "/" + id;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetStep", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void SaveStep(Context context, Response.Listener<JSONObject> callback, Recipe recipe) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + StepsUrl;

        // Request a string response from the provided URL.
        try {
            JSONObject payload = new JSONObject(new JSONStringer().value(recipe).toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, payload, callback,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.println(Log.ERROR, "SaveStep", error.toString());
                        }
                    });
            // Add the request to the RequestQueue.
            queue.add(request);
        } catch (Exception e) {
            Log.println(Log.ERROR, "Service", e.toString());
        }
    }

    public static void UpdateStep(Context context, Response.Listener<JSONObject> callback, Recipe recipe) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + StepsUrl + "/" + recipe.Id;

        // Request a string response from the provided URL.
        try {
            JSONObject payload = new JSONObject(new JSONStringer().value(recipe).toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, payload, callback,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.println(Log.ERROR, "UpdateStep", error.toString());
                        }
                    });
            // Add the request to the RequestQueue.
            queue.add(request);
        } catch (Exception e) {
            Log.println(Log.ERROR, "Service", e.toString());
        }
    }

    public static void DeleteStep(Context context, Response.Listener<String> callback, int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + StepsUrl + "/" + id;

        // Request a string response from the provided URL.
        try {
            StringRequest request = new StringRequest(Request.Method.DELETE, url, callback,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.println(Log.ERROR, "DeleteStep", error.toString());
                        }
                    });
            // Add the request to the RequestQueue.
            queue.add(request);
        } catch (Exception e) {
            Log.println(Log.ERROR, "Service", e.toString());
        }
    }
    // endregion

    // region Categories
    public static void GetAllCategories(Context context, Response.Listener<String> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + CategoriesUrl;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetAllCategories", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void GetCategory(Context context, Response.Listener<String> callback, int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + CategoriesUrl + "/" + id;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetCategory", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }
    // endregion

    // region RecipeTypes
    public static void GetAllRecipeTypes(Context context, Response.Listener<String> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipeTypesUrl;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetAllRecipeTypes", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void GetRecipeType(Context context, Response.Listener<String> callback, int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipeTypesUrl + "/" + id;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetRecipeType", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }
    // endregion

    // region Ingredients
    public static void GetIngredients(Context context, Response.Listener<String> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + IngredientsUrl;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetIngredients", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void GetIngredients(Context context, Response.Listener<String> callback, int id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + IngredientsUrl + "/" + id;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetIngredients", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }
    // endregion
}