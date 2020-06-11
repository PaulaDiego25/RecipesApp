package com.example.recipesbook.ui.versionList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
import com.example.recipesbook.ui.ListView.StepsAdapter;
import com.example.recipesbook.ui.ListView.VersionsAdapter;
import com.example.recipesbook.ui.home.HomeFragment;
import com.example.recipesbook.ui.recipe.RecipeFragment;
import com.example.recipesbook.ui.step.StepFragment;

import java.util.ArrayList;

public class VersionListFragment extends Fragment {

    private VersionListViewModel versionListViewModel;
    private int recipeId;
    private ArrayList<Recipe> versionArrayList;

    public VersionListFragment(int recipeId){
        this.recipeId = recipeId;
    }

    public VersionListFragment(){
        this.recipeId = 1;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();

        versionListViewModel =
                ViewModelProviders.of(this).get(VersionListViewModel.class);
        versionListViewModel.getVersionsList(this.getContext(),recipeId);

        View root = inflater.inflate(R.layout.fragment_versions_list, container, false);
        versionListViewModel.getVersionsLiveData().observe(this, new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Recipe> versions) {
                versionArrayList = versions;
                if(versions.size()>0){
                    ListView versionsList = getView().findViewById(R.id.list_versions);
                    versionsList.setAdapter(new VersionsAdapter(getContext(), versions));
                }

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

        final ListView versionsList = getView().findViewById(R.id.list_versions);
        versionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Recipe recipe = versionArrayList.get(i);

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
                .setActionBarTitle(R.string.versions_list);
        ((MainActivity) getActivity()).setOptionsMenu("version",recipeId);

    }


}