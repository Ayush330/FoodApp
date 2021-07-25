package com.example.test.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.test.Networking.ClientService;
import com.example.test.Networking.RetrofitClientInstance;
import com.example.test.POJO.RandomFoodResponse;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineRepository
{
    public MutableLiveData<RandomFoodResponse> randomFoodResponse = null;
    public ClientService service = RetrofitClientInstance.getRetrofit().create(ClientService.class);
    public MutableLiveData<RandomFoodResponse> searchResults;
    private Application application;

    public OnlineRepository(Application application)
    {
        this.randomFoodResponse = new MutableLiveData<RandomFoodResponse>(new RandomFoodResponse());
        this.searchResults = new MutableLiveData<RandomFoodResponse>(new RandomFoodResponse());
        this.application = application;

    }

    public MutableLiveData<RandomFoodResponse> getData()
    {
        return randomFoodResponse;
    }

    public MutableLiveData<RandomFoodResponse> getSearchData() {return searchResults;}

    public void randomFoodApiCall()
    {
        Call<RandomFoodResponse> call = service.randomFoodFunction();
        call.enqueue(new Callback<RandomFoodResponse>() {
            @Override
            public void onResponse(Call<RandomFoodResponse> call, Response<RandomFoodResponse> response)
            {
                Log.i("DataRandom","Data called: "+response.body().getMeals().size());
                if(response.isSuccessful())
                {
                    randomFoodResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RandomFoodResponse> call, Throwable t)
            {
                Log.i("DataRandom","Data called Error: "+t.getLocalizedMessage());
                Toast.makeText(application,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setSearchResult(String data)
    {
        Call<RandomFoodResponse> call = service.searchFoodResult(data);
        call.enqueue(new Callback<RandomFoodResponse>() {
            @Override
            public void onResponse(Call<RandomFoodResponse> call, Response<RandomFoodResponse> response) {
                Log.i("Search","Body: "+response.raw());
                if(response.isSuccessful())
                {
                    searchResults.setValue(response.body());
                    if(response.body().getMeals()!=null)
                    {
                        Log.i("SearchView","Size : "+response.body().getMeals().size());
                    }
                    else
                    {
                        Log.i("SearchView","Size : null");
                        setSearchResult(data);
                    }
                }
                else
                {
                    searchResults.setValue(new RandomFoodResponse());
                    Toast.makeText(application,"Something went wrong!",Toast.LENGTH_LONG).show();
                    Log.i("SearchView","Size : mull");
                }
            }

            @Override
            public void onFailure(Call<RandomFoodResponse> call, Throwable t) {
                Log.i("ErrorSearxh","Body: "+t.getLocalizedMessage());
                Toast.makeText(application,"Something went wrong!",Toast.LENGTH_LONG).show();
                searchResults.setValue(new RandomFoodResponse());
            }
        });
    }
}
