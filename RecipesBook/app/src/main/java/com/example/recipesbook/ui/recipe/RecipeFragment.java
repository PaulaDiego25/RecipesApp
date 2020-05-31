package com.example.recipesbook.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recipesbook.R;
import com.example.recipesbook.model.RecipeType;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipeViewModel =
                ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getData(this.getContext());
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);


        recipeViewModel.getText().observe(this, new Observer<ArrayList<RecipeType>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RecipeType> s) {
                //textView.setText(s);
                String[] lenguajes = {"Seleccione", "Ruby", "Java", ".NET", "Python", "PHP", "JavaScript", "GO"};

                Spinner Slenguajes = (Spinner) getView().findViewById(R.id.recipe_type_spinner);

                Slenguajes.setAdapter(new ArrayAdapter<RecipeType>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));
            }
        });


        return root;
    }


}