package com.example.samuelseptiano.employeeselfservice.Fragment;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.EventAbsentHistoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.EventAbsentUserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.MVVM.History.ItemAdapter;
import com.example.samuelseptiano.employeeselfservice.MVVM.History.ItemViewModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EventAbsentUserRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class  EventsFragment extends Fragment {
    View rootView;
    Context context;
    private RecyclerView recyclerView;
    EventAbsentHistoryAdapter hAdapter;
    LinearLayout lnProgressEvent;
    List<EventAbsentUser> eventAbsentUserr;

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
//        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Event History");

        lnProgressEvent = rootView.findViewById(R.id.linlaHeaderProgressEvent);
        refreshData();


        SwipeRefreshLayout pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        return rootView;
    }

    private void refreshData(){
        lnProgressEvent.setVisibility(View.GONE);
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

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            ItemViewModel itemViewModel = ViewModelProviders.of((MainActivity)getActivity()).get(ItemViewModel.class);
            final ItemAdapter adapter = new ItemAdapter(getContext(),eventAbsentUserr, getContext(), isConnState());

            itemViewModel.itemPagedList.observe((MainActivity) getActivity(), new Observer<PagedList<EventAbsentUser>>() {
                @Override
                public void onChanged(PagedList<EventAbsentUser> eventAbsentUsers) {
                    adapter.submitList(eventAbsentUsers);
                }
            });

            recyclerView.setAdapter(adapter);
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
//

////            apiService.getEventAbsentUserID(id,"Bearer "+RToken)
////                    .subscribeOn(Schedulers.io())
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .unsubscribeOn(Schedulers.io())
////                    .subscribe(new Observer<List<EventAbsentUser>>() {
////
////                        @Override
////                        public void onSubscribe(Disposable d) {
////
////                        }
////                        @Override
////                        public void onNext(List<EventAbsentUser> leventAbsentUser) {
////                            eventAbsentUserr = leventAbsentUser;
////                            if(eventAbsentUser.isEmpty()){ //db realm empty
////
////
////                                Observable.fromIterable(eventAbsentUserr).buffer(5)
////                                        .subscribe(new Observer<List<EventAbsentUser>>() {
////                                            @Override
////                                            public void onSubscribe(Disposable d) {
////                                            }
////                                            @Override
////                                            public void onNext(List<EventAbsentUser> homey) {
////                                                for (EventAbsentUser obj : homey) {
////                                                    eventAbsentUserRealmHelper.addArticle(obj);
////                                                }
////                                            }
////                                            @Override
////                                            public void onError(Throwable e) {
////                                            }
////                                            @Override
////                                            public void onComplete() {
////                                                // Toast.makeText(getContext(),"realm inserted!",Toast.LENGTH_LONG).show();
////                                            }
////                                        });
////
//////                                for (EventAbsentUser object: eventAbsentUserr) {
//////                                    eventAbsentUserRealmHelper.addArticle(object);
//////                                }
////                            }
////                            else{ //if DB not empty
//////                               eventAbsentUserr = new ArrayList<>();
//////                                for (EventAbsentUserRealmModel object: eventAbsentUser) {
//////                                    EventAbsentUser eventAbsentUserrrr = new EventAbsentUser(object.getEaid(),object.getEmpNIK(),object.getEventDate(),object.getEventType(),object.getEventID(),object.getEventName(),"","","","");
//////                                    //Toast.makeText(context,eventAbsentUserrrr.getEventName(),Toast.LENGTH_SHORT).show();
//////                                    eventAbsentUserr.add(eventAbsentUserrrr);
//////                                }
////
////                                eventAbsentUserRealmHelper.deleteAllData();
//////                                for (EventAbsentUser object: eventAbsentUserr) {
//////                                    eventAbsentUserRealmHelper.addArticle(object);
//////                                }
////
////                                Observable.fromIterable(eventAbsentUserr).buffer(5)
////                                        .subscribe(new Observer<List<EventAbsentUser>>() {
////                                            @Override
////                                            public void onSubscribe(Disposable d) {
////                                            }
////                                            @Override
////                                            public void onNext(List<EventAbsentUser> homey) {
////                                                for (EventAbsentUser obj : homey) {
////                                                    eventAbsentUserRealmHelper.addArticle(obj);
////                                                }
////                                            }
////                                            @Override
////                                            public void onError(Throwable e) {
////                                            }
////                                            @Override
////                                            public void onComplete() {
////                                                // Toast.makeText(getContext(),"realm inserted!",Toast.LENGTH_LONG).show();
////                                            }
////                                        });
//                            }
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            lnProgressEvent.setVisibility(View.GONE);
//                            hAdapter = new EventAbsentHistoryAdapter(eventAbsentUserr,getContext(),isConnState());
//                            recyclerView.setAdapter(hAdapter);
//                        }
//                    });






//            Call<List<EventAbsentUser>> call = apiService.getEventAbsentUserID(id,"Bearer "+RToken);
//            call.enqueue(new Callback<List<EventAbsentUser>>() {
//                @Override
//                public void onResponse(Call<List<EventAbsentUser>> call, Response<List<EventAbsentUser>> response) {
//                    int statusCode = response.code();
//                    List<EventAbsentUser> eventAbsentUserr = response.body();
//                    lnProgressEvent.setVisibility(View.GONE);
//                    //Toast.makeText(context,eventAbsentUserr.get(0).getEaid(),Toast.LENGTH_SHORT).show();
//
//                    if(eventAbsentUser.isEmpty()){ //db realm empty
//                        hAdapter = new EventAbsentHistoryAdapter(eventAbsentUserr,getContext(),isConnState());
//                        recyclerView.setAdapter(hAdapter);
//                        for (EventAbsentUser object: eventAbsentUserr) {
//
//                            eventAbsentUserRealmHelper.addArticle(object);
//                        }
//                    }
//                    else{ //if DB not empty
//                        List<EventAbsentUser>eventAbsentUserrr = new ArrayList<>();
//                        for (EventAbsentUserRealmModel object: eventAbsentUser) {
//                            EventAbsentUser eventAbsentUserrrr = new EventAbsentUser(object.getEaid(),object.getEmpNIK(),object.getEventDate(),object.getEventType(),object.getEventID(),object.getEventName(),"","","","");
//                            //Toast.makeText(context,eventAbsentUserrrr.getEventName(),Toast.LENGTH_SHORT).show();
//                            eventAbsentUserrr.add(eventAbsentUserrrr);
//                        }
//                        hAdapter = new EventAbsentHistoryAdapter(eventAbsentUserrr,getContext(),isConnState());
//                        recyclerView.setAdapter(hAdapter);
//
//                        eventAbsentUserRealmHelper.deleteAllData();
//                        for (EventAbsentUser object: eventAbsentUserr) {
//                            eventAbsentUserRealmHelper.addArticle(object);
//                        }
//                        //Toast.makeText(getContext(),"Dari Realm", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                @Override
//                public void onFailure(Call<List<EventAbsentUser>> call, Throwable t) {
//                    lnProgressEvent.setVisibility(View.GONE);
//
//                    // Log error here since request failed
//                    Toast.makeText(context,t.toString(),Toast.LENGTH_SHORT).show();
//                    Log.e(TAG, t.toString());
//                }
//            });
        }
        else{
            lnProgressEvent.setVisibility(View.GONE);
            ArrayList<EventAbsentUser>eventAbsentUserr = new ArrayList<>();
            eventAbsentUser = eventAbsentUserRealmHelper.findAllArticle();
            for (EventAbsentUserRealmModel object: eventAbsentUser) {
                EventAbsentUser eventAbsentUserrrr = new EventAbsentUser(object.getEaid(),object.getEmpNIK(),object.getEventDate(),object.getEventType(),object.getEventID(),object.getEventName(),"","","","");
                eventAbsentUserr.add(eventAbsentUserrrr);
            }
            hAdapter = new EventAbsentHistoryAdapter(eventAbsentUserr,getContext(),isConnState());
            recyclerView.setAdapter(hAdapter);

//            eventAbsentUserRealmHelper.deleteAllData();
//            for (EventAbsentUser object: eventAbsentUserr) {
//                eventAbsentUserRealmHelper.addArticle(object);
//            }
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                String text = s.toLowerCase(Locale.getDefault());
                try{
                    //hAdapter.filter(text);

                }
                catch(Exception e){

                }

                return true;

            }
        });

    }

}
