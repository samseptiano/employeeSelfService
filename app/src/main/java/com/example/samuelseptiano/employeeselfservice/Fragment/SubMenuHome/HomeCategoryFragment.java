package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.HomeCategoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeCategoryFragment extends Fragment {

    View rootView;
    Context context;
    private RecyclerView recyclerView;
    private HomeCategoryAdapter hAdapter;
    private TextView tvNotFound;
    LinearLayout lnProgress;

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

        SwipeRefreshLayout pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        refreshData();
        return  rootView;
    }

    private void refreshData(){
        lnProgress.setVisibility(View.VISIBLE);
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;

        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_home_category);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
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
        if(isConnState()){
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<List<Home>> call = apiService.getAllHomeNews(usr.get(0).getEmpNIK(),"Bearer "+RToken);
            call.enqueue(new Callback<List<Home>>() {
                @Override
                public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {

                    lnProgress.setVisibility(View.GONE);
                    try {
                        int statusCode = response.code();
                        List<Home> homes = response.body();
//                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(home.get(0).getEventType());
                        if (home.isEmpty()) { //db realm empty

                            hAdapter = new HomeCategoryAdapter(homes, getContext(), isConnState(), getActivity());
                            recyclerView.setAdapter(hAdapter);
                            for (Home object : homes) {
                                homeRealmHelper.addArticle(object);
                            }


                        } else { //if DB not empty
                            List<Home> homess = new ArrayList<>();
                            for (HomeRealmModel object : home) {
                                Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(), "", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN(), object.getfGAbsenYN());
                                homess.add(homesss);
                            }
                            hAdapter = new HomeCategoryAdapter(homess, getContext(), isConnState(), getActivity());
                            recyclerView.setAdapter(hAdapter);

                            homeRealmHelper.deleteAllData();
                            for (Home object : homes) {
                                homeRealmHelper.addArticle(object);
                            }
                            //Toast.makeText(getContext(),"Dari Realm", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                            tvNotFound.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<List<Home>> call, Throwable t) {
                    lnProgress.setVisibility(View.GONE);
                    // Log error here since request failed
                    //Log.e(TAG, t.toString());
                }
            });
        }
        else{
            lnProgress.setVisibility(View.GONE);
                try {
                    List<Home> homess = new ArrayList<>();
                    for (HomeRealmModel object : home) {
                        Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(), "", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN(), object.getfGAbsenYN());
                        homess.add(homesss);
                    }
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getCategory());
                    hAdapter = new HomeCategoryAdapter(homess, getContext(), isConnState(), getActivity());
                    recyclerView.setAdapter(hAdapter);
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

        android.support.v7.widget.SearchView mSearchView = (android.support.v7.widget.SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                    String text = s.toLowerCase(Locale.getDefault());
                    try {
                        hAdapter.filter(text);
                    } catch (Exception e) {

                    }


                return true;

            }
        });
    }
}
