package com.example.recipesbook.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Response;
import com.example.recipesbook.MainActivity;
import com.example.recipesbook.R;
import com.example.recipesbook.model.Category;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.RecipeType;
import com.example.recipesbook.ui.step.StepFragment;
import com.example.recipesbook.ui.stepsList.StepListFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;
    private Recipe recipe;
    private int recipeId =0;
    private ArrayList<RecipeType> recipeTypesArray;

    public RecipeFragment(){

    }

    public RecipeFragment(Recipe selectedRecipe){
        this.recipe = selectedRecipe;
    }

    public RecipeFragment(int recipeId){
        this.recipeId = recipeId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        recipeViewModel =
                ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getTypes(this.getContext());
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);


        recipeViewModel.getTextLiveData().observe(this, new Observer<ArrayList<RecipeType>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RecipeType> s) {
            recipeTypesArray = s;
            Spinner types_spinner = (Spinner) getView().findViewById(R.id.recipe_type_spinner);
            types_spinner.setAdapter(new ArrayAdapter<RecipeType>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));

            }
        });


        Spinner types_spinner = (Spinner) root.findViewById(R.id.recipe_type_spinner);
        types_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                recipeViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Category> s) {

                        Spinner category_spinner = (Spinner) getView().findViewById(R.id.recipe_category_spinner);
                        category_spinner.setAdapter(new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));
                        if(recipe!=null && recipe.Category != null){

                            for (int i = 0; i< (category_spinner.getAdapter()).getCount();i++){
                                if(recipe.Category.Id == ((Category)(category_spinner.getAdapter()).getItem(i)).Id){
                                    category_spinner.setSelection(i);
                                }
                            }
                            category_spinner.setEnabled(false);
                        }
                    }
                });

                RecipeType type = (RecipeType) parentView.getItemAtPosition(position);
                recipeViewModel.getCategories(getContext(),type.Id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });


        if(recipe==null){
            Button button = root.findViewById(R.id.search_button);
            button.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View v) {

                    //Read data
                    EditText title = getView().findViewById(R.id.recipe_title);
                    String recipeTitle = title.getText().toString();

                    EditText description = getView().findViewById(R.id.recipe_description);
                    String recipeDescription = description.getText().toString();

                    Spinner category_spinner = getView().findViewById(R.id.recipe_category_spinner);
                    Category recipeCategory = (Category) category_spinner.getItemAtPosition(category_spinner.getSelectedItemPosition());


                    EditText time = getView().findViewById(R.id.search_is_public);
                    Double recipeTime = 0.0;
                    if(time.getText().toString().length()>0){
                        recipeTime = Double.parseDouble(time.getText().toString());
                    }


                    Switch isPublic = getView().findViewById(R.id.recipe_public);
                    Boolean recipePublic = isPublic.isChecked();

                    Recipe recipe = new Recipe();
                    recipe.Title = recipeTitle;
                    recipe.Description = recipeDescription;
                    recipe.Category = recipeCategory;
                    recipe.Time = recipeTime;
                    recipe.IsPublic = recipePublic;
                    recipe.Picture = null;

                    if(recipeId != 0){
                        recipe.FatherRecipeId = recipeId;
                    }

                    recipeViewModel.saveRecipe(getContext(),new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                Recipe recipe = new Recipe(json);

                                Fragment fragment = new StepFragment(recipe.Id);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },recipe);

                }
            });
        }else{
            recipeViewModel.getRecipeCategory(getContext(),recipe.Id);
            recipeViewModel.getRecipeCategoryLiveData().observe(this, new Observer<Category>() {
                @Override
                public void onChanged(@Nullable Category c) {
                    recipe.Category = c;
                    recipeViewModel.getRecipeTypeLiveData().observe(getViewLifecycleOwner(), new Observer<RecipeType>() {
                        @Override
                        public void onChanged(@Nullable RecipeType t) {
                            recipe.Category.RecipeType = t;
                            Spinner types_spinner = (Spinner) getView().findViewById(R.id.recipe_type_spinner);
                            for (int i = 0; i< (types_spinner.getAdapter()).getCount();i++){
                                if(t.Id == ((RecipeType)(types_spinner.getAdapter()).getItem(i)).Id){
                                    types_spinner.setSelection(i);
                                }
                            }
                            types_spinner.setEnabled(false);
                        }
                    });
                    recipeViewModel.getRecipeType(getContext(),c.Id);


                }
            });
            Button button = root.findViewById(R.id.search_button);
            button.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Fragment fragment = new StepListFragment(recipe.Id);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }


        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

       if(recipe!=null){
            //Read data
            EditText title = getView().findViewById(R.id.recipe_title);
            title.setText(recipe.Title);
            title.setEnabled(false);
            EditText description = getView().findViewById(R.id.recipe_description);
            description.setText(recipe.Description);
            description.setEnabled(false);


           Spinner type_spinner = getView().findViewById(R.id.recipe_type_spinner);
           type_spinner.setEnabled(false);

            EditText time = getView().findViewById(R.id.search_is_public);
            time.setText(String.valueOf(recipe.Time));
            time.setEnabled(false);

            Switch isPublic = getView().findViewById(R.id.recipe_public);
            isPublic.setChecked(recipe.IsPublic);
            isPublic.setEnabled(false);


       }
        super.onViewCreated(view, savedInstanceState);
    }
    public void onResume(){
        super.onResume();
        if(recipe!= null){
            ((MainActivity) getActivity())
                    .setActionBarTitle(R.string.recipe);
            ((MainActivity) getActivity()).setOptionsMenu("Recipe",recipe.Id);
        }else if(recipeId>=0){
            ((MainActivity) getActivity())
                    .setActionBarTitle(R.string.new_version);
        }



    }
}