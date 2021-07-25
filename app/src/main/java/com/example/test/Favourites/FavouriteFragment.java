package com.example.test.Favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test.databinding.FavouriteBinding;
import org.jetbrains.annotations.NotNull;

public class FavouriteFragment extends Fragment
{
    private FavouriteBinding binding;
    private FavouritesViewModel model;

    public FavouriteFragment()
    {

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = FavouriteBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(this).get(FavouritesViewModel.class);

        model.fetchDataFromDatabase();

        if(model.data!=null)
        {
            model.data.observe(getViewLifecycleOwner(),newData->{
                if(newData!=null)
                {
                    FoodAdapter adapter = new FoodAdapter(newData, requireActivity().getApplication());
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.recyclerView.setAdapter(adapter);
                }
            });
        }
    }
}
