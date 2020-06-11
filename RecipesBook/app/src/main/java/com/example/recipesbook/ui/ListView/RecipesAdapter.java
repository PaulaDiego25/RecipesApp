package com.example.recipesbook.ui.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recipesbook.R;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.Step;

import java.util.ArrayList;

public class RecipesAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Recipe> recipes; //data source of the list adapter

    //public constructor
    public RecipesAdapter(Context context, ArrayList<Recipe> items) {
        this.context = context;
        this.recipes = items;
    }

    @Override
    public int getCount() {
        return recipes.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_recipes, parent, false);
        }
        Recipe currentItem = (Recipe) getItem(position);
        TextView recipeTitle = (TextView) convertView.findViewById(R.id.recipeList_recipe_title);
        recipeTitle.setText(currentItem.Title);

        TextView recipeTime = (TextView) convertView.findViewById(R.id.recipeList_recipe_time);
        recipeTime.setText(currentItem.ParseRecipeTime());

        return convertView;
    }

    public void updateReceiptsList(ArrayList<Recipe> newlist) {
        //stepIngredients.removeAll(stepIngredients);
        //stepIngredients = new ArrayList<StepIngredient>();s
        //stepIngredients.addAll(newlist);
        this.recipes = newlist;
        this.notifyDataSetChanged();
    }


}
