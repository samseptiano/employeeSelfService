package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.EventSessionAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.QuizFragment.QuizFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.EventSessionRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSession;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.EventSessionRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class HomeDetailFragment extends Fragment {
    private ScrollView mRelativeLayout;
    private PopupWindow mPopupWindow;

    View rootView;
    Context context;
    TextView newsAuthor, newsDate, newsDesc;
    ImageView newsPoster;
    Button btnSurvey,btnAbsence;
    ImageButton btnDownload;
    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    EventSessionAdapter hAdapter;
    LinearLayout lnProgress, lnProgressEventSession;

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
        newsPoster = (ImageView) rootView.findViewById(R.id.news_image);
        newsAuthor = (TextView) rootView.findViewById(R.id.tv_news_author);
        newsDate = (TextView) rootView.findViewById(R.id.tv_news_date);
        newsDesc = (TextView) rootView.findViewById(R.id.tv_news_detail);

        btnAbsence = (Button) rootView.findViewById(R.id.btnAbsence);
        btnSurvey = (Button) rootView.findViewById(R.id.btnSurvey);
        btnDownload = (ImageButton) rootView.findViewById(R.id.btnDownload);
        lnProgress = rootView.findViewById(R.id.linlaHeaderProgressHomeDet);
        lnProgressEventSession = rootView.findViewById(R.id.linlaHeaderProgressEventSession);

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
            lnProgress.setVisibility(View.VISIBLE);
            //showToast(ConnectivityReceiver.isConnected());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            String id = getIdNews();
            Toast.makeText(getContext(),"id event: "+id,Toast.LENGTH_SHORT).show();

            Call<List<Home>> call = apiService.getHomeNews(usr.get(0).getEmpNIK(),id ,"Bearer "+RToken);
            call.enqueue(new Callback<List<Home>>() {
                @Override
                public void onResponse(Call<List<Home>> call, Response<List<Home>> response) {
                    lnProgress.setVisibility(View.GONE);


                    try{

                        int statusCode = response.code();
                        List<Home> homee = response.body();
                        //Toast.makeText(getContext(),"id: "+homee.getfGAllSurveyDoneYN(),Toast.LENGTH_SHORT).show();


                        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(homee.get(0).getEventName());
//                    newsDate.setText("Posted at: "+Calendar.getInstance().getTime().toString());
                        newsDate.setText("Diposting pada: -");
                        newsDesc.setText(homee.get(0).getEventDesc());
//                        Glide.with(getContext())
//                                .load(homee.get(0).getEventImage())
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .centerCrop()
//                                .error(R.drawable.imgalt)
//                                .into(newsPoster);

                        //================================
                        try {
                            byte [] encodeByte=Base64.decode(homee.get(0).getEventImage(),Base64.DEFAULT);
                            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                            Display display = getActivity().getWindowManager().getDefaultDisplay();
                            Point size = new Point();
                            display.getSize(size);

                            if(size.x >1081 ){
                                int imageWidth = bitmap.getWidth();
                                int imageHeight = bitmap.getHeight();

                                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                                //Point size = new Point();
                                display.getSize(size);
                                int width = size.x - (size.x/3);
                                int height = size.y - (size.y/3);

                                int newWidth = width; //this method should return the width of device screen.
                                float scaleFactor = (float)newWidth/(float)imageWidth;
                                int newHeight = (int)(imageHeight * scaleFactor);

                                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
                            }
                            else{
                                int imageWidth = bitmap.getWidth();
                                int imageHeight = bitmap.getHeight();

                                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                                //Point size = new Point();
                                display.getSize(size);
                                int width = size.x - (size.x/3);
                                int height = size.y - (size.y/3);

                                int newWidth = width; //this method should return the width of device screen.
                                float scaleFactor = (float)newWidth/(float)imageWidth;
                                int newHeight = (int)(imageHeight * scaleFactor);

                                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
                            }

                            newsPoster.setImageBitmap(bitmap);



                        } catch(Exception e) {
                            e.getMessage();
                            //return null;
                        }
                        //================================

                        if(homee.get(0).getfGAbsenYN().equals("N")){
                            btnAbsence.setVisibility(View.VISIBLE);
                            btnAbsence.setEnabled(true);
                        }

                        if(homee.get(0).getfGHasSurveyYN().equals("Y")  && !homee.get(0).getSurveyId().equals("0") && !homee.get(0).getfGSurveyDoneYN().equals("Y")){
                            btnSurvey.setVisibility(View.VISIBLE);
                            btnSurvey.setEnabled(true);
                        }

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
                            //Toast.makeText(getContext(),"Dari Realm", Toast.LENGTH_SHORT).show();
                        }


                        //=========
                        Call<List<EventSession>> call2 = apiService.getEventSessionID(id,nik,"Bearer "+RToken);
                        lnProgressEventSession.setVisibility(View.VISIBLE);
                        call2.enqueue(new Callback<List<EventSession>>() {
                            @Override
                            public void onResponse(Call<List<EventSession>> call, Response<List<EventSession>> response) {
                                List<EventSession> res = response.body();
//                            hAdapter = new EventSessionAdapter(res,getContext(),isConnState(), homie);
//                            recyclerView.setAdapter(hAdapter);
                                lnProgressEventSession.setVisibility(View.GONE);
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
                                    //Toast.makeText(getContext(),"Dari Realm", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<List<EventSession>> call, Throwable t) {
                                // Log error here since request failed
                                lnProgressEventSession.setVisibility(View.GONE);
                                Toast.makeText(getContext(),t.toString(), Toast.LENGTH_SHORT).show();
                                //Log.e(TAG, t.toString());
                            }
                        });
                    }
                    catch(Exception e){
                        getActivity().getSupportFragmentManager().popBackStack();
                        Toast.makeText(getContext(),"No Event Found", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<List<Home>> call, Throwable t) {
                    // Log error here since request failed
                    lnProgress.setVisibility(View.GONE);

                    Toast.makeText(getContext(),t.toString(), Toast.LENGTH_SHORT).show();
                    //Log.e(TAG, t.toString());
                }
            });
        }
        else{

            try{
                lnProgress.setVisibility(View.VISIBLE);
                showToast(ConnectivityReceiver.isConnected());
                //collapsingToolbarLayout.setTitle(homie.getEventName());
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(homie.getEventName());
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
                lnProgress.setVisibility(View.GONE);

            }
            catch(Exception e){
                lnProgress.setVisibility(View.GONE);

                getActivity().getSupportFragmentManager().popBackStack();
                    Toast.makeText(getContext(), "No Event Found", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(getContext(),"homie",Toast.LENGTH_SHORT).show();
        }

        btnAbsence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    absent(homie.getEventId());
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> call = apiService2.getCertificate(usr.get(0).getEmpNIK(),getIdNews(), "Bearer " + RToken);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


//                        File file = new File("retrofit-2.0.0-beta2.pdf");
//                        try {
//                            file.createNewFile();
//                            Files.asByteSink(file).write(response.body().bytes());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }



                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try  {
                                    //Your code goes here
                                    //Toast.makeText(context, "File Berhasil Didownloaded!", Toast.LENGTH_SHORT).show();
                                    try {
                                        File extDir = Environment.getExternalStorageDirectory();
                                        FileOutputStream os;
                                        os = new FileOutputStream(extDir.getAbsolutePath()+"/Download/certificate.pdf", false);
                                        os.write(response.body().bytes());
                                        os.flush();
                                        os.close();
                                        Toast.makeText(context, "File Berhasil Didownloaded! "+Integer.toString(response.code()), Toast.LENGTH_SHORT).show();
                                    }
                                    catch (Exception e){
                                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        thread.start();
                        Toast.makeText(context, "File Berhasil Didownloaded!", Toast.LENGTH_SHORT).show();




                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(getContext(), "Error: " + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


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

                Toast.makeText(getContext(), "Quiz Multiple Choice Area", Toast.LENGTH_SHORT).show();

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


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(File.separator + "Certificate.pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("File Download: " , fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


    private void absent(String eventId){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();

        UserRealmHelper userRealmHelper = new UserRealmHelper(context);
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        EventAbsentUser eventAbsentUser = new EventAbsentUser();

        eventAbsentUser.setEmpNIK(usr.get(0).getEmpNIK());
        //eventAbsentUser.setEPID(0);
        eventAbsentUser.setEventDate(dateFormat.format(date));
        eventAbsentUser.setEventID(eventId);
        eventAbsentUser.setFgActiveYN(usr.get(0).getfGActiveYN());
        eventAbsentUser.setLastUpdate(dateFormat.format(date));
        eventAbsentUser.setStatusHadirYN("Y");
        eventAbsentUser.setLastUpdateBy(usr.get(0).getUsername());


        Call<EventAbsentUser> call = apiService.postEventAbsentUser(eventAbsentUser, "Bearer " + RToken);
        call.enqueue(new Callback<EventAbsentUser>() {
            @Override
            public void onResponse(Call<EventAbsentUser> call, Response<EventAbsentUser> response) {
                int statusCode = response.code();
                Toast.makeText(context, "Absence Inserted Successfully! " + Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<EventAbsentUser> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                //Log.e(TAG, t.toString());
            }
        });
    }


}

