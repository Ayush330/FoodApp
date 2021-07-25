package com.example.test.RandomFood;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.test.Database.FoodDataClass;
import com.example.test.POJO.Meal;
import com.example.test.R;
import com.example.test.databinding.RandomFoodFragmentBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class RandomFoodFragment extends Fragment
{

    private RandomFoodFragmentBinding binding;
    private ArrayList<String> ingredients;
    private Boolean checked;
    private RandomFoodViewModel model;

    public RandomFoodFragment()
    {
        ingredients =  new ArrayList<String>();
        checked = false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = RandomFoodFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Log.i("OnCreated","Hey it is called");
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(this).get(RandomFoodViewModel.class);

        model.apiCall();
        model.loading.setValue(true);

        binding.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checked)
                {
                    binding.favourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_liked));
                    checked= true;
                    if(model.response!=null && Objects.requireNonNull(model.response.getValue()).getMeals()!=null)
                    {
                        Meal meal = model.response.getValue().getMeals().get(0);
                        FoodDataClass data = new FoodDataClass(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb());
                        model.add(data);
                    }
                }
                else
                {
                    binding.favourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                    checked = false;
                    if(model.response!=null && Objects.requireNonNull(model.response.getValue()).getMeals()!=null)
                    {
                        Meal meal = model.response.getValue().getMeals().get(0);
                        FoodDataClass data = new FoodDataClass(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb());
                        model.delete(data);
                    }
                }
            }
        });

        model.loading.observe(getViewLifecycleOwner(),newValue->{
            Log.i("ProgressBar","Changeddddddddddddd");
            if(newValue)
            {
                binding.progressBar.setVisibility(View.VISIBLE);
                //model.loading.setValue(false);
            }
            else
            {
                binding.progressBar.setVisibility(View.GONE);
                //model.loading.setValue(false);
            }
        });

        binding.imageView.setClipToOutline(true);

        if(model.response!=null)
        {
            model.response.observe(getViewLifecycleOwner(), newData->{
                //Log.i("Random","RandomData: "+newData.getMeals().size());
                model.loading.setValue(false);
                if(newData.getMeals()!=null)
                {
                    Log.i("Random","RandomData: "+newData.getMeals().size());

                    Meal meal = newData.getMeals().get(0);

                    binding.title.setText(meal.getStrMeal());
                    ingredients.clear();

                    ingredients.add(meal.getStrIngredient1());
                    ingredients.add(meal.getStrIngredient2());
                    ingredients.add(meal.getStrIngredient3());
                    ingredients.add(meal.getStrIngredient4());
                    ingredients.add(meal.getStrIngredient5());

                    // add data to the chipgroup
                    for(int i = 0;i<ingredients.size();i++)
                    {
                        Chip chip = new Chip(requireActivity());
                        chip.setText(ingredients.get(i));
                        binding.chipGroup.addView(chip);
                    }
                    Picasso.get()
                            .load(meal.getStrMealThumb())
                            .fit()
                            .centerCrop()
                            .into(binding.imageView);
                }

            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(model!=null)
        {
            model.loading.setValue(false);
        }
    }
}
