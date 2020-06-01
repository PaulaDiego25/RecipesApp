package com.example.recipesbook.ui.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recipesbook.R;
import com.example.recipesbook.model.RecipeType;

import java.util.ArrayList;

public class StepFragment extends Fragment {

    private StepViewModel stepViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        stepViewModel =
                ViewModelProviders.of(this).get(StepViewModel.class);
        stepViewModel.getData(this.getContext());
        View root = inflater.inflate(R.layout.fragment_step, container, false);


        /*recipeViewModel.getText().observe(this, new Observer<ArrayList<RecipeType>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RecipeType> s) {
                //textView.setText(s);
                //String[] lenguajes = {"Seleccione", "Ruby", "Java", ".NET", "Python", "PHP", "JavaScript", "GO"};

                Spinner types_spinner = (Spinner) getView().findViewById(R.id.recipe_type_spinner);

                types_spinner.setAdapter(new ArrayAdapter<RecipeType>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));
            }
        });*/


        return root;
    }


}