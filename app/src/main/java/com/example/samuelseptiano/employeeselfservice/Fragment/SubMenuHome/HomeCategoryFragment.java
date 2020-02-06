package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.samuelseptiano.employeeselfservice.Adapter.PaginationAdapter.HomeeCategoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.HomeCategoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.MVVM.Home.ItemAdapter;
import com.example.samuelseptiano.employeeselfservice.MVVM.Home.ItemViewModel;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeCategoryFragment extends Fragment {


    //Constant used for Logs
    //Bundle constant to save the last searched query
    private static final String LAST_SEARCH_QUERY = "last_search_query";
    //The default query to load
    private static final String DEFAULT_QUERY = "Android";


    View rootView;
    Context context;
    private RecyclerView recyclerView;
    private HomeCategoryAdapter hAdapter;
    private TextView tvNotFound;
    LinearLayout lnProgress;


    List<Home> homess;
    ItemAdapter adapter;
    ItemViewModel itemViewModel;

    //=================================================
    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    SwipeRefreshLayout pullToRefresh;
    List<Home> homes;
    //================================


    public boolean isConnState() {
        return connState;
    }

    public void setConnState(boolean connState) {
        this.connState = connState;
    }

    boolean connState;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    String query;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting application context
        context = getActivity();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            setCategory(bundle.getString("Category"));
            setQuery(bundle.getString("Query"));
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // refreshData();
        homess = new ArrayList<>();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home_category, container, false);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getCategory());

        tvNotFound = rootView.findViewById(R.id.tvNotFound);
        lnProgress = rootView.findViewById(R.id.linlaHeaderProgressHomeCat);

        pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(""); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        refreshData("");
        return  rootView;
    }

    private void refreshData(String Username){
        //Toast.makeText(getContext(),Username,Toast.LENGTH_LONG).show();
        //Set the Empty text with emoji unicode
        //mDataBinding.emptyList.setText(getString(R.string.no_results, "\uD83D\uDE13"));

//        //Get the view model
//        mViewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(getContext()))
//                .get(SearchRepositoriesViewModel.class);
//
//        //Initialize RecyclerView
//        initRecyclerView();
//
//        //Get the query to search
//        String query = DEFAULT_QUERY;
////        if (savedInstanceState != null) {
////            query = savedInstanceState.getString(LAST_SEARCH_QUERY, DEFAULT_QUERY);
////        }
//
//        //Post the query to be searched
//        mViewModel.searchRepo(query);
//
//        //Initialize the EditText for Search Actions
//        initSearch(query);




        lnProgress.setVisibility(View.GONE);
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;

        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_home_category);

//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getContext());
        ArrayList<HomeRealmModel> home;
//        home = homeRealmHelper.findCategoryArticle(getCategory());

        if(getQuery()!= null){
                home = homeRealmHelper.findArticles(getQuery());
        }
        else{
            home = homeRealmHelper.findAllArticle();
        }


        //if connection exist
        if(ConnectivityReceiver.isConnected()){
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            try {
                itemViewModel = new ItemViewModel(Username);
                itemViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        return (T) new ItemViewModel(Username);
                    }
                }).get(ItemViewModel.class);
                adapter = new ItemAdapter(getContext(), homes, getContext(), isConnState(), getActivity());
//            Toast.makeText(getContext(),Username,Toast.LENGTH_LONG).show();

                itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Home>>() {
                    @Override
                    public void onChanged(PagedList<Home> homes) {
                        adapter.submitList(homes);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
            catch (Exception e){
                Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++




            ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
//=========================================   NO PAGINATION  =============================================================================================================
//            apiService.getAllHomeNews(1,3,"Bearer "+RToken)
////            apiService.getAllHomeNews(usr.get(0).getEmpNIK(),"Bearer "+RToken)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .unsubscribeOn(Schedulers.io())
//                    .subscribe(new Observer<List<Home>>() {
//
//
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(List<Home> homeList) {
//
//                           homes = homeList;
//                            if (home.isEmpty()) { //db realm empty
//
//
//                                Observable.fromIterable(homeList).buffer(5)
//                                        .subscribe(new Observer<List<Home>>() {
//                                            @Override
//                                            public void onSubscribe(Disposable d) {
//
//                                            }
//
//                                            @Override
//                                            public void onNext(List<Home> homey) {
//                                                for (Home obj : homey) {
//                                                    homeRealmHelper.addArticle(obj);
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onError(Throwable e) {
//
//                                            }
//
//                                            @Override
//                                            public void onComplete() {
//                                                   // Toast.makeText(getContext(),"realm inserted!",Toast.LENGTH_LONG).show();
//                                            }
//                                        });
//
//
////                                for (Home object : homes) {
////                                    homeRealmHelper.addArticle(object);
////                                }
//
//                            } else { //if DB not empty
//                                homeRealmHelper.deleteAllData();
//                                Observable.fromIterable(homeList).buffer(5)
//                                                        .subscribe(new Observer<List<Home>>() {
//                                                            @Override
//                                                            public void onSubscribe(Disposable d) {
//                                                            }
//                                                            @Override
//                                                            public void onNext(List<Home> homey) {
//                                                                for (Home obj : homey) {
//                                                                    homeRealmHelper.addArticle(obj);
//                                                                }
//                                                            }
//                                                            @Override
//                                                            public void onError(Throwable e) {
//                                                            }
//                                                            @Override
//                                                            public void onComplete() {
//                                                                // Toast.makeText(getContext(),"realm inserted!",Toast.LENGTH_LONG).show();
//                                                            }
//                                                        });
//
////                                for (Home object : homes) {
////                                    homeRealmHelper.addArticle(object);
////                                }
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onComplete() {
//                        lnProgress.setVisibility(View.GONE);
//
//                            if (home.isEmpty()) { //db realm empty
//                                hAdapter = new HomeCategoryAdapter(homes, getContext(), isConnState(), getActivity());
//                                recyclerView.setAdapter(hAdapter);
//                            }
//                            else { //if DB not empty
//                                hAdapter = new HomeCategoryAdapter(homes, getContext(), isConnState(), getActivity());
//                                recyclerView.setAdapter(hAdapter);
//                        }
//                        }
//                    });


//            Call<List<Home>> call = apiService.getAllHomeNews(usr.get(0).getEmpNIK(),"Bearer "+RToken);
//            call.enqueue(new Callback<List<Home>>() {
//                @Override
//                public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {
//
//                    lnProgress.setVisibility(View.GONE);
//                    try {
//                        int statusCode = response.code();
//                        List<Home> homes = response.body();
////                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(home.get(0).getEventType());
//                        if (home.isEmpty()) { //db realm empty
//
//                            hAdapter = new HomeCategoryAdapter(homes, getContext(), isConnState(), getActivity());
//                            recyclerView.setAdapter(hAdapter);
////                      display(homes);
//
//                            for (Home object : homes) {
//                                homeRealmHelper.addArticle(object);
//                            }
//
//
//                        } else { //if DB not empty
//                            List<Home> homess = new ArrayList<>();
//                            for (HomeRealmModel object : home) {
//                                Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(), "", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN(), object.getfGAbsenYN());
//                                homess.add(homesss);
//                            }
//                            hAdapter = new HomeCategoryAdapter(homess, getContext(), isConnState(), getActivity());
//                            recyclerView.setAdapter(hAdapter);
////                            display(homess);
//
//                            homeRealmHelper.deleteAllData();
//                            for (Home object : homes) {
//                                homeRealmHelper.addArticle(object);
//                            }
//                            //Toast.makeText(getContext(),"Dari Realm", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    catch (Exception e){
//                            tvNotFound.setVisibility(View.VISIBLE);
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<List<Home>> call, Throwable t) {
//                    lnProgress.setVisibility(View.GONE);
//                    // Log error here since request failed
//                    //Log.e(TAG, t.toString());
//                }
//            });
//=======================================================================================================================================================================

        }
        else{
            lnProgress.setVisibility(View.GONE);
                try {
                    home = homeRealmHelper.findAllArticle();
                    List<Home> homess = new ArrayList<>();
                    for (HomeRealmModel object : home) {
                        Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(), "", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN(), object.getfGAbsenYN());
                        homess.add(homesss);
                    }
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getCategory());
                    hAdapter = new HomeCategoryAdapter(homess, getContext(), isConnState(), getActivity());
                    recyclerView.setAdapter(hAdapter);
//                    display(homess);
                }
                catch (Exception e){
                    tvNotFound.setVisibility(View.VISIBLE);
                }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem mSearch = menu.findItem(R.id.mi_search);

       SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
               return  false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String ss = s.toLowerCase(Locale.getDefault());
                try {
//                        hhAdapter.filter(text);
                    //hAdapter.filter(text);
                    replaceSubscription(ss);
                } catch (Exception e) {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
                return true;

            }
        });
    }

    private void  replaceSubscription(String userName) {
        try {
            itemViewModel = new ItemViewModel(userName);
            adapter = new ItemAdapter(getContext(),homes, getContext(), isConnState(), getActivity());

            itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Home>>() {
                @Override
                public void onChanged(PagedList<Home> homes) {
                    adapter.submitList(homes);
                }
            });

            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    //=============================
   // pagination

    //https://stackoverflow.com/questions/28047272/handle-paging-with-rxjava

    //=============================


    //======================

    //======================
}
