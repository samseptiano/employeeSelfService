package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.EventSessionAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.HomeCategoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.HomeFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.QuizFragment.QuizFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.EventSessionRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSession;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSessionResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventUser.EventUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.HomeResponse;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EventAbsentUserRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EventSessionRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.SettingsActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.constraint.Constraints.TAG;


public class HomeDetailFragment extends Fragment {
    private ScrollView mRelativeLayout;
    private PopupWindow mPopupWindow;

    View rootView;
    Context context;
    TextView newsTitle,newsAuthor, newsDate, newsDesc;
    ImageView newsPoster;
    Button btnSurvey;
    ImageButton btnDownload;
    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    EventSessionAdapter hAdapter;

    RecyclerView recyclerView;

    public HomeDetailFragment() {
        // Required empty public constructor
    }

    public boolean isConnState() {
        return connState;
    }

    public void setConnState(boolean connState) {
        this.connState = connState;
    }

    public String getIdNews() {
        return idNews;
    }

    public void setId(String idNews) {
        this.idNews = idNews;
    }

    String idNews;
    boolean connState;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            setId(bundle.getString("id"));
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_detail, container,    false);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        newsTitle = (TextView) rootView.findViewById(R.id.tv_news_title);
        newsPoster = (ImageView) rootView.findViewById(R.id.news_image);
        newsAuthor = (TextView) rootView.findViewById(R.id.tv_news_author);
        newsDate = (TextView) rootView.findViewById(R.id.tv_news_date);
        newsDesc = (TextView) rootView.findViewById(R.id.tv_news_detail);


        btnSurvey = (Button) rootView.findViewById(R.id.btnSurvey);
        btnDownload = (ImageButton) rootView.findViewById(R.id.btnDownload);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_event_session);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);

        //=== snap recycler view item to center =====
//        SnapHelper helper = new PagerSnapHelper();
//        helper.attachToRecyclerView(recyclerView);
        //===========================================

        btnDownload.setVisibility(View.GONE);
        btnSurvey.setVisibility(View.GONE);


        //showBackButton();

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("Event Detail");
        loadDataTraining();
        return rootView;


    }

    protected void loadDataTraining(){

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();
        String nik = usr.get(0).getEmpNIK();


        HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getContext());
        HomeRealmModel homie = homeRealmHelper.findArticle(getIdNews());

        EventSessionRealmHelper eventSessionRealmHelper = new EventSessionRealmHelper(getContext());
        ArrayList<EventSessionRealmModel> eventSessionList;
        eventSessionList = eventSessionRealmHelper.findArticle(homie.getEventId());

        //if connection exist
        if(ConnectivityReceiver.isConnected()){
            //showToast(ConnectivityReceiver.isConnected());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            String id = getIdNews();
            Toast.makeText(getContext(),"id event: "+id,Toast.LENGTH_LONG).show();

            Call<List<Home>> call = apiService.getHomeNews(usr.get(0).getEmpNIK(),id ,"Bearer "+RToken);
            call.enqueue(new Callback<List<Home>>() {
                @Override
                public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {


                    try{

                        int statusCode = response.code();
                        List<Home> homee = response.body();

                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(homee.get(0).getEventName());
                        newsTitle.setText(homee.get(0).getEventName());
//                    newsDate.setText("Posted at: "+Calendar.getInstance().getTime().toString());
                        newsDate.setText("Posted at: -");
                        newsDesc.setText(homee.get(0).getEventDesc());
                        Glide.with(getContext())
                                .load(homee.get(0).getEventImage())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                                .error(R.drawable.imgalt)
                                .into(newsPoster);
                        //Toast.makeText(getContext(),"id: "+homee.getfGAllSurveyDoneYN(),Toast.LENGTH_LONG).show();

                        //==== SEMENTARA ====
                        if(homee.get(0).getfGHasSurveyYN().equals("Y")  && !homee.get(0).getSurveyId().equals("0") && !homee.get(0).getfGSurveyDoneYN().equals("Y")){
                            btnSurvey.setVisibility(View.VISIBLE);
                            btnSurvey.setEnabled(true);
                        }
//                        =====================

                        if(homee.get(0).getfGAllSurveyDoneYN().equals("Y") && homee.get(0).getfGSurveyDoneYN().equals("Y")){
                            btnDownload.setVisibility(View.VISIBLE);
                        }


                        if(!eventSessionList.isEmpty()){ //if DB not empty
                            List<EventSession>homess = new ArrayList<>();
                            for (EventSessionRealmModel object: eventSessionList) {

                                EventSession homesss = new EventSession(object.getEsid(), object.getEventID(),object.getEventType(), object.getInstructorID(), object.getSessionID(), object.getSessionName(), object.getSessionPlace(), object.getInstructorName(),"","", object.getSurveyID(), "", object.getSessionDateStart(),object.getSessionDateEnd(),object.getFileData(),object.getFileType());
                                homess.add(homesss);
                            }
                            hAdapter = new EventSessionAdapter(homess,getContext(),isConnState(), homie);
                            recyclerView.setAdapter(hAdapter);
                            //Toast.makeText(getContext(),"Dari Realm", Toast.LENGTH_LONG).show();
                        }


                        //=========
                        Call<List<EventSession>> call2 = apiService.getEventSessionID(id,nik,"Bearer "+RToken);
                        call2.enqueue(new Callback<List<EventSession>>() {
                            @Override
                            public void onResponse(Call<List<EventSession>> call, Response<List<EventSession>> response) {
                                List<EventSession> res = response.body();
//                            hAdapter = new EventSessionAdapter(res,getContext(),isConnState(), homie);
//                            recyclerView.setAdapter(hAdapter);



                                if(eventSessionList.isEmpty()){ //db realm empty

                                    hAdapter = new EventSessionAdapter(res,getContext(),isConnState(), homie);
                                    recyclerView.setAdapter(hAdapter);
                                    for (EventSession object: res) {
                                        eventSessionRealmHelper.addArticle(object);
                                    }


                                }
                                else{ //if DB not empty
                                    List<EventSession>homess = new ArrayList<>();
                                    for (EventSessionRealmModel object: eventSessionList) {

                                        EventSession homesss = new EventSession(object.getEsid(), object.getEventID(), object.getEventType(), object.getInstructorID(), object.getSessionID(), object.getSessionName(), object.getSessionPlace(), object.getInstructorName(),"","", object.getSurveyID(), "", object.getSessionDateStart(),object.getSessionDateEnd(),object.getFileData(),object.getFileType());
                                        homess.add(homesss);
                                    }
                                    hAdapter = new EventSessionAdapter(res,getContext(),isConnState(), homie);
                                    recyclerView.setAdapter(hAdapter);

                                    eventSessionRealmHelper.deleteAllData();
                                    for (EventSession object: res) {
                                        eventSessionRealmHelper.addArticle(object);
                                    }
                                    //Toast.makeText(getContext(),"Dari Realm", Toast.LENGTH_LONG).show();
                                }

                            }
                            @Override
                            public void onFailure(Call<List<EventSession>> call, Throwable t) {
                                // Log error here since request failed
                                Toast.makeText(getContext(),t.toString(), Toast.LENGTH_LONG).show();
                                //Log.e(TAG, t.toString());
                            }
                        });
                    }
                    catch(Exception e){
                        getActivity().getSupportFragmentManager().popBackStack();
                        Toast.makeText(getContext(),"No Event Found", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<List<Home>> call, Throwable t) {
                    // Log error here since request failed
                    Toast.makeText(getContext(),t.toString(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, t.toString());
                }
            });
        }
        else{

            try{
                showToast(ConnectivityReceiver.isConnected());
                //collapsingToolbarLayout.setTitle(homie.getEventName());
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(homie.getEventName());
                newsTitle.setText(homie.getEventName());
                newsDesc.setText(homie.getEventDesc());
                Glide.with(getContext())
                        .load(homie.getEventImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .error(R.drawable.imgalt)
                        .into(newsPoster);
                btnSurvey.setEnabled(false);
                btnDownload.setEnabled(false);

                List<EventSession>homess = new ArrayList<>();
                for (EventSessionRealmModel object: eventSessionList) {

                    EventSession homesss = new EventSession(object.getEsid(), object.getEventID(),object.getEventType(), object.getInstructorID(), object.getSessionID(), object.getSessionName(), object.getSessionPlace(), object.getInstructorName(),"","", object.getSurveyID(), "", object.getSessionDateStart(),object.getSessionDateEnd(),object.getFileData(),object.getFileType());
                    homess.add(homesss);
                }
                hAdapter = new EventSessionAdapter(homess,getContext(),isConnState(), homie);
                recyclerView.setAdapter(hAdapter);

            }
            catch(Exception e){
                getActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(getContext(),"No Event Found", Toast.LENGTH_LONG).show();

            }

            //Toast.makeText(getContext(),"homie",Toast.LENGTH_LONG).show();
        }


        btnSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSurvey(homie.getSurveyId(),homie.getEventId(),homie.getEventType());
            }
        });
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

    private void showDialogSurvey(String surveyId, String eventId, String eventType){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.survey_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Button btnStartSurvey = (Button) dialog.findViewById(R.id.btnEnterSurvey);
        btnStartSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr = new QuizFragment();
                Bundle bundle = new Bundle();
                bundle.putString("surveyID", surveyId);
                bundle.putString("eventID", eventId);
                bundle.putString("eventType", eventType);
                bundle.putString("isFeedbackInstructor", "NO");
                fr.setArguments(bundle);
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack(null);
                ft.commit();

                frr = new TabLayoutFragment();
                fmm = ((FragmentActivity)context).getSupportFragmentManager();
                ftt = fmm.beginTransaction();
                ft.replace(R.id.fragment_tablayout, frr);
                ftt.remove(frr);
                ftt.commit();

                Toast.makeText(getContext(), "Quiz Multiple Choice Area", Toast.LENGTH_LONG).show();

                dialog.dismiss();
            }
        });

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}

