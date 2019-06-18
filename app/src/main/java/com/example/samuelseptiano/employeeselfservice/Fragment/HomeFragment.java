package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.InboxAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.HomeCategoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Carrousel.ViewPagerCarouselView;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.InboxChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.QrResultModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener {
    View rootView;
    Context context;
    private DatabaseReference root;

    public void setConnState(boolean connState) {
        this.connState = connState;
    }

    boolean connState;

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    RecyclerView recyclerViewInbox;
    InboxAdapter inboxChatAdapter;

    List <InboxChatModel> inboxChatModels;

    long carouselSlideInterval = 3000; // 3 SECONDS
    int [] imageResourceIds = {R.drawable.home3, R.drawable.home1, R.drawable.home2, R.drawable.home3, R.drawable.home1}; // car6 on 0, and car1 on last for circular scroll purpose;
    //qr code scanner object
    private IntentIntegrator qrScan;

    ImageButton imgBtnEvent,imgBtnRequest,imgBtnBenefit,imgBtnPA, imgBtnPAPJ,imgBtnSurvey,imgBtnMore, imgBtnScan;
    EditText edtSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Getting application context
        context = getActivity();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);

    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        viewPagerCarouselView.onDestroy();
//    }

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
        imgBtnEvent = rootView.findViewById(R.id.imgBtnEvent);
        imgBtnRequest = rootView.findViewById(R.id.imgBtnRequest);
        imgBtnBenefit = rootView.findViewById(R.id.imgBtnBenefit);
        imgBtnPA = rootView.findViewById(R.id.imgBtnPA);
        imgBtnPAPJ = rootView.findViewById(R.id.imgBtnPAPJ);
        imgBtnMore = rootView.findViewById(R.id.imgBtnMore);
        imgBtnSurvey = rootView.findViewById(R.id.imgBtnSurvey);
        imgBtnScan = rootView.findViewById(R.id.imgBtnScan);
        edtSearch = rootView.findViewById(R.id.edtSearch);
        recyclerViewInbox = rootView.findViewById(R.id.recycler_view_request_inbox);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewInbox.setLayoutManager(mLayoutManager);






            //================================================================
                //getAllUnreadOneChatRoom("181201101-381201101","181201101");
            //================================================================

        inboxChatModels = new ArrayList<>();
        for(int i=0;i<10;i++){
            InboxChatModel inboxChatModel = new InboxChatModel();
            inboxChatModel.setFrom("samuel septiano");
            inboxChatModel.setMessage("testMessage");
            inboxChatModel.setFrom("admin");
            inboxChatModel.setTotalMessageNew(12);
            inboxChatModels.add(inboxChatModel);
        }
        inboxChatAdapter = new InboxAdapter(inboxChatModels, getContext(), ConnectivityReceiver.isConnected(), getActivity());
        recyclerViewInbox.setAdapter(inboxChatAdapter);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int keyAction, KeyEvent keyEvent) {
                if (
                    //Soft keyboard search
                        keyAction == EditorInfo.IME_ACTION_SEARCH ||
                                //Physical keyboard enter key
                                (keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()
                                        && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {

                    fr = new HomeCategoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Category", "Event");
                    bundle.putString("Query", edtSearch.getText().toString());
                    bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle);
                    fm = ((FragmentActivity)context).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("fragmentHome");
                    ft.commit();

                    edtSearch.setText("");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    return true;
                }
                return false;
            }
        });

        imgBtnSurvey.setEnabled(false);
        imgBtnSurvey.setAlpha((float) 0.6);

        imgBtnMore.setEnabled(false);
        imgBtnMore.setAlpha((float) 0.6);

        imgBtnBenefit.setEnabled(false);
        imgBtnBenefit.setAlpha((float) 0.6);

        try {
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    fr = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle);
                    fm = ((FragmentActivity)context).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("fragmentHome");
                    ft.commit();

                    pullToRefresh.setRefreshing(false);
                }
            });
            refreshData();
        }
        catch (Exception e){

        }


        return  rootView;
    }

    private void refreshData(){
        ViewPagerCarouselView viewPagerCarouselView;
        viewPagerCarouselView = (ViewPagerCarouselView) rootView.findViewById(R.id.carousel_view);
        viewPagerCarouselView.setData(getFragmentManager(), imageResourceIds, carouselSlideInterval);


        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();


        //if connection exist
        if(ConnectivityReceiver.isConnected()){
            showToast(ConnectivityReceiver.isConnected());


            //call event API
            callEvent(RToken,usr.get(0).getEmpNIK());
            //=======================


            imgBtnPA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(ConnectivityReceiver.isConnected()) {
                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                        routingHomeDetailInterface.routingKPI("PA List tahunan", getContext(), "", "");
                    }
                    else{
                        Toast.makeText(context,"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            imgBtnPAPJ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(ConnectivityReceiver.isConnected()) {
                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                        routingHomeDetailInterface.routingKPI("PA List", getContext(), "", "");
                    }
                    else{
                        Toast.makeText(context,"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            imgBtnEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingHome("Event",context);

                    //Toast.makeText(v.getContext(),"Event List Area",Toast.LENGTH_SHORT).show();
                }
            });
            imgBtnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingHome("Request",context);

                    //Toast.makeText(v.getContext(),"Request List Area",Toast.LENGTH_SHORT).show();
                }
            });

            imgBtnScan.setOnClickListener(this);

        }
        else{
            imgBtnPA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // jika user punya bawahan
//                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
//                    routingHomeDetailInterface.routingKPI("Fill PA",getContext(),"","");

                    //else
                    showDialogOption();
                }
            });

            imgBtnEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingHome("Event",context);
                }
            });
            imgBtnRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingHome("Request",context);
                }
            });
            imgBtnScan.setOnClickListener(this);
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

    private void searchQR() {
        qrScan = new IntentIntegrator(getActivity());
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setPrompt("Scan QR for search Event");
        qrScan.setBeepEnabled(true);
        qrScan.setCameraId(0);
        qrScan.setBarcodeImageEnabled(true);
        qrScan.setCaptureActivity(CaptureActivity.class);
        qrScan.forSupportFragment(HomeFragment.this).initiateScan();
    }

    @Override
    public void onClick(View v) {
        searchQR();
    }

    private void callEvent(String token,String nik){
        HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getContext());
        ArrayList<HomeRealmModel> home;
        home = homeRealmHelper.findAllArticle();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Home>> call = apiService.getAllHomeNews(nik,"Bearer "+token);
        call.enqueue(new Callback<List<Home>>() {
            @Override
            public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {
                int statusCode = response.code();
                List<Home> homes = response.body();
                if(home.isEmpty()){ //db realm empty
                    for (Home object: homes) {
                        homeRealmHelper.addArticle(object);
                    }
                }
                else{ //if DB not empty
                    List<Home>homess = new ArrayList<>();
                    for (HomeRealmModel object: home) {
                        Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(),"", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN(),object.getfGAbsenYN());
                        homess.add(homesss);
                    }
                    homeRealmHelper.deleteAllData();
                    for (Home object: homes) {
                        homeRealmHelper.addArticle(object);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Home>> call, Throwable t) {
                // Log error here since request failed
                //Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(getContext(), "QR Not found", Toast.LENGTH_SHORT).show();
            }else{
                // jika qrcode berisi data
                try{
                        try{
                            Gson g = new Gson();
                            QrResultModel results = g.fromJson(result.getContents(), QrResultModel.class);

                            HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getContext());
                            HomeRealmModel homie = homeRealmHelper.findArticle(results.getEventId());

                            if(homie.getEventType().equals(results.getEventType())){
                                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                            routingHomeDetailInterface.routingHomeDetail(results.getEventType(),getContext(),results.getEventId());

                            }
                            else{
                                Toast.makeText(context,"No Event Found",Toast.LENGTH_SHORT).show();
                            }

                        }
                        catch (Exception e){

                            Toast.makeText(getContext()," No Event Found",Toast.LENGTH_SHORT).show();
                        }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "An Error Occurred While Scanning", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showDialogOption(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.kpi_option_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton btnFill = (ImageButton) dialog.findViewById(R.id.imageButtonFill);
        ImageButton btnApprove = (ImageButton) dialog.findViewById(R.id.imageButtonApprove);

//        btnApprove.setEnabled(false);
//        btnApprove.setAlpha((float) 0.6);

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copy url
                if(ConnectivityReceiver.isConnected()) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingKPI("PA List tahunan", getContext(), "", "");
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(context,"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copy url

               if(ConnectivityReceiver.isConnected()) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingKPI("PA List", getContext(), "", "");
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(context,"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }

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


    private void getAllUnreadOneChatRoom(String room_name, String empNIK){
        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        Query query = root.orderByChild("status-"+empNIK).equalTo("UNREAD");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    Toast.makeText( context,dataSnap.getKey(),Toast.LENGTH_LONG).show();
                    // get all unread message in specific room_name
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getAllRoomChat(String contains){
        root = FirebaseDatabase.getInstance().getReference();
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    if(dataSnap.getKey().contains(contains)) {
                        Toast.makeText(context, dataSnap.getKey(), Toast.LENGTH_LONG).show();
                    }
                    // get all room_name
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }



}
