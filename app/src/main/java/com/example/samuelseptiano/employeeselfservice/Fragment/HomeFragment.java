package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.IconHomeAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Carrousel.ViewPagerCarouselView;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.ModuleRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.IconModel;
import com.example.samuelseptiano.employeeselfservice.Model.QrResultModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.ModuleRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener, IconHomeAdapter.EventListener{
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
    ViewPagerCarouselView viewPagerCarouselView;
    RecyclerView recyclerViewInbox;

    private RecyclerView recyclerViewHome;
    private IconHomeAdapter iconHomeAdapter;
    List<IconModel> iconModels = new ArrayList<>();

    UserList userList = new UserList();

    List<Home> homes;
    ArrayList<UserRealmModel> usr = new ArrayList<>();

    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
    CarouselView carouselView;

    long carouselSlideInterval = 5000; // 5 SECONDS
    int [] imageResourceIds = {R.drawable.home3, R.drawable.home1, R.drawable.home2, R.drawable.home3, R.drawable.home1}; // car6 on 0, and car1 on last for circular scroll purpose;
    //qr code scanner object
    private IntentIntegrator qrScan;

    ImageButton imgBtnScan;
    EditText edtSearch;
    TextView tvEmpName;
    LinearLayout lnProgress2;

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

//        PackageInfo pinfo = null;
//        try {
//            pinfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        String versionName = pinfo.versionName;
//
//
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<List<VersionModel>> call = apiService.getVersion(new UserLogin("mario","secret"));
//        call.enqueue(new Callback<List<VersionModel>>() {
//            @Override
//            public void onResponse(Call<List<VersionModel>>call, Response<List<VersionModel>> response) {
//                if(!versionName.equals(response.body().get(0).getVersion())){
//                    Intent i = new Intent(getActivity(), UpdateActivity.class);
//                    startActivity(i);
////                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                    getActivity().finish();
//                }
//            }
//            @Override
//            public void onFailure(Call<List<VersionModel>>call, Throwable t) {
//
//                Toast.makeText(getContext(), "Error Connection Found", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPagerCarouselView.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();


        ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getContext());
        ArrayList<ModuleRealmModel> mdl;
        mdl = moduleRealmHelper.findAllArticle();


        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("");

        SwipeRefreshLayout pullToRefresh = rootView.findViewById(R.id.swipeContainer);
        imgBtnScan = rootView.findViewById(R.id.imgBtnScan);
        edtSearch = rootView.findViewById(R.id.edtSearch);
        lnProgress2 = rootView.findViewById(R.id.linlaHeaderProgressInbox);
        tvEmpName = rootView.findViewById(R.id.tvEmpName);


        //========== icons HOME =================
        iconModels=new ArrayList<>();
        recyclerViewHome = (RecyclerView) rootView.findViewById(R.id.rv_icon_home);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(getContext(), 2));
        for(int i=0;i<mdl.size();i++) {
            if(!mdl.get(i).getModuleCode().contains("-")) {
                IconModel im = new IconModel();
                im.setIconTitle(mdl.get(i).getModuleName());
                im.setIconCode(mdl.get(i).getModuleCode());
                im.setIconStatus(mdl.get(i).getModuleStatus());
                String name = mdl.get(i).getIcon();
                int id = getResources().getIdentifier(name, "drawable", getContext().getPackageName());
                im.setIconImage(id);
                iconModels.add(im);
            }
        }
        iconHomeAdapter = new IconHomeAdapter(iconModels,getContext(), ConnectivityReceiver.isConnected(), getActivity(), mdl,this);
        recyclerViewHome.setAdapter(iconHomeAdapter);
        //===============================================


        tvEmpName.setText(usr.get(0).getEmpName());


        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int keyAction, KeyEvent keyEvent) {
                try {
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
                        fm = ((FragmentActivity) context).getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_frame, fr);
                        ft.addToBackStack("fragmentHome");
                        ft.commit();

                        edtSearch.setText("");
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        return true;
                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "Training Not Found", Toast.LENGTH_LONG).show();
                    return  false;
                }
                return false;

            }
        });

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
            refreshData(RToken,usr);
        }
        catch (Exception e){

        }


        return  rootView;
    }



    private void refreshData(String RToken, List<UserRealmModel>usr){
        viewPagerCarouselView = (ViewPagerCarouselView) rootView.findViewById(R.id.carousel_view);
        viewPagerCarouselView.setData(getChildFragmentManager(), imageResourceIds, carouselSlideInterval);

        //if connection exist
        if(ConnectivityReceiver.isConnected()){
            showToast(ConnectivityReceiver.isConnected());

            //call event API
            callEvent(RToken,usr.get(0).getEmpNIK());
            //=======================

            userList = new UserList();

            imgBtnScan.setOnClickListener(this);

        }
        else{
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

        ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);

//=========================================   NO PAGINATION  =============================================================================================================
//        apiService.getAllHomeNews(1,3,"Bearer "+token)
//                //apiService.getAllHomeNews(usr.get(0).getEmpNIK(),"Bearer "+RToken)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(new Observer<List<Home>>() {
//
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<Home> homeList) {
//                        if(home.isEmpty()){ //db realm empty
//
////                    for (Home object: homeList) {
////                        homeRealmHelper.addArticle(object);
////                    }
//
//                            Observable.fromIterable(homeList).buffer(5)
//                                    .subscribe(new Observer<List<Home>>() {
//                                        @Override
//                                        public void onSubscribe(Disposable d) {
//
//                                        }
//                                        @Override
//                                        public void onNext(List<Home> homey) {
//                                            for (Home obj : homey) {
//                                                homeRealmHelper.addArticle(obj);
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//
//                                        }
//
//                                        @Override
//                                        public void onComplete() {
//                                            //Toast.makeText(getContext(),"realm inserted!",Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//
//                }
//                else{ //if DB not empty
////                    List<Home>homess = new ArrayList<>();
////                    for (HomeRealmModel object: home) {
////                        Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(),"", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN(),object.getfGAbsenYN());
////                        homess.add(homesss);
////                    }
//                    homeRealmHelper.deleteAllData();
////                    for (Home object: homeList) {
////                        homeRealmHelper.addArticle(object);
////                    }
//                            Observable.fromIterable(homeList).buffer(5)
//                                    .subscribe(new Observer<List<Home>>() {
//                                        @Override
//                                        public void onSubscribe(Disposable d) {
//                                        }
//                                        @Override
//                                        public void onNext(List<Home> homey) {
//                                            for (Home obj : homey) {
//                                                homeRealmHelper.addArticle(obj);
//                                            }
//                                        }
//                                        @Override
//                                        public void onError(Throwable e) {
//                                        }
//                                        @Override
//                                        public void onComplete() {
//                                            // Toast.makeText(getContext(),"realm inserted!",Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                }}
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//=======================================================================================================================================================

//        Call<List<Home>> call = apiService.getAllHomeNews(nik,"Bearer "+token);
//        call.enqueue(new Callback<List<Home>>() {
//            @Override
//            public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {
//                int statusCode = response.code();
//                List<Home> homes = response.body();
//                if(home.isEmpty()){ //db realm empty
//                    for (Home object: homes) {
//                        homeRealmHelper.addArticle(object);
//                    }
//                }
//                else{ //if DB not empty
//                    List<Home>homess = new ArrayList<>();
//                    for (HomeRealmModel object: home) {
//                        Home homesss = new Home(object.getEventId(), object.getEventName(), object.getEventType(), object.getExternalEventCode(), object.getEventDesc(), object.getEventImage(), object.getfGHasPasscodeYN(),"", object.getPasscode(), object.getfGHasSurveyYN(), object.getSurveyId(), object.getfGSurveyDoneYN(),object.getfGAbsenYN());
//                        homess.add(homesss);
//                    }
//                    homeRealmHelper.deleteAllData();
//                    for (Home object: homes) {
//                        homeRealmHelper.addArticle(object);
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<List<Home>> call, Throwable t) {
//                // Log error here since request failed
//                //Log.e(TAG, t.toString());
//            }
//        });
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

                            //=============== FORMAT QR YANG BISA DI SCAN {"eventId":"5","eventType":"Training"} ===================================//


                            Gson g = new Gson();
                            QrResultModel results = g.fromJson(result.getContents(), QrResultModel.class);

                            Toast.makeText(getContext(),results.getEventType(),Toast.LENGTH_LONG).show();

                            if(results.getEventType().equals("Training")){
                                HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getContext());
                                HomeRealmModel homie = homeRealmHelper.findArticle(results.getEventId());

                                if(homie.getEventType().equals(results.getEventType())){
                                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                    routingHomeDetailInterface.routingHomeDetail(results.getEventType(),getContext(),results.getEventId());
                                }
                                else{
                                    Toast.makeText(context,"No Training Found",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(results.getEventType().equals("Request")){
                                HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getContext());
                                HomeRealmModel homie = homeRealmHelper.findArticle(results.getEventId());

                                if(homie.getEventType().equals(results.getEventType())){
                                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                    routingHomeDetailInterface.routingHomeDetail(results.getEventType(),getContext(),results.getEventId());
                                }
                                else{
                                    Toast.makeText(context,"No Request Found",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(results.getEventType().equals("PA")){

                            }
                            else if(results.getEventType().equals("PK")){
                                usr = userRealmHelper.findAllArticle();
                                ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
//                                lnProgessBar.setVisibility(View.VISIBLE);

                            }



                        }
                        catch (Exception e){
                           // Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();

//                            Toast.makeText(getContext()," Result Not Found",Toast.LENGTH_SHORT).show();
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
         userList = new UserList();

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copy url
                if(ConnectivityReceiver.isConnected()) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingKPI("PA List tahunan", getContext(), userList, "","");
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
                    routingHomeDetailInterface.routingKPI("PA List", getContext(), userList, "","");
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


    @Override
    public void callAction(String moduleCode) {
        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();

        switch(moduleCode) {
            case "FPTK":
                break;
            case "HCSV":
                break;
            case "TRNG":
                routingHomeDetailInterface.routingHome("Event",context);
                break;
            case "PNKJ":
                if(ConnectivityReceiver.isConnected()) {
                    routingHomeDetailInterface.routingKPI("Penilaian", getContext(), userList, "","");
                }
                else{
                    Toast.makeText(context,"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                // code block
        }
    }
}
