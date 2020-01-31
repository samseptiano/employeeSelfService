package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.ChatAdapter.InboxAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.ListDevPlanAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter.EmployeeSetupPAAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.CameraApp.AppExecutor;
import com.example.samuelseptiano.employeeselfservice.CameraApp.BitmapUtils;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPostPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.PAPeriodeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.EmailSentModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.InboxChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.utils.FilePath;
import com.example.samuelseptiano.employeeselfservice.utils.FileUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KPIKuantitatifTahunanFragment extends Fragment implements KPIAdapterTahunan.EventListener{

    public static KPIHeader smt1 = new KPIHeader();
    public static KPIHeader smt2 = new KPIHeader();
    public static KPIHeader initKH = new KPIHeader();

    private DatabaseReference root;

    ImageView imgNoResult;

    List<PAPeriodeModel> paPeriodeModelList = new ArrayList<>();

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

    List<InboxChatModel> lInbox;

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.samuelseptiano.employeeselfservice.fileprovider";

    private AppExecutor mAppExcutor;

    LinearLayout lnProgressBar;

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

    boolean diUbah=false;

    int pos=0;

    String Jenis="";

    String nik="";
    String tahun  = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

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

            tahun = bundle.getString("tahun");
            Jenis = bundle.getString("jenisKPI");
            semester = bundle.getString("semester");
        }
        //Toast.makeText(getContext(),userList.getEmpNiK(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_back_arrow);
        actionBar.setTitle("");

        actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    //================================================
//                    if (getType().equals("Approve")) {
//
//                        if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {
//
//                            if (kh.getStatus().equals("A") || kh.getStatus().equals("C")) {
//                                getActivity().getSupportFragmentManager().popBackStack();
//
//                            } else {
//
//                                if(!diUbah){
//                                    getActivity().getSupportFragmentManager().popBackStack();
//                                }
//                                else {
//                                    AlertDialog alertbox = new AlertDialog.Builder(getContext())
//                                            .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                                // do something when the button is clicked
//                                                public void onClick(DialogInterface arg0, int arg1) {
//                                                    Context context = getContext();
//                                                    SaveKPI(context);
//                                                    getActivity().getSupportFragmentManager().popBackStack();
//                                                    //close();
//                                                }
//                                            })
//                                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                                // do something when the button is clicked
//                                                public void onClick(DialogInterface arg0, int arg1) {
//                                                    getActivity().getSupportFragmentManager().popBackStack();
//                                                }
//                                            })
//                                            .show();
//                                }}
//
//                        } else if (kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK())) {
//                            if (kh.getStatus().equals("C")) {
//                                isEditable = "N";
//                                getActivity().getSupportFragmentManager().popBackStack();
//
//                            } else {
//                                isEditable = "Y";
//                                if(!diUbah){
//                                    getActivity().getSupportFragmentManager().popBackStack();
//                                }
//                                else {
//                                    AlertDialog alertbox = new AlertDialog.Builder(getContext())
//                                            .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                                // do something when the button is clicked
//                                                public void onClick(DialogInterface arg0, int arg1) {
//                                                    Context context = getContext();
//                                                    SaveKPI(context);
//                                                    getActivity().getSupportFragmentManager().popBackStack();
//                                                    //close();
//                                                }
//                                            })
//                                            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                                // do something when the button is clicked
//                                                public void onClick(DialogInterface arg0, int arg1) {
//                                                    getActivity().getSupportFragmentManager().popBackStack();
//                                                }
//                                            })
//                                            .show();
//                                }
//
//                            }
//                        }
//                    } else {
//                        if (kh.getStatus().equals("S") || kh.getStatus().equals("A") || kh.getStatus().equals("C")) {
//                            isEditable = "N";
//                            getActivity().getSupportFragmentManager().popBackStack();
//
//                        } else {
//                            isEditable = "Y";
//                            if(!diUbah){
//                                getActivity().getSupportFragmentManager().popBackStack();
//                            }
//                            else {
//                                AlertDialog alertbox = new AlertDialog.Builder(getContext())
//                                        .setMessage("Apakah Anda Ingin Menyimpan Penilaian Sebelum Keluar Dari Menu Ini?")
//                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//
//                                            // do something when the button is clicked
//                                            public void onClick(DialogInterface arg0, int arg1) {
//                                                Context context = getContext();
//                                                SaveKPI(context);
//                                                getActivity().getSupportFragmentManager().popBackStack();
//                                                //close();
//                                            }
//                                        })
//                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                            // do something when the button is clicked
//                                            public void onClick(DialogInterface arg0, int arg1) {
//                                                getActivity().getSupportFragmentManager().popBackStack();
//                                            }
//                                        })
//                                        .show();
//                            }
//                        }
//                    }

                    //================================================
                    if((kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK()) && kh.getStatus().equals("C"))){
                        getActivity().getSupportFragmentManager().popBackStack();

                    }
                    else if((kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK()) && (kh.getStatus().equals("A")|| kh.getStatus().equals("C")))){
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    else if((kh.getNIK().equals(usr.get(0).getEmpNIK()) && (kh.getStatus().equals("S") || kh.getStatus().equals("A") || kh.getStatus().equals("C")))){
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                    else if(!diUbah){
                        getActivity().getSupportFragmentManager().popBackStack();

                    }
                    else{
                        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                .setMessage("Apakah anda ingin menyimpan penilaian yang telah diisi sebelum keluar dari menu ini??")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Context context = getContext();
                                        SaveKPI(context);
                                        getActivity().getSupportFragmentManager().popBackStack();
                                        //close();
                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        getActivity().getSupportFragmentManager().popBackStack();
                                    }
                                })
                                .show();
                        alertbox.setOnShowListener( new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey));
                                alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

                            }
                        });
                    }
                }
                catch (Exception e){
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        lInbox = new ArrayList<>();

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        usr = userRealmHelper.findAllArticle();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());




        if (rootView == null) {

            rootView = inflater.inflate(R.layout.fragment_kpi_kuantitatif_tahunan, container, false);
            imgNoResult = rootView.findViewById(R.id.imgNoResult);
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
            spinnerSemester.setBackgroundResource(R.drawable.shapedropdown);
            spinnerSemester.setPadding(25, 10, 25, 10);
            empPhoto = rootView.findViewById(R.id.empPhoto);
            imgExpandDetail = rootView.findViewById(R.id.imgExpandDetail);
            lnEmpDetail = rootView.findViewById(R.id.lnEmpDetail);
            lnProgressBar = rootView.findViewById(R.id.linlaHeaderProgressPA);
            if(type.equals("Approve")){
                btnSubmit.setVisibility(View.GONE);
                tvEmpName.setText(userList.getEmpName());
                tvEmpJobTitle.setText(userList.getJobTitleName());
                tvEmpBranchName.setText(userList.getOrgName());
                tvAtasan1.setText(userList.getNamaAtasanLangsung());
                tvAtasan2.setText(userList.getNamaAtasanTakLangsung());
                tvJobTitleAtasan1.setText(userList.getJobTitleAtasan1());
                tvJobTitleAtasan2.setText(userList.getJobTitleAtasan2());
                tvPurpose.setText("Penilaian Tahunan");
                nik = userList.getEmpNiK();
            }
            else{
                btnApprove.setVisibility(View.GONE);
                tvEmpName.setText(usr.get(0).getEmpName());
                tvEmpJobTitle.setText(usr.get(0).getJobTitleName());
                tvEmpBranchName.setText(usr.get(0).getOrgName());
                tvAtasan1.setText(usr.get(0).getNamaAtasanLangsung());
                tvAtasan2.setText(usr.get(0).getNamaAtasanTakLangsung());
                tvJobTitleAtasan1.setText(usr.get(0).getJobTitleAtasan1());
                tvJobTitleAtasan2.setText(usr.get(0).getJobTitleAtasan2());
                tvPurpose.setText("Penilaian Tahunan");
                nik = usr.get(0).getEmpNIK();
            }



            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressDialog pd = new ProgressDialog(getContext());
                    pd.setMessage("Submitting...");
                    int ctr = 0;
                    float total=0;
                    pd.setCancelable(false);
                    pd.show();
                    KPIAnswerList kpiAnswerList1 = new KPIAnswerList();
//                    lnProgressBar.setVisibility(View.VISIBLE);
//                    recyclerViewKPI.setVisibility(View.INVISIBLE);
//                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.white));
                    //Toast.makeText(getContext(),kh.getDevPlanHeaderList().get(0).getDevPlanDetail().get(2).getDEVPLANACHIEVEMENT(),Toast.LENGTH_LONG).show();

                    List<KPIAnswer> kpiAnswerList = new ArrayList<>();
                    for (int i = 0; i < initKH.getKpiQuestionsList().size(); i++) {
                        KPIAnswer kpiAnswer = new KPIAnswer();
                        if (initKH.getKpiQuestionsList().get(i).getSemester().equals("1") && initKH.getKpiQuestionsList().get(i).getCheckedId() < 1) {
                            lnProgressBar.setVisibility(View.GONE);
                            pd.dismiss();
//                                recyclerViewKPI.setVisibility(View.VISIBLE);
//                                recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));
                            ctr++;
                            Toast.makeText(getContext(), "Masih ada penilaian yang masih belum terisi. Pastikan semua penilaian sudah terisi", Toast.LENGTH_LONG).show();
                        }
                        else {

                            if (initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                                kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                kpiAnswer.setKPIID("0");
                            } else {
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


                            kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                            kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                            kpiAnswerList1.setSTATUS("S");
                            kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                            kpiAnswerList1.setKpiAnswerList(kpiAnswerList);


                        }
                        //lnProgressBar.setVisibility(View.GONE);

                        if(initKH.getKpiQuestionsList().get(i).getSemester().equals("1") ){
                            total += Float.parseFloat(initKH.getKpiQuestionsList().get(i).getBobot().replace(",","."));
                        }
                    }

                    try {
                        for (int i = 0; i < initKH.getDevPlanHeaderList().size(); i++) {
                            for (int j = 0; j < initKH.getDevPlanHeaderList().get(i).getDevPlanDetail().size(); j++) {
                                if (initKH.getDevPlanHeaderList().get(i).getDevPlanDetail().get(j).getDEVPLANACTIVITIES().equals("")
                                        || initKH.getDevPlanHeaderList().get(i).getDevPlanDetail().get(j).getDEVPLANDUEDATE().equals("")
                                        || initKH.getDevPlanHeaderList().get(i).getDevPlanDetail().get(j).getDEVPLANKPI().equals("")) {
                                    ctr++;
                                    pd.dismiss();
                                    Toast.makeText(getContext(), "Masih ada penilaian yang masih belum terisi. Pastikan semua penilaian sudah terisi", Toast.LENGTH_LONG).show();
                                    break;
                                }

                            }
                        }
                    }catch(Exception e){
                        ctr++;
                    }
                    if (ctr == 0 && total==100) {
                        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                        Call<KPIAnswerList> call = apiService.postStatusPA(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
                        call.enqueue(new Callback<KPIAnswerList>() {
                            @Override
                            public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                                int statusCode = response.code();
                                pd.dismiss();
                                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                        .setMessage("Data berhasil di submit ke atasan")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            // do something when the button is clicked
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                arg0.dismiss();
                                                //EMAIL
                                                EmailSentModel emailSentModel = new EmailSentModel();
                                                emailSentModel.SUBJECT="[HCKiosk] PA karyawan atas nama "+usr.get(0).getEmpName()+" menunggu approval";
                                                emailSentModel.MESSAGE="<p>Yth. Bapak/Ibu "+usr.get(0).getNamaAtasanLangsung()+"<br /> <br /> Bersama ini kami informasikan, PA untuk karyawan dibawah ini menunggu approval Bapak/Ibu</p>\n" +
                                                        "<table style=\"border: 0px;\">\n" +
                                                        "<tbody>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Nama</td>\n" +
                                                        "<td style=\"border: 0px;\">: Contact</td>\n" +
                                                        "</tr>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Jabatan</td>\n" +
                                                        "<td style=\"border: 0px;\">: Maria Anders</td>\n" +
                                                        "</tr>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Organisasi</td>\n" +
                                                        "<td style=\"border: 0px;\">: Francisco Chang</td>\n" +
                                                        "</tr>\n" +
                                                        "</tbody>\n" +
                                                        "</table>\n" +
                                                        "<p><br /> Approval PA dapat dilakukan melalui aplikasi HC Kiosk yang dapat diakses <strong>melalui HC Kiosk web <a href=\"#\">(klik disini)</a> atau HC Kiosk Mobile. </strong> <br /> <br /> Terima kasih <br /> <br /> <em>(Note: email ini adalah hasil generate otomatis dari aplikasi HC Kiosk)</em></p>";
                                                emailSentModel.SENDERNAME=usr.get(0).getEmpName();
                                                emailSentModel.SENDERNIK=usr.get(0).getEmpNIK();
                                                emailSentModel.SENDEREMAIL="hr.service@enseval.com";
                                                emailSentModel.MAILTYPE="SUBMIT";
                                                emailSentModel.RECEIVERNAME=usr.get(0).getNamaAtasanLangsung();
                                                emailSentModel.RECEIVERNIK=usr.get(0).getNikAtasanLangsung();
                                                emailSentModel.RECEIVEREMAIL=usr.get(0).getEmailAtasan1();

                                                sendEmail(emailSentModel);

                                                FragmentManager fm = getActivity()
                                                        .getSupportFragmentManager();
                                                fm.popBackStack("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                            }
                                        })
                                        .show();
                                pd.dismiss();
                            }

                            @Override
                            public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                                pd.dismiss();
                                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                        .setMessage("Data berhasil di submit ke atasan")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            // do something when the button is clicked
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                arg0.dismiss();

                                                EmailSentModel emailSentModel = new EmailSentModel();
                                                emailSentModel.SUBJECT="[HCKiosk] PA karyawan atas nama "+usr.get(0).getEmpName()+" menunggu approval";
                                                emailSentModel.MESSAGE="";
                                                emailSentModel.SENDERNAME=usr.get(0).getEmpName();
                                                emailSentModel.SENDERNIK=usr.get(0).getEmpNIK();
                                                emailSentModel.SENDEREMAIL="hr.service@enseval.com";
                                                emailSentModel.MAILTYPE="SUBMIT";
                                                emailSentModel.RECEIVERNAME=usr.get(0).getNamaAtasanLangsung();
                                                emailSentModel.RECEIVERNIK=usr.get(0).getNikAtasanLangsung();
                                                emailSentModel.RECEIVEREMAIL=usr.get(0).getEmailAtasan1();

                                                sendEmail(emailSentModel);

                                                FragmentManager fm = getActivity()
                                                        .getSupportFragmentManager();
                                                fm.popBackStack("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                            }
                                        })
                                        .show();
                                pd.dismiss();
                            }
                        });

                    }
                    else{
                        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                .setMessage("Jumlah Bobot tidak sama dengan 100%, silakan hubungi PIC di direktorat")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();

                                        EmailSentModel emailSentModel = new EmailSentModel();
                                        emailSentModel.SUBJECT="[HCKiosk] Bobot Faktor Kuantitatif tidak sama dengan 100%";
                                        emailSentModel.MESSAGE="";
                                        emailSentModel.SENDERNAME=usr.get(0).getEmpName();
                                        emailSentModel.SENDERNIK=usr.get(0).getEmpNIK();
                                        emailSentModel.SENDEREMAIL="hr.service@enseval.com";
                                        emailSentModel.MAILTYPE="PIC";
                                        emailSentModel.RECEIVERNAME=usr.get(0).getNamaAtasanLangsung();
                                        emailSentModel.RECEIVERNIK=usr.get(0).getNikAtasanLangsung();
                                        emailSentModel.RECEIVEREMAIL=usr.get(0).getEmailAtasan1();

                                        sendEmail(emailSentModel);


                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();
                                    }
                                })
                                .show();
                    }
                }
            });

            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int ctr=0;
                    float total=0;
                    ProgressDialog pd = new ProgressDialog(getContext());
                    pd.setMessage("Approving...");
                    pd.setCancelable(false);
                    pd.show();
                    KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

//                    lnProgressBar.setVisibility(View.VISIBLE);
//                    recyclerViewKPI.setVisibility(View.INVISIBLE);
//                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.white));

                    List<KPIAnswer> kpiAnswerList = new ArrayList<>();
                    for (int i = 0; i < initKH.getKpiQuestionsList().size(); i++) {
                        if (initKH.getKpiQuestionsList().get(i).getSemester().equals("1") && initKH.getKpiQuestionsList().get(i).getCheckedId() < 1 || initKH.getKpiQuestionsList().get(i).getActual().equals("") || initKH.getKpiQuestionsList().get(i).getTarget().equals("") || initKH.getKpiQuestionsList().get(i).getTarget().equals("0") || initKH.getKpiQuestionsList().get(i).getEvidence().length() < 1 || initKH.getStrength().length() < 1) {
                            lnProgressBar.setVisibility(View.GONE);
                            initKH.getKpiQuestionsList().get(i).setNotif(true);
//                            recyclerViewKPI.setVisibility(View.VISIBLE);
//                            recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));
                            pd.dismiss();
                            ctr++;
                        }
                        if(initKH.getKpiQuestionsList().get(i).getSemester().equals("1") ){
                            total += Float.parseFloat(initKH.getKpiQuestionsList().get(i).getBobot().replace(",","."));
                        }
                    }
                    kpiAdapterTahunan.notifyDataSetChanged();
                    if(ctr==0 && total==100) {
                        for (int i = 0; i < initKH.getKpiQuestionsList().size(); i++) {
                            KPIAnswer kpiAnswer = new KPIAnswer();

                            if (initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                                kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                kpiAnswer.setKPIID("0");
                            } else {
                                kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                kpiAnswer.setCOMPID("0");
                            }
                            kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                            kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                            kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                            kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                            kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                            kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                            kpiAnswer.setACTUAL(initKH.getKpiQuestionsList().get(i).getActual());
                            kpiAnswer.setTARGET(initKH.getKpiQuestionsList().get(i).getTarget());
                            kpiAnswerList.add(kpiAnswer);
                            //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();


                            kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                            kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                            kpiAnswerList1.setDevPlanHeaderList(initKH.getDevPlanHeaderList());

                            //hanya punya 1 atasan
                            if (kh.getNIKAtasanTakLangsung().length() < 2) {
                                if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {
                                    kpiAnswerList1.setSTATUS("A");
                                } else {
                                    kpiAnswerList1.setSTATUS(kh.getStatus());
                                }
                                kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                            } else {

                                if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {
                                    kpiAnswerList1.setSTATUS("S");
                                } else if (kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK())) {
                                    kpiAnswerList1.setSTATUS("A");
                                } else if (initKH.getStatus().equals("O")) {
                                    kpiAnswerList1.setSTATUS("S");
                                } else {
                                    kpiAnswerList1.setSTATUS(kh.getStatus());
                                }
                                kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                            }
                        }
                        kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                        Call<KPIAnswerList> call = apiService.postStatusPA(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
                        call.enqueue(new Callback<KPIAnswerList>() {
                            @Override
                            public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                                int statusCode = response.code();
                                pd.dismiss();


                                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                        .setMessage("Data berhasil diapprove")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            // do something when the button is clicked
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                arg0.dismiss();

                                                if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {
                                                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                EmailSentModel emailSentModel = new EmailSentModel();
                                                emailSentModel.SUBJECT="[HCKiosk] PA karyawan atas nama "+initKH.getEmpName()+" menunggu approval";
                                                emailSentModel.MESSAGE="<p>Yth. Bapak/Ibu "+kh.getAtasanLangsung()+"<br /> <br /> Bersama ini kami informasikan, PA untuk karyawan dibawah ini menunggu approval Bapak/Ibu</p>\n" +
                                                        "<table style=\"border: 0px;\">\n" +
                                                        "<tbody>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Nama</td>\n" +
                                                        "<td style=\"border: 0px;\">: "+initKH.getEmpName()+"</td>\n" +
                                                        "</tr>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Jabatan</td>\n" +
                                                        "<td style=\"border: 0px;\">: "+initKH.getJobTitleName()+"</td>\n" +
                                                        "</tr>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Organisasi</td>\n" +
                                                        "<td style=\"border: 0px;\">: "+initKH.getOrgName()+"</td>\n" +
                                                        "</tr>\n" +
                                                        "</tbody>\n" +
                                                        "</table>\n" +
                                                        "<p><br /> Approval PA dapat dilakukan melalui aplikasi HC Kiosk yang dapat diakses <strong>melalui HC Kiosk web <a href=\"#\">(klik disini)</a> atau HC Kiosk Mobile. </strong> <br /> <br /> Terima kasih <br /> <br /> <em>(Note: email ini adalah hasil generate otomatis dari aplikasi HC Kiosk)</em></p>";
                                                emailSentModel.SENDERNAME=kh.getEmpName();
                                                emailSentModel.SENDERNIK=kh.getNIK();
                                                emailSentModel.SENDEREMAIL="hr.service@enseval.com";
                                                emailSentModel.MAILTYPE="APPROVE";

                                                    emailSentModel.RECEIVERNAME=kh.getAtasanTakLangsung();
                                                    emailSentModel.RECEIVERNIK=kh.getNIKAtasanTakLangsung();
//                                                      emailSentModel.RECEIVEREMAIL=kh.getEmailAtasan2();
                                                    emailSentModel.RECEIVEREMAIL="samuel.septiano@enseval.com";

                                                    sendEmail(emailSentModel);
                                                }
                                                //+++++++++++++++++++++++++++++++++++++++++++++++++++=+++

                                                FragmentManager fm = getActivity()
                                                        .getSupportFragmentManager();
                                                fm.popBackStack("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                            }
                                        })
                                        .show();
                            }

                            @Override
                            public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                                pd.dismiss();
                                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                        .setMessage("Data berhasil diapprove")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                            // do something when the button is clicked
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                arg0.dismiss();

                                                //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {
                                                EmailSentModel emailSentModel = new EmailSentModel();
                                                emailSentModel.SUBJECT="[HCKiosk] PA karyawan atas nama "+initKH.getEmpName()+" menunggu approval";
                                                emailSentModel.MESSAGE="<p>Yth. Bapak/Ibu "+initKH.getAtasanTakLangsung()+"<br /> <br /> Bersama ini kami informasikan, PA untuk karyawan dibawah ini menunggu approval Bapak/Ibu</p>\n" +
                                                        "<table style=\"border: 0px;\">\n" +
                                                        "<tbody>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Nama</td>\n" +
                                                        "<td style=\"border: 0px;\">: "+initKH.getEmpName()+"</td>\n" +
                                                        "</tr>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Jabatan</td>\n" +
                                                        "<td style=\"border: 0px;\">: "+initKH.getJobTitleName()+"</td>\n" +
                                                        "</tr>\n" +
                                                        "<tr>\n" +
                                                        "<td style=\"border: 0px;\">Organisasi</td>\n" +
                                                        "<td style=\"border: 0px;\">: "+initKH.getOrgName()+"</td>\n" +
                                                        "</tr>\n" +
                                                        "</tbody>\n" +
                                                        "</table>\n" +
                                                        "<p><br /> Approval PA dapat dilakukan melalui aplikasi HC Kiosk yang dapat diakses <strong>melalui HC Kiosk web <a href=\"#\">(klik disini)</a> atau HC Kiosk Mobile. </strong> <br /> <br /> Terima kasih <br /> <br /> <em>(Note: email ini adalah hasil generate otomatis dari aplikasi HC Kiosk)</em></p>";
                                                emailSentModel.SENDERNAME=kh.getEmpName();
                                                emailSentModel.SENDERNIK=kh.getNIK();
                                                emailSentModel.SENDEREMAIL="hr.service@enseval.com";
                                                emailSentModel.MAILTYPE="APPROVE";

                                                    emailSentModel.RECEIVERNAME=kh.getAtasanTakLangsung();
                                                    emailSentModel.RECEIVERNIK=kh.getNIKAtasanTakLangsung();
                                                      emailSentModel.RECEIVEREMAIL=kh.getEmailAtasan2();
//                                                    emailSentModel.RECEIVEREMAIL="samuel.septiano@enseval.com";

                                                    sendEmail(emailSentModel);
                                                }
                                                //+++++++++++++++++++++++++++++++++++++++++++++++++++=+++

                                                FragmentManager fm = getActivity()
                                                        .getSupportFragmentManager();
                                                fm.popBackStack("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                            }
                                        })
                                        .show();
                            }
                        });
                    }
                    else if(total!=100){
                        float finalTotal = total;
                        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                .setMessage("Jumlah Bobot tidak sama dengan 100%, silakan hubungi PIC di direktorat")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();

                                        EmailSentModel emailSentModel = new EmailSentModel();
                                        emailSentModel.SUBJECT="[HCKiosk] Bobot Faktor Kuantitatif tidak sama dengan 100%";
                                        emailSentModel.MESSAGE="<br /><br /> Bersama ini kami informasikan, bobot faktor kuantitatif untuk karyawan dibawah <strong>tidak sama dengan 100%.</strong></p>\n" +
                                                "<table style=\"border: 0px;\">\n" +
                                                "<tbody>\n" +
                                                "<tr>\n" +
                                                "<td style=\"border: 0px;\">Nama</td>\n" +
                                                "<td style=\"border: 0px;\">: "+initKH.getEmpName()+"</td>\n" +
                                                "</tr>\n" +
                                                "<tr>\n" +
                                                "<td style=\"border: 0px;\">Jabatan</td>\n" +
                                                "<td style=\"border: 0px;\">: "+initKH.getJobTitleName()+"</td>\n" +
                                                "</tr>\n" +
                                                "<tr>\n" +
                                                "<td style=\"border: 0px;\">Organisasi</td>\n" +
                                                "<td style=\"border: 0px;\">: "+initKH.getOrgName()+"</td>\n" +
                                                "</tr>\n" +
                                                "<tr>\n" +
                                                "<td style=\"border: 0px;\">Total Bobot</td>\n" +
                                                "<td style=\"border: 0px;\">: "+ finalTotal +"</td>\n" +
                                                "</tr>\n" +
                                                "</tbody>\n" +
                                                "</table>\n" +
                                                "<p><br /> Mohon kerjasama Bapak/Ibu untuk melengkapi pada aplikasi HC Kiosk yang dapat diakses melalui<strong> HC Kiosk web <a href=\"#\">(klik disini)</a> atau HC Kiosk Mobile.</strong> Pastikan total bobot faktor kuantitatif untuk semester 1 dan 2 sama dengan 100%. <br /> <br /> <em>(Note: email ini adalah hasil generate otomatis dari aplikasi HC Kiosk)</em></p>";
                                        emailSentModel.SENDERNAME=usr.get(0).getEmpName();
                                        emailSentModel.SENDERNIK=usr.get(0).getEmpNIK();
                                        emailSentModel.SENDEREMAIL="hr.service@enseval.com";
                                        emailSentModel.MAILTYPE="PIC";
                                        emailSentModel.RECEIVERNAME=usr.get(0).getNamaAtasanLangsung();
                                        emailSentModel.RECEIVERNIK=usr.get(0).getNikAtasanLangsung();
//                                        emailSentModel.RECEIVEREMAIL=usr.get(0).getEmailAtasan1();
                                        emailSentModel.RECEIVEREMAIL="samuel.septiano@enseval.com";
                                        sendEmail(emailSentModel);

                                    }
                                })
                                .show();
                    }
                    else{
                        Toast.makeText(getContext(), "Data penilaian belum lengkap, silakan melengkapi penilaian sebelum submit/approve", Toast.LENGTH_LONG).show();
                    }
////                    FragmentManager fm = getActivity()
////                            .getSupportFragmentManager();
////                    fm.popBackStack("KPIApproveTahunanFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
////                    Toast.makeText(getContext(), "Approved!!", Toast.LENGTH_SHORT).show();

                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals("Approve")) {
                            List<KPIAnswer> kpiAnswerList = new ArrayList<>();
                            KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

                            for (int i = 0; i < initKH.getKpiQuestionsList().size(); i++) {
                                KPIAnswer kpiAnswer = new KPIAnswer();


                                if (initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                                    kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                    kpiAnswer.setKPIID("0");
                                } else {
                                    kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                    kpiAnswer.setCOMPID("0");
                                }
                                kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                                kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                                kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                                kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                                kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                                kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                                kpiAnswer.setACTUAL(initKH.getKpiQuestionsList().get(i).getActual());
                                kpiAnswer.setTARGET(initKH.getKpiQuestionsList().get(i).getTarget());
                                kpiAnswerList.add(kpiAnswer);
                                //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();

                                kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                                kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                                kpiAnswerList1.setSTATUS(initKH.getStatus());
                                kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                                kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                                kpiAnswerList1.setDevPlanHeaderList(initKH.getDevPlanHeaderList());


                            }


//                        if (initKH.getStrength().length() < 0 ) {
//                            Toast.makeText(getActivity(),"Strength wajib diisi!!!",Toast.LENGTH_LONG).show();
//                        } else {
                            ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                            Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
                            call.enqueue(new Callback<KPIAnswerList>() {
                                @Override
                                public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                                    int statusCode = response.code();
                                    //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                                    diUbah = false;
                                    AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                            .setMessage("Data berhasil disimpan")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                // do something when the button is clicked
                                                public void onClick(DialogInterface arg0, int arg1) {

                                                    arg0.dismiss();
                                                }
                                            })
                                            .show();
                                    alertbox.setOnShowListener( new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface arg0) {
                                            alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                                    //Toast.makeText(getActivity(), "Saved!!", Toast.LENGTH_SHORT).show();
                                    //pd.dismiss()
                                    diUbah = false;
                                    AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                            .setMessage("Data berhasil disimpan")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                // do something when the button is clicked
                                                public void onClick(DialogInterface arg0, int arg1) {

                                                    arg0.dismiss();
                                                }
                                            })
                                            .show();
                                    alertbox.setOnShowListener( new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface arg0) {
                                            alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey));

                                        }
                                    });
//                                            lnProgressBar.setVisibility(View.GONE);
//                                            recyclerViewKPI.setVisibility(View.VISIBLE);
//                                            recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));


                                }
                            });
//                        }
                    }
                    else {
                            ProgressDialog pd = new ProgressDialog(getContext());
                            pd.setMessage("Menyimpan...");
                            pd.setCancelable(false);
                            pd.show();
                            KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

//                    recyclerViewKPI.setVisibility(View.INVISIBLE);
//                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.white));

                            List<KPIAnswer> kpiAnswerList = new ArrayList<>();
                            for (int i = 0; i < initKH.getKpiQuestionsList().size(); i++) {
                                KPIAnswer kpiAnswer = new KPIAnswer();

                                if (initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                                    kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                    kpiAnswer.setKPIID("0");
                                } else {
                                    kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                                    kpiAnswer.setCOMPID("0");
                                }
                                kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                                kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                                kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                                kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                                kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                                kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                                kpiAnswer.setACTUAL(initKH.getKpiQuestionsList().get(i).getActual());
                                kpiAnswer.setTARGET(initKH.getKpiQuestionsList().get(i).getTarget());
                                kpiAnswerList.add(kpiAnswer);
                                //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();


                                kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                                kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                                kpiAnswerList1.setSTATUS(initKH.getStatus());
                                kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                                kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                                kpiAnswerList1.setDevPlanHeaderList(initKH.getDevPlanHeaderList());
                            }


                            ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                            Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
                            call.enqueue(new Callback<KPIAnswerList>() {
                                @Override
                                public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                                    pd.dismiss();
//                                    lnProgressBar.setVisibility(View.GONE);
//                                    recyclerViewKPI.setVisibility(View.VISIBLE);
//                                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));
                                    diUbah=false;

                                    int statusCode = response.code();
                                    //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                                    AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                            .setMessage("Data berhasil disimpan")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                // do something when the button is clicked
                                                public void onClick(DialogInterface arg0, int arg1) {

                                                    arg0.dismiss();
                                                }
                                            })
                                            .show();
                                    alertbox.setOnShowListener( new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface arg0) {
                                            alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                                    pd.dismiss();
                                    AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                            .setMessage("Data berhasil disimpan")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                // do something when the button is clicked
                                                public void onClick(DialogInterface arg0, int arg1) {

                                                    arg0.dismiss();
                                                }
                                            })
                                            .show();
                                    alertbox.setOnShowListener( new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface arg0) {
                                            alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

                                        }
                                    });
//                                    lnProgressBar.setVisibility(View.GONE);
//                                    recyclerViewKPI.setVisibility(View.VISIBLE);
//                                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));
                                    diUbah=false;
//                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();

                                }
                            });
//                        Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();
                    }
                    diUbah=false;
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

            if(getType().equals("Approve")) {
                Picasso.get()
                        .load(userList.getEmpPhoto())
                        .placeholder(R.drawable.user)
                        .error(R.drawable.imgalt)
                        .into(empPhoto);
            }
            else{
                Picasso.get()
                        .load(usr.get(0).getEmpPhoto())
                        .placeholder(R.drawable.user)
                        .error(R.drawable.imgalt)
                        .into(empPhoto);
            }

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
        tahuns.add("SMT 1");
        tahuns.add("SMT 2");

        spinnerSemester.setItems(tahuns);

        spinnerSemester.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (item.toString().equals("SMT 1")) {
                    SEMESTER="1";
                    try {
                        kpiAdapterTahunan.refresh();
                    }catch(Exception e){}
                }
                else{
                    SEMESTER="2";
                    try {

                        //Filter Periode semester 1 dan 2
//                        if(paPeriodeModelList.get(0).getSemester().equals("1")){
//                            spinnerSemester.setSelectedIndex(0);
//                            AlertDialog alertbox = new AlertDialog.Builder(getContext())
//                                    .setMessage("Periode Penilaian Yang Sedang Berlangsung Adalah Semester 1")
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//                                        // do something when the button is clicked
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            SEMESTER="1";
//                                            kpiAdapterTahunan.refresh();
//                                        }
//                                    })
//                                    .show();
//                        }
//                        else{
                        kpiAdapterTahunan.refresh();
//                        }
                    }catch(Exception e){}
                }
            }
        });



        //=============================  MASIH HARDCODE  =============================================================
        lnProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Observable<KPIHeader> observable = apiService.getKPIHeaderPA(nik, tahun,"Bearer "+usr.get(0).getToken());
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<KPIHeader>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(KPIHeader response) {
                try{

//                    if(response == null){
//                        imgNoResult.setVisibility(View.VISIBLE);
//                        recyclerViewKPI.setVisibility(View.GONE);
//                    }
                    ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                    Call<List<PAPeriodeModel>> call = apiService.getPAPeriode("Bearer "+usr.get(0).getToken());
                    call.enqueue(new Callback<List<PAPeriodeModel>>() {
                        @Override
                        public void onResponse(Call<List<PAPeriodeModel>> call, Response<List<PAPeriodeModel>> response) {
                            int statusCode = response.code();
                            paPeriodeModelList = response.body();
                        }
                        @Override
                        public void onFailure(Call<List<PAPeriodeModel>> call, Throwable t) {
                            Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                        }
                    });


                    //Toast.makeText(getContext(),"dari "+lInbox.size(),Toast.LENGTH_LONG).show();

                    kh = new KPIHeader();

                try {
                    kh.setDevPlanHeaderList(response.getDevPlanHeaderList()); //KALAU YANG DITARIK DARI DB KPI SAJA ATAU KUALITATIF SAJA, MAKAN DEVPALN HEADER NULL
                    for (int i = 0; i < kh.getDevPlanHeaderList().size(); i++) {
                        kh.getDevPlanHeaderList().get(i).setDevPlanDetail(response.getDevPlanHeaderList().get(i).getDevPlanDetail());
                    }
                    kh.setMasterDevelopmentPlanList(response.getMasterDevelopmentPlanList());
                }catch (Exception e){}
                kh.setBobot(response.getBobot());
                kh.setSemester(semester);
                kh.setTahun(response.getTahun());
                kh.setStatus(response.getStatus());
                kh.setStrength(response.getStrength());
                kh.setStatus1(response.getStatus1());
                kh.setStatus2(response.getStatus2());
                kh.setPaId(response.getPaId());
                lKPIQuestion = new ArrayList<>();
                int j=0;
                for(int i=0;i<response.getKpiQuestionsList().size();i++){
                    if(i>=1 && (!response.getKpiQuestionsList().get(i).getSemester().equals(response.getKpiQuestionsList().get(i-1).getSemester())|| !response.getKpiQuestionsList().get(i).getKPIcategory().equals(response.getKpiQuestionsList().get(i-1).getKPIcategory()))){
                        j=0;
                    }

                    KPIQuestions kq = new KPIQuestions();

                    //Set<KPIHint> set = new HashSet<>(response.body().getKpiQuestionsList().get(i).getHint());


                    kq.setHint((response.getKpiQuestionsList().get(i).getHint()));
                    kq.setBobot(response.getKpiQuestionsList().get(i).getBobot());
                    kq.setKPIcategory(response.getKpiQuestionsList().get(i).getKPIcategory());
                    kq.setPerspective(response.getKpiQuestionsList().get(i).getPerspective());
                    kq.setKPIDesc(response.getKpiQuestionsList().get(i).getKPIDesc());
                    kq.setAnswered(true);
                    kq.setCheckedId(Integer.parseInt(response.getKpiQuestionsList().get(i).getCP()));
                    kq.setPaId(response.getKpiQuestionsList().get(i).getPaId());
                    kq.setKpiId(response.getKpiQuestionsList().get(i).getKpiId());
                    kq.setSemester(response.getKpiQuestionsList().get(i).getSemester());
                    kq.setAnsweredPendukung(true);
                    kq.setAnsweredPenghambat(true);
                    kq.setAnsweredCatatan(true);
                    kq.setAnsweredEvidence(true);
                    kq.setAnsweredActual(true);
                    kq.setAnsweredTarget(true);
                    kq.setEvidence(response.getKpiQuestionsList().get(i).getEvidence());
                    kq.setActual(response.getKpiQuestionsList().get(i).getActual());
                    kq.setTarget(response.getKpiQuestionsList().get(i).getTarget());
                    kq.setCatatanLain("");
                    kq.setPendukung("");
                    kq.setPenghambat("");

                    kq.setIdKPI(Integer.toString(j + 1));
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
                    kh.setFotoBawahan(userList.getEmpPhoto());
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
                    kh.setAtasanTakLangsung(usr.get(0).getNamaAtasanTakLangsung());
                    kh.setNIKAtasanTakLangsung(usr.get(0).getNikAtasanTakLangsung());
                    //kh.setNIKAtasanLangsung(usr.get(0).getNikAtasanLangsung());
                    kh.setAtasanLangsung(usr.get(0).getNamaAtasanLangsung());
                    kh.setFotoAtasanLangsung(usr.get(0).getFotoAtasanLangsung());
                    kh.setFotoAtasanTakLangsung(usr.get(0).getFotoAtasanTakLangsung());
                    kh.setNIKAtasanLangsung(usr.get(0).getNikAtasanLangsung());
                    kh.setNIKAtasanTakLangsung(usr.get(0).getNikAtasanTakLangsung());
                    kh.setFotoBawahan("");
                }

                    if(kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK()) && (kh.getStatus().equals("O") || kh.getStatus().equals("S"))){
                        btnApprove.setVisibility(View.GONE);
                        btnSave.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.GONE);
                        isEditable="N";
//                        kpiAdapterTahunan = new KPIAdapterTahunan(getActivity(), getContext(), kh, userList.getEmpNiK(), getType(), KPIKuantitatifTahunanFragment.this, Jenis,lInbox,paPeriodeModelList);
//                        kpiAdapterTahunan.refresh();
                    }

                    getAllRoomChat(usr.get(0).getEmpNIK(),kh);


                }
                catch (Exception e){
                    //Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();

                }
                lnProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "An error occurred in the Retrofit request. Perhaps no response/cache", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });



//        Call<KPIHeader> call = apiService.getKPIHeaderPA(nik, tahun,"Bearer "+usr.get(0).getToken());
//        call.enqueue(new Callback<KPIHeader>() {
//            @Override
//            public void onResponse(Call<KPIHeader> call, Response<KPIHeader> response) {
//                int statusCode = response.code();
//                try{
//                    kh = new KPIHeader();
//                kh.setDevPlanHeaderList(response.body().getDevPlanHeaderList()); //KALAU YANG DITARIK DARI DB KPI SAJA ATAU KUALITATIF SAJA, MAKAN DEVPALN HEADER NULL
//                for(int i=0;i<kh.getDevPlanHeaderList().size();i++){
//                    kh.getDevPlanHeaderList().get(i).setDevPlanDetail(response.body().getDevPlanHeaderList().get(i).getDevPlanDetail());
//                }
//                kh.setMasterDevelopmentPlanList(response.body().getMasterDevelopmentPlanList());
//                kh.setBobot(response.body().getBobot());
//                kh.setSemester(semester);
//                kh.setTahun(response.body().getTahun());
//                kh.setStatus(response.body().getStatus());
//                kh.setStrength(response.body().getStrength());
//                kh.setStatus1(response.body().getStatus1());
//                kh.setStatus2(response.body().getStatus2());
//
//                lKPIQuestion = new ArrayList<>();
//                int j=0;
//                for(int i=0;i<response.body().getKpiQuestionsList().size();i++){
//                    if(i>=1 && !response.body().getKpiQuestionsList().get(i).getSemester().equals(response.body().getKpiQuestionsList().get(i-1).getSemester())){
//                        j=0;
//                    }
//                    KPIQuestions kq = new KPIQuestions();
//
//                    //Set<KPIHint> set = new HashSet<>(response.body().getKpiQuestionsList().get(i).getHint());
//
//                    kq.setHint((response.body().getKpiQuestionsList().get(i).getHint()));
//                    kq.setBobot(response.body().getKpiQuestionsList().get(i).getBobot());
//                    kq.setKPIcategory(response.body().getKpiQuestionsList().get(i).getKPIcategory());
//                    kq.setPerspective(response.body().getKpiQuestionsList().get(i).getPerspective());
//                    kq.setKPIDesc(response.body().getKpiQuestionsList().get(i).getKPIDesc());
//                    kq.setAnswered(true);
//                    kq.setCheckedId(Integer.parseInt(response.body().getKpiQuestionsList().get(i).getCP()));
//                    kq.setPaId(response.body().getKpiQuestionsList().get(i).getPaId());
//                    kq.setKpiId(response.body().getKpiQuestionsList().get(i).getKpiId());
//                    kq.setSemester(response.body().getKpiQuestionsList().get(i).getSemester());
//                    kq.setAnsweredPendukung(true);
//                    kq.setAnsweredPenghambat(true);
//                    kq.setAnsweredCatatan(true);
//                    kq.setAnsweredEvidence(true);
//                    kq.setAnsweredActual(true);
//                    kq.setAnsweredTarget(true);
//                    kq.setEvidence(response.body().getKpiQuestionsList().get(i).getEvidence());
//                    kq.setActual(response.body().getKpiQuestionsList().get(i).getActual());
//                    kq.setTarget(response.body().getKpiQuestionsList().get(i).getTarget());
//                    kq.setCatatanLain("");
//                    kq.setPendukung("");
//                    kq.setPenghambat("");
//                    kq.setIdKPI(Integer.toString(j+1));
//                    lKPIQuestion.add(kq);
//                    j++;
//                }
//                kh.setKpiQuestionsList(lKPIQuestion);
//
//                if(getType().equals("Approve")){
//
//                    //================= DUMMY ====================================
//                    kh.setCompany(userList.getCompanyName());
//                    kh.setLocationName(userList.getLocationName());
//
//                    kh.setEmpName(userList.getEmpName());
//                    kh.setNIK(userList.getEmpNiK());
//                    kh.setDept(userList.getDept());
//                    kh.setAtasanLangsung(userList.getNamaAtasanLangsung());
//                    kh.setNIKAtasanLangsung(userList.getNIKAtasanLangsung());
//                    kh.setAtasanTakLangsung(userList.getNamaAtasanTakLangsung());
//                    kh.setNIKAtasanTakLangsung(userList.getNIKAtasanTakLangsung());
//                    kh.setBranchName(userList.getBranchName());
//                    kh.setJobTitleName(userList.getJobTitleName());
//                    kh.setFotoBawahan(userList.getEmpPhoto());
//
//                }
//                else {
//                    kh.setCompany(usr.get(0).getCompanyName());
//                    kh.setLocationName(usr.get(0).getLocationName());
//                    kh.setEmpName(usr.get(0).getEmpName());
//                    kh.setNIK(usr.get(0).getEmpNIK());
//                    kh.setDept(usr.get(0).getDept());
//                    kh.setBranchName(usr.get(0).getBranchName());
//                    kh.setJobTitleName(usr.get(0).getJobTitleName());
//                    //================= DUMMY ====================================
//                    kh.setAtasanTakLangsung(usr.get(0).getNamaAtasanTakLangsung());
//                    kh.setNIKAtasanTakLangsung(usr.get(0).getNikAtasanTakLangsung());
//                    //kh.setNIKAtasanLangsung(usr.get(0).getNikAtasanLangsung());
//                    kh.setAtasanLangsung(usr.get(0).getNamaAtasanLangsung());
//                    kh.setNIKAtasanLangsung(usr.get(0).getNikAtasanLangsung());
//                    kh.setNIKAtasanTakLangsung(usr.get(0).getNikAtasanTakLangsung());
//                    kh.setFotoBawahan("");
//                }
//
//
//                //Toast.makeText(getContext(),"isi: "+kh.getKpiQuestionsList(),Toast.LENGTH_LONG).show();
//                setInitKh(kh);
//                initQuestionsAdapter(kh);
//                }
//                catch (Exception e){
//                   // Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
//
//                }
//                lnProgressBar.setVisibility(View.GONE);
//
//                //Toast.makeText(getContext(), kh.getTahun(), Toast.LENGTH_LONG).show();
//            }
//            @Override
//            public void onFailure(Call<KPIHeader> call, Throwable t) {
//                lnProgressBar.setVisibility(View.GONE);
//                Toast.makeText(getActivity(),"No connection to internet",Toast.LENGTH_LONG).show();
//            }
//        });


    }

    private void initQuestionsAdapter(KPIHeader kh,List<InboxChatModel>lInbox) {
//        Toast.makeText(getContext(),"isi: "+Integer.toString(kh.getKpiQuestionsList().size()),Toast.LENGTH_LONG).show();

        recyclerViewKPI.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewQuestions.setHasFixedSize(true);
        if(getType().equals("Approve")) {
            if (kh.getNIKAtasanLangsung().equals(usr.get(0).getEmpNIK())) {

                if (kh.getStatus().equals("A") || kh.getStatus().equals("C")) {
                    isEditable = "N";
                    btnSubmit.setEnabled(false);
                    btnApprove.setEnabled(false);
                    btnSave.setEnabled(false);

                } else {
                    isEditable = "Y";
                    btnSubmit.setEnabled(true);
                    btnApprove.setEnabled(true);
                    btnSave.setEnabled(true);

                }

             }else if (kh.getNIKAtasanTakLangsung().equals(usr.get(0).getEmpNIK())) {
                if(kh.getStatus().equals("O") || kh.getStatus().equals("S")){
                    isEditable="N";
                    btnSubmit.setEnabled(false);
                    btnApprove.setEnabled(false);
                    btnSave.setEnabled(false);
                }
                else if (kh.getStatus().equals("C")) {
                    isEditable = "N";
                    btnSubmit.setEnabled(false);
                    btnApprove.setEnabled(false);
                    btnSave.setEnabled(false);

                } else {
                    isEditable = "Y";
                    btnSubmit.setEnabled(true);
                    btnApprove.setEnabled(true);
                    btnSave.setEnabled(true);

                }
            }

            kpiAdapterTahunan = new KPIAdapterTahunan(getActivity(), getContext(), kh, userList.getEmpNiK(), getType(), this, Jenis,lInbox,paPeriodeModelList);
        }
        else{
            if(kh.getStatus().equals("S") || kh.getStatus().equals("A") || kh.getStatus().equals("C")){
                isEditable="N";
                btnSubmit.setEnabled(false);
                btnApprove.setEnabled(false);
                btnSave.setEnabled(false);
            }
            else{
                isEditable="Y";
                btnSubmit.setEnabled(true);
                btnSave.setEnabled(true);
                btnApprove.setEnabled(true);

            }

            kpiAdapterTahunan = new KPIAdapterTahunan(getActivity(), getContext(), kh, usr.get(0).getEmpNIK(), getType(), this, Jenis,lInbox,paPeriodeModelList);
        }

        recyclerViewKPI.setAdapter(kpiAdapterTahunan);
        //Toast.makeText(getContext(),kh.getCompany(),Toast.LENGTH_SHORT).show();

    }


    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("*/*"); // intent.setType("video/*"); to select videos to upload
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih dokumen"), PICK_FILE_REQUEST);
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
                imageUploadModelPostPA.setFileExt(".jpg");
                imageUploadModelPostPA.setFilename(mTempPhotoPath);
                imageUploadModelPostPA.setImgString(temp);



                File file = new File(mTempPhotoPath);
                File fileComp = new Compressor(getContext()).compressToFile(file);
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), fileComp);
                MultipartBody.Part fileupload = MultipartBody.Part.createFormData("Image", fileComp.getName(),requestBody);
                String filenames ="";
                RequestBody KPIId=null;
                RequestBody compId=null;
                if(kh.getKpiQuestionsList().get(pos).getKPIcategory().equals("KUALITATIF")) {
                    filenames =  kh.getKpiQuestionsList().get(pos).getPaId() + "-0-" + kh.getKpiQuestionsList().get(pos).getKpiId() + "-" + kh.getKpiQuestionsList().get(pos).getSemester()+"-"+fileComp.getName();
                    compId = RequestBody.create(MediaType.parse("text/plain"),kh.getKpiQuestionsList().get(pos).getKpiId());
                    KPIId = RequestBody.create(MediaType.parse("text/plain"),"0");


                }
                else{
                    filenames = kh.getKpiQuestionsList().get(pos).getPaId() + "-" + kh.getKpiQuestionsList().get(pos).getKpiId() + "-0-" + kh.getKpiQuestionsList().get(pos).getSemester()+"-"+fileComp.getName();
                    KPIId = RequestBody.create(MediaType.parse("text/plain"),kh.getKpiQuestionsList().get(pos).getKpiId());
                    compId = RequestBody.create(MediaType.parse("text/plain"),"0");
                }

                RequestBody ext = RequestBody.create(MediaType.parse("text/plain"),".jpg");
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), filenames);

                RequestBody PaID = RequestBody.create(MediaType.parse("text/plain"),kh.getKpiQuestionsList().get(0).getPaId());
                RequestBody semester = RequestBody.create(MediaType.parse("text/plain"),kh.getKpiQuestionsList().get(pos).getSemester());
                RequestBody empNIK = RequestBody.create(MediaType.parse("text/plain"),usr.get(0).getEmpNIK());


                captureDone = true;
                ium.setFileExt(".jpg");
                ium.setImgString(temp);
                ium.setFilename(filenames+".jpg");

                ProgressDialog pd = new ProgressDialog(getContext());
                pd.setMessage("Uploading...");
                pd.setCancelable(false);
                pd.show();

                ApiInterface apiService2 = ApiClient.getClient(getContext()).create(ApiInterface.class);
                Call<String> call = apiService2.uploadFile(fileupload,filename,ext, PaID,semester,empNIK,compId,KPIId);
                //calling the api
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        //Toast.makeText(getContext(), response.code()+" ", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getContext(),"Photo Uploaded!",Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
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
                        pd.dismiss();

                    }
                });


//                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//                Call<ImageUploadModelPostPA> call = apiService.postKPIEvidencePA(imageUploadModelPostPA,"Bearer "+usr.get(0).getToken());
//                call.enqueue(new Callback<ImageUploadModelPostPA>() {
//                    @Override
//                    public void onResponse(Call<ImageUploadModelPostPA> call, Response<ImageUploadModelPostPA> response) {
//                        int statusCode = response.code();
//
//                        question.get(pos).setPhotoCapture(imgCaptureList);
//                        postTemp = pos;
//                        if (kpiAdapterTahunan != null) {
//                            kpiAdapterTahunan.currentPosition = pos+1;
////                    kpiAdapterTahunan.isVisible = true;
//                            kpiAdapterTahunan.notifyDataSetChanged();
//                            kpiAdapterTahunan.dialog.dismiss();
//                            kpiAdapterTahunan.showUploadDialog( question.get(pos).getPhotoCapture(),pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());
//                        }
//
//                        recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                        recyclerViewImg.setLayoutManager(mLayoutManager);
//                        KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity, "Y",question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());
//
//                        recyclerViewImg.setAdapter(adapter);
//
//                    }
//                    @Override
//                    public void onFailure(Call<ImageUploadModelPostPA> call, Throwable t) {
//                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
//                        question.get(pos).setPhotoCapture(imgCaptureList);
//                        postTemp = pos;
//                        if (kpiAdapterTahunan != null) {
//                            kpiAdapterTahunan.currentPosition = pos+1;
////                    kpiAdapterTahunan.isVisible = true;
//                            kpiAdapterTahunan.notifyDataSetChanged();
//                            kpiAdapterTahunan.dialog.dismiss();
//                            kpiAdapterTahunan.showUploadDialog( question.get(pos).getPhotoCapture(),pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());
//                        }
//
//                        recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                        recyclerViewImg.setLayoutManager(mLayoutManager);
//                        KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity, "Y",question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());
//                        recyclerViewImg.setAdapter(adapter);
//                    }
//                });

////
//                //cara buat imgCaptureList setiap beda recycler view rest jadi null
            }
            catch (Exception e){
               // Toast.makeText(getContext(), "error: "+e.toString(), Toast.LENGTH_LONG).show();

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
                String imagepath="";
                Uri selectedImageUri = data.getData();
//                if(!FilePath.isDownloadsDocument(selectedImageUri)) {
                imagepath = FileUtils.getRealPath(getContext(), selectedImageUri);

//                imagepath = FilePath.getPath(getContext(), selectedImageUri);
//                }
//                else{
//                    imagepath = FilePath.getPath(getContext(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) );
//                }
                String extension = imagepath.substring(imagepath.lastIndexOf("."));
                BitmapFactory.Options options = new BitmapFactory.Options();

                File file = new File(imagepath);
                int file_size = Integer.parseInt(String.valueOf(file.length()/1024));

                if(file_size<2049) { //2MB file size

                    Toast.makeText(getContext(), imagepath, Toast.LENGTH_LONG).show();

                    // down sizing image as it throws OutOfMemory Exception for larger images
                    // options.inSampleSize = 10;

//
                    if (extension.equals(".pdf")) {
                        File originalFile = new File(imagepath);
                        temp = null;
                        try {
                            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
                            byte[] bytes = new byte[(int) originalFile.length()];
                            fileInputStreamReader.read(bytes);
                            temp = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (extension.equals(".jpg") || extension.equals(".png") || extension.equals(".jpeg") || extension.equals(".bmp") || extension.equals(".gif")) {
                        //============ image =======================
                        final Bitmap bitmap = BitmapFactory.decodeFile(imagepath, options);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        byte[] b = baos.toByteArray();
                        temp = Base64.encodeToString(b, Base64.DEFAULT);
                        //===========================================
                    }


                    ImageUploadModelPostPA imageUploadModelPostPA = new ImageUploadModelPostPA();
                    imageUploadModelPostPA.setEmpNIK(usr.get(0).getEmpNIK());
                    imageUploadModelPostPA.setPaId(question.get(pos).getPaId());
                    if (question.get(pos).getKPIcategory().equals("KUALITATIF")) {
                        imageUploadModelPostPA.setCompId(question.get(pos).getKpiId());
                        imageUploadModelPostPA.setKpiId("");
                    } else {
                        imageUploadModelPostPA.setKpiId(question.get(pos).getKpiId());
                        imageUploadModelPostPA.setCompId("");
                    }
                    imageUploadModelPostPA.setSemester(question.get(pos).getSemester());
                    imageUploadModelPostPA.setFileExt(extension);
                    imageUploadModelPostPA.setFilename(imagepath);
                    imageUploadModelPostPA.setImgString(temp);


                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part fileupload = MultipartBody.Part.createFormData("Image", file.getName(), requestBody);
                    String filenames = "";
                    RequestBody KPIId = null;
                    RequestBody compId = null;
                    if (kh.getKpiQuestionsList().get(pos).getKPIcategory().equals("KUALITATIF")) {
                        filenames = kh.getKpiQuestionsList().get(pos).getPaId() + "-0-" + kh.getKpiQuestionsList().get(pos).getKpiId() + "-" + kh.getKpiQuestionsList().get(pos).getSemester() + "-" + file.getName();
                        compId = RequestBody.create(MediaType.parse("text/plain"), kh.getKpiQuestionsList().get(pos).getKpiId());
                        KPIId = RequestBody.create(MediaType.parse("text/plain"), "0");


                    } else {
                        filenames = kh.getKpiQuestionsList().get(0).getPaId() + "-" + kh.getKpiQuestionsList().get(pos).getKpiId() + "-0-" + kh.getKpiQuestionsList().get(pos).getSemester() + "-" +file.getName();
                        KPIId = RequestBody.create(MediaType.parse("text/plain"), kh.getKpiQuestionsList().get(pos).getKpiId());
                        compId = RequestBody.create(MediaType.parse("text/plain"), "0");
                    }
                    RequestBody ext = RequestBody.create(MediaType.parse("text/plain"), extension);

                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), filenames);
                    RequestBody PaID = RequestBody.create(MediaType.parse("text/plain"), kh.getKpiQuestionsList().get(pos).getPaId());
                    RequestBody semester = RequestBody.create(MediaType.parse("text/plain"), kh.getKpiQuestionsList().get(pos).getSemester());
                    RequestBody empNIK = RequestBody.create(MediaType.parse("text/plain"), usr.get(0).getEmpNIK());


                    captureDone = true;
                    ium.setFileExt(extension);
                    ium.setImgString(temp);
                    ium.setFilename(filenames+extension);
                    Toast.makeText(getContext(), "Document Uploaded!", Toast.LENGTH_LONG).show();
                    imgCaptureList.add(ium);

                    ProgressDialog pd = new ProgressDialog(getContext());
                    pd.setMessage("Uploading...");
                    pd.setCancelable(false);
                    pd.show();

                    ApiInterface apiService2 = ApiClient.getClient(getContext()).create(ApiInterface.class);
                    Call<String> call = apiService2.uploadFile(fileupload, filename, ext, PaID, semester, empNIK, compId, KPIId);
                    //calling the api
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
//                        Toast.makeText(getContext(), response.code()+" ", Toast.LENGTH_LONG).show();
                            question.get(pos).setPhotoCapture(imgCaptureList);
                            postTemp = pos;
                            if (kpiAdapterTahunan != null) {
                                kpiAdapterTahunan.currentPosition = pos + 1;
//                    kpiAdapterTahunan.isVisible = true;
                                kpiAdapterTahunan.notifyDataSetChanged();
                                kpiAdapterTahunan.dialog.dismiss();
                                kpiAdapterTahunan.showUploadDialog(question.get(pos).getPhotoCapture(), pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());
                            }

                            recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerViewImg.setLayoutManager(mLayoutManager);
                            KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity, "Y", question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());
                            recyclerViewImg.setAdapter(adapter);
                            pd.dismiss();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            question.get(pos).setPhotoCapture(imgCaptureList);
                            postTemp = pos;
                            if (kpiAdapterTahunan != null) {
                                kpiAdapterTahunan.currentPosition = pos + 1;
//                    kpiAdapterTahunan.isVisible = true;
                                kpiAdapterTahunan.notifyDataSetChanged();
                                kpiAdapterTahunan.dialog.dismiss();
                                kpiAdapterTahunan.showUploadDialog(question.get(pos).getPhotoCapture(), pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());
                            }

                            recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerViewImg.setLayoutManager(mLayoutManager);
                            KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity, "Y", question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());
                            recyclerViewImg.setAdapter(adapter);
                            pd.dismiss();
                        }
                    });


//                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//                Call<ImageUploadModelPostPA> call = apiService.postKPIEvidencePA(imageUploadModelPostPA,"Bearer "+usr.get(0).getToken());
//                call.enqueue(new Callback<ImageUploadModelPostPA>() {
//                    @Override
//                    public void onResponse(Call<ImageUploadModelPostPA> call, Response<ImageUploadModelPostPA> response) {
//                        int statusCode = response.code();
//
//                        question.get(pos).setPhotoCapture(imgCaptureList);
//                        postTemp = pos;
//                        if (kpiAdapterTahunan != null) {
//                            kpiAdapterTahunan.currentPosition = pos+1;
////                    kpiAdapterTahunan.isVisible = true;
//                            kpiAdapterTahunan.notifyDataSetChanged();
//                            kpiAdapterTahunan.dialog.dismiss();
//                            kpiAdapterTahunan.showUploadDialog( question.get(pos).getPhotoCapture(),pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());
//                        }
//
//                        recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                        recyclerViewImg.setLayoutManager(mLayoutManager);
//                        KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity, "Y",question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());
//
//                        recyclerViewImg.setAdapter(adapter);
//
//                    }
//                    @Override
//                    public void onFailure(Call<ImageUploadModelPostPA> call, Throwable t) {
//                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
//                        question.get(pos).setPhotoCapture(imgCaptureList);
//                        postTemp = pos;
//                        if (kpiAdapterTahunan != null) {
//                            kpiAdapterTahunan.currentPosition = pos+1;
////                    kpiAdapterTahunan.isVisible = true;
//                            kpiAdapterTahunan.notifyDataSetChanged();
//                            kpiAdapterTahunan.dialog.dismiss();
//                            kpiAdapterTahunan.showUploadDialog( question.get(pos).getPhotoCapture(),pos, question.get(pos).getKpiId(), question.get(pos).getPaId(), question.get(pos).getKPIcategory());
//                        }
//
//                        recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
//                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                        recyclerViewImg.setLayoutManager(mLayoutManager);
//                        KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity, "Y",question.get(pos).getPaId(), question.get(pos).getKpiId(), question.get(pos).getKpiId(), question.get(pos).getSemester(), question.get(pos).getKPIcategory());
//                        recyclerViewImg.setAdapter(adapter);
//                    }
//                });
                }
                else{
                    Toast.makeText(getContext(), "Only 2MB file size allowed!", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //handle the home button onClick event here.
                Toast.makeText(getContext(),"clicked",Toast.LENGTH_LONG).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    public boolean getdiUbah(){
        return diUbah;
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
    public void setUbah(boolean diUbah) {
        this.diUbah = diUbah;

    }

    @Override
    public boolean getUbah() {
        return diUbah;
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
            //File TempPhotoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(getContext());
                //photoFile = new Compressor(getContext()).compressToFile(TempPhotoFile);

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

               try { // Add the URI so the camera can store the image
                   takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                   // Launch the camera activity
                   startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
               }
               catch (Exception e){

               }
            }
        }
    }
    private void getAllRoomChat(String contains, KPIHeader kh){
//        lnProgress2.setVisibility(View.VISIBLE);

        root = FirebaseDatabase.getInstance().getReference();

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    if(dataSnap.getKey().contains(contains)) {
                        InboxChatModel ic = new InboxChatModel();
                        ic.setFrom(dataSnap.getKey());

                        //==============================================
                        root = FirebaseDatabase.getInstance().getReference().child(dataSnap.getKey());
                        Query query = root.orderByChild("status-"+contains).equalTo("UNREAD");

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                List<String> message = new ArrayList<>();
                                List<String> time = new ArrayList<>();
                                List<String> fromPhoto = new ArrayList<>();
                                List<String> kualitatif = new ArrayList<>();
                                List<String> kuantitatif = new ArrayList<>();

                                ic.setFrom(dataSnap.getKey());
                                try {
                                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                                        try {
                                            message.add(dataSnap.child("message").getValue().toString());
                                            time.add(dataSnap.child("sentTime").getValue().toString());
                                            fromPhoto.add(dataSnap.child("userPhoto").getValue().toString());
                                            kualitatif.add(dataSnap.child("kualitatif").getValue().toString());
                                            kuantitatif.add(dataSnap.child("kuantitatif").getValue().toString());
                                        } catch (Exception e) {
                                            message.add("");
                                            time.add("");
                                            fromPhoto.add("");
                                            kualitatif.add("");
                                            kuantitatif.add("");
                                        }
                                    }
                                }
                                catch (Exception e){
                                    message.add("");
                                    time.add("");
                                    fromPhoto.add("");
                                    kualitatif.add("");
                                    kuantitatif.add("");
                                }
                                ic.setTime(time);
                                ic.setMessage(message);
                                ic.setFromPhoto(fromPhoto);
                                ic.setKualitatif(kualitatif);
                                ic.setKuantitatif(kuantitatif);
                                lInbox.add(ic);
                                try {
                                    for(int i=0;i<kh.getKpiQuestionsList().size();i++){
                                        int count=0;
                                        for (int j=0;j<lInbox.size();j++){
                                            if(lInbox.get(j).getFrom().contains(usr.get(0).getEmpNIK())){
                                                for (int k=0;k<lInbox.get(j).getMessage().size();k++) {
                                                    if(lInbox.get(j).getKuantitatif().get(k).equals(kh.getKpiQuestionsList().get(i).getKPIDesc())){
                                                        count++;
                                                        //Toast.makeText(getContext(),lInbox.get(j).getMessage().get(k),Toast.LENGTH_SHORT).show();
                                                        kh.getKpiQuestionsList().get(i).setMessageNotification(lInbox.get(j).getMessage().get(k));
                                                    }
                                                    else if(lInbox.get(j).getKualitatif().get(k).equals(kh.getKpiQuestionsList().get(i).getKPIDesc())){
                                                        count++;
                                                        kh.getKpiQuestionsList().get(i).setMessageNotification(lInbox.get(j).getMessage().get(k));

                                                    }
                                                }
                                            }
                                        }

                                        //  kh.getKpiQuestionsList().get(i).setMessageNotification(Integer.toString(count));
                                    }
                                    setInitKh(kh);
                                    initQuestionsAdapter(kh,lInbox);

                                }
                                catch (Exception e){
                                    //Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                                    setInitKh(kh);
                                    initQuestionsAdapter(kh,lInbox);
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        //==============================================
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public KPIHeader getkpiHeader(){
        return kh;
    }

    public void SaveKPI(Context context){
        if (type.equals("Approve")) {
            List<KPIAnswer> kpiAnswerList = new ArrayList<>();
            KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

            for (int i = 0; i < initKH.getKpiQuestionsList().size(); i++) {
                KPIAnswer kpiAnswer = new KPIAnswer();


                if (initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                    kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                    kpiAnswer.setKPIID("0");
                } else {
                    kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                    kpiAnswer.setCOMPID("0");
                }
                kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                kpiAnswer.setACTUAL(initKH.getKpiQuestionsList().get(i).getActual());
                kpiAnswer.setTARGET(initKH.getKpiQuestionsList().get(i).getTarget());
                kpiAnswerList.add(kpiAnswer);
                //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();

                kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                kpiAnswerList1.setSTATUS(initKH.getStatus());
                kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                kpiAnswerList1.setDevPlanHeaderList(initKH.getDevPlanHeaderList());


            }


//            if (initKH.getStrength().length() < 0 ) {
//                Toast.makeText(context,"Strength wajib diisi!!!",Toast.LENGTH_LONG).show();
//            } else {
                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
                call.enqueue(new Callback<KPIAnswerList>() {
                    @Override
                    public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                        int statusCode = response.code();
                        //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Saved Successfully!!", Toast.LENGTH_SHORT).show();
                        //pd.dismiss();
//                                            lnProgressBar.setVisibility(View.GONE);
//                                            recyclerViewKPI.setVisibility(View.VISIBLE);
//                                            recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));
                    }

                    @Override
                    public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                        Toast.makeText(context, "Saved!!", Toast.LENGTH_SHORT).show();
                        //pd.dismiss();
//                                            lnProgressBar.setVisibility(View.GONE);
//                                            recyclerViewKPI.setVisibility(View.VISIBLE);
//                                            recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));


                    }
                });
//            }

        } else {


            //lnProgressBar.setVisibility(View.VISIBLE);
            ProgressDialog pd = new ProgressDialog(getContext());
            pd.setMessage("Saving...");
            pd.setCancelable(false);
            pd.show();
            KPIAnswerList kpiAnswerList1 = new KPIAnswerList();

//                    recyclerViewKPI.setVisibility(View.INVISIBLE);
//                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.white));

            List<KPIAnswer> kpiAnswerList = new ArrayList<>();
            Toast.makeText(getActivity(), "Saved!!", Toast.LENGTH_SHORT).show();

            for (int i = 0; i < initKH.getKpiQuestionsList().size(); i++) {
                KPIAnswer kpiAnswer = new KPIAnswer();

                if (initKH.getKpiQuestionsList().get(i).getKPIcategory().equals("KUALITATIF")) {
                    kpiAnswer.setCOMPID(initKH.getKpiQuestionsList().get(i).getKpiId());
                    kpiAnswer.setKPIID("0");
                } else {
                    kpiAnswer.setKPIID(initKH.getKpiQuestionsList().get(i).getKpiId());
                    kpiAnswer.setCOMPID("0");
                }
                kpiAnswer.setCP(Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()));
                kpiAnswer.setEMPNIK(usr.get(0).getEmpNIK());
                kpiAnswer.setPAID(initKH.getKpiQuestionsList().get(i).getPaId());
                kpiAnswer.setKPITYPE(initKH.getKpiQuestionsList().get(i).getKPIcategory());
                kpiAnswer.setSEMESTER(initKH.getKpiQuestionsList().get(i).getSemester());
                kpiAnswer.setEVIDENCES(initKH.getKpiQuestionsList().get(i).getEvidence());
                kpiAnswer.setACTUAL(initKH.getKpiQuestionsList().get(i).getActual());
                kpiAnswer.setTARGET(initKH.getKpiQuestionsList().get(i).getTarget());
                kpiAnswerList.add(kpiAnswer);
                //Toast.makeText(getContext(),Integer.toString(initKH.getKpiQuestionsList().get(i).getCheckedId()),Toast.LENGTH_LONG).show();


                kpiAnswerList1.setNIKBAWAHAN(initKH.getNIK());
                kpiAnswerList1.setPAID(initKH.getKpiQuestionsList().get(0).getPaId());
                kpiAnswerList1.setSTATUS(initKH.getStatus());
                kpiAnswerList1.setSTRENGTH(initKH.getStrength());
                kpiAnswerList1.setKpiAnswerList(kpiAnswerList);
                kpiAnswerList1.setDevPlanHeaderList(initKH.getDevPlanHeaderList());
            }


            ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
            Call<KPIAnswerList> call = apiService.postKPIAnswer(kpiAnswerList1, "Bearer " + usr.get(0).getToken());
            call.enqueue(new Callback<KPIAnswerList>() {
                @Override
                public void onResponse(Call<KPIAnswerList> call, Response<KPIAnswerList> response) {
                    pd.dismiss();
//                                    lnProgressBar.setVisibility(View.GONE);
//                                    recyclerViewKPI.setVisibility(View.VISIBLE);
//                                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));

                    int statusCode = response.code();
                    //Toast.makeText(getContext(), Integer.toString(statusCode), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Saved!!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<KPIAnswerList> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(context, "Saved!!", Toast.LENGTH_SHORT).show();
//                                    lnProgressBar.setVisibility(View.GONE);
//                                    recyclerViewKPI.setVisibility(View.VISIBLE);
//                                    recyclerViewKPI.setBackgroundColor(getResources().getColor(R.color.greenPastel));

//                            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();

                }
            });
//                        Toast.makeText(getContext(), "Saved!!", Toast.LENGTH_SHORT).show();
        }
    }



    private void sendEmail(EmailSentModel emailSentModel){
        List<EmailSentModel> emailSentModelList = new ArrayList<>();
        emailSentModelList.add(emailSentModel);

        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<Void> call2 = apiService.sendEmailtoPIC(emailSentModelList,"Bearer " + usr.get(0).getToken());
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                Toast.makeText(getContext(),"Email berhasil dikirim",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"Email berhasil dikirim",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
