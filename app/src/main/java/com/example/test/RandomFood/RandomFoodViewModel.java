package com.example.test.RandomFood;

import android.app.Application;
import android.content.pm.LauncherApps;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.Database.FoodDataClass;
import com.example.test.POJO.RandomFoodResponse;
import com.example.test.Repository.LocalRepository;
import com.example.test.Repository.OnlineRepository;


public class RandomFoodViewModel extends AndroidViewModel
{
    private OnlineRepository repository ;
    private LocalRepository local_repository;
    public MutableLiveData<RandomFoodResponse> response;
    public MutableLiveData<String> _errorMessage ;
    public MutableLiveData<Boolean> loading;

    public RandomFoodViewModel(Application application)
    {
        super(application);
        //assert this._errorMessage != null;
        this._errorMessage = new MutableLiveData<>("");
        this.repository = new OnlineRepository(application);
        this.local_repository = new LocalRepository(application);
        this.loading = new MutableLiveData<>(false);
    }

    public void apiCall()
    {
        repository.randomFoodApiCall();
        loading.setValue(false);
        response = repository.getData();

        if(response==null)
        {
            _errorMessage.setValue("Something wrong happened! Please try again.");
            Log.i("SearchModel","Size is null : ");
        }
        else
        {
            if(response.getValue().getMeals()==null)
            {
                Log.i("SearchModel","Size: null");
            }
            else
            {
                Log.i("SearchModel","Size : "+response.getValue().getMeals().size());
            }
        }
    }

    public void add(FoodDataClass food)
    {
        local_repository.insert(food);
    }

    public void delete(FoodDataClass food)
    {
        local_repository.delete(food);
    }
}
