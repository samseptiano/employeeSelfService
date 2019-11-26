package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIApproveListAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.ListDevPlanAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.CameraApp.AppExecutor;
import com.example.samuelseptiano.employeeselfservice.CameraApp.BitmapUtils;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.TotalFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPostPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIStatusModelPostPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.utils.FilePath;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KPIKuantitatifTahunanFragment extends Fragment implements KPIAdapterTahunan.EventListener, ListDevPlanAdapter.EventListener {

    public static KPIHeader smt1 = new KPIHeader();
    public static KPIHeader smt2 = new KPIHeader();
    public static KPIHeader initKH = new KPIHeader();


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.samuelseptiano.employeeselfservice.fileprovider";

    private AppExecutor mAppExcutor;

    private String mTempPhotoPath;

    private Bitmap mResultsBitmap;

    String SEMESTER = "1";

    KPIHeader kh;

    View rootView;
    RecyclerView recyclerViewKPI, recyclerViewImg;
    KPIAdapterTahunan kpiAdapterTahunan;
    List<KPIQuestions> lKPIQuestion;

    String mCurrentPhotoPath;

    private String pictureFilePath;

    Boolean connState, captureDone = false;
    UserList userList;
    String type="";
    public static String typeStatic;
    String semester ="";
    List<KPIQuestions> question;

    ImageButton btnSubmit, btnSave, btnApprove, imgExpandDetail;


    String isEditable="Y";

    TextView tvEmpName, tvEmpJobTitle, tvEmpBranchName,tvAtasan1, tvAtasan2,tvJobTitleAtasan1,tvJobTitleAtasan2, tvPurpose;
    LinearLayout lnEmpDetail;

    CircleImageView empPhoto;

    private static final int PICK_FILE_REQUEST = 2;

    int postTemp = -1;

    int pos=0;

    String Jenis="";

    String nik="";

    Activity activity;

    MaterialSpinner spinnerSemester;

    private List<ImageUploadModelPA> imgCaptureList = new ArrayList<>();

    public Boolean getConnState() {
        return connState;
    }

    public void setConnState(Boolean connState) {
        this.connState = connState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    int tabPos;
    ArrayList<UserRealmModel> usr;

    boolean isExpanded=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRetainInstance(true);
        requestRead();


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            setType(bundle.getString("KPIType"));
            typeStatic = bundle.getString("KPIType");
            try {
                userList = (UserList) bundle.getSerializable("id");
            }
            catch (Exception e){

            }
            Jenis = bundle.getString("jenisKPI");
            semester = bundle.getString("semester");
        }
        //Toast.makeText(getContext(),userList.getEmpNiK(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("");

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        usr = userRealmHelper.findAllArticle();


        if (rootView == null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            rootView = inflater.inflate(R.layout.fragment_kpi_kuantitatif_tahunan, container, false);

//            btnEdit = rootView.findViewById(R.id.imgBtnEdit);
            btnSubmit =  rootView.findViewById(R.id.imgBtnSubmit);
            btnSave =  rootView.findViewById(R.id.imgBtnSave);
            btnApprove =  rootView.findViewById(R.id.imgBtnApprove);

            tvAtasan1 = rootView.findViewById(R.id.tvAtasan1);
            tvAtasan2 = rootView.findViewById(R.id.tvAtasan2);
            tvEmpName = rootView.findViewById(R.id.tv_empName);
            tvEmpJobTitle = rootView.findViewById(R.id.tv_jobTitle);
            tvEmpBranchName = rootView.findViewById(R.id.tv_branchName);
            tvJobTitleAtasan1 = rootView.findViewById(R.id.tvJobTitleAtasan1);
            tvJobTitleAtasan2 = rootView.findViewById(R.id.tvJobTitleAtasan2);
            tvPurpose = rootView.findViewById(R.id.tvPurpose);
            spinnerSemester = rootView.findViewById(R.id.spinnerPeriode);
            spinnerSemester.setBackgroundResource(R.drawable.shapesignup);
            spinnerSemester.setPadding(25, 10, 25, 10);
            empPhoto = rootView.findViewById(R.id.empPhoto);
            imgExpandDetail = rootView.findViewById(R.id.imgExpandDetail);
            lnEmpDetail = rootView.findViewById(R.id.lnEmpDetail);

            if(type.equals("Approve")){
                btnSave.setVisibility(View.GONE);
                tvEmpName.setText(userList.getEmpName());
                tvEmpJobTitle.setText(userList.getJobTitleName());
                tvEmpBranchName.setText(userList.getDept()+" - Cab. "+userList.getBranchName());
                tvAtasan1.setText(userList.getNamaAtasanLangsung());
                tvAtasan2.setText(userList.getNamaAtasanTakLangsung());
                tvJobTitleAtasan1.setText("Example JobTitle Atasan 1");
                tvJobTitleAtasan2.setText("Example JobTitle Atasan 2");
                tvPurpose.setText("Example Purpose");
                nik = userList.getEmpNiK();
            }
            else{
                btnApprove.setVisibility(View.GONE);
                tvEmpName.setText(usr.get(0).getEmpName());
                tvEmpJobTitle.setText(usr.get(0).getJobTitleName());
                tvEmpBranchName.setText(usr.get(0).getDept()+" - Cab. "+usr.get(0).getBranchName());
                tvAtasan1.setText(usr.get(0).getNamaAtasanLangsung());
                tvAtasan2.setText(usr.get(0).getNamaAtasanTakLangsung());
                tvJobTitleAtasan1.setText("Example JobTitle Atasan 1");
                tvJobTitleAtasan2.setText("Example JobTitle Atasan 2");
                tvPurpose.setText("Example Purpose");
                nik = usr.get(0).getEmpNIK();
            }

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(),kh.getDevPlanHeaderList().get(0).getDevPlanDetail().get(2).getDEVPLANACHIEVEMENT(),Toast.LENGTH_LONG).show();

                    if(type.equals("Approve")){
                            List<KPIAnswer> kpiAnswerList = new ArrayList<>();

                            for(int i=0;i<initKH.getKpiQuestionsList().size();i++){
                                KPIAnswer kpiAnswer = new KPIAnswer();
                                if(initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")){
                                    kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                    kpiAnswer.setKPIID("0");
                                }
                                else{
                                    kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                    kpiAnswer.setCOMPID("0");
                                }
                                kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                                kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                                kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                                kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                                kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                                kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                                kpiAnswerList.add(kpiAnswer);
                                //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();
                            }
                            KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

                            kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                            kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                            kpiAnswerList1.setSTATUS(initKH.getStatus());
                            kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                            kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                            kpiAnswerList1.setDevPlanHeaderList(initKH.getDevPlanHeaderList());

                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                            Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1,"Bearer "+usr.get(0).getToken());
                            call.enqueue(new Callback<KPIAnswerList>() {
                                @Override
                                public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                                    int statusCode = response.code();
                                    //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getContext(), "Submitted Successfully!!", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                                    Toast.makeText(getContext(), "Submitted!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                    else{
                        List<KPIAnswer> kpiAnswerList = new ArrayList<>();
                        for(int i=0;i<initKH.getKpiQuestionsList().size();i++){
                            KPIAnswer kpiAnswer = new KPIAnswer();
                            if(initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")){
                                kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                kpiAnswer.setKPIID("0");
                            }
                            else{
                                kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                kpiAnswer.setCOMPID("0");
                            }
                            kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                            kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                            kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                            kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                            kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                            kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                            kpiAnswerList.add(kpiAnswer);
                            //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();
                        }
                        KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

                        kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                        kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                        kpiAnswerList1.setSTATUS(initKH.getStatus());
                        kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                        kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Call<KPIAnswerList> call = apiService.postStatusPA(kpiAnswerList1,"Bearer "+usr.get(0).getToken());
                        call.enqueue(new Callback<KPIAnswerList>() {
                            @Override
                            public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                                int statusCode = response.code();
                                //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getContext(), "Submitted Successfully!!", Toast.LENGTH_SHORT).show();
                                FragmentManager fm = getActivity()
                                        .getSupportFragmentManager();
                                fm.popBackStack ("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            }
                            @Override
                            public void onFailure(Call<KPIAnswerList> call, Throwable t) {

                                FragmentManager fm = getActivity()
                                        .getSupportFragmentManager();
                                fm.popBackStack ("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                Toast.makeText(getContext(), "Submitted!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<KPIAnswer> kpiAnswerList = new ArrayList<>();
                    for(int i=0;i<initKH.getKpiQuestionsList().size();i++){
                        KPIAnswer kpiAnswer = new KPIAnswer();
                        if(initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")){
                            kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                            kpiAnswer.setKPIID("0");
                        }
                        else{
                            kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                            kpiAnswer.setCOMPID("0");
                        }
                        kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                        kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                        kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                        kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                        kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                        kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                        kpiAnswerList.add(kpiAnswer);
                        //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();
                    }
                    KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

                    kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                    kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                    kpiAnswerList1.setSTATUS(initKH.getStatus());
                    kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                    kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<KPIAnswerList> call = apiService.postStatusPA(kpiAnswerList1,"Bearer "+usr.get(0).getToken());
                    call.enqueue(new Callback<KPIAnswerList>() {
                        @Override
                        public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                            int statusCode = response.code();
                            //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "Submitted Successfully!!", Toast.LENGTH_SHORT).show();
                            FragmentManager fm = getActivity()
                                    .getSupportFragmentManager();
                            fm.popBackStack ("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            Toast.makeText(getContext(), "Submitted!!", Toast.LENGTH_SHORT).show();

                        }
                        @Override
                        public void onFailure(Call<KPIAnswerList> call, Throwable t) {

                            FragmentManager fm = getActivity()
                                    .getSupportFragmentManager();
                            fm.popBackStack ("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                            Toast.makeText(getContext(), "Approved!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<KPIAnswer> kpiAnswerList = new ArrayList<>();
                    Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();

                    for(int i=0;i<initKH.getKpiQuestionsList().size();i++){
                        KPIAnswer kpiAnswer = new KPIAnswer();
                        if(initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")){
                            kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                            kpiAnswer.setKPIID("0");
                        }
                        else{
                            kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                            kpiAnswer.setCOMPID("0");
                        }
                        kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                        kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                        kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                        kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                        kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                        kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                        kpiAnswerList.add(kpiAnswer);
                        //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();
                    }
                    KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

                    kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                    kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                    kpiAnswerList1.setSTATUS(initKH.getStatus());
                    kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                    kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                    kpiAnswerList1.setDevPlanHeaderList(initKH.getDevPlanHeaderList());

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1,"Bearer "+usr.get(0).getToken());
                    call.enqueue(new Callback<KPIAnswerList>() {
                        @Override
                        public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                            int statusCode = response.code();
                            //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<KPIAnswerList> call, Throwable t) {
//                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });

            lnEmpDetail.setVisibility(View.GONE);


            imgExpandDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isExpanded){
                        lnEmpDetail.setVisibility(View.GONE);
                        isExpanded=false;
                        imgExpandDetail.setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_down));
                    }
                    else if(!isExpanded){
                        lnEmpDetail.setVisibility(View.VISIBLE);
                        isExpanded=true;
                        imgExpandDetail.setImageDrawable(getContext().getResources().getDrawable(R.drawable.expand_up));

                    }
                }
            });

            //================================ foto bawahan =======================
            try {
                //Toast.makeText(context,userList.getEmpPhoto(),Toast.LENGTH_LONG).show();
                byte [] encodeByte= Base64.decode(userList.getEmpPhoto(),Base64.DEFAULT);
                Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

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

                empPhoto.setImageBitmap(bitmap);
            } catch(Exception e) {
                e.getMessage();
                //return null;
            }
            //===========================================================================

            recyclerViewKPI = (RecyclerView) rootView.findViewById(R.id.recyclerViewKPI);
            fillKPI();
            onSaveInstanceState(savedInstanceState);
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove _rootView from the existing parent view group
            // in onDestroyView() (it will be added back).
        }

        //Toast.makeText(getContext(),tabPos,Toast.LENGTH_SHORT).show();

        return rootView;
    }
    private void fillKPI() {

            //Edit Button
        //        btnEdit.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                if (isEditable.equals("N")){
        //                    isEditable = "Y";
        //                    kpiAdapterTahunan.refresh();
        //                    Toast.makeText(getContext(),"Edit On", Toast.LENGTH_SHORT).show();
        //                }
        //                else if(isEditable.equals("Y")) {
        //                    isEditable = "N";
        //                    kpiAdapterTahunan.refresh();
        //                    Toast.makeText(getContext(),"Edit Off", Toast.LENGTH_SHORT).show();
        //
        //                }
        //            }
        //        });

        List<String> tahuns = new ArrayList<String>();
        tahuns.add("Semester 1");
        tahuns.add("Semester 2");

        spinnerSemester.setItems(tahuns);

        spinnerSemester.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (item.toString().equals("Semester 1")) {
                    SEMESTER="1";
                    kpiAdapterTahunan.refresh();
                }
                else{
                    SEMESTER="2";
                    kpiAdapterTahunan.refresh();
                }
            }
        });

        //=============================  MASIH HARDCODE  =============================================================
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<KPIHeader> call = apiService.getKPIHeaderPA(nik, Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<KPIHeader>() {
            @Override
            public void onResponse(Call<KPIHeader> call, Response<KPIHeader> response) {

                int statusCode = response.code();
                try{
                kh = new KPIHeader();
                kh.setDevPlanHeaderList(response.body().getDevPlanHeaderList());
                for(int i=0;i<kh.getDevPlanHeaderList().size();i++){
                    kh.getDevPlanHeaderList().get(i).setDevPlanDetail(response.body().getDevPlanHeaderList().get(i).getDevPlanDetail());
                }
                kh.setMasterDevelopmentPlanList(response.body().getMasterDevelopmentPlanList());
                kh.setBobot(response.body().getBobot());
                kh.setSemester(semester);
                kh.setTahun(response.body().getTahun());
                kh.setStatus(response.body().getStatus());
                kh.setStrength(response.body().getStrength());
                kh.setStatus1(response.body().getStatus1());
                kh.setStatus2(response.body().getStatus2());

                lKPIQuestion = new ArrayList<>();
                int j=0;
                for(int i=0;i<response.body().getKpiQuestionsList().size();i++){
                    if(i>=1 && !response.body().getKpiQuestionsList().get(i).getSemester().equals(response.body().getKpiQuestionsList().get(i-1).getSemester())){
                        j=0;
                    }
                    KPIQuestions kq = new KPIQuestions();

                    //Set<KPIHint> set = new HashSet<>(response.body().getKpiQuestionsList().get(i).getHint());

                    kq.setHint((response.body().getKpiQuestionsList().get(i).getHint()));
                    kq.setBobot(response.body().getKpiQuestionsList().get(i).getBobot());
                    kq.setKPIcategory(response.body().getKpiQuestionsList().get(i).getKPIcategory());
                    kq.setPerspective(response.body().getKpiQuestionsList().get(i).getPerspective());
                    kq.setKPIDesc(response.body().getKpiQuestionsList().get(i).getKPIDesc());
                    kq.setAnswered(true);
                    kq.setCheckedId(Integer.parseInt(response.body().getKpiQuestionsList().get(i).getCP()));
                    kq.setPaId(response.body().getKpiQuestionsList().get(i).getPaId());
                    kq.setKpiId(response.body().getKpiQuestionsList().get(i).getKpiId());
                    kq.setSemester(response.body().getKpiQuestionsList().get(i).getSemester());
                    kq.setAnsweredPendukung(true);
                    kq.setAnsweredPenghambat(true);
                    kq.setAnsweredCatatan(true);
                    kq.setEvidence(response.body().getKpiQuestionsList().get(i).getEvidence());
                    kq.setCatatanLain("");
                    kq.setPendukung("");
                    kq.setPenghambat("");
                    kq.setIdKPI(Integer.toString(j+1));
                    lKPIQuestion.add(kq);
                    j++;
                }
                kh.setKpiQuestionsList(lKPIQuestion);

                if(getType().equals("Approve")){

                    //================= DUMMY ====================================
                    kh.setCompany(userList.getCompanyName());
                    kh.setLocationName(userList.getLocationName());

                    kh.setEmpName(userList.getEmpName());
                    kh.setNIK(userList.getEmpNiK());
                    kh.setDept(userList.getDept());
                    kh.setAtasanLangsung(userList.getNamaAtasanLangsung());
                    kh.setNIKAtasanLangsung(userList.getNIKAtasanLangsung());
                    kh.setAtasanTakLangsung(userList.getNamaAtasanTakLangsung());
                    kh.setNIKAtasanTakLangsung(userList.getNIKAtasanTakLangsung());
                    kh.setBranchName(userList.getBranchName());
                    kh.setJobTitleName(userList.getJobTitleName());
                }
                else {
                    kh.setCompany(usr.get(0).getCompanyName());
                    kh.setLocationName(usr.get(0).getLocationName());
                    kh.setEmpName(usr.get(0).getEmpName());
                    kh.setNIK(usr.get(0).getEmpNIK());
                    kh.setDept(usr.get(0).getDept());
                    kh.setBranchName(usr.get(0).getBranchName());
                    kh.setJobTitleName(usr.get(0).getJobTitleName());

                    //================= DUMMY ====================================
                    kh.setAtasanTakLangsung("Rudy Djajasaputra");
                    kh.setNIKAtasanTakLangsung("070500332");
                    //kh.setNIKAtasanLangsung(usr.get(0).getNikAtasanLangsung());
                    kh.setAtasanLangsung("Norish Hanoch");
                    kh.setNIKAtasanLangsung("030300324");
                    kh.setNIKAtasanTakLangsung(usr.get(0).getNikAtasanTakLangsung());
                }

                setInitKh(kh);
                initQuestionsAdapter(kh);}
                catch (Exception e){

                }
                //Toast.makeText(getContext(), kh.getTahun(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<KPIHeader> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

    private void initQuestionsAdapter(KPIHeader kh) {
        recyclerViewKPI.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewQuestions.setHasFixedSize(true);
        if(getType().equals("Approve")) {
            if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {

                if (kh.getStatus().equals("A") || kh.getStatus().equals("C")) {
                    isEditable = "N";
                    btnSubmit.setEnabled(false);
                    btnApprove.setEnabled(false);
                } else {
                    isEditable = "Y";
                    btnSubmit.setEnabled(true);
                    btnApprove.setEnabled(true);
                }

            } else if (kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK())) {
                if (kh.getStatus().equals("C")) {
                    isEditable = "N";
                    btnSubmit.setEnabled(false);
                    btnApprove.setEnabled(false);
                } else {
                    isEditable = "Y";
                    btnSubmit.setEnabled(true);
                    btnApprove.setEnabled(true);
                }
            }
            kpiAdapterTahunan = new KPIAdapterTahunan(getActivity(), getContext(), kh, userList.getEmpNiK(), getType(), this, Jenis);
        }
        else{
            if(kh.getStatus().equals("S") || kh.getStatus().equals("A") || kh.getStatus().equals("C")){
                isEditable="N";
                btnSubmit.setEnabled(false);
                btnSave.setEnabled(false);
            }
            else{
                isEditable="Y";
                btnSubmit.setEnabled(true);
                btnSave.setEnabled(true);
            }
            kpiAdapterTahunan = new KPIAdapterTahunan(getActivity(), getContext(), kh, usr.get(0).getEmpNIK(), getType(), this, Jenis);
        }

        recyclerViewKPI.setAdapter(kpiAdapterTahunan);
        //Toast.makeText(getContext(),kh.getCompany(),Toast.LENGTH_SHORT).show();

    }


    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("*/*"); // intent.setType("video/*"); to select videos to upload
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_FILE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK)
        {
            try {

                // Resample the saved image to fit the ImageView
                mResultsBitmap = BitmapUtils.resamplePic(getContext(), mTempPhotoPath);

                //galleryAddPic();
                if (pos != postTemp) {
                    if (question.get(pos).getPhotoCapture() != null) {
                        imgCaptureList = new ArrayList<>();
                        for (int i = 0; i < question.get(pos).getPhotoCapture().size(); i++) {
                            imgCaptureList.add(question.get(pos).getPhotoCapture().get(i));
                        }
                        // Toast.makeText(getContext(),"not null", Toast.LENGTH_LONG).show();
                    } else {
                        imgCaptureList = new ArrayList<>();
                    }

                }
                ImageUploadModelPA ium = new ImageUploadModelPA();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BitmapFactory.decodeFile(mTempPhotoPath).compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] b = baos.toByteArray();
                String temp = Base64.encodeToString(b, Base64.DEFAULT);
//                String temp = "";
//                //============================================================================

                captureDone = true;

                ium.setFileExt(".jpg");
                ium.setImgString(temp);
                Toast.makeText(getContext(),"Document Uploaded!",Toast.LENGTH_LONG).show();

                ium.setFilename(mTempPhotoPath);



                imgCaptureList.add(ium);

                question.get(pos).setPhotoCapture(imgCaptureList);

                //Toast.makeText( getContext(),Integer.toString(question.get(pos).getPhotoCapture().size()),Toast.LENGTH_LONG).show();

                postTemp = pos;
                if (kpiAdapterTahunan != null) {
                    kpiAdapterTahunan.currentPosition = pos+1;
//                    kpiAdapterTahunan.isVisible = true;
                    kpiAdapterTahunan.notifyDataSetChanged();
                    kpiAdapterTahunan.dialog.dismiss();
                    kpiAdapterTahunan.showUploadDialog( question.get(pos).getPhotoCapture(),pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());

                }

                recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerViewImg.setLayoutManager(mLayoutManager);
                KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity,"Y",question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());
                recyclerViewImg.setAdapter(adapter);

////
//                //cara buat imgCaptureList setiap beda recycler view rest jadi null
            }
            catch (Exception e){
                Toast.makeText(getContext(), "errore: "+e.toString(), Toast.LENGTH_LONG).show();

            }
        }

        else if(requestCode == PICK_FILE_REQUEST){
            String temp ="";
            try {

                if (pos != postTemp) {
                    if (question.get(pos).getPhotoCapture() != null) {
                        imgCaptureList = new ArrayList<>();
                        for (int i = 0; i < question.get(pos).getPhotoCapture().size(); i++) {
                            imgCaptureList.add(question.get(pos).getPhotoCapture().get(i));
                        }
                        // Toast.makeText(getContext(),"not null", Toast.LENGTH_LONG).show();
                    } else {
                        imgCaptureList = new ArrayList<>();
                    }

                }
                ImageUploadModelPA ium = new ImageUploadModelPA();

                Uri selectedImageUri = data.getData();
                String imagepath = FilePath.getPath(getContext(), selectedImageUri);
                String extension = imagepath.substring(imagepath.lastIndexOf("."));
                BitmapFactory.Options options = new BitmapFactory.Options();

                Toast.makeText(getContext(),imagepath,Toast.LENGTH_LONG).show();

                // down sizing image as it throws OutOfMemory Exception for larger images
                // options.inSampleSize = 10;

//
                if(extension.equals(".pdf")){
                    File originalFile = new File(imagepath);
                    temp = null;
                    try {
                        FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
                        byte[] bytes = new byte[(int)originalFile.length()];
                        fileInputStreamReader.read(bytes);
                        temp = new String(Base64.encodeToString(bytes,Base64.DEFAULT));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(extension.equals(".jpg") ||extension.equals(".png") ||extension.equals(".jpeg") || extension.equals(".bmp") || extension.equals(".gif")){
                    //============ image =======================
                        final Bitmap bitmap = BitmapFactory.decodeFile(imagepath, options);
                        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
                        byte [] b=baos.toByteArray();
                        temp=Base64.encodeToString(b, Base64.DEFAULT);
                    //===========================================
                }

                captureDone = true;
                ium.setFileExt(extension);
                ium.setImgString(temp);
                ium.setFilename(imagepath);
                Toast.makeText(getContext(),"Document Uploaded!",Toast.LENGTH_LONG).show();
                imgCaptureList.add(ium);


                ImageUploadModelPostPA imageUploadModelPostPA = new ImageUploadModelPostPA();
                imageUploadModelPostPA.setEmpNIK(usr.get(0).getEmpNIK());
                imageUploadModelPostPA.setPaId(question.get(pos).getPaId());
                if(question.get(pos).getKPIcategory().equals("KUALITATIF")){
                    imageUploadModelPostPA.setCompId(question.get(pos).getKpiId());
                    imageUploadModelPostPA.setKpiId("");
                }
                else{
                    imageUploadModelPostPA.setKpiId(question.get(pos).getKpiId());
                    imageUploadModelPostPA.setCompId("");
                }
                imageUploadModelPostPA.setSemester(question.get(pos).getSemester());
                imageUploadModelPostPA.setFileExt(extension);
                imageUploadModelPostPA.setFilename(imagepath);
                imageUploadModelPostPA.setImgString(temp);


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ImageUploadModelPostPA> call = apiService.postKPIEvidencePA(imageUploadModelPostPA,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<ImageUploadModelPostPA>() {
                    @Override
                    public void onResponse(Call<ImageUploadModelPostPA> call, Response<ImageUploadModelPostPA> response) {
                        int statusCode = response.code();

                        question.get(pos).setPhotoCapture(imgCaptureList);
                        postTemp = pos;
                        if (kpiAdapterTahunan != null) {
                            kpiAdapterTahunan.currentPosition = pos+1;
//                    kpiAdapterTahunan.isVisible = true;
                            kpiAdapterTahunan.notifyDataSetChanged();
                            kpiAdapterTahunan.dialog.dismiss();
                            kpiAdapterTahunan.showUploadDialog( question.get(pos).getPhotoCapture(),pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());
                        }

                        recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewImg.setLayoutManager(mLayoutManager);
                        KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity, "Y",question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());

                        recyclerViewImg.setAdapter(adapter);

                    }
                    @Override
                    public void onFailure(Call<ImageUploadModelPostPA> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                        question.get(pos).setPhotoCapture(imgCaptureList);
                        postTemp = pos;
                        if (kpiAdapterTahunan != null) {
                            kpiAdapterTahunan.currentPosition = pos+1;
//                    kpiAdapterTahunan.isVisible = true;
                            kpiAdapterTahunan.notifyDataSetChanged();
                            kpiAdapterTahunan.dialog.dismiss();
                            kpiAdapterTahunan.showUploadDialog( question.get(pos).getPhotoCapture(),pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());
                        }

                        recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerViewImg.setLayoutManager(mLayoutManager);
                        KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity, "Y",question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());
                        recyclerViewImg.setAdapter(adapter);
                    }
                });

            }
            catch (Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    /**
     * requestPermissions and do something
     *
     */
    public void requestRead() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            readFile();
        }
    }



    public void setInitKh(KPIHeader kh){
        initKH = kh;
    }
    public KPIHeader getInitKh(){
            return initKH;
    }

    /**
     * do you want to do
     */
    public void readFile() {
        // do something
    }

    /**
     * onRequestPermissionsResult
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFile();
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onEvent(int position, List<KPIQuestions> questions, int action) {

        if(action == 0){
            showFileChooser();
        }
        else {
            dispatchTakePictureIntent();
        }
        pos = position;
        question = questions;
    }

    @Override
    public List<ImageUploadModelPA> onResult() {
        return imgCaptureList;
    }

    @Override
    public void redirect (int tabPos) {
        ViewPager vp = getActivity().findViewById(R.id.KPIViewPager);
        vp.setCurrentItem(tabPos);
    }

    @Override
    public String isEditables() {
        return isEditable;
    }

    @Override
    public void setEditables(String edit, KPIHeaderPJ khh) {

        fillKPI();
        kpiAdapterTahunan.notifyDataSetChanged();
    }


    @Override
    public String getSemster() {
        return SEMESTER;
    }

    @Override
    public void setSemester(String semester, KPIHeaderPJ khh) {
        SEMESTER = semester;
        fillKPI();
        kpiAdapterTahunan.notifyDataSetChanged();
    }

    @Override
    public void setQuestion(KPIHeader a, int semester) {
        if(semester==1){
            smt1 = a;
         }
        else{
            smt2 = a;
        }
    }

    @Override
    public void getQuestionSmt() {
        try{
            Toast.makeText(getContext(),Integer.toString(smt1.getKpiQuestionsList().get(0).getCheckedId())+" "+Integer.toString(smt2.getKpiQuestionsList().get(0).getCheckedId()), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(getContext(),"Field Masih Kosong.. Silahkan Isi KPI Anda",Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public KPIHeader getQuestionSmt1() {
        return smt1;
    }

    public void resetQuestion(){
        smt1 = new KPIHeader();
        smt2 = new KPIHeader();
    }

    @Override
    public KPIHeader getQuestionSmt2() {
        return smt2;
    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Pilih Aksi");
        String[] pictureDialogItems = {
                "Pilih dokumen dari media penyimpanan",
                "Ambil gambar dari kamera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showFileChooser();
                                break;
                            case 1:
                                try {

                                    dispatchTakePictureIntent();

                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(), "error: "+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void dispatchTakePictureIntent() {

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // If you do not have permission, request it
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            // Launch the camera if the permission exists
            launchCamera();
        }

    }



    private void launchCamera() {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(getContext());
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();
                mCurrentPhotoPath = photoFile.getName();
                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }




}
