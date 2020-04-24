package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.FinalPAFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.ApprovalListAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.PerhitunganPAAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.PerhitunganPAEmpAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.SortAdapter;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.CameraApp.BitmapUtils;
import com.enseval.samuelseptiano.hcservice.ExpandableListAdapter.ExpandAdapter;
import com.enseval.samuelseptiano.hcservice.ExpandableListAdapter.ExpandModel;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.KPIKuantitatifTahunanFragment;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.MVVM.DistNormal.ItemAdapter;
import com.enseval.samuelseptiano.hcservice.MVVM.DistNormal.ItemViewModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ApprDNModell;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ApproveRejectDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.DistNormalH;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ProcessDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.SortDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.ImageUploadModelPostPA;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.PAPeriodeModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.EmailSentModel;
import com.enseval.samuelseptiano.hcservice.Model.FileUploadAPModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.utils.FileUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.textfield.TextInputEditText;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DistribusiNormalFragment extends Fragment implements ItemAdapter.EventListener,ExpandAdapter.EventListener{

    View rootView;
    RecyclerView recyclerView, rvEmployee;
    PerhitunganPAAdapter perhitunganPAAdapter;
    PerhitunganPAEmpAdapter perhitunganPAEmpAdapter;

    //===========================

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    //=================================

    private boolean canAdjust = true;
    String DNTitle="";
    private String DNID = "";
    private String RejectDialog="";
    int page=0;
    int pageSize=500;

    List<PAPeriodeModel> paPeriodeModelList = new ArrayList<>();

    private static final int PICK_FILE_REQUEST = 2;
    private static final String FILE_PROVIDER_AUTHORITY = "com.enseval.samuelseptiano.hcservice.fileprovider";

    List<PerhitunganPAModel> perhitunganPAModelList = new ArrayList<>();
    List<PerhitunganPAEMPModel> perhitunganPAEMPModelList = new ArrayList<>();

    List<ApprDNModell> apprDNModellList = new ArrayList<>();

    List<DistNormalH> distNormalHList = new ArrayList<>();


    List<SortDNModel> sortDNModelList = new ArrayList<>();
    SortAdapter sortAdapter;

    WebView wbChart;
    BarChart barChart;

    ImageButton btnListAppr, btnSort, btnNotifReject;

    Button btnApprove,btnReject;
    TextInputEditText edtSearch;
    MaterialSpinner spinnerTahun;

    private SmartMaterialSpinner spDN;


    List<String> dataDN=new ArrayList<>();

    TextView tvMean,tvStdDev;

    ArrayList<UserRealmModel> usr = new ArrayList<>();

    LinearLayoutManager mLayoutManager;

    Button btnProcessDN;

    ImageButton imgBtnExport, imgBtnImport;
    Dialog dialog;

    AutoCompleteTextView autoCompleteDN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_distribusi_normal, container, false);

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Distribusi Normal");

        wbChart = (WebView) rootView.findViewById(R.id.wbChart);
        barChart = (BarChart) rootView.findViewById(R.id.barchart);
        btnListAppr = rootView.findViewById(R.id.btnListAppr);
        btnSort = rootView.findViewById(R.id.btnSort);
        edtSearch = rootView.findViewById(R.id.edtSearch);
        btnReject = rootView.findViewById(R.id.btnReject);
        btnNotifReject = rootView.findViewById(R.id.btnNotifReject);
        btnApprove = rootView.findViewById(R.id.btnApproveDN);
        tvMean = rootView.findViewById(R.id.tvMean);
        tvStdDev = rootView.findViewById(R.id.tvStandarDeviasi);

        spinnerTahun = rootView.findViewById(R.id.spinnerTahun);
        spinnerTahun.setBackgroundResource(R.drawable.shapedropdown);
        spinnerTahun.setPadding(25, 10, 25, 10);

        imgBtnExport = rootView.findViewById(R.id.btnExport);
        imgBtnImport = rootView.findViewById(R.id.btnImport);

        autoCompleteDN = rootView.findViewById(R.id.autoCompleteDN);
        spDN = rootView.findViewById(R.id.spDN);
        spDN.setSearchable(true);
        spDN.setAlwaysShowFloatingLabel(false);

        btnProcessDN = rootView.findViewById(R.id.btnProcessDN);

        btnApprove.setVisibility(View.GONE);
        btnReject.setVisibility(View.GONE);
        btnNotifReject.setVisibility(View.GONE);
        btnProcessDN.setVisibility(View.GONE);
        btnListAppr.setVisibility(View.GONE);
        imgBtnExport.setVisibility(View.GONE);
        imgBtnImport.setVisibility(View.GONE);
        initData();

        return  rootView;
    }

    public void initData(){

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        usr = userRealmHelper.findAllArticle();

        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<PAPeriodeModel>> calls = apiService.getPAPeriode("Bearer "+usr.get(0).getToken());
        calls.enqueue(new Callback<List<PAPeriodeModel>>() {
            @Override
            public void onResponse(Call<List<PAPeriodeModel>> calls, Response<List<PAPeriodeModel>> responses) {
                int statusCode = responses.code();
                paPeriodeModelList = responses.body();
                List<String> tahunSelf = new ArrayList<String>();
                for(int i=0;i< paPeriodeModelList.size();i++){
                    tahunSelf.add(paPeriodeModelList.get(i).getTahun());
                }

                spinnerTahun.setItems(tahunSelf);
                //============================ HARDCODE =====================
                ApiInterface apiService2 = ApiClient.getClientTest(getContext()).create(ApiInterface.class);

                Call<List<DistNormalH>> call = apiService2.getDistNormalH(usr.get(0).getEmpNIK(),usr.get(0).getPrivilege(),spinnerTahun.getText().toString(), "Bearer " + usr.get(0).getToken());
                call.enqueue(new Callback<List<DistNormalH>>() {
                    @Override
                    public void onResponse(Call<List<DistNormalH>> call, Response<List<DistNormalH>> response) {
                        distNormalHList = response.body();

                        if(response.body().size()<1){}
                        else {


                            autoCompleteDN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View arg0) {
                                    //autoCompleteDN.showDropDown();
                                    showExpandDialog();
                                }
                            });

                                //                    autoCompleteDN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            Toast.makeText(getContext(),adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_LONG).show();
//                            btnApprove.setVisibility(View.VISIBLE);
//                            btnReject.setVisibility(View.VISIBLE);
//                            //btnNotifReject.setVisibility(View.VISIBLE);
//                            btnListAppr.setVisibility(View.VISIBLE);
//                            btnProcessDN.setVisibility(View.VISIBLE);
//                            int ctrReject=0;
//                            for (int j = 0; j < distNormalHList.size(); j++) {
//                                if(adapterView.getItemAtPosition(i).equals(distNormalHList.get(j).getDNGROUPNAME())){
//                                    imgBtnExport.setVisibility(View.VISIBLE);
//                                    getApprList(distNormalHList.get(j).getDNID());
////
//
//                                    prepareGraphTable(distNormalHList.get(j));
//
//                                    tvMean.setText("Mean :"+(Math.round(Double.parseDouble(distNormalHList.get(j).getDNMEAN().replace(",",".")) * 100.0) / 100.0));
//                                    tvStdDev.setText("Standar Deviasi :"+(Math.round(Double.parseDouble(distNormalHList.get(j).getDNSTDEV().replace(",",".")) * 100.0) / 100.0));
//
//                                    contoh2bar(distNormalHList.get(j));
//
//                                    DNID = distNormalHList.get(j).getDNID();
//                                    RejectDialog = distNormalHList.get(j).getDNALASANREJECT();
//                                    getActivity().getViewModelStore().clear();
//                                    prepareEmployeeTable(distNormalHList.get(j).getDNID(),page,pageSize,"0");
//
//                                    LinearLayoutManager finalMLayoutManager = mLayoutManager;
//                                    int finalJ = j;
//
//
//                                    btnProcessDN.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//
//                                            AlertDialog alertbox = new AlertDialog.Builder(getContext())
//                                                    .setMessage("Apakah anda yakin ingin memproses balancing "+autoCompleteDN.getText().toString()+"?")
//                                                    .setPositiveButton("YA", new DialogInterface.OnClickListener() {
//
//                                                        // do something when the button is clicked
//                                                        public void onClick(DialogInterface arg0, int arg1) {
//                                                            ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
//                                                            //============================ HARDCODE =====================
//                                                            ProcessDNModel processDNModel = new ProcessDNModel();
//                                                            processDNModel.setDnID(distNormalHList.get(finalJ).getDNID());
//                                                            processDNModel.setGroupID(distNormalHList.get(finalJ).getDNID());
//                                                            processDNModel.setGroupIDName(distNormalHList.get(finalJ).getDNGROUPNAME());
//                                                            processDNModel.setPeriode(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
//                                                            processDNModel.setUpdUser(usr.get(0).getUsername());
//                                                            Call<Void> call = apiService.processDN(processDNModel, "Bearer " + usr.get(0).getToken());
//                                                            call.enqueue(new Callback<Void>() {
//                                                                @Override
//                                                                public void onResponse(Call<Void> call, Response<Void> response) {
//                                                                    int statusCode = response.code();
//                                                                    Toast.makeText(getContext(), "Process Complete", Toast.LENGTH_LONG).show();
//
//                                                                }
//
//                                                                @Override
//                                                                public void onFailure(Call<Void> call, Throwable t) {
//                                                                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
//                                                                }
//                                                            });
//                                                        }
//                                                    })
//                                                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                                        // do something when the button is clicked
//                                                        public void onClick(DialogInterface arg0, int arg1) {
//
//                                                        }
//                                                    })
//                                                    .show();
//                                            alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey2));
//                                            alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));
//
//                                        }
//                                    });
//
//                                }
//                            }
//                        }
//                    });


//                    spinnerDN.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                            for (int j = 0; j < distNormalHList.size(); j++) {
//                                if(item.equals(distNormalHList.get(j).getDNGROUPNAME())){
//
//                                    getApprList(distNormalHList.get(j).getDNID());
////
//
//                                    prepareGraphTable(distNormalHList.get(j));
//
//                                    tvMean.setText("Mean :"+distNormalHList.get(j).getDNMEAN());
//                                    tvStdDev.setText("Standar Deviasi :"+distNormalHList.get(j).getDNSTDEV());
//
//                                    contoh2bar(distNormalHList.get(j));
//
//                                    DNID = distNormalHList.get(j).getDNID();
//                                    RejectDialog = distNormalHList.get(j).getDNALASANREJECT();
//                                    getActivity().getViewModelStore().clear();
//                                    prepareEmployeeTable(distNormalHList.get(j).getDNID(),page,pageSize,"0");
//
//                                    LinearLayoutManager finalMLayoutManager = mLayoutManager;
//                                    int finalJ = j;
//
//
//                                    btnProcessDN.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//
//                                            AlertDialog alertbox = new AlertDialog.Builder(getContext())
//                                                    .setMessage("Apakah anda yakin ingin memproses balancing "+spinnerDN.getText().toString()+"?")
//                                                    .setPositiveButton("YA", new DialogInterface.OnClickListener() {
//
//                                                        // do something when the button is clicked
//                                                        public void onClick(DialogInterface arg0, int arg1) {
//                                                            ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
//                                                            //============================ HARDCODE =====================
//                                                            ProcessDNModel processDNModel = new ProcessDNModel();
//                                                            processDNModel.setDnID(distNormalHList.get(finalJ).getDNID());
//                                                            processDNModel.setGroupID(distNormalHList.get(finalJ).getDNID());
//                                                            processDNModel.setGroupIDName(distNormalHList.get(finalJ).getDNGROUPNAME());
//                                                            processDNModel.setPeriode(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
//                                                            processDNModel.setUpdUser(usr.get(0).getUsername());
//                                                            Call<Void> call = apiService.processDN(processDNModel, "Bearer " + usr.get(0).getToken());
//                                                            call.enqueue(new Callback<Void>() {
//                                                                @Override
//                                                                public void onResponse(Call<Void> call, Response<Void> response) {
//                                                                    int statusCode = response.code();
//                                                                    Toast.makeText(getContext(), "Process Complete", Toast.LENGTH_LONG).show();
//
//                                                                }
//
//                                                                @Override
//                                                                public void onFailure(Call<Void> call, Throwable t) {
//                                                                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
//                                                                }
//                                                            });
//                                                        }
//                                                    })
//                                                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//
//                                                        // do something when the button is clicked
//                                                        public void onClick(DialogInterface arg0, int arg1) {
//
//                                                        }
//                                                    })
//                                                    .show();
//                                            alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey2));
//                                            alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));
//
//                                        }
//                                    });
//
//                                }
//                            }
//                        }
//                    });
                        }

                    }

                    @Override
                    public void onFailure(Call<List<DistNormalH>> call, Throwable t) {
                        Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                //pd.dismiss();
            }
            @Override
            public void onFailure(Call<List<PAPeriodeModel>> call, Throwable t) {
                Toast.makeText(getContext(),"Error Connection..",Toast.LENGTH_SHORT).show();
                //pd.dismiss();

            }
        });



        imgBtnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("*/*"); // intent.setType("video/*"); to select videos to upload
                String[] mimetypes = {"application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pilih dokumen"), PICK_FILE_REQUEST);
            }
        });
        //LINK DOWNLOAD  FILE EXPORT EXCEL MASIH HARDCODE
        imgBtnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                        .setMessage("Apakah anda ingin melakukan export data "+autoCompleteDN.getText()+"?")
                        .setPositiveButton("YA", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(ApiClient.BASE_URL_TEST+"api/distnormal/export/"+DNID));

                                request.setTitle("Export-"+DNID+".xlsx");
                                request.setDescription("Downloading Evidence File");
                                    // we just want to download silently
                                request.setVisibleInDownloadsUi(true);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Export-"+DNID+".xlsx");
                                    // enqueue this request
                                DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                long downloadID = downloadManager.enqueue(request);

                                Toast.makeText(getContext(),"Export File Sukses",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        })
                        .show();
                alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey2));
                alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));
            }
        });


        btnListAppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showApprListDialog(apprDNModellList);
            }
        });

        btnNotifReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRejectDialog(autoCompleteDN.getText().toString(),DNID,true,RejectDialog);

            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRejectDialog(autoCompleteDN.getText().toString(),DNID,false,"");
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortDialog();

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getActivity().getViewModelStore().clear();
                if(charSequence.length()<1 || charSequence.equals("")) {
                    prepareEmployeeTable(DNID, page, pageSize, "0");
                }
                else{
                    prepareEmployeeTable(DNID, page, pageSize, charSequence.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener()
        {
            @Override
            public void onValueSelected(Entry e, Highlight h)
            {
                float x=e.getX();
                float y=e.getY();

                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.CEILING);

                //Toast.makeText(getContext(),df.format(x)+"",Toast.LENGTH_SHORT).show();

//                List<PerhitunganPAEMPModel> xx = new ArrayList<>();
//                xx.addAll(perhitunganPAEMPModelList);
//               for(int i=0;i<xx.size();i++){
//                   if(xx.get(i).getStarDN() != Math.ceil(x)){
//                       xx.remove(i);
//                   }
//               }
//                perhitunganPAEmpAdapter = new PerhitunganPAEmpAdapter(xx, getContext(), ConnectivityReceiver.isConnected(), getActivity());
//                rvEmployee.setAdapter(perhitunganPAEmpAdapter);
            }

            @Override
            public void onNothingSelected()
            {

            }
        });

        //============DN Table Employee=======================
        rvEmployee = (RecyclerView) rootView.findViewById(R.id.rvDNEmp);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvEmployee.setLayoutManager(mLayoutManager);
        //==================================================

        //============DN Table Grafik=======================
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDNGraphTable);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        perhitunganPAAdapter = new PerhitunganPAAdapter(perhitunganPAModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity());
        recyclerView.setAdapter(perhitunganPAAdapter);
        //==================================================

    }


    private void contoh2bar(DistNormalH dh){

        List<BarEntry> DNDone = new ArrayList();
        DNDone.add(new BarEntry(0f, Float.parseFloat(dh.getJUMLAHDNBAE().replace(",","."))));
        DNDone.add(new BarEntry(1f, Float.parseFloat(dh.getJUMLAHDNBAB().replace(",","."))));
        DNDone.add(new BarEntry(2f, Float.parseFloat(dh.getJUMLAHDNBAL().replace(",","."))));
        DNDone.add(new BarEntry(3f, Float.parseFloat(dh.getJUMLAHDNBAA().replace(",","."))));
        DNDone.add(new BarEntry(4f, Float.parseFloat(dh.getJUMLAHDNBAK().replace(",","."))));
        DNDone.add(new BarEntry(5f, 0));

        List<BarEntry> DNundone = new ArrayList();
        DNundone.add(new BarEntry(0f, Float.parseFloat(dh.getJUMLAHDNBAEADJ().replace(",","."))));
        DNundone.add(new BarEntry(1f, Float.parseFloat(dh.getJUMLAHDNBABADJ().replace(",","."))));
        DNundone.add(new BarEntry(2f, Float.parseFloat(dh.getJUMLAHDNBALADJ().replace(",","."))));
        DNundone.add(new BarEntry(3f, Float.parseFloat(dh.getJUMLAHDNBAAADJ().replace(",","."))));
        DNundone.add(new BarEntry(4f, Float.parseFloat(dh.getJUMLAHDNBAKADJ().replace(",","."))));
        DNDone.add(new BarEntry(5f, 0));

//        List<String> year = new ArrayList();
//        year.add("2020");

        BarDataSet bardataset = new BarDataSet(DNDone, "Hasil Distribusi Normal");
        BarDataSet bardataset2 = new BarDataSet(DNundone, "Hasil Adjustment");
        barChart.animateY(5000);

        bardataset.setColors(getContext().getResources().getColor(R.color.colorPrimaryDark));
        bardataset2.setColors(getContext().getResources().getColor(R.color.light_grey2));
        BarData data = new BarData((IBarDataSet)  bardataset,(IBarDataSet)  bardataset2);
//
        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
//        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"
//
        barChart.getDescription().setText("Distribusi Normal");

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(xAxisFormatter);

        data.setBarWidth(barWidth); // set the width of each bar
        barChart.setData(data);
        barChart.groupBars(0f, groupSpace, barSpace); // perform the "explicit" grouping
        //barChart.animateXY(2000, 2000);
        barChart.invalidate(); // refresh
    }

    private void contoh1bar() {
        List<BarEntry> DNDone = new ArrayList();
        DNDone.add(new BarEntry(0f, 10f));
        DNDone.add(new BarEntry(1f, 12f));
        DNDone.add(new BarEntry(2f, 26f));
        DNDone.add(new BarEntry(3f, 192f));
        DNDone.add(new BarEntry(4f, 46f));

        List<BarEntry> DNundone = new ArrayList();
        DNundone.add(new BarEntry(0f, 11f));
        DNundone.add(new BarEntry(1f, 10f));
        DNundone.add(new BarEntry(2f, 6f));
        DNundone.add(new BarEntry(3f, 14f));
        DNundone.add(new BarEntry(4f, 11f));

        BarDataSet bardataset = new BarDataSet(DNDone, "Done");
        barChart.animateY(5000);

        bardataset.setColors(getContext().getResources().getColor(R.color.colorPrimaryDark));
        BarData data = new BarData((IBarDataSet) bardataset);

        barChart.getDescription().setText("Distribusi Normal");

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(xAxisFormatter);

        barChart.setData(data);
        barChart.invalidate(); // refresh
    }

    public void getApprList(String dnID){

        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
        //============================ HARDCODE =====================
        Call<List<ApprDNModell>> call = apiService.getDistNormarAppr(dnID, "Bearer " + usr.get(0).getToken());
        call.enqueue(new Callback<List<ApprDNModell>>() {
            @Override
            public void onResponse(Call<List<ApprDNModell>> call, Response<List<ApprDNModell>> response) {
                int statusCode = response.code();
                   apprDNModellList = response.body();
                if(usr.get(0).getEmpNIK().equals(apprDNModellList.get(0).getApprNIK())){
                    btnReject.setVisibility(View.GONE);
                }

                    for(int x=0;x<apprDNModellList.size();x++){
                                        if(usr.get(0).getEmpNIK().equals(apprDNModellList.get(x).getApprNIK())
                                        && (apprDNModellList.get(x).getApprStatus().equals("APPROVED")
                                                || apprDNModellList.get(x).getApprStatus().equals("REJECTED"))){
                                            btnApprove.setVisibility(View.GONE);
                                            btnReject.setVisibility(View.GONE);
                                            canAdjust=false;
                                        }
                                        if(apprDNModellList.get(x).getApprStatus().equals("Rejected")){
                                            btnNotifReject.setVisibility(View.VISIBLE);
                                        }

                                    }

                btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                .setMessage("Apakah anda yakin ingin melakukan Approve Distribusi normal ini?")
                                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                                        //============================ HARDCODE =====================
                                        ApproveRejectDNModel ar = new ApproveRejectDNModel();
                                        ar.setDNID(dnID);
                                        ar.setALASANREJECT("");
                                        ar.setEMPNIK(usr.get(0).getEmpNIK());
                                        ar.setUPDUSER(usr.get(0).getUsername());
                                        for(int x=0;x<apprDNModellList.size();x++){
                                            if(usr.get(0).getEmpNIK().equals(apprDNModellList.get(x).getApprNIK())){
                                                ar.setDNAPPROVALSEQ(apprDNModellList.get(x).getDnApprovalSeq());
                                            }
                                        }
                                        Call<Void> call = apiService.approveDistNormal(ar, "Bearer " + usr.get(0).getToken());
                                        call.enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                int statusCode = response.code();
                                                AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                                        .setMessage("Proses approve berhasil")
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                                            // do something when the button is clicked
                                                            public void onClick(DialogInterface arg0, int arg1) {
//                                                                EmailSentModel emailSentModel = new EmailSentModel();
//                                                                emailSentModel.SUBJECT="[HCService] Distirbusi Normla atas nama  menunggu approval";
//                                                                emailSentModel.MESSAGE="";
//                                                                emailSentModel.SENDERNAME=kh.getEmpName();
//                                                                emailSentModel.SENDERNIK=kh.getNIK();
//                                                                emailSentModel.SENDEREMAIL="hr.service@enseval.com";
//                                                                emailSentModel.MAILTYPE="APPROVE";
//
//                                                                emailSentModel.RECEIVERNAME=kh.getAtasanTakLangsung();
//                                                                emailSentModel.RECEIVERNIK=kh.getNIKAtasanTakLangsung();
//                                                                emailSentModel.RECEIVEREMAIL=kh.getEmailAtasan2();
////                                                    emailSentModel.RECEIVEREMAIL="samuel.septiano@enseval.com";
//
//                                                                sendEmail(emailSentModel, getActivity());
                                                                arg0.dismiss();
                                                            }
                                                        })

                                                        .show();
                                                alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));
                                                prepareEmployeeTable(dnID,page,pageSize,"0");
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();
                                    }
                                })
                                .show();
                        alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey2));
                        alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ApprDNModell>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });

        for(int i=0;i<3;i++){
            ApprDNModell ap = new ApprDNModell();
            ap.setApprName("Donald Duck");
            ap.setApprStatus("Approved");
            ap.setApprDate("13/02/2020");
            apprDNModellList.add(ap);
        }
    }

    public void showApprListDialog(List<ApprDNModell> apprDNModellList){
        Dialog dialog = new Dialog(getContext());
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.approval_list_dn_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        RecyclerView rvApprovalList = dialog.findViewById(R.id.rv_approvalListDN);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvApprovalList.setLayoutManager(mLayoutManager);
        ApprovalListAdapter approvalListAdapter = new ApprovalListAdapter(apprDNModellList,getContext(),ConnectivityReceiver.isConnected(),getActivity());
        rvApprovalList.setAdapter(approvalListAdapter);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showRejectDialog( String title, String DNID, boolean isNotif,String rejectMessage){
        Dialog dialog = new Dialog(getContext());
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.reject_dn_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        EditText edtAlasanReject = dialog.findViewById(R.id.edtReject);
        Button btnRejectDN = dialog.findViewById(R.id.btnRejectDN);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        tvTitle.setText("Reject "+title);

        if(isNotif){
            tvTitle.setText("Notifikasi Reject "+title);
            btnRejectDN.setVisibility(View.GONE);
            edtAlasanReject.setText(rejectMessage);
            edtAlasanReject.setEnabled(false);
        }

        btnRejectDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAlasanReject.getText().toString().length()>0){

                    AlertDialog alertbox = new AlertDialog.Builder(getContext())
                            .setMessage(Html.fromHtml("Apakah anda yakin ingin melakukan reject distribusi normal ini? <br><br> <i>Note :</i><br><b>Ya (1 level): </b>distribusi normal akan dikembalikan ke approver sebelum anda.<br><b>Ya (semua):</b> distribusi normal akan dikembalikan ke approver pertama"))

                            .setPositiveButton("YA(1 Level)", new DialogInterface.OnClickListener() {
                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    sendReject(edtAlasanReject.getText().toString(), dialog, "");
                                }
                            })
                            .setNegativeButton("YA(Semua)", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                    sendReject(edtAlasanReject.getText().toString(), dialog, "Y");
                                }
                            })
                            .setNeutralButton("Tidak", new DialogInterface.OnClickListener() {

                                // do something when the button is clicked
                                public void onClick(DialogInterface arg0, int arg1) {
                                        arg0.dismiss();
                                }
                            })
                            .show();
                    alertbox.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getContext().getColor(R.color.light_grey2));
                    alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey2));
                    alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

                }
                else{
                    Toast.makeText(getContext(),"Alasan reject tidak boleh kosong..", Toast.LENGTH_LONG).show();

                }

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showExpandDialog(){
        dialog = new Dialog(getContext());
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.expand_list_dialog);
//        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        RecyclerView rvExpand = dialog.findViewById(R.id.rvExpand);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvExpand.setLayoutManager(mLayoutManager);
        List<ExpandModel> em = new ArrayList<>();

        if(usr.get(0).getPrivilege().contains("A")){
            ExpandModel expandModel = new ExpandModel();
            for (int j = 0; j < distNormalHList.size(); j++) {
                    if (distNormalHList.get(j).getDNSTATUS().equals("A")) {
                        expandModel = new ExpandModel();
                        expandModel.setItem(distNormalHList.get(j).getDNGROUPNAME());
                        expandModel.setTitle("ON PROGRESS");
                        em.add(expandModel);
                    }
            }
            for (int j = 0; j < distNormalHList.size(); j++) {
                    if (distNormalHList.get(j).getDNSTATUS().equals("C")) {
                        {
                        expandModel = new ExpandModel();
                        expandModel.setItem(distNormalHList.get(j).getDNGROUPNAME());
                        expandModel.setTitle("COMPLETE");
                        em.add(expandModel);
                    }
            }
        }
        }
        else {
            ExpandModel expandModel = new ExpandModel();
            for (int j = 0; j < distNormalHList.size(); j++) {
                for (int i = 0; i < distNormalHList.get(j).getDistNormalApprList().size(); i++) {
                    if (distNormalHList.get(j).getDistNormalApprList().get(i).getApprNIK().equals(
                            usr.get(0).getEmpNIK()
                    ) && distNormalHList.get(j).getDistNormalApprList().get(i).getFgCanApproveYN().equals("Y")
                            && distNormalHList.get(j).getDistNormalApprList().get(i).getFgHasApproveYN().equals("N")) {
                        expandModel = new ExpandModel();
                        expandModel.setItem(distNormalHList.get(j).getDNGROUPNAME());
                        expandModel.setTitle("NEED APPROVAL");
                        em.add(expandModel);

                    }
                }
            }
            for (int j = 0; j < distNormalHList.size(); j++) {
                for (int i = 0; i < distNormalHList.get(j).getDistNormalApprList().size(); i++) {
                    if (distNormalHList.get(j).getDistNormalApprList().get(i).getApprNIK().equals(
                            usr.get(0).getEmpNIK()
                    ) && distNormalHList.get(j).getDistNormalApprList().get(i).getFgCanApproveYN().equals("Y")
                            && distNormalHList.get(j).getDistNormalApprList().get(i).getFgHasApproveYN().equals("Y") && !distNormalHList.get(j).getDNSTATUS().equals("C")) {
                        expandModel = new ExpandModel();
                        expandModel.setItem(distNormalHList.get(j).getDNGROUPNAME());
                        expandModel.setTitle("ON PROGRESS");
                        em.add(expandModel);

                    }
                }
            }
            for (int j = 0; j < distNormalHList.size(); j++) {
                for (int i = 0; i < distNormalHList.get(j).getDistNormalApprList().size(); i++) {
                    if (distNormalHList.get(j).getDistNormalApprList().get(i).getApprNIK().equals(
                            usr.get(0).getEmpNIK()
                    ) && distNormalHList.get(j).getDistNormalApprList().get(i).getFgCanApproveYN().equals("Y")
                            && distNormalHList.get(j).getDistNormalApprList().get(i).getFgHasApproveYN().equals("Y") && distNormalHList.get(j).getDNSTATUS().equals("C")) {
                        expandModel = new ExpandModel();
                        expandModel.setItem(distNormalHList.get(j).getDNGROUPNAME());
                        expandModel.setTitle("COMPLETE");
                        em.add(expandModel);
                    }
                }
            }
        }
        ExpandAdapter expandAdapter = new ExpandAdapter(em, getContext(), getActivity(),this);
        rvExpand.setAdapter(expandAdapter);

        SearchView searchGroup = dialog.findViewById(R.id.searchGroup);
        searchGroup.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                expandAdapter.filter(s);
                return true;
            }
        });


        dialog.show();
    }


    private void sendReject(String alasanReject, Dialog dialog, String isRejectAll){
        ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);

        ApproveRejectDNModel ar = new ApproveRejectDNModel();
        ar.setDNID(DNID);
        ar.setALASANREJECT(alasanReject);
        ar.setEMPNIK(usr.get(0).getEmpNIK());
        ar.setUPDUSER(usr.get(0).getUsername());
        ar.setISREJECTALL(isRejectAll);

        for(int x=0;x<apprDNModellList.size();x++){
            if(usr.get(0).getEmpNIK().equals(apprDNModellList.get(x).getApprNIK())){
                ar.setDNAPPROVALSEQ(apprDNModellList.get(x).getDnApprovalSeq());
            }
        }
        Call<Void> call = apiService.rejectDistNormal(ar, "Bearer " + usr.get(0).getToken());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                Toast.makeText(getContext(),"Reject Distribusi Normal Berhasil",Toast.LENGTH_LONG).show();
                if(isRejectAll.equals("N")){
                    //EmailSentModel emailSentModel = new EmailSentModel();
//                                                                emailSentModel.SUBJECT="[HCService] Distirbusi Normla atas nama  menunggu approval";
//                                                                emailSentModel.MESSAGE="";
//                                                                emailSentModel.SENDERNAME=kh.getEmpName();
//                                                                emailSentModel.SENDERNIK=kh.getNIK();
//                                                                emailSentModel.SENDEREMAIL="hr.service@enseval.com";
//                                                                emailSentModel.MAILTYPE="APPROVE";
//
//                                                                emailSentModel.RECEIVERNAME=kh.getAtasanTakLangsung();
//                                                                emailSentModel.RECEIVERNIK=kh.getNIKAtasanTakLangsung();
//                                                                emailSentModel.RECEIVEREMAIL=kh.getEmailAtasan2();
////                                                    emailSentModel.RECEIVEREMAIL="samuel.septiano@enseval.com";
//
//                                                                sendEmail(emailSentModel, getActivity());
                }
                else{
                    for(int i=0;i<apprDNModellList.size();i++){
                        //EmailSentModel emailSentModel = new EmailSentModel();
//                                                                emailSentModel.SUBJECT="[HCService] Distirbusi Normla atas nama  menunggu approval";
//                                                                emailSentModel.MESSAGE="";
//                                                                emailSentModel.SENDERNAME=kh.getEmpName();
//                                                                emailSentModel.SENDERNIK=kh.getNIK();
//                                                                emailSentModel.SENDEREMAIL="hr.service@enseval.com";
//                                                                emailSentModel.MAILTYPE="APPROVE";
//
//                                                                emailSentModel.RECEIVERNAME=kh.getAtasanTakLangsung();
//                                                                emailSentModel.RECEIVERNIK=kh.getNIKAtasanTakLangsung();
//                                                                emailSentModel.RECEIVEREMAIL=kh.getEmailAtasan2();
////                                                    emailSentModel.RECEIVEREMAIL="samuel.septiano@enseval.com";
//
//                                                                sendEmail(emailSentModel, getActivity());
                    }
                }
                dialog.dismiss();
                prepareEmployeeTable(DNID,page,pageSize,"0");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }


    public void showSortDialog(){
        Dialog dialog = new Dialog(getContext());
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.sort_dn_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        RecyclerView rvSortDN = dialog.findViewById(R.id.rv_sortDN);
        Button plusBtn = dialog.findViewById(R.id.btnPlus);
        Button minusBtn = dialog.findViewById(R.id.btnMinus);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSave = dialog.findViewById(R.id.btnSort);


        if(sortDNModelList.size()<1) {
            SortDNModel sd = new SortDNModel();
            sd.setOrder("");
            sd.setSortBy("");
            sd.setOrdinal(1);
            sortDNModelList.add(sd);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSortDN.setLayoutManager(mLayoutManager);
            sortAdapter = new SortAdapter(sortDNModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity(),perhitunganPAEMPModelList);
            rvSortDN.setAdapter(sortAdapter);
        }
        else{
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rvSortDN.setLayoutManager(mLayoutManager);
            sortAdapter = new SortAdapter(sortDNModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity(),perhitunganPAEMPModelList);
            rvSortDN.setAdapter(sortAdapter);
        }
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDNModel sd = new SortDNModel();
                sd.setOrder("");
                sd.setSortBy("");
                sd.setOrdinal(1);
                sortDNModelList.add(sd);
                sortAdapter.notifyDataSetChanged();
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDNModelList.remove(sortDNModelList.size()-1);
                sortAdapter.notifyDataSetChanged();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setSort(sortDNModelList);
                dialog.dismiss();
                perhitunganPAEmpAdapter.notifyDataSetChanged();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
     if(requestCode == PICK_FILE_REQUEST){

         AlertDialog alertbox = new AlertDialog.Builder(getContext())
                 .setMessage("Apakah anda ingin melakukan import untuk "+autoCompleteDN.getText()+"?")
                 .setPositiveButton("YA", new DialogInterface.OnClickListener() {

                     // do something when the button is clicked
                     public void onClick(DialogInterface arg0, int arg1) {
                             String temp ="";
                             try {

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

                                     Toast.makeText(getContext(), imagepath, Toast.LENGTH_SHORT).show();

                                     // down sizing image as it throws OutOfMemory Exception for larger images
                                     // options.inSampleSize = 10;

    //
                                     if (extension.equals(".xlsx") || extension.equals(".xls")) {
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
                                     }

                                     RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                                     MultipartBody.Part fileupload = MultipartBody.Part.createFormData("Image", file.getName(), requestBody);
                                     String filenames = "";

                                     filenames = "Imported-"+DNID+extension;
                                     RequestBody ext = RequestBody.create(MediaType.parse("text/plain"), extension);
                                     RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), filenames);
                                     RequestBody dnID = RequestBody.create(MediaType.parse("text/plain"), DNID);
                                     RequestBody username = RequestBody.create(MediaType.parse("text/plain"), usr.get(0).getUsername());

                                     ProgressDialog pd = new ProgressDialog(getContext());
                                     pd.setMessage("Uploading...");
                                     pd.setCancelable(false);
                                     pd.show();

                                     ApiInterface apiService2 = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                                     Call<String> call = apiService2.ImportDN(fileupload, filename, ext,dnID, username);
                                     //calling the api
                                     call.enqueue(new Callback<String>() {
                                         @Override
                                         public void onResponse(Call<String> call, Response<String> responses) {
                                             if(responses.code()==200){
                                                 Toast.makeText(getContext(), responses.body(), Toast.LENGTH_LONG).show();

                                             }
                                             else if(responses.code()==500){
                                                 Toast.makeText(getContext(), "Internal Server Error", Toast.LENGTH_LONG).show();

                                             }pd.dismiss();
                                         }

                                         @Override
                                         public void onFailure(Call<String> call, Throwable t) {
                                             Toast.makeText(getContext(), "File Imported", Toast.LENGTH_LONG).show();
                                             pd.dismiss();
                                         }
                                     });

                                 }
                                 else{
                                     Toast.makeText(getContext(), "Only 2MB file size allowed!", Toast.LENGTH_LONG).show();
                                 }
                             }
                             catch (Exception e){
                                 //Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                             }
                     }
                 })
                 .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                     // do something when the button is clicked
                     public void onClick(DialogInterface arg0, int arg1) {
                         arg0.dismiss();
                     }
                 })
                 .show();
         alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey2));
         alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

        }

    }

private void prepareGraphTable(DistNormalH dh){
    perhitunganPAModelList = new ArrayList<>();
        PerhitunganPAModel pa = new PerhitunganPAModel();
        pa.setBA(dh.getDNBAE());
        pa.setRange(dh.getRANGEDNE());
        pa.setJumlah(dh.getJUMLAHDNBAE());
        pa.setPersen(dh.getPERSENDNBAE());
        pa.setJumlah2(dh.getJUMLAHDNBAEADJ());
        pa.setPersen2(dh.getPERSENDNBAEADJ());
        pa.setN("E");
        pa.setStar(1);
        perhitunganPAModelList.add(pa);

        pa = new PerhitunganPAModel();
        pa.setBA(dh.getDNBAB());
    pa.setRange(dh.getRANGEDNB());
    pa.setJumlah(dh.getJUMLAHDNBAB());
        pa.setPersen(dh.getPERSENDNBAB());
        pa.setJumlah2(dh.getJUMLAHDNBABADJ());
        pa.setPersen2(dh.getPERSENDNBABADJ());
        pa.setN("B");
        pa.setStar(2);
        perhitunganPAModelList.add(pa);

        pa = new PerhitunganPAModel();
        pa.setBA(dh.getDNBAL());
    pa.setRange(dh.getRANGEDNL());
    pa.setJumlah(dh.getJUMLAHDNBAL());
        pa.setPersen(dh.getPERSENDNBAL());
        pa.setJumlah2(dh.getJUMLAHDNBALADJ());
        pa.setPersen2(dh.getPERSENDNBALADJ());
        pa.setN("L");
        pa.setStar(3);
        perhitunganPAModelList.add(pa);

        pa = new PerhitunganPAModel();
        pa.setBA(dh.getDNBAA());
    pa.setRange(dh.getRANGEDNA());
    pa.setJumlah(dh.getJUMLAHDNBAA());
        pa.setPersen(dh.getPERSENDNBAA());
        pa.setJumlah2(dh.getJUMLAHDNBAAADJ());
        pa.setPersen2(dh.getPERSENDNBAAADJ());
        pa.setN("A");
        pa.setStar(4);
        perhitunganPAModelList.add(pa);

        pa = new PerhitunganPAModel();
        pa.setBA("");
        pa.setJumlah("0");
        pa.setPersen("0");
        pa.setJumlah2("");
    pa.setRange("");
    pa.setPersen2("");
        pa.setN("K");
        pa.setStar(5);
        perhitunganPAModelList.add(pa);
    perhitunganPAAdapter = new PerhitunganPAAdapter(perhitunganPAModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity());
    recyclerView.setAdapter(perhitunganPAAdapter);

}

public void prepareEmployeeTable(String dnid, int page, int pageSize, String search) {

//    ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
//    //============================ HARDCODE =====================
//    apiService.getDistNormalD(dnid,page,pageSize,"Bearer "+usr.get(0).getToken())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .unsubscribeOn(Schedulers.io())
//                    .subscribe(new Observer<List<PerhitunganPAEMPModel>>() {
//
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(List<PerhitunganPAEMPModel> homeList) {
//
//                            //if(homeList.size()!=0){
////                                for(int i=0;i<homeList.size();i++) {
////                                perhitunganPAEMPModelList.add(homeList.get(i));
////                                }
//                                perhitunganPAEMPModelList = homeList;
//                            //}
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            //Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                            //============DN Table Employee=======================
//                            perhitunganPAEmpAdapter = new PerhitunganPAEmpAdapter(perhitunganPAEMPModelList, getContext(), ConnectivityReceiver.isConnected(), getActivity(), DistribusiNormalFragment.this);
//                            //rvEmployee.setAdapter(perhitunganPAEmpAdapter);
//                            perhitunganPAEmpAdapter.setStartPage(page); //set first page of data. default value is 1.
//                            perhitunganPAEmpAdapter.setPageSize(pageSize);
//                            perhitunganPAEmpAdapter.setDefaultRecyclerView(getActivity(), R.id.rvDNEmp); //set recyclerview with id and Activity. by default set LinearLayout for LayoutManager and setFixSize to true
//                            perhitunganPAEmpAdapter.setOnPaginationListener(new PaginatedAdapter.OnPaginationListener() {
//                                @Override
//                                public void onCurrentPage(int page) {
//                                    perhitunganPAEmpAdapter.submitItems(perhitunganPAEMPModelList);
//                                }
//
//                                @Override
//                                public void onNextPage(int pages) {
//                                    // call your method to get next page
//                                    Toast.makeText(getContext(),"NEXT",Toast.LENGTH_LONG).show();
//                                    prepareEmployeeTable(dnid,page+pageSize,pageSize);
//                                }
//
//                                @Override
//                                public void onFinish() {
//                                    // end of the list and all pages loaded
//                                }
//                            });
//
////                            prepareEmployeeTable(dnid,page+pageSize,pageSize);
//                            //==================================================
//                        }
//                    });

    //if connection exist
    if (ConnectivityReceiver.isConnected()) {
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        try {

            //untuk data yang lebih dari 100, masih ke double saat load after

            ItemViewModel itemViewModel = new ItemViewModel(DNID,search);
            itemViewModel = ViewModelProviders.of(getActivity(), new ViewModelProvider.Factory() {
                @NonNull
                @Override
                public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                    return (T) new ItemViewModel(DNID,search);
                }

            }).get(ItemViewModel.class);
            ItemAdapter adapter = new ItemAdapter(getContext(), perhitunganPAEMPModelList, getContext(), true,this, canAdjust);
//            Toast.makeText(getContext(),Username,Toast.LENGTH_LONG).show();

            itemViewModel.itemPagedList.observe(this, new Observer<PagedList<PerhitunganPAEMPModel>>() {
                @Override
                public void onChanged(PagedList<PerhitunganPAEMPModel> homes) {
                    adapter.submitList(homes);
                }
            });
            rvEmployee.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    }
}


    @Override
    public void reCallAPI() {
        prepareEmployeeTable(DNID, page, pageSize, "0");
}

    @Override
    public boolean getCanAdjust() {
        return canAdjust;
    }

    @Override
    public void setItem(String item) {
        autoCompleteDN.setText(item);
        dialog.dismiss();
            DNTitle=item;

            if(usr.get(0).getPrivilege().contains("A")){
                btnApprove.setVisibility(View.GONE);
                btnReject.setVisibility(View.GONE);
            }
            else{
                btnApprove.setVisibility(View.VISIBLE);
                btnReject.setVisibility(View.VISIBLE);
            }

            //btnNotifReject.setVisibility(View.VISIBLE);
            btnListAppr.setVisibility(View.VISIBLE);
            btnProcessDN.setVisibility(View.VISIBLE);
            int ctrReject=0;
            for (int j = 0; j < distNormalHList.size(); j++) {
                if( item.equals(distNormalHList.get(j).getDNGROUPNAME())){
                    imgBtnExport.setVisibility(View.VISIBLE);
                    imgBtnImport.setVisibility(View.VISIBLE);

                    getApprList(distNormalHList.get(j).getDNID());
//

                    prepareGraphTable(distNormalHList.get(j));

                    tvMean.setText("Mean :"+(Math.round(Double.parseDouble(distNormalHList.get(j).getDNMEAN().replace(",",".")) * 100.0) / 100.0));
                    tvStdDev.setText("Standar Deviasi :"+(Math.round(Double.parseDouble(distNormalHList.get(j).getDNSTDEV().replace(",",".")) * 100.0) / 100.0));

                    contoh2bar(distNormalHList.get(j));

                    DNID = distNormalHList.get(j).getDNID();
                    RejectDialog = distNormalHList.get(j).getDNALASANREJECT();
                    getActivity().getViewModelStore().clear();
                    prepareEmployeeTable(distNormalHList.get(j).getDNID(),page,pageSize,"0");

                    LinearLayoutManager finalMLayoutManager = mLayoutManager;
                    int finalJ = j;


                    btnProcessDN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog alertbox = new AlertDialog.Builder(getContext())
                                    .setMessage("Apakah anda yakin ingin memproses balancing "+DNTitle.toString()+"?")
                                    .setPositiveButton("YA", new DialogInterface.OnClickListener() {

                                        // do something when the button is clicked
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            ApiInterface apiService = ApiClient.getClientTest(getContext()).create(ApiInterface.class);
                                            //============================ HARDCODE =====================
                                            ProcessDNModel processDNModel = new ProcessDNModel();
                                            processDNModel.setDnID(distNormalHList.get(finalJ).getDNID());
                                            processDNModel.setGroupID(distNormalHList.get(finalJ).getDNID());
                                            processDNModel.setGroupIDName(distNormalHList.get(finalJ).getDNGROUPNAME());
                                            processDNModel.setPeriode(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                                            processDNModel.setUpdUser(usr.get(0).getUsername());
                                            Call<Void> call = apiService.processDN(processDNModel, "Bearer " + usr.get(0).getToken());
                                            call.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    int statusCode = response.code();
                                                    Toast.makeText(getContext(), "Process Complete", Toast.LENGTH_LONG).show();

                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                        // do something when the button is clicked
                                        public void onClick(DialogInterface arg0, int arg1) {

                                        }
                                    })
                                    .show();
                            alertbox.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getContext().getColor(R.color.light_grey2));
                            alertbox.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getContext().getColor(R.color.colorPrimary));

                        }
                    });

                }
            }

    }
    private void sendEmail(EmailSentModel emailSentModel, Context ctx){
        List<EmailSentModel> emailSentModelList = new ArrayList<>();
        emailSentModelList.add(emailSentModel);

        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<Void> call2 = apiService.sendEmailtoPIC(emailSentModelList,"Bearer " + usr.get(0).getToken());
        call2.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int statusCode = response.code();
                Toast.makeText(ctx,"Email berhasil dikirim",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ctx,"Email berhasil dikirim",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class DayAxisValueFormatter extends ValueFormatter {
    private final BarLineChartBase<?> chart;
    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }
    @Override
    public String getFormattedValue(float value) {
//        float a = value;
//        String aa="";
//        if(value>0){
//            aa = Float.toString(value);
//        }
        return Float.toString(value+1);
    }
}