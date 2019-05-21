package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.EventAbsentHistoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.EventAbsentUserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EventAbsentUserRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class EventsFragment extends Fragment {
    View rootView;
    Context context;
    private RecyclerView recyclerView;
    EventAbsentHistoryAdapter hAdapter;


    public boolean isConnState() {
        return connState;
    }

    public void setConnState(boolean connState) {
        this.connState = connState;
    }

    boolean connState;


    // TODO: Rename parameter arguments, choose names that match

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_events, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Event History");

        SwipeRefreshLayout pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

        refreshData();
        return rootView;
    }

    private void refreshData(){
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();
        String id = usr.get(0).getEmpNIK();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_events);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        EventAbsentUserRealmHelper eventAbsentUserRealmHelper = new EventAbsentUserRealmHelper(getContext());
        ArrayList<EventAbsentUserRealmModel> eventAbsentUser;
        eventAbsentUser = eventAbsentUserRealmHelper.findAllArticle();

        //if connection exist
        if(ConnectivityReceiver.isConnected()){
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//
            Call<List<EventAbsentUser>> call = apiService.getEventAbsentUserID(id,"Bearer "+RToken);
            call.enqueue(new Callback<List<EventAbsentUser>>() {
                @Override
                public void onResponse(Call<List<EventAbsentUser>> call, Response<List<EventAbsentUser>> response) {
                    int statusCode = response.code();
                    List<EventAbsentUser> eventAbsentUserr = response.body();
                    //Toast.makeText(context,eventAbsentUserr.get(0).getEaid(),Toast.LENGTH_SHORT).show();

                    if(eventAbsentUser.isEmpty()){ //db realm empty
                        hAdapter = new EventAbsentHistoryAdapter(eventAbsentUserr,getContext(),isConnState());
                        recyclerView.setAdapter(hAdapter);
                        for (EventAbsentUser object: eventAbsentUserr) {

                            eventAbsentUserRealmHelper.addArticle(object);
                        }
                    }
                    else{ //if DB not empty
                        List<EventAbsentUser>eventAbsentUserrr = new ArrayList<>();
                        for (EventAbsentUserRealmModel object: eventAbsentUser) {
                            EventAbsentUser eventAbsentUserrrr = new EventAbsentUser(object.getEaid(),object.getEmpNIK(),object.getEventDate(),object.getEventType(),object.getEventID(),object.getEventName(),"","","","");
                            //Toast.makeText(context,eventAbsentUserrrr.getEventName(),Toast.LENGTH_SHORT).show();
                            eventAbsentUserrr.add(eventAbsentUserrrr);
                        }
                        hAdapter = new EventAbsentHistoryAdapter(eventAbsentUserrr,getContext(),isConnState());
                        recyclerView.setAdapter(hAdapter);

                        eventAbsentUserRealmHelper.deleteAllData();
                        for (EventAbsentUser object: eventAbsentUserr) {
                            eventAbsentUserRealmHelper.addArticle(object);
                        }
                        //Toast.makeText(getContext(),"Dari Realm", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<EventAbsentUser>> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(context,t.toString(),Toast.LENGTH_SHORT).show();
                    Log.e(TAG, t.toString());
                }
            });
        }
        else{
            List<EventAbsentUser>eventAbsentUserr = new ArrayList<>();
            for (EventAbsentUserRealmModel object: eventAbsentUser) {
                EventAbsentUser eventAbsentUserrrr = new EventAbsentUser(object.getEaid(),object.getEmpNIK(),object.getEventDate(),object.getEventType(),object.getEventID(),object.getEventName(),"","","","");
                eventAbsentUserr.add(eventAbsentUserrrr);
            }
            hAdapter = new EventAbsentHistoryAdapter(eventAbsentUserr,getContext(),isConnState());
            recyclerView.setAdapter(hAdapter);

            eventAbsentUserRealmHelper.deleteAllData();
            for (EventAbsentUser object: eventAbsentUserr) {
                eventAbsentUserRealmHelper.addArticle(object);
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
                try{
                    hAdapter.filter(text);
                }
                catch(Exception e){

                }

                return true;

            }
        });

    }

}
