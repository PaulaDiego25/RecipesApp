package com.example.recipesbook.ui.step;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.recipesbook.model.Ingredient;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.Step;
import com.example.recipesbook.model.StepIngredient;
import com.example.recipesbook.service.RecipesService;
import com.example.recipesbook.ui.ListView.IngredientsAdapter;
import com.example.recipesbook.ui.stepsList.StepListFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StepFragment extends Fragment {

    private StepViewModel stepViewModel;
    private int recipeId;
    private ArrayList<StepIngredient> sIngredients;
    private int petitionCounter;
    private Step step;

    public StepFragment() {
        sIngredients = new ArrayList<StepIngredient>();
    }

    public StepFragment(int recipeId) {
        this.recipeId = recipeId;
        sIngredients = new ArrayList<StepIngredient>();
    }

    public StepFragment(Step step, int recipeId) {
        sIngredients = new ArrayList<StepIngredient>();
        this.step = step;
        this.recipeId = recipeId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        stepViewModel =
                ViewModelProviders.of(this).get(StepViewModel.class);
        stepViewModel.getData(this.getContext());
        View root = inflater.inflate(R.layout.fragment_step, container, false);

        if(step == null){
            stepViewModel.getIngredients().observe(this, new Observer<ArrayList<Ingredient>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Ingredient> s) {
                    Spinner ingredients_spinner = (Spinner) getView().findViewById(R.id.ingredient_spinner);
                    ingredients_spinner.setAdapter(new ArrayAdapter<Ingredient>(getContext(), android.R.layout.simple_spinner_dropdown_item, s));
                }
            });

            Button addButton = root.findViewById(R.id.ingredient_add_button);
            addButton.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View v) {

                    //Read recipeId
                    Spinner ingredient_spinner = getView().findViewById(R.id.ingredient_spinner);
                    Ingredient stepIngredient = (Ingredient) ingredient_spinner.getItemAtPosition(ingredient_spinner.getSelectedItemPosition());

                    EditText quantity = getView().findViewById(R.id.ingredient_quantity);
                    String stepQuantity = quantity.getText().toString();
                    //local save
                    StepIngredient sIngredient = new StepIngredient();
                    sIngredient.Ingredient = stepIngredient;
                    sIngredient.Quantity = stepQuantity;
                    sIngredients.add(sIngredient);

                    //clear field
                    quantity.getText().clear();
                    ingredient_spinner.setSelection(0);

                    ListView listIngredients = getView().findViewById(R.id.ingredients_list);
                    listIngredients.setAdapter(new IngredientsAdapter(getContext(), sIngredients));

                    final float scale = getActivity().getResources().getDisplayMetrics().density;
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round((listIngredients.getCount() * 30) * scale));
                    listIngredients.setLayoutParams(p);
                }
            });


            Button button = root.findViewById(R.id.step_next_button);
            button.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View v) {

                    //Read recipeId
                    EditText title = getView().findViewById(R.id.step_title);
                    String stepTitle = title.getText().toString();

                    EditText description = getView().findViewById(R.id.step_description);
                    String stepDescription = description.getText().toString();

                    Step step = new Step();
                    step.Title = stepTitle;
                    step.Explanation = stepDescription;
                    step.StepIngredients = sIngredients;

                    stepViewModel.saveStep(getContext(),new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                final Step step = new Step(json);
                                petitionCounter = sIngredients.size();
                                for(int e =0; e < sIngredients.size(); e++){
                                    petitionCounter--;
                                    stepViewModel.saveStepIngredient(getContext(),new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            if(petitionCounter == 0){
                                                Fragment fragment = new StepListFragment(recipeId);
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();

                                            }

                                        }

                                    },sIngredients.get(e), step.Id);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },step, recipeId);

                }
            });
        }else{
            stepViewModel.getIngredientsByStep(getContext(),step.Id);
            stepViewModel.getStepIngredients().observe(this, new Observer<ArrayList<StepIngredient>>() {
                @Override
                public void onChanged(@Nullable ArrayList<StepIngredient> s) {
                    stepViewModel.getAllIngredientByStepIngredient(getContext(),s);

                    stepViewModel.getAllIngredientsLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<StepIngredient>>() {
                        @Override
                        public void onChanged(ArrayList<StepIngredient> stepIngredients) {
                            sIngredients = stepIngredients;
                            if(sIngredients.size()>0){
                                ListView listIngredients = getView().findViewById(R.id.ingredients_list);
                                listIngredients.setAdapter(new IngredientsAdapter(getContext(), stepIngredients));
                                final float scale = getActivity().getResources().getDisplayMetrics().density;
                                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.round((listIngredients.getCount() * 30) * scale));
                                listIngredients.setLayoutParams(p);
                            }


                        }
                    });



                }
            });
            Button button = root.findViewById(R.id.step_next_button);
            button.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Fragment fragment = new StepListFragment(recipeId);
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
        if(step == null){
            ListView listIngredients = getView().findViewById(R.id.ingredients_list);
            listIngredients.setAdapter(new IngredientsAdapter(getContext(), sIngredients));
        }else{

            LinearLayout add_ingredient = (LinearLayout) getView().findViewById(R.id.add_ingredient_layout);
            ViewGroup.LayoutParams params = add_ingredient.getLayoutParams();
            params.height =0;
            add_ingredient.setLayoutParams(params);
            add_ingredient.setVisibility(View.INVISIBLE);

            //Read data
            EditText title = getView().findViewById(R.id.step_title);
            title.setText(step.Title);
            title.setEnabled(false);
            EditText explanation = getView().findViewById(R.id.step_description);
            explanation.setText(step.Explanation);
            explanation.setEnabled(false);

        }

    }

    public void onResume(){
        super.onResume();
        if(step==null){
            ((MainActivity) getActivity())
                    .setActionBarTitle(R.string.new_step);

        }else{
            ((MainActivity) getActivity())
                    .setActionBarTitle(R.string.step);
        }
    }




}
