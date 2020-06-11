package com.example.recipesbook.ui.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.recipesbook.R;
import com.example.recipesbook.model.Step;

import java.util.ArrayList;

public class StepsAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Step> steps; //data source of the list adapter

    //public constructor
    public StepsAdapter(Context context, ArrayList<Step> items) {
        this.context = context;
        this.steps = items;
    }

    @Override
    public int getCount() {
        return steps.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return steps.get(position); //returns list item at the specified position
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
                    inflate(R.layout.list_steps, parent, false);
        }
        Step currentItem = (Step) getItem(position);
        TextView ingredientInfo = (TextView) convertView.findViewById(R.id.step_info);
        ingredientInfo.setText(currentItem.toString());

        return convertView;
    }

    public void updateReceiptsList(ArrayList<Step> newlist) {
        //stepIngredients.removeAll(stepIngredients);
        //stepIngredients = new ArrayList<StepIngredient>();
        //stepIngredients.addAll(newlist);
        this.steps = newlist;
        this.notifyDataSetChanged();
    }
}
