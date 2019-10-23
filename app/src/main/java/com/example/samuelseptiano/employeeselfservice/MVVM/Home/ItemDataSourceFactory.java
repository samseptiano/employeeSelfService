package com.example.samuelseptiano.employeeselfservice.MVVM.Home;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ItemDataSourceFactory extends DataSource.Factory {

    String eventName;
    private MutableLiveData<ItemDataSource> itemLiveDataSource = new MutableLiveData<>();

    public ItemDataSourceFactory(String eventName) {
        this.eventName = eventName;
        Log.d(TAG, "ItemDataSource: "+eventName);
    }


    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource(eventName);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<ItemDataSource> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
