package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.NestedAdapters.RecyclerViewDataAdapter;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.HomeHeaderFragment;
import com.example.samuelseptiano.employeeselfservice.Model.NestedRecyclerViewModels.SectionDataModel;
import com.example.samuelseptiano.employeeselfservice.Model.NestedRecyclerViewModels.SingleItemModel;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.HomeResponse;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


public class HomeFragment extends Fragment {
    View rootView;
    Context context;
    private CompositeDisposable disposable = new CompositeDisposable();

    ArrayList<SectionDataModel> allSampleData;
    ArrayList<String> category = new ArrayList<>();

    public boolean isConnState() {
        return connState;
    }

    public void setConnState(boolean connState) {
        this.connState = connState;
    }

    boolean connState;

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting application context
        context = getActivity();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
        }
        allSampleData = new ArrayList<SectionDataModel>();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("E-KiosK");

        SwipeRefreshLayout pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

//        linlaHeaderProgress.setVisibility(View.VISIBLE);
        refreshData();
//        linlaHeaderProgress.setVisibility(View.GONE);


        return  rootView;
    }

    private void refreshData(){

        fr = new HomeHeaderFragment();
        fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_header_home, fr);
        ft.addToBackStack(null);
        ft.commit();

        allSampleData.clear();
        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();

        RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view_home);
        my_recycler_view.setHasFixedSize(true);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getContext());
        ArrayList<HomeRealmModel> home;
        home = homeRealmHelper.findAllArticle();

        //if connection exist
        if(ConnectivityReceiver.isConnected()){
            showToast(ConnectivityReceiver.isConnected());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<List<Home>> call = apiService.getAllHomeNews(usr.get(0).getEmpNIK(),"Bearer "+RToken);
            call.enqueue(new Callback<List<Home>>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {
                    int statusCode = response.code();
                    List<Home> homes = response.body();
                    //Toast.makeText(getContext(),"dataa:: "+ homes.get(0).getEventId(), Toast.LENGTH_LONG).show();

                    if(home.isEmpty()){ //db realm empty

                        for(int i = 0; i < homes.size(); i++) {
                            // listOfLists.get(i).add(new Home(homes.get(i).getId(),homes.get(i).getNewsName(),homes.get(i).getNewsDate(),homes.get(i).getNewsImage(),homes.get(i).getNewsCategory(, homes.get(i).getNewsAuthor(),homes.get(i).getNewsDesc()));
                            category.add(homes.get(i).getEventType());
                        }

                        ArrayList<String> newList = removeDuplicates(category);


                        for (int i = 0; i < newList.size(); i++) {
                            SectionDataModel dm = new SectionDataModel();
                            dm.setHeaderTitle("Category " + newList.get(i).toString());
                            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();

                            for (int j = 0; j < homes.size(); j++) {
                                if(homes.get(j).getEventType().equals(newList.get(i).toString())){
                                    singleItem.add(new SingleItemModel(homes.get(j).getEventName(),homes.get(j).getEventType(),homes.get(j).getEventId(),homes.get(j).getEventDesc(),homes.get(j).getEventImage()));
                                  }
                            }
                            dm.setAllItemsInSection(singleItem);

                            allSampleData.add(dm);

                        }
                        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allSampleData);
                        my_recycler_view.setAdapter(adapter);

                        for (Home object: homes) {
                            homeRealmHelper.addArticle(object);
                        }
                    }
                    else{ //if DB not empty
                        List<Home>homess = new ArrayList<>();
                        for (HomeRealmModel object: home) {
                            Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(),"", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN());
                            homess.add(homesss);
                        }
                        for(int i = 0; i < homess.size(); i++) {
                            // listOfLists.get(i).add(new Home(homes.get(i).getId(),homes.get(i).getNewsName(),homes.get(i).getNewsDate(),homes.get(i).getNewsImage(),homes.get(i).getNewsCategory(, homes.get(i).getNewsAuthor(),homes.get(i).getNewsDesc()));
                            category.add(homess.get(i).getEventType());
                        }

                        ArrayList<String> newList = removeDuplicates(category);
                        //Toast.makeText(getContext(),Integer.toString(newList.size()), Toast.LENGTH_LONG).show();

                        allSampleData.clear();
                        for (int i = 0; i < newList.size(); i++) {
                            SectionDataModel dm = new SectionDataModel();
                            dm.setHeaderTitle("Category " + newList.get(i).toString());
                            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();

                            for (int j = 0; j < homess.size(); j++) {
                                if(homess.get(j).getEventType().equals(newList.get(i).toString())){
                                    singleItem.add(new SingleItemModel(homess.get(j).getEventName(),homess.get(j).getEventType(), homess.get(j).getEventId(),homess.get(j).getEventDesc(),homess.get(j).getEventImage()));
                                    //Toast.makeText(getContext(),homes.get(j).getEventId(),Toast.LENGTH_LONG).show();
                                }
                            }
                            dm.setAllItemsInSection(singleItem);
                            allSampleData.add(dm);
                        }

                        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allSampleData);
                        my_recycler_view.setAdapter(adapter);
                        homeRealmHelper.deleteAllData();
                        for (Home object: homes) {
                            homeRealmHelper.addArticle(object);
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Home>> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }
        else{
            showToast(ConnectivityReceiver.isConnected());
            List<Home>homess = new ArrayList<>();
            for (HomeRealmModel object: home) {
                Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(),"", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN());
                homess.add(homesss);
            }
            for(int i = 0; i < homess.size(); i++) {
                // listOfLists.get(i).add(new Home(homes.get(i).getId(),homes.get(i).getNewsName(),homes.get(i).getNewsDate(),homes.get(i).getNewsImage(),homes.get(i).getNewsCategory(, homes.get(i).getNewsAuthor(),homes.get(i).getNewsDesc()));
                category.add(homess.get(i).getEventType());
            }

            ArrayList<String> newList = removeDuplicates(category);
            //Toast.makeText(getContext(),Integer.toString(newList.size()), Toast.LENGTH_LONG).show();

            for (int i = 0; i < newList.size(); i++) {
                SectionDataModel dm = new SectionDataModel();
                dm.setHeaderTitle("Category " + newList.get(i).toString());
                ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();

                for (int j = 0; j < homess.size(); j++) {
                    if(homess.get(j).getEventType().equals(newList.get(i).toString())){
                        singleItem.add(new SingleItemModel(homess.get(j).getEventName(),homess.get(j).getEventType(), homess.get(j).getEventId(),homess.get(j).getEventDesc(),homess.get(j).getEventImage()));
                    }
                }
                dm.setAllItemsInSection(singleItem);
                allSampleData.add(dm);
            }
            RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allSampleData);
            my_recycler_view.setAdapter(adapter);

        }
    }

    private void showToast(boolean isConnected) {
        String message;
        if (isConnected) {
            message = "Good! Connected to Internet";
        } else {
            message = "Sorry! Not connected to internet";
        }
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();
        // Traverse through the first list
        for (T element : list) {
            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }
}
