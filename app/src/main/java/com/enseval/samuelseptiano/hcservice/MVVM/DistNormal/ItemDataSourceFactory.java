package com.enseval.samuelseptiano.hcservice.MVVM.DistNormal;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ItemDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<ItemDataSource> itemLiveDataSource = new MutableLiveData<>();
    String DNID;
    String searchQuery;

    public ItemDataSourceFactory(String DNID, String searchQuery) {
        this.DNID = DNID;
        this.searchQuery = searchQuery;
        Log.d(TAG, "ItemDataSourceFactory: "+DNID);
    }
    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource(DNID,searchQuery);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<ItemDataSource> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
