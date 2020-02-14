package com.enseval.samuelseptiano.hcservice.MVVM.History;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.EventAbsentUser.EventAbsentUser;

public class ItemViewModel extends ViewModel {

    public LiveData<PagedList<EventAbsentUser>> itemPagedList;
    MutableLiveData<ItemDataSource> liveDataSource;
    private final MutableLiveData<String> filterText = new MutableLiveData<>();

    public ItemViewModel() {

        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSource.PAGE_SIZE)
                        .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();

        }

}
