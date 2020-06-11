package com.example.recipesbook.ui.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.recipesbook.R;
import com.example.recipesbook.model.StepIngredient;

import java.util.ArrayList;

public class IngredientsAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<StepIngredient> stepIngredients; //data source of the list adapter

    //public constructor
    public IngredientsAdapter(Context context, ArrayList<StepIngredient> items) {
        this.context = context;
        this.stepIngredients = items;
    }

    @Override
    public int getCount() {
        return stepIngredients.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return stepIngredients.get(position); //returns list item at the specified position
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
                    inflate(R.layout.list_ingredients, parent, false);
        }

        // get current item to be displayed
        StepIngredient currentItem = (StepIngredient) getItem(position);

        // get the TextView for item name and item description
        TextView ingredientInfo = (TextView) convertView.findViewById(R.id.ingredient_info);

        //sets the text for item name and item description from the current item object
        ingredientInfo.setText(currentItem.toString());
        // returns the view for the current row
        return convertView;
    }

    public void updateReceiptsList(ArrayList<StepIngredient> newlist) {
        //stepIngredients.removeAll(stepIngredients);
        //stepIngredients = new ArrayList<StepIngredient>();
        //stepIngredients.addAll(newlist);
        this.stepIngredients = newlist;
        this.notifyDataSetChanged();
    }
}
