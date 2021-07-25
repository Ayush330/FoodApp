package com.example.test.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.test.Database.FoodDao;
import com.example.test.Database.FoodDataClass;
import com.example.test.Database.FoodDatabase;

import java.util.List;

public class LocalRepository
{
    private LiveData<List<FoodDataClass>> data;
    private FoodDao dao;

    public LocalRepository(Application application)
    {

        FoodDatabase db = FoodDatabase.getDatabase(application);
        dao = db.foodDao();
        data = dao.getAll();
    }

    public LiveData<List<FoodDataClass>> getAllWords() {
        return data;
    }

    public void insert(FoodDataClass food) {
            FoodDatabase.databaseWriteExecutor.execute(() -> {
                dao.addFavourite(food);
            });
    }

    public void delete(FoodDataClass food)
    {
        FoodDatabase.databaseWriteExecutor.execute(() -> {
            dao.removeFavourite(food);
        });
    }

}
