package com.example.samuelseptiano.employeeselfservice.MVVM;

import android.content.ClipData;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Home> {

    public static final int PAGE_SIZE = 8;
    private static final int FIRST_PAGE = 1;
    //private static final String SITE_NAME = "stackoverflow";

//    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
//    ArrayList<UserRealmModel> usr;
    //usr = userRealmHelper.findAllArticle();
    String RToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJNYXJpbyBSb3NzaSIsImVtYWlsIjoibWFyaWEucm9zc2lAZG9tYWluLmNvbSIsIkRhdGVPZkpvaW5nIjoiMjAxOC0xMi0yNyIsIk5JSyI6IjEwMDAwMDAxIiwianRpIjoiMWMwNGQwNDItMWMxNy00NmY4LTkxNmUtNzQ2NjAxNDhjMTRhIiwiZXhwIjoxNTczNzI4NzY5LCJpc3MiOiJodHRwczovL2UtcmVjcnVpdG1lbnQuZW5zZXZhbC5jb20vIiwiYXVkIjoiaHR0cHM6Ly9lLXJlY3J1aXRtZW50LmVuc2V2YWwuY29tLyJ9.dWB975h22pYPNjXhVWAE8P2HQagz1A1ptuA2nh7Pkrc";


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Home> callback) {
        RetrofitClient.getInsance()
                .getApi()
                .getAllHomeNews(FIRST_PAGE, PAGE_SIZE, "Bearer "+RToken)
                .enqueue(new Callback<List<Home>>() {
                    @Override
                    public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {

                        if(response.body() != null){

                            callback.onResult(response.body(), null, FIRST_PAGE + 1);

                        }

                    }

                    @Override
                    public void onFailure(Call<List<Home>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Home> callback) {

        RetrofitClient.getInsance()
                .getApi()
                .getAllHomeNews(params.key, PAGE_SIZE, "Bearer "+RToken)
                .enqueue(new Callback<List<Home>>() {
                    @Override
                    public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {



                        if(response.body() != null){
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Home>> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Home> callback) {

        RetrofitClient.getInsance()
                .getApi()
                .getAllHomeNews(params.key, PAGE_SIZE, "Bearer "+RToken)
                .enqueue(new Callback<List<Home>>() {
                    @Override
                    public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {

                        if(response.body() != null){
//                            Integer key = response.body().has_more ? params.key + 1 : null;
                            callback.onResult(response.body(), params.key+1);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Home>> call, Throwable t) {

                    }
                });


    }
}
