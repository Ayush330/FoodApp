package com.example.test.Favourites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.test.Database.FoodDataClass;
import com.example.test.Repository.LocalRepository;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class FavouritesViewModel extends AndroidViewModel
{
    public LiveData<List<FoodDataClass>> data;
    private LocalRepository local_repository;

    public FavouritesViewModel(@NonNull @NotNull Application application) {
        super(application);
        //data = new MutableLiveData<>(Collections.EMPTY_LIST);
        local_repository = new LocalRepository(application);
    }

    public void fetchDataFromDatabase()
    {
        data =  local_repository.getAllWords();
    }
}
