package com.example.recipesbook.ui.favouriteRecipes;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recipesbook.MainActivity;
import com.example.recipesbook.R;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.ui.ListView.RecipesAdapter;
import com.example.recipesbook.ui.recipe.RecipeFragment;
import com.example.recipesbook.ui.stepsList.StepListFragment;

import java.util.ArrayList;

public class FavouriteRecipesFragment extends Fragment {

    private FavouriteRecipesViewModel recipeListViewModel;
    private ArrayList<Recipe> recipeArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        recipeListViewModel =
                ViewModelProviders.of(this).get(FavouriteRecipesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        recipeListViewModel.getRecipes(this.getContext());

        recipeListViewModel.getText().observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Recipe> recipes) {
                recipeArrayList = recipes;
                ListView recipesList = getView().findViewById(R.id.list_recipes);
                recipesList.setAdapter(new RecipesAdapter(getContext(), recipes));
            }
        });

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListView recipesList = getView().findViewById(R.id.list_recipes);
        recipesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Recipe recipe = recipeArrayList.get(i);

                Fragment fragment = new RecipeFragment(recipe);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle(R.string.menu_favourite_recipes);

    }
}