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
import com.example.recipesbook.model.Step;
import com.example.recipesbook.model.StepIngredient;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class RecipesService {
    //private static String ApiUrl = "http://192.168.1.146:57806/api/";
    private static String ApiUrl = "http://192.168.8.200:57806/api/";

    private static String RecipesUrl = "recipes";
    private static String MainRecipesUrl = "mainrecipes";
    private static String RecipeVersionsUrl = "RecipeVersions";
    private static String RatingsUrl = "ratings";
    private static String UsersUrl = "users";
    private static String RolesUrl = "roles";
    private static String CategoriesUrl = "categories";
    private static String CategoriesByRecipeTypeUrl = "CategoriesByRecipeType";
    private static String CategoryByRecipeUrl = "CategoryByRecipe";
    private static String StepsUrl = "steps";
    private static String StepsByRecipeUrl = "StepsByRecipe";
    private static String NewStepOrderUrl = "NewStepOrder";
    private static String UserRecipesUrl = "userRecipes";
    private static String IngredientsUrl = "ingredients";
    private static String RecipeTypesUrl = "recipeTypes";
    private static String RecipeTypeByCategoryUrl = "recipeTypeByCategory";
    private static String StepIngredientsUrl = "stepIngredients";
    private static String IngredientsByStepUrl = "IngredientsByStep";
    private static String IngredientByStepIngredientUrl = "IngredientByStepIngredient";


    // region Recipes
    public static void GetAllRecipes(Context context, Response.Listener<String> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + MainRecipesUrl;

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

    public static void GetRecipeVersions(Context context, Response.Listener<String> callback, int recipeId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipeVersionsUrl + "?recipeId=" + recipeId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetRecipeVersions", error.toString());
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

    public static void SaveRecipe(Context context, Response.Listener<String> callback, final Recipe recipe) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipesUrl;

        // Request a string response from the provided URL.
        try {

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,callback,
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("FKCategoryId", String.valueOf(recipe.Category.Id));
                    params.put("Title", recipe.Title);
                    params.put("Description", recipe.Description);
                    if(recipe.FatherRecipeId >0) {
                        params.put("FKFatherRecipeId", String.valueOf(recipe.FatherRecipeId));
                    }
                    params.put("Time", String.valueOf(recipe.Time));
                    params.put("isPublic", String.valueOf(recipe.IsPublic));
                    params.put("CreationDate", dateFormat.format(recipe.CreationDate));

                    return params;
                }
            };
            queue.add(postRequest);

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

    public static void GetStepsByRecipe(Context context, Response.Listener<String> callback, int recipeId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + StepsByRecipeUrl + "?id=" + recipeId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetStepsByRecipe", error.toString());
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

    public static void GetNewStepOrder(Context context, Response.Listener<String> callback, int recipeId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + NewStepOrderUrl + "?id=" + recipeId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetNewStepOrder", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void SaveStep(Context context, Response.Listener<String> callback, final Step step, final int recipeId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + StepsUrl;

        // Request a string response from the provided URL.
        try {

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,callback,
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("FKRecipeId", String.valueOf(recipeId));
                    params.put("Title", step.Title);
                    params.put("Explanation", step.Explanation);
                    params.put("Order", String.valueOf(step.Order));

                    return params;
                }
            };
            queue.add(postRequest);

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

    public static void GetCategoriesByRecipeType(Context context, Response.Listener<String> callback, int recipeTypeId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + CategoriesByRecipeTypeUrl + "?id=" + recipeTypeId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetCategoriesByRecipe", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void GetCategoryByRecipe(Context context, Response.Listener<String> callback, int recipeId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + CategoryByRecipeUrl + "?recipeId=" + recipeId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetCategoryByRecipe", error.toString());
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

    public static void GetRecipeTypeByCategory(Context context, Response.Listener<String> callback, int categoryId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + RecipeTypeByCategoryUrl + "?categoryId=" + categoryId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetRecipeTypeByCategory", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    // endregion

    // region StepIngredient
    public static void GetIngredientsByStep(Context context, Response.Listener<String> callback, int stepId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + IngredientsByStepUrl + "?stepId=" + stepId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "GetIngredientsByStep", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void SaveStepIngredient(Context context, Response.Listener<String> callback, final StepIngredient stepIngredient, final int stepId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + StepIngredientsUrl;

        // Request a string response from the provided URL.
        try {

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,callback,
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("FKStepId", String.valueOf(stepId));
                    params.put("Quantity", stepIngredient.Quantity);
                    params.put("FKIngredientId", String.valueOf(stepIngredient.Ingredient.Id));
                    return params;
                }
            };
            queue.add(postRequest);

        } catch (Exception e) {
            Log.println(Log.ERROR, "Service", e.toString());
        }
    }
    //endregion StepIngredient
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

    public static void GetIngredient(Context context, Response.Listener<String> callback, int id) {
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

    public static void GetIngredientByStepIngredient(Context context, Response.Listener<String> callback, int stepIngredientId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = ApiUrl + IngredientByStepIngredientUrl + "?stepIngredientId=" + stepIngredientId;

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url, callback,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ERROR, "IngredientBySIngredient", error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }
    // endregion
}