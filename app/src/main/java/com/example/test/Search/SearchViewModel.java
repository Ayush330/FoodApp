package com.example.test.Search;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.test.Database.FoodDataClass;
import com.example.test.POJO.RandomFoodResponse;
import com.example.test.Repository.LocalRepository;
import com.example.test.Repository.OnlineRepository;

public class SearchViewModel extends AndroidViewModel
{
    private OnlineRepository repository ;
    public MutableLiveData<RandomFoodResponse> response;
    public MutableLiveData<String> _errorMessage ;
    private LocalRepository local_repository;
    public MutableLiveData<Boolean> loading;

    public SearchViewModel(Application application)
    {
        super(application);
        this._errorMessage = new MutableLiveData<>("");
        this.repository = new OnlineRepository(application);
        this.local_repository = new LocalRepository(application);
        this.loading = new MutableLiveData<>(false);
    }

    public void apiCall(String data)
    {
        repository.setSearchResult(data);

        //LiveData<RandomFoodResponse> temp= Transformations.switchMap(data, id->repository.getSearchData());

        loading.setValue(false);
        response = repository.getSearchData();
        if(response==null)
        {
            _errorMessage.setValue("Something wrong happened! Please try again.");
            Log.i("ResultViewModel","null");
        }
        else
        {
            if(response.getValue().getMeals()!=null)
            {
                Log.i("ResultViewModel","Size : "+response.getValue().getMeals().size());
                response.setValue(response.getValue());
            }
            else
            {
                Log.i("ResultViewModel","Size : null");
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
