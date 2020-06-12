package com.enseval.samuelseptiano.hcservice.MVVM.Home;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.enseval.samuelseptiano.hcservice.Helper.HomeRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Home.Home;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.HomeRealmModel;

import java.util.ArrayList;

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
