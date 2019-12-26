package com.example.samuelseptiano.employeeselfservice.MVVM.History;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class ItemDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<ItemDataSource> itemLiveDataSource = new MutableLiveData<>();


    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource();
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<ItemDataSource> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
