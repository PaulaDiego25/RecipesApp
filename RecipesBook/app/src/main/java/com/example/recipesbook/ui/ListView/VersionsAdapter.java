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

public class VersionsAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Recipe> versions; //data source of the list adapter

    //public constructor
    public VersionsAdapter(Context context, ArrayList<Recipe> items) {
        this.context = context;
        this.versions = items;
    }

    @Override
    public int getCount() {
        return versions.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return versions.get(position); //returns list item at the specified position
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
                    inflate(R.layout.list_versions, parent, false);
        }
        Recipe currentItem = (Recipe) getItem(position);
        TextView ingredientInfo = (TextView) convertView.findViewById(R.id.version_info);
        ingredientInfo.setText(currentItem.toString());

        return convertView;
    }

    public void updateReceiptsList(ArrayList<Recipe> newlist) {
        //stepIngredients.removeAll(stepIngredients);
        //stepIngredients = new ArrayList<StepIngredient>();
        //stepIngredients.addAll(newlist);
        this.versions = newlist;
        this.notifyDataSetChanged();
    }
}
