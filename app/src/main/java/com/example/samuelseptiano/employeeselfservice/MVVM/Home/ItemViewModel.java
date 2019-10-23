package com.example.samuelseptiano.employeeselfservice.MVVM.Home;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ItemViewModel extends ViewModel {

    public LiveData<PagedList<Home>> itemPagedList;
    MutableLiveData<ItemDataSource> liveDataSource;

    HomeRealmHelper homeRealmHelper = new HomeRealmHelper();
    ArrayList<HomeRealmModel> home;
    public String eventName="";

    public ItemViewModel(String eventName) {

        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory(eventName);
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSource.PAGE_SIZE)
                        .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();

    }




    public void replaceSubscription(LifecycleOwner lifecycleOwner, String eventName) {
        this.eventName = eventName;
        Log.d(TAG, "ItemViewModel: "+eventName);
        itemPagedList.removeObservers(lifecycleOwner);
        liveDataSource = new MutableLiveData<>();
        new ItemViewModel(eventName);
    }
}
