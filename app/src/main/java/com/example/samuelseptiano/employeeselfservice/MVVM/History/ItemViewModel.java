package com.example.samuelseptiano.employeeselfservice.MVVM.History;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.google.zxing.common.StringUtils;

import java.util.function.Function;

import static com.example.samuelseptiano.employeeselfservice.MVVM.History.ItemDataSource.PAGE_SIZE;

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
                        .setPageSize(PAGE_SIZE)
                        .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();

        }

}
