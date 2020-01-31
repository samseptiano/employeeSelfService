package com.example.samuelseptiano.employeeselfservice.MVVM.History;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ItemDataSource extends PageKeyedDataSource<Integer, EventAbsentUser> {

    public static final int PAGE_SIZE = 15;
    private static final int FIRST_PAGE = 1;
    //private static final String SITE_NAME = "stackoverflow";

    UserRealmHelper userRealmHelper = new UserRealmHelper();
    ArrayList<UserRealmModel> usr = userRealmHelper.findAllArticle();
    String RToken = usr.get(0).getToken();
    String nik = usr.get(0).getEmpNIK();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, EventAbsentUser> callback) {
        RetrofitClient.getInsance()
                .getApi()
                .getEventAbsentUserID(nik,FIRST_PAGE, PAGE_SIZE, "Bearer "+RToken)
                .enqueue(new Callback<List<EventAbsentUser>>() {
                    @Override
                    public void onResponse(Call<List<EventAbsentUser>> call, Response<List<EventAbsentUser>> response) {

                        if(response.body() != null){

                            callback.onResult(response.body(), null, FIRST_PAGE + 1);

                        }

                    }

                    @Override
                    public void onFailure(Call<List<EventAbsentUser>> call, Throwable t) {
                        Log.d(TAG, "errornya dimana: "+t.toString());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, EventAbsentUser> callback) {

        RetrofitClient.getInsance()
                .getApi()
                .getEventAbsentUserID(nik,params.key, PAGE_SIZE, "Bearer "+RToken)
                .enqueue(new Callback<List<EventAbsentUser>>() {
                    @Override
                    public void onResponse(Call<List<EventAbsentUser>> call, Response<List<EventAbsentUser>> response) {



                        if(response.body() != null){
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EventAbsentUser>> call, Throwable t) {
                        Log.d(TAG, "errornya dimana: "+t.toString());

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, EventAbsentUser> callback) {

        RetrofitClient.getInsance()
                .getApi()
                .getEventAbsentUserID(nik,params.key, PAGE_SIZE, "Bearer "+RToken)
                .enqueue(new Callback<List<EventAbsentUser>>() {
                    @Override
                    public void onResponse(Call<List<EventAbsentUser>> call, Response<List<EventAbsentUser>> response) {

                        if(response.body() != null){
//                            Integer key = response.body().has_more ? params.key + 1 : null;
                            callback.onResult(response.body(), params.key+1);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<EventAbsentUser>> call, Throwable t) {
                        Log.d(TAG, "errornya dimana: "+t.toString());

                    }
                });


    }
}
