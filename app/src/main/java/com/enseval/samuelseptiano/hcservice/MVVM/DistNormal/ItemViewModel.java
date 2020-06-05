package com.enseval.samuelseptiano.hcservice.MVVM.DistNormal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.EventAbsentUser.EventAbsentUser;

public class ItemViewModel extends ViewModel {

    public LiveData<PagedList<PerhitunganPAEMPModel>> itemPagedList;
    MutableLiveData<ItemDataSource> liveDataSource;
    private final MutableLiveData<String> filterText = new MutableLiveData<>();

    String DNID;
    String searchQuery;
    public ItemViewModel(String DNID, String searchQuery) {
        this.DNID = DNID;
        this.searchQuery = searchQuery;
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory(DNID,searchQuery);
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ItemDataSource.PAGE_SIZE)
                        .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();

        }

}
