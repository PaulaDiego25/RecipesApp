package com.example.recipesbook.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recipesbook.R;
import com.example.recipesbook.model.Category;
import com.example.recipesbook.model.Ingredient;
import com.example.recipesbook.model.RecipeType;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        searchViewModel.getTypes(this.getContext());
        searchViewModel.getIngredients(this.getContext());

        searchViewModel.getTypesLiveData().observe(this, new Observer<ArrayList<RecipeType>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RecipeType> s) {

                Spinner types_spinner = (Spinner) getView().findViewById(R.id.search_type_spinner);
                types_spinner.setAdapter(new ArrayAdapter<RecipeType>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));

            }
        });


        Spinner types_spinner = (Spinner) root.findViewById(R.id.search_type_spinner);
        types_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                searchViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Category> s) {
                        Spinner category_spinner = (Spinner) getView().findViewById(R.id.search_category_spinner);
                        category_spinner.setAdapter(new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));
                    }
                });

                RecipeType type = (RecipeType) parentView.getItemAtPosition(position);
                searchViewModel.getCategories(getContext(),type.Id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        searchViewModel.getIngredientsLiveData().observe(this, new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Ingredient> s) {
                Spinner ingredients_spinner = (Spinner) getView().findViewById(R.id.search_ingredient_spinner);
                ingredients_spinner.setAdapter(new ArrayAdapter<Ingredient>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));
            }
        });


        return root;
    }
}