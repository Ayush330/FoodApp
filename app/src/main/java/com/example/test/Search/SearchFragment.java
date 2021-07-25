package com.example.test.Search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test.Database.FoodDataClass;
import com.example.test.POJO.Meal;
import com.example.test.R;
import com.example.test.databinding.SearchBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragment extends Fragment
{
    private SearchBinding binding;
    private SearchViewModel model;
    public SearchFragment()
    {

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = SearchBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchViewModel model = new ViewModelProvider(this).get(SearchViewModel.class);

        model.apiCall("Arrabiata");

        //binding.image.setClipToOutline(true);
        binding.helper.setVisibility(View.GONE);


        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.loading.setValue(true);
                model.apiCall(binding.searchBarData.getEditableText().toString());
            }
        });



        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0)
                {
                    Log.i("fgdgdhgfhhfhfhgfj",""+count);
                    binding.searchButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    binding.searchButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        binding.searchBarData.addTextChangedListener(watcher);

        model._errorMessage.observe(getViewLifecycleOwner(), newData->{
            if(!newData.equals(""))
            {
                Snackbar.make(view,newData,Snackbar.LENGTH_LONG).show();
                model._errorMessage.setValue("");
            }
        });

        model.loading.observe(getViewLifecycleOwner(),newValue->{
            if(newValue)
            {
                binding.progressBar.setVisibility(View.VISIBLE);
            }
            else
            {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        if(model.response !=  null)
        {
            model.response.observe(getViewLifecycleOwner(),newData-> {
                Log.i("Changed","Dataset Changed.");
                if(newData.getMeals()!=null)
                {
                    Log.i("Ayush","Sachin TendulkaRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");

                    Log.i("Changed","Dataset Changed Value: "+newData.getMeals().size());
                    binding.searchButton.setVisibility(View.GONE);
                    SearchAdapter adapter = new SearchAdapter(newData.getMeals(), requireActivity().getApplication(), requireContext());
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
                    binding.recyclerView.setAdapter(adapter);
                }
            });
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        binding.searchButton.setVisibility(View.GONE);
        //binding.group.setVisibility(View.GONE);
        //binding.helper.setVisibility(View.VISIBLE);
        if(model!=null)
        {
            model.loading.setValue(false);
        }
    }
}
