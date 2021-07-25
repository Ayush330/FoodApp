package com.example.test.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao
{
    @Query("Select * From food")
    LiveData<List<FoodDataClass>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Void addFavourite(FoodDataClass data);

    @Delete
    Void removeFavourite(FoodDataClass data);

}
