package com.example.samuelseptiano.employeeselfservice.MVVM.Home;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ItemDataSource extends PageKeyedDataSource<Integer, Home> {

    public static final int PAGE_SIZE = 8;
    private static final int FIRST_PAGE = 1;
    //private static final String SITE_NAME = "stackoverflow";

    UserRealmHelper userRealmHelper = new UserRealmHelper();
    ArrayList<UserRealmModel> usr = userRealmHelper.findAllArticle();
    String RToken = usr.get(0).getToken();
    String nik = usr.get(0).getEmpNIK();
    String eventName;

    public ItemDataSource(String eventName) {
        this.eventName = eventName;
        Log.d(TAG, "ItemDataSource: "+eventName);
    }

//    HomeRealmHelper homeRealmHelper = new HomeRealmHelper();
//    List<HomeRealmModel> Rhome = homeRealmHelper.findAllArticle();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Home> callback) {
        RetrofitClient.getInsance()
                .getApi()
                .getAllHomeNews(FIRST_PAGE, PAGE_SIZE,eventName, "Bearer "+RToken)
                .enqueue(new Callback<List<Home>>() {
                    @Override
                    public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {
                        callback.onResult(response.body(), null, FIRST_PAGE + 1);
                        Log.d(TAG, "onResponseInitial: "+eventName);

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
                .getAllHomeNews(params.key, PAGE_SIZE,eventName, "Bearer "+RToken)
                .enqueue(new Callback<List<Home>>() {
                    @Override
                    public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {



                        if(response.body() != null){
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body(), key);
                            Log.d(TAG, "onResponseBefore: "+eventName);

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
                .getAllHomeNews(params.key, PAGE_SIZE,eventName, "Bearer "+RToken)
                .enqueue(new Callback<List<Home>>() {
                    @Override
                    public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {

                        if(response.body() != null){
//                            Integer key = response.body().has_more ? params.key + 1 : null;
                            callback.onResult(response.body(), params.key+1);
                            Log.d(TAG, "onResponseAfter: "+eventName);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Home>> call, Throwable t) {

                    }
                });


    }
}
