package com.enseval.samuelseptiano.hcservice.MVVM.DistNormal;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ItemDataSource extends PageKeyedDataSource<Integer, PerhitunganPAEMPModel> {

    public static final int PAGE_SIZE = 50;
    private static final int FIRST_PAGE = 1;
    String DNID="";
    String searchQuery="";
    UserRealmHelper userRealmHelper = new UserRealmHelper();
    ArrayList<UserRealmModel> usr = userRealmHelper.findAllArticle();
    String RToken = usr.get(0).getToken();
    String nik = usr.get(0).getEmpNIK();
    List<PerhitunganPAEMPModel> pa = new ArrayList<>();

    //private static final String SITE_NAME = "stackoverflow";
    public ItemDataSource(String DNID,String searchQuery) {
        this.DNID = DNID;
        this.searchQuery = searchQuery;
        Log.d(TAG, "ItemDataSource: "+DNID);
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, PerhitunganPAEMPModel> callback) {
        RetrofitClient.getInsance()
                .getApi()
                .getDistNormalDNonRX(DNID,FIRST_PAGE, PAGE_SIZE,searchQuery, "Bearer "+RToken)
                .enqueue(new Callback<List<PerhitunganPAEMPModel>>() {
                    @Override
                    public void onResponse(Call<List<PerhitunganPAEMPModel>> call, Response<List<PerhitunganPAEMPModel>> response) {
                        if(response.body() != null){
//                            for (PerhitunganPAEMPModel element : response.body()) {
//                                pa.add(element);
//                            }
                            callback.onResult(response.body(), null, FIRST_PAGE + 1);

                        }

                    }

                    @Override
                    public void onFailure(Call<List<PerhitunganPAEMPModel>> call, Throwable t) {
                        Log.d(TAG, "errornya dimana: "+t.toString());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, PerhitunganPAEMPModel> callback) {

        RetrofitClient.getInsance()
                .getApi()
                .getDistNormalDNonRX(DNID,params.key, PAGE_SIZE,searchQuery, "Bearer "+RToken)
                .enqueue(new Callback<List<PerhitunganPAEMPModel>>() {
                    @Override
                    public void onResponse(Call<List<PerhitunganPAEMPModel>> call, Response<List<PerhitunganPAEMPModel>> response) {



                        if(response.body() != null){
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PerhitunganPAEMPModel>> call, Throwable t) {
                        Log.d(TAG, "errornya dimana: "+t.toString());

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, PerhitunganPAEMPModel> callback) {

        RetrofitClient.getInsance()
                .getApi()
                .getDistNormalDNonRX(DNID,params.key, PAGE_SIZE,searchQuery, "Bearer "+RToken)
                .enqueue(new Callback<List<PerhitunganPAEMPModel>>() {
                    @Override
                    public void onResponse(Call<List<PerhitunganPAEMPModel>> call, Response<List<PerhitunganPAEMPModel>> response) {

                        if(pa.size()>PAGE_SIZE) {
                            if (response.body() != null) {
//                            Integer key = response.body().has_more ? params.key + 1 : null;
                                callback.onResult(response.body(), params.key + 1);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<PerhitunganPAEMPModel>> call, Throwable t) {
                        Log.d(TAG, "errornya dimana: "+t.toString());

                    }
                });


    }
}
