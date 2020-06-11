package com.example.recipesbook.ui.stepsList;

import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.recipesbook.MainActivity;
import com.example.recipesbook.R;
import com.example.recipesbook.model.Recipe;
import com.example.recipesbook.model.Step;
import com.example.recipesbook.model.StepIngredient;
import com.example.recipesbook.ui.ListView.IngredientsAdapter;
import com.example.recipesbook.ui.ListView.StepsAdapter;
import com.example.recipesbook.ui.home.HomeFragment;
import com.example.recipesbook.ui.recipe.RecipeFragment;
import com.example.recipesbook.ui.step.StepFragment;

import java.util.ArrayList;

public class StepListFragment extends Fragment {

    private StepListViewModel stepListViewModel;
    private int recipeId;
    private ArrayList<Step> stepArrayList;

    public StepListFragment(int recipeId){
        this.recipeId = recipeId;
    }

    public StepListFragment(){
        this.recipeId = 1;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();

        stepListViewModel =
                ViewModelProviders.of(this).get(StepListViewModel.class);
        stepListViewModel.getStepList(this.getContext(),recipeId);

        View root = inflater.inflate(R.layout.fragment_steps_list, container, false);
        stepListViewModel.getText().observe(this, new Observer<ArrayList<Step>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Step> steps) {
                stepArrayList = steps;
                ListView stepsList = getView().findViewById(R.id.list_steps);
                stepsList.setAdapter(new StepsAdapter(getContext(), steps));
            }
        });

        Button btn_finish = root.findViewById(R.id.stepList_next_button);
        btn_finish.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final ListView stepsList = getView().findViewById(R.id.list_steps);
        stepsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Step step = stepArrayList.get(i);

                Fragment fragment = new StepFragment(step,recipeId);
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
                .setActionBarTitle(R.string.steps_list);
        ((MainActivity) getActivity()).setOptionsMenu("step",recipeId);

    }


}