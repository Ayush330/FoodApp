package com.example.test.POJO;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class RandomFoodResponse
{
    @SerializedName("meals")
    private List<Meal> meals = null;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}

