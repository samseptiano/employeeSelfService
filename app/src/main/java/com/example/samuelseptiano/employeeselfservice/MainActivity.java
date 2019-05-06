package com.example.samuelseptiano.employeeselfservice;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Application.MyApplication;
import com.example.samuelseptiano.employeeselfservice.Fragment.HomeFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.EventsFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.NotificationFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.ProfileFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.QuizFragment.QuizFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.EventAbsentUserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.EventSessionRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserAnswerRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.User;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.userBodyParameter;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.Session.SessionManagement;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  implements ConnectivityReceiver.ConnectivityReceiverListener {

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    // Session Manager Class
    SessionManagement session;

    Realm realm;
    UserRealmHelper userRealmHelper;

    //for recycler view get current scroll position
    int positionIndex,topView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Manually checking internet connection
        checkConnection();

        // Session class instance
        session = new SessionManagement(getApplicationContext());

        //initiallization realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getApplicationContext());
        UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());

        ///if session ended
        if(session.checkLogin().equals("logout")){
            finish();
        }
        else if(session.isLoggedIn() == true) {

            //if connection exist
            if(checkConnection()){

                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                String RToken = usr.get(0).getToken();
                String id = usr.get(0).getUserId();

                //jika token di realm masih ada
                if(RToken.length()>0){
                    ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);

                    Call<User> call = apiService2.getUserDetail(new userBodyParameter(id), "Bearer " + RToken);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.code() == 401){
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                session.logoutUser();
                                finish();
                                Toast.makeText(getApplicationContext(), "Token Expired ", Toast.LENGTH_LONG).show();
                            }
                            else{
                                fr = new HomeFragment();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                                fr.setArguments(bundle);
                                fm = getSupportFragmentManager();
                                ft = fm.beginTransaction();
                                ft.replace(R.id.fragment_frame, fr);
                                ft.detach(fr);
                                ft.attach(fr);
                                ft.commit();
                                Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_LONG).show();
                                inflateTabLayoutFragment();
                            }

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            // Log error here since request failed
                            Toast.makeText(getApplicationContext(), "Error: " + t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                    }
                    else{
                        //get user data from session
                        HashMap<String, String> user = session.getUserDetails();
                        // name
                        String nik = user.get(SessionManagement.KEY_NIK);
                        userRealmHelper.deleteData(nik);
                        session.logoutUser();
                        finish();
                    }

            }
            else{
                fr = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                fr.setArguments(bundle);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.detach(fr);
                ft.attach(fr);
                ft.commit();
                Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_LONG).show();
                inflateTabLayoutFragment();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.logout:
                UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());
                userRealmHelper.deleteAllData();
                HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getApplicationContext());
                homeRealmHelper.deleteAllData();
                EventAbsentUserRealmHelper eventAbsentUserRealmHelper = new EventAbsentUserRealmHelper(getApplicationContext());
                eventAbsentUserRealmHelper.deleteAllData();
                EventSessionRealmHelper eventSessionRealmHelper = new EventSessionRealmHelper(getApplicationContext());
                eventSessionRealmHelper.deleteAllData();
                UserAnswerRealmHelper userAnswerRealmHelper = new UserAnswerRealmHelper(getApplicationContext());
                userAnswerRealmHelper.deleteAllData();
//            UserEventRealmHelper userEventRealmHelper = new UserEventRealmHelper(getApplicationContext());
//            userRealmHelper.deleteAllData();
                session.logoutUser();
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    // Method to manually check connection status
    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        //showToast(isConnected);
        return  isConnected;
    }
    // Showing the status in Toast
    private void showToast(boolean isConnected) {
        String message;
        if (isConnected) {
            message = "Good! Connected to Internet";
        } else {
            message = "Sorry! Not connected to internet";
        }
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Fragment  f = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
        if (f instanceof HomeDetailFragment)
        {
           //code
            RecyclerView rv = findViewById(R.id.recycler_view_event_session);
            positionIndex=  ((LinearLayoutManager)rv.getLayoutManager()).findFirstVisibleItemPosition();
            View startView = rv.getChildAt(0);
            topView = (startView == null) ? 0 : (startView.getTop() - rv.getPaddingTop());
//            Toast.makeText(getApplicationContext(),"On resume",Toast.LENGTH_SHORT).show();
        }
        if (f instanceof HomeCategoryFragment)
        {
           //code
        }

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Fragment  f = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
        if (f instanceof HomeDetailFragment)
        {
            //code
            RecyclerView rv = findViewById(R.id.recycler_view_event_session);
            ((LinearLayoutManager)rv.getLayoutManager()).scrollToPositionWithOffset(positionIndex, topView);
//            Toast.makeText(getApplicationContext(),"On pause",Toast.LENGTH_SHORT).show();
        }
        if (f instanceof HomeCategoryFragment)
        {
            //code

        }
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }


    //===================Inflate Fragment=============================================
    public void inflateTabLayoutFragment(){
        frr = new TabLayoutFragment();
        fmm = getSupportFragmentManager();
        ftt = fmm.beginTransaction();
        ftt.add(R.id.fragment_tablayout, frr);
        ftt.commit();
    }


        public void inflateHomeFragment(boolean Connstate){

            //inflateTabLayoutFragment();
            fr = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("ConnState", Connstate);
            fr.setArguments(bundle);
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            ft.replace(R.id.fragment_frame, fr);
            ft.detach(fr);
            ft.attach(fr);
            ft.addToBackStack("fragmentHome");
            ft.commit();
            Toast.makeText(getApplicationContext(), "Home Area", Toast.LENGTH_LONG).show();
        }

    public void inflateProfileFragment(boolean Connstate){
        //inflateTabLayoutFragment();
        fr = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ConnState", Connstate);
        fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(), "Profile Area", Toast.LENGTH_LONG).show();
    }

    public void inflateNotificationFragment(boolean Connstate){
        //inflateTabLayoutFragment();
        fr = new NotificationFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ConnState", Connstate);
        fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(), "Notification Area", Toast.LENGTH_LONG).show();

    }


    public void inflateEventFragment(boolean Connstate){
        //inflateTabLayoutFragment();
        fr = new EventsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ConnState", Connstate);
        fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(),"History Area",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Fragment  f = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);
        if (f instanceof HomeFragment)
        {
            super.onBackPressed();
            finish();
        }
        if (f instanceof ProfileFragment)
        {
            //super.onBackPressed();
            finish();
        }
        if (f instanceof EventsFragment)
        {
            //super.onBackPressed();
            finish();
        }
        if (f instanceof NotificationFragment)
        {
            super.onBackPressed();
            finish();
        }
        else if (f instanceof HomeDetailFragment)
        {
            getSupportFragmentManager().popBackStack();
            defineActionbar();
        }
        else if (f instanceof QuizFragment)
        {
            getSupportFragmentManager().popBackStack();
        }

        else if (f instanceof HomeCategoryFragment)
        {
            getSupportFragmentManager().popBackStack();
            defineActionbar();
        }
    }

    public void defineActionbar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }




}
