package com.example.recipesbook.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.toolbox.StringRequest;
import com.example.recipesbook.R;
import com.example.recipesbook.model.Category;
import com.example.recipesbook.model.RecipeType;
import com.example.recipesbook.service.RecipesService;
import com.example.recipesbook.ui.step.StepFragment;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipeViewModel =
                ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getTypes(this.getContext());
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);


        recipeViewModel.getText().observe(this, new Observer<ArrayList<RecipeType>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RecipeType> s) {

                Spinner types_spinner = (Spinner) getView().findViewById(R.id.recipe_type_spinner);
                types_spinner.setAdapter(new ArrayAdapter<RecipeType>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));

            }
        });


        Spinner types_spinner = (Spinner) root.findViewById(R.id.recipe_type_spinner);
        types_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                recipeViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Category> s) {

                        Spinner category_spinner = (Spinner) getView().findViewById(R.id.recipe_category_spinner);
                        category_spinner.setAdapter(new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));
                    }
                });

                RecipeType type = (RecipeType) parentView.getItemAtPosition(position);
                recipeViewModel.getCategories(getContext(),type.Id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        Button button = root.findViewById(R.id.recipe_next_button);
        button.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                StepFragment stepFragment = new StepFragment(23);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, stepFragment);
                ft.commit();
            }
        });

        return root;
    }




}