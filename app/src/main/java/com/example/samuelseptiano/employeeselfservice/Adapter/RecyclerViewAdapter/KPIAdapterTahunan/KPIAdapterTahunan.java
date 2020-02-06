package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.PAPeriodeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.InboxChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KPIAdapterTahunan extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ListImprovementAreaAdapter.EventListener {

    private static final String TAG = "KPIAdapterTahunan";
    private LayoutInflater inflater;
    KPIHeader kpiHeader;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    ListImprovementAreaAdapter listImprovementAreaAdapter;
    boolean diubah = false;
    KPIImageUploadAdapterTahunan adapter;
    public Dialog dialog;
    private Context context;

    boolean isFromIDPLayout=false;

    List<DevPlanHeader> devPlanHeaderList = new ArrayList<>();

    List<InboxChatModel> lInbox = new ArrayList<>();

    protected String friendName = "";
    protected String friendNIK = "";
    protected String friendJobTitle = "";
    protected String friendDept = "";
    protected String friendBranchName = "";

    protected String foto="";
    protected String kualitatif = "";
    protected String kuantitatif = "";
    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
    ArrayList<UserRealmModel> usr;
    List<IDPModel> idpModelList;

    public static int currentPosition=1;

    List<ImageUploadModel> aaa = new ArrayList<ImageUploadModel>();

    Activity activity;

    KPIUserAnswerList kpiUserAnswerList;
    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    String test="",test2="";

    public static boolean isVisible=true;

    String id,type;
    String isEditable;
    String Jenis = "";
    String SEMESTER;

    private List<PAPeriodeModel> paPeriodeModelList;
    private List<KPIQuestions> questions;

    //semester 2


    private LinearLayout linearLayoutContainer;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;


    EventListener listener;

    @Override
    public boolean getFromIDPLayout() {
        return isFromIDPLayout;
    }

    @Override
    public void setFromIDPLayout(boolean isFromIDPLayout) {
        this.isFromIDPLayout=isFromIDPLayout;
    }

    public interface EventListener {
        void onEvent(int position, List<KPIQuestions> questions, int action);
        List <ImageUploadModelPA> onResult();
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void redirect(int tabPos);
        String isEditables();
        String getSemster();
        void setQuestion(KPIHeader a, int semester);
        void setEditables(String edit, KPIHeaderPJ kh);
        void setSemester(String semester, KPIHeaderPJ kh);
        KPIHeader getQuestionSmt1();
        KPIHeader getQuestionSmt2();
        void getQuestionSmt();
        void setUbah(boolean diUbah);
        boolean getUbah();
        void setFromIDPLayout(boolean isFromIDPLayout);
    }

    public KPIAdapterTahunan(Activity activity, Context context, KPIHeader kpiHeader, String id, String type, EventListener listener, String Jenis,List<InboxChatModel>lInbox,List<PAPeriodeModel>paPeriodeModelList) {
        this.inflater = LayoutInflater.from(context);
        this.questions = kpiHeader.getKpiQuestionsList();
        //this.questions2 = kpiHeader.getKpiQuestionsList();
        this.context = context;
        this.activity=activity;
        this.kpiHeader=kpiHeader;
        this.id=id;
        this.type=type;
        this.listener = listener;
        this.Jenis = Jenis;
        this.lInbox=lInbox;
        this.paPeriodeModelList=paPeriodeModelList;
        usr = userRealmHelper.findAllArticle();
        isEditable = listener.isEditables();
        SEMESTER = listener.getSemster();
        diubah = listener.getUbah();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_list_tahunan, parent, false);
            return new KPIAdapterTahunan.ItemViewHolder(itemView);
        }else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_header, parent, false);
            return new KPIAdapterTahunan.HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_footer, parent, false);
            return new KPIAdapterTahunan.FooterViewHolder(itemView);
        } else return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof KPIAdapterTahunan.HeaderViewHolder) {
                KPIAdapterTahunan.HeaderViewHolder headerHolder = (KPIAdapterTahunan.HeaderViewHolder) holder;

                if(id.length()== 0 && !type.equals("Approve")){

                }

                ((HeaderViewHolder) holder).imgBtnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isEditable.equals("N")) {
                            isEditable = "Y";
                            Toast.makeText(context, "Edit: ON", Toast.LENGTH_SHORT).show();

                        }
                        else if(isEditable.equals("Y")){
                            isEditable = "N";
                            Toast.makeText(context, "Edit: OFF", Toast.LENGTH_SHORT).show();

                        }
                        notifyDataSetChanged();
                    }
                });

                ((HeaderViewHolder) holder).spinnerSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Object item = parent.getItemAtPosition(position);
                        if (item.toString().equals("SMT 1")) {
                           SEMESTER="1";
                           notifyDataSetChanged();
                        }
                        else{
                            SEMESTER="2";
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
            else if (holder instanceof KPIAdapterTahunan.FooterViewHolder) {
                final KPIAdapterTahunan.FooterViewHolder footerHolder = (KPIAdapterTahunan.FooterViewHolder) holder;

                final int requestCode = 20;

                if(id.length()>0 && type.equals("Approve")){
                    ((FooterViewHolder) holder).footerButton.setVisibility(View.GONE);
                }
                else{
                    ((FooterViewHolder) holder).btnApprove.setVisibility(View.GONE);
                }


                if(listener.isEditables().equals("N") && type.equals("Approve")){
                    footerHolder.rvImprovementArea.setEnabled(false);
                    footerHolder.edtRencanaTindaklanjut.setEnabled(false);
                    footerHolder.edtKelebihan.setEnabled(false);
                    footerHolder.edtkekurangan.setEnabled(false);
                    footerHolder.addBtn.setEnabled(false);
                    footerHolder.delBtn.setEnabled(false);
                    footerHolder.spinnerDevPlan.setEnabled(false);
                }
                else if(listener.isEditables().equals("Y") && type.equals("Approve")){
                    footerHolder.edtRencanaTindaklanjut.setEnabled(true);
                    footerHolder.rvImprovementArea.setEnabled(true);
                    footerHolder.edtKelebihan.setEnabled(true);
                    footerHolder.edtkekurangan.setEnabled(true);
                    footerHolder.addBtn.setEnabled(true);
                    footerHolder.delBtn.setEnabled(true);
                    footerHolder.spinnerDevPlan.setEnabled(true);
                }
                else{
                    footerHolder.rvImprovementArea.setEnabled(true);
                    footerHolder.edtRencanaTindaklanjut.setEnabled(true);
                    footerHolder.edtKelebihan.setEnabled(true);
                    footerHolder.edtkekurangan.setEnabled(true);
                    footerHolder.addBtn.setEnabled(true);
                    footerHolder.delBtn.setEnabled(true);
                    footerHolder.spinnerDevPlan.setEnabled(true);
                }

                footerHolder.spinnerDevPlan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        footerHolder.spinnerDevPlan.setFocusable(true);
                    }
                });
                footerHolder.spinnerDevPlan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                    Devplan = spinnerDevPlan.getItems().get(spinnerDevPlan.getSelectedIndex()).toString();
                    }
                });

                UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                String NIK = usr.get(0).getEmpNIK();

                footerHolder.edtKelebihan.setText(kpiHeader.getStrength());

                footerHolder.btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(type.equals("Approve")){

                            if(kpiHeader.getSemester().equals("1")) {
//                                listener.redirect(1);
//                                footerHolder.btnApprove.setText("Next");
                            }
                            else {
//                                listener.redirect(2);
//                                listener.getQuestionSmt();

//                                footerHolder.btnApprove.setText("Submit KPI");

                            }
                        }
                    }
                });

                footerHolder.footerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!type.equals("Approve")){
                            if(kpiHeader.getSemester().equals("1")) {
                                //listener.redirect(1);
                                //footerHolder.footerButton.setText("Next");

//                                listener.setQuestion(questions,1);

                            }
                            else{
                                //listener.redirect(2);
                                    //listener.getQuestionSmt();
                                //submitKPI(NIK,footerHolder);
                            }


                            // code block
                            //RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                            //routingHomeDetailInterface.routingKPI("Fill PA",context,"","",2);

                        }
                    }
                });
            }
            else if (holder instanceof KPIAdapterTahunan.ItemViewHolder) {
                holder.setIsRecyclable(false);

                    ((ItemViewHolder) holder).tv_notif_message.setText(questions.get(position - 1).getMessageNotification());
                    ((ItemViewHolder) holder).tv_notif_message.setVisibility(View.GONE);

                    if(kpiHeader.getKpiQuestionsList().get(position-1).isNotif() && kpiHeader.getKpiQuestionsList().get(position-1).getKPIcategory().equals("KUANTITATIF")){
                        ((ItemViewHolder) holder).tvNotif.setText("*Target/Actual/Evidence Masih kosong");
                        ((ItemViewHolder) holder).tvNotif.setVisibility(View.VISIBLE);

                    }

                    else if(kpiHeader.getKpiQuestionsList().get(position-1).isNotif() && kpiHeader.getKpiQuestionsList().get(position-1).getKPIcategory().equals("KUALITATIF")){
                        ((ItemViewHolder) holder).tvNotif.setText("*Evidence Masih kosong");
                        ((ItemViewHolder) holder).tvNotif.setVisibility(View.VISIBLE);
                    }
                    else{
                        ((ItemViewHolder) holder).tvNotif.setVisibility(View.GONE);
                    }

//                if(questions.get(position-1).getKPIcategory().equals("KUALITATIF")){
//                    ((ItemViewHolder) holder).tv_notif_message.setText(Integer.toString(count));
//                }
//                else if(questions.get(position-1).getKPIcategory().equals("KUANTITATIF")){
//                    ((ItemViewHolder) holder).tv_notif_message.setText(Integer.toString(count));
//                }


                ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.GONE);
                ((ItemViewHolder) holder).imgExpand.setImageResource(R.drawable.expand_down);

                if(questions.get(position-1).getKPIcategory().equals("KUALITATIF")){
                    ((ItemViewHolder) holder).btnMore.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).btnChat .setVisibility(View.VISIBLE);
                }
                else if(questions.get(position-1).getKPIcategory().equals("KUANTITATIF")){
                    ((ItemViewHolder) holder).btnMore.setVisibility(View.VISIBLE);
                }

//                if(paPeriodeModelList.get(0).getSemester().equals("1") && questions.get(position-1).getSemester().equals("2")){
//                    ((ItemViewHolder) holder).rating.setEnabled(false);
//                    ((ItemViewHolder) holder).btnUpload.setEnabled(false);
//                    ((ItemViewHolder) holder).imgBtnHint.setEnabled(false);
//                    ((ItemViewHolder) holder).imgBtnUpload.setVisibility(View.GONE);
//                    ((ItemViewHolder) holder).btnChat.setVisibility(View.GONE);
//
//                }

                if(questions.get(position-1).getSemester().equals(listener.getSemster())){
                    ((ItemViewHolder) holder).lnKPIItem.setVisibility(View.VISIBLE);
                }
                else{
                    ((ItemViewHolder) holder).lnKPIItem.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).lnKPICategory.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).lnKPICategory.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                    ((ItemViewHolder) holder).hrLine2.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).hrLine.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).tvCategory.setVisibility(View.GONE);
                }
                if(type.equals("Approve") && listener.isEditables().equals("N")){
                    ((ItemViewHolder) holder).rating.setIsIndicator(true);
                    ((ItemViewHolder) holder).edtCatatan.setEnabled(false);
                    ((ItemViewHolder) holder).edtPendukung.setEnabled(false);
                    ((ItemViewHolder) holder).edtPenghambat.setEnabled(false);
                    ((ItemViewHolder) holder).btnUpload.setEnabled(false);
                    ((ItemViewHolder) holder).imgBtnUpload.setEnabled(false);
//                btnUpload.setVisibility(View.GONE);
                }
                else if(type.equals("Approve") && listener.isEditables().equals("Y")){
                    ((ItemViewHolder) holder).rating.setIsIndicator(false);
                    ((ItemViewHolder) holder).edtCatatan.setEnabled(true);
                    ((ItemViewHolder) holder).edtPendukung.setEnabled(true);
                    ((ItemViewHolder) holder).edtPenghambat.setEnabled(true);
                    ((ItemViewHolder) holder).btnUpload.setEnabled(true);
                    ((ItemViewHolder) holder).imgBtnUpload.setEnabled(true);

//                btnUpload.setVisibility(View.GONE);
                }
                else if(!type.equals("Approve") && listener.isEditables().equals("N")){
                    ((ItemViewHolder) holder).rating.setIsIndicator(true);
                    ((ItemViewHolder) holder).edtCatatan.setEnabled(false);
                    ((ItemViewHolder) holder).edtPendukung.setEnabled(false);
                    ((ItemViewHolder) holder).edtPenghambat.setEnabled(false);
                    ((ItemViewHolder) holder).btnUpload.setEnabled(false);
                    ((ItemViewHolder) holder).imgBtnUpload.setEnabled(false);
//                btnUpload.setVisibility(View.GONE);
                }
                else if(!type.equals("Approve") && listener.isEditables().equals("Y")){
                    ((ItemViewHolder) holder).rating.setIsIndicator(false);
                    ((ItemViewHolder) holder).edtCatatan.setEnabled(false);
                    ((ItemViewHolder) holder).edtPendukung.setEnabled(false);
                    ((ItemViewHolder) holder).edtPenghambat.setEnabled(false);
                    ((ItemViewHolder) holder).btnUpload.setEnabled(true);
                    ((ItemViewHolder) holder).imgBtnUpload.setEnabled(true);
//                btnUpload.setVisibility(View.GONE);
                }


                ((ItemViewHolder) holder).imgBtnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            if (questions.get(position - 1).getPhotoCapture().size() > 5) {
                            } else {
                                showUploadDialog(questions.get(position - 1).getPhotoCapture(), position - 1, questions.get(position - 1).getKpiId(), questions.get(position - 1).getPaId(), questions.get(position - 1).getKPIcategory());
                            }
                        }
                        catch(Exception e){
                            showUploadDialog(questions.get(position - 1).getPhotoCapture(), position - 1, questions.get(position - 1).getKpiId(), questions.get(position - 1).getPaId(), questions.get(position - 1).getKPIcategory());
                        }
                    }
                });


                ((ItemViewHolder) holder).btnChat.setOnClickListener(v -> {
                    //room chat sudah ada
                    try {
                        ((ItemViewHolder) holder).btnChat.setEnabled(false);
                        List <String> KPIItems = new ArrayList<>();

                        KPIItems.add("Overall Performance");
                        for(int i=0;i< questions.size();i++){
                            KPIItems.add(questions.get(i).getKPIDesc());
                        }


                        String nik = usr.get(0).getEmpNIK();
                        String nama = usr.get(0).getEmpName().replace(".", "");
                        String semester = questions.get(position - 1).getSemester();
                        String tahun =  Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

                        if (questions.get(position - 1).getKPIcategory().equals("KUALITATIF")){
                            kualitatif = questions.get(position - 1).getKPIDesc();
                            kuantitatif="";
                        }
                        else if (questions.get(position - 1).getKPIcategory().equals("KUANTITATIF")){
                            kuantitatif = questions.get(position - 1).getKPIDesc();
                            kualitatif="";
                        }

                        //user berperan sebagai atasan langsung
                        if(usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanLangsung())){
//                                friendName = kpiHeader.getAtasanTakLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanTakLangsung();
                            friendName = kpiHeader.getEmpName().replace(".", "");
                            friendNIK = kpiHeader.getNIK();
                            friendBranchName = kpiHeader.getBranchName();
                            friendDept = kpiHeader.getDept();
                            friendJobTitle = kpiHeader.getJobTitleName();
                            foto = kpiHeader.getFotoBawahan();


                        }
                        //user berperan sebagai atasan tak langsung
                        if(usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanTakLangsung())){
//                                friendName = kpiHeader.getAtasanLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanLangsung();
                            friendName = kpiHeader.getEmpName().replace(".", "");
                            friendNIK = kpiHeader.getNIK();
                            friendBranchName = kpiHeader.getBranchName();
                            friendDept = kpiHeader.getDept();
                            friendJobTitle = kpiHeader.getJobTitleName();
                            foto = kpiHeader.getFotoBawahan();

                        }
                        // user berperan sebagai karyawan
                        if(!usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanTakLangsung()) && !usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanLangsung())){
//                      friendName = kpiHeader.getAtasanLangsung();
//                      friendNIK = kpiHeader.getNIKAtasanLangsung();
                            friendName = usr.get(0).getNamaAtasanLangsung().replace(".", "");
                            friendNIK =  usr.get(0).getNikAtasanLangsung();
                            friendBranchName = usr.get(0).getBranchName();
                            friendDept = usr.get(0).getDept();
                            friendJobTitle = usr.get(0).getJobTitleName();
                            foto = kpiHeader.getFotoAtasanLangsung();

                        }

//                      FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//                      Toast.makeText(context, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                                if (dataSnapshot.hasChild(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR")) {
                                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                    routingHomeDetailInterface.routingChat(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR",friendName, friendNIK,semester,tahun,kualitatif,kuantitatif,context, KPIItems,friendBranchName,friendDept,friendJobTitle,foto);

                                }
                                else if(dataSnapshot.hasChild(friendNIK+"-"+nik+"-"+friendName+"-"+nama+"-YEAR")){
                                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                    routingHomeDetailInterface.routingChat(friendNIK +"-"+nik+"-"+friendName+"-"+nama+"-YEAR",friendName,friendNIK,semester,tahun,kualitatif,kuantitatif,context,KPIItems,friendBranchName,friendDept,friendJobTitle,foto);
                                }
                                else{
                                    Map<String,Object> map = new HashMap<String,Object>();
                                    map.put(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR",""); //diganti room name random uniqueID
                                    root.updateChildren(map);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    catch (Exception e){
                        //jika roomChat belum ada, buat room

                        String nik = usr.get(0).getEmpNIK();
                        String nikLawan = kpiHeader.getNIKAtasanLangsung();

                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put(nik+"-"+ friendNIK+"-"+usr.get(0).getEmpName()+"-"+friendName+"-YEAR",""); //diganti room name random uniqueID
                        root.updateChildren(map);

                    }


                });



                //if the position is equals to the item position which is to be expanded
                if(questions.get(position-1).getKPIcategory().equals("KUANTITATIF")) {
                    if (currentPosition == position) {
                        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.anim_drop_down_recycler);
                        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.anim_drop_up_recycler);

                        //creating an animation

                    if(isVisible == false){
                        //toggling visibility
                        ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.VISIBLE);

                        ((ItemViewHolder) holder).imgExpand.setImageResource(R.drawable.expand_up);
                        ((ItemViewHolder) holder).btnMore.setText("Less...");

                        //adding sliding effect
                        ((ItemViewHolder) holder).lnKuantitatif.startAnimation(slideDown);
                        //isVisible = true;
                    }
                    else if(isVisible == true){
//
//                            ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.GONE);
//                        ((ItemViewHolder) holder).lnKuantitatif.startAnimation(slideUp);
//                            //isVisible=false;
                    }
                    }

                    ((ItemViewHolder) holder).btnMore.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).btnMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //getting the position of the item to expand_down it
                            currentPosition = position;
                            //reloding the list

                            if(isVisible){
                                isVisible=false;
                            }
                            else{
                                isVisible=true;
                            }

                            notifyDataSetChanged();
                        }
                    });
                    ((ItemViewHolder) holder).lnKPIName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //getting the position of the item to expand_down it

                                currentPosition = position;
                            //reloding the list

                            notifyDataSetChanged();
                        }
                    });
                }

                final KPIAdapterTahunan.ItemViewHolder itemViewHolder = (KPIAdapterTahunan.ItemViewHolder) holder;
                KPIQuestions current = questions.get(position-1);
                itemViewHolder.setQuestion(current.getKPIDesc());
                itemViewHolder.setNumber(Long.parseLong(current.getIdKPI()));
                itemViewHolder.setOptions(current, position-1);
                itemViewHolder.setBobot("Bobot: "+current.getBobot());
                itemViewHolder.setCategory(current.getKPIcategory(),current.getPerspective(),position-1);
                itemViewHolder.setUpload(current.getKPIcategory(),current, position-1);
                itemViewHolder.setAnswer(current,position-1);
            }
        }
        catch (Exception e){

        }
    }

    @Override
    public int getItemViewType(int position) {
        int returned = 0;
        if (position == 0) {
            returned = TYPE_HEADER;
        }
        else if (position == questions.size()+1) {
            returned = TYPE_FOOTER;
        }
        else {
            returned = TYPE_ITEM;
        }
        return returned;
    }


    @Override
    public int getItemCount() {
        if (questions == null) {
            return 0;
        } else {
            return (questions.size()+2);
        }
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        ImageButton imgBtnEdit,imgBtnSave, imgBtnApprove;
        TextView tvPAType;
        Spinner spinnerSemester;

        public HeaderViewHolder(View view) {
            super(view);
//            tahun = (TextView) view.findViewById(R.id.tvTahun);
//            semester = (TextView) view.findViewById(R.id.tvSemester);



            imgBtnEdit = view.findViewById(R.id.imgBtnEdit);
            imgBtnSave = view.findViewById(R.id.imgBtnSave);
            imgBtnApprove = view.findViewById(R.id.imgBtnApprove);
            tvPAType = view.findViewById(R.id.tv_PAType);
            spinnerSemester = view.findViewById(R.id.spinnerPeriode);

            List<String> tahuns = new ArrayList<String>();
            tahuns.add("SMT 1");
            tahuns.add("SMT 2");
            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, tahuns);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            spinnerSemester.setAdapter(dataAdapter);

            if(!type.equals("Approve")){
                imgBtnEdit.setVisibility(View.GONE);
            }

        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        Button footerButton, btnApprove;
        EditText edtKelebihan, edtkekurangan, edtRencanaTindaklanjut;
        TextView footertext, tvMaxtext;
        LinearLayout lnKualitatif, lnDevPlan, lnSummary;
        ViewPager viewPager;
        MaterialSpinner spinnerDevPlan;
        Button addBtn, delBtn;
        RecyclerView rvImprovementArea;

        public FooterViewHolder(View view) {
            super(view);

            footerButton = (Button) view.findViewById(R.id.btnSubmitKPI);
            btnApprove = (Button) view.findViewById(R.id.btnApproveKPI);
            footertext = (TextView) view.findViewById(R.id.tvFooterTitle);
            edtkekurangan = view.findViewById(R.id.edtKekurangan);
            edtKelebihan = view.findViewById(R.id.edtKelebihan);
            tvMaxtext = view.findViewById(R.id.tvMaxText);
            edtRencanaTindaklanjut = view.findViewById(R.id.edtRencanaTindaklanjut);
            spinnerDevPlan = view.findViewById(R.id.spinnerImprovement);
            spinnerDevPlan.setBackgroundResource(R.drawable.shapedropdown);
            spinnerDevPlan.setPadding(25, 10, 25, 10);
            addBtn = view.findViewById(R.id.btnAdd);
            lnDevPlan = view.findViewById(R.id.lnAddDevPlan);
            delBtn = view.findViewById(R.id.btnDelete);
            lnKualitatif = view.findViewById(R.id.lnKualitatif);
            lnSummary = view.findViewById(R.id.lnSummary);
            rvImprovementArea = view.findViewById(R.id.rv_improvement_area);

            if(!type.equals("Approve")){
                lnSummary.setVisibility(View.GONE);
            }

            List<String> tahuns = new ArrayList<String>();
            tahuns.add("- Pilih  -");
            for (int x=0;x<questions.size();x++){
                if(questions.get(x).getKPIcategory().equals("KUALITATIF") && questions.get(x).getSemester().equals("1")) {
                    tahuns.add(questions.get(x).getKPIDesc());
                }
            }
            // Creating adapter for spinner
            // attaching data adapter to spinner
            spinnerDevPlan.setItems(tahuns);

            edtKelebihan.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if(edtKelebihan.getText().toString().length()>0) {
                        kpiHeader.setStrength(edtKelebihan.getText().toString().replaceAll("'", ""));
                            listener.setQuestion(kpiHeader,1);
                            listener.setUbah(true);
                    }
                    else{
                        kpiHeader.setStrength("");
                        listener.setQuestion(kpiHeader,1);
                        listener.setUbah(true);
                    }
                    tvMaxtext.setText(s.length()+"/1000");
                }
            });

            try{
            devPlanHeaderList = kpiHeader.getDevPlanHeaderList();
            Collections.sort(devPlanHeaderList);
            //Toast.makeText(context,devPlanHeaderList.size()+"",Toast.LENGTH_LONG).show();

            listImprovementAreaAdapter = new ListImprovementAreaAdapter(devPlanHeaderList,kpiHeader, context, listener.isEditables(),type, KPIAdapterTahunan.this);
            LinearLayoutManager llm = new LinearLayoutManager(context);

            //Setting the adapter
            rvImprovementArea.setAdapter(listImprovementAreaAdapter);
            rvImprovementArea.setLayoutManager(llm);


            devPlanHeaderList = kpiHeader.getDevPlanHeaderList();
            }
            catch (Exception e){

            }
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setUbah(true);
                    int ctr=0;
                    if(!spinnerDevPlan.getText().toString().equals("- Pilih  -")){
                        DevPlanHeader devPlanHeader = new DevPlanHeader();
                        devPlanHeader.setCOMPNAME(spinnerDevPlan.getText().toString());
                        devPlanHeader.setChecked(false);
                        devPlanHeader.setDevPlanDetail(new ArrayList<DevPlanDetail>());
                        devPlanHeader.setPAID(kpiHeader.getKpiQuestionsList().get(0).getPaId());
                        for(int i=0;i<kpiHeader.getKpiQuestionsList().size();i++){
                            if(kpiHeader.getKpiQuestionsList().get(i).getKPIDesc().equals(spinnerDevPlan.getText())){
                                devPlanHeader.setCOMPID(kpiHeader.getKpiQuestionsList().get(i).getKpiId());
                                devPlanHeader.setDEVID(kpiHeader.getKpiQuestionsList().get(i).getKpiId());
                            }
                        }
                        for(int i=0;i<kpiHeader.getDevPlanHeaderList().size();i++) {
                            if (spinnerDevPlan.getText().toString().equals(kpiHeader.getDevPlanHeaderList().get(i).getCOMPNAME())) {
                                ctr++;
                                Toast.makeText(context,"Anda Sudah memilih improvement area Ini",Toast.LENGTH_SHORT).show();
                            }
                        }
                        if(ctr==0) {
                            devPlanHeaderList.add(devPlanHeader);
                            listImprovementAreaAdapter.notifyDataSetChanged();
                        }

                    }

                }
            });

            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setUbah(true);

                    for(int i=0;i<devPlanHeaderList.size();i++){
                        if(devPlanHeaderList.get(i).getChecked()){
//                            Toast.makeText(context,i+"",Toast.LENGTH_LONG).show();
                            devPlanHeaderList.remove(i);
                        }
                    }
                    listImprovementAreaAdapter.notifyDataSetChanged();
                }
            });


        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutContainer,linearLayoutContainer2, lnKPIItem,lnKPICategory;
        private TextView textViewQuestion, textViewNumber, tvIndicator,tvBobot, tvCategory, tvPerspective, tv_notif_message;
        private RatingBar rating;
        private ImageButton imgBtnHint, imgExpand, imgBtnUpload;
        private EditText  edtPendukung,edtPenghambat,edtCatatan;
        private View hrLine, hrLine2;
        Button btnUpload, btnMore;
        ImageButton btnChat;
        LinearLayout lnKuantitatif,lnKPIName;
        RecyclerView recyclerViewImg;
        TextView tvNotif;

        public ItemViewHolder(View itemView) {
            super(itemView);
            btnUpload = itemView.findViewById(R.id.btnUploadImage);
            btnMore = itemView.findViewById(R.id.btnMore);
            lnKuantitatif = itemView.findViewById(R.id.lnKuantitatif);
           linearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linear_layout_container);
            lnKPIName = (LinearLayout) itemView.findViewById(R.id.lnKPIName);
            linearLayoutContainer2 = (LinearLayout) itemView.findViewById(R.id.linear_layout_container2);
            textViewQuestion = (TextView) itemView.findViewById(R.id.tvKPIDesc);
            textViewNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            tvNotif = itemView.findViewById(R.id.tvNotif);
//            LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
//            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.placeholder_grey), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(1).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(3).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(4).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
//            stars.getDrawable(5).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);


            tvIndicator = (TextView) itemView.findViewById(R.id.tvRatingDesc);
            tvBobot = (TextView) itemView.findViewById(R.id.tvBobot);
            edtPendukung = itemView.findViewById(R.id.edtPendukung);
            edtPenghambat = itemView.findViewById(R.id.edtPenghambat);
            imgBtnHint = (ImageButton) itemView.findViewById(R.id.imgBtnHint);
            edtCatatan = itemView.findViewById(R.id.edtCatatan);
            btnChat = itemView.findViewById(R.id.btnChat);
            tvCategory = (TextView) itemView.findViewById(R.id.tvKPICategory);
            hrLine2 = itemView.findViewById(R.id.HrLine2);
            hrLine = itemView.findViewById(R.id.HrLine);
            tvPerspective = itemView.findViewById(R.id.tvPerspective);
            recyclerViewImg = itemView.findViewById(R.id.recycler_view_image_upload);
            imgExpand = itemView.findViewById(R.id.imgExpand);
            imgBtnUpload = itemView.findViewById(R.id.imgBtnUpload);
            lnKPIItem =  itemView.findViewById(R.id.lnPAItem);
            lnKPICategory =  itemView.findViewById(R.id.lnKPICategory);
            tv_notif_message =  itemView.findViewById(R.id.tv_message_count);

            rating.setNumStars(5);
            //rating.setRating(numbRadio);
            rating.setStepSize(1);

        }

        public void setUpload(String category, KPIQuestions question, int position){

           // if(isEditable.equals("N")){
                //recyclerViewImg.setEnabled(false);
            //}

            if(question.getKPIcategory().equals("Kualitatif")){
                imgExpand.setVisibility(View.GONE );
            }

            KPIQuestions que = questions.get(position);
            List<String> tempImg = new ArrayList<>();




            if(category.equals("Kualitatif")){
                btnUpload.setVisibility(View.GONE);
                edtCatatan.setVisibility(View.GONE);
                edtPendukung.setVisibility(View.GONE);
                edtPenghambat.setVisibility(View.GONE);
                }

            if(question.getPhotoCapture()!=null) {
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 6);
                recyclerViewImg.setLayoutManager(mLayoutManager);
                KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.getPhotoCapture(), context, activity, isEditable,question.getPaId(), question.getKpiId(), question.getKpiId(), question.getSemester(), question.getKPIcategory());

                recyclerViewImg.setAdapter(adapter);
            }

            btnUpload.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onEvent(position, questions,3);

                }
            });

        }

        public void setQuestion(String question) {
            textViewQuestion.setText(question);
        }

        public void setBobot(String bobot) {
            tvBobot.setText(bobot);
        }

        public void setAnswer(KPIQuestions question, int position) {

            //======== problem ===========
            if(question.getAnsweredCatatan()) {
                edtCatatan.setText(question.getCatatanLain());
                //Toast.makeText(context,"hit here",Toast.LENGTH_LONG).show();
            } else {
                edtCatatan.setText("");
                //Toast.makeText(context,"hit over here",Toast.LENGTH_LONG).show();

            }
            //=====================

            edtCatatan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {


                        KPIQuestions que = questions.get(position);
                        if(edtCatatan.getText().toString().length()>0) {
                            que.setAnsweredCatatan(true);
                            que.setCatatanLain(edtCatatan.getText().toString().replaceAll("'", ""));
                            if(kpiHeader.getSemester().equals("1")){
                                listener.setQuestion(kpiHeader,1);
                            }
                            else{
                                listener.setQuestion(kpiHeader,2);
                            }
                        }
                    }
                }
            });

            if(question.getAnsweredPenghambat()) {
                edtPenghambat.setText(question.getPenghambat());

            } else {
                edtPenghambat.setText("");
            }

            edtPenghambat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        KPIQuestions que = questions.get(position);
                        if(edtPenghambat.getText().toString().length()>0) {
                            que.setAnsweredPenghambat(true);
                            que.setPenghambat(edtPenghambat.getText().toString());
                            if(kpiHeader.getSemester().equals("1")){
                                listener.setQuestion(kpiHeader,1);
                            }
                            else{
                                listener.setQuestion(kpiHeader,2);
                            }
                        }
                    }
                }
            });

            if(question.getAnsweredPendukung()) {
                edtPendukung.setText(question.getPendukung());

            } else {
                edtPendukung.setText("");
            }

            edtPendukung.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        KPIQuestions que = questions.get(position);

                        if(edtPendukung.getText().toString().length()>0) {
                            que.setAnsweredPendukung(true);
                            que.setPendukung(edtPendukung.getText().toString());
                            if(kpiHeader.getSemester().equals("1")){
                                listener.setQuestion(kpiHeader,1);
                            }
                            else{
                                listener.setQuestion(kpiHeader,2);
                            }
                        }
                    }
                }
            });
        }

        public void setCategory(String category, String perspective, int position) {
            if(position != 0) {
                if (questions.get(position - 1).getKPIcategory().equals(category)) {
                    tvCategory.setVisibility(View.GONE);
                    hrLine.setVisibility(View.GONE);
                    hrLine2.setVisibility(View.GONE);
                } else {
                    tvCategory.setText("Faktor "+category.substring(0, 1).toUpperCase() + category.substring(1,category.toCharArray().length).toLowerCase());
                }

                if(questions.get(position - 1).getPerspective().equals(perspective)){
                    tvPerspective.setVisibility(View.GONE);
                }
                else {
                    tvPerspective.setText(perspective);
                }
            }
            else{
                tvCategory.setText("Faktor "+category.substring(0, 1).toUpperCase() + category.substring(1,category.toCharArray().length).toLowerCase());
                tvPerspective.setText(perspective);
            }

//            Toast.makeText(context,Integer.toString(position)+" "+questions.get(position-1).getKPIcategory()+" "+category,Toast.LENGTH_SHORT).show();
        }

        public void setNumber(long id) {
            textViewNumber.setText(Long.toString(id)+". ");
        }

        public void setOptions(KPIQuestions question, int position) {
//

            Log.e(TAG, position + " :setOptions: " + question.toString());



                if (question.isAnswered()) {
                    Drawable progress = rating.getProgressDrawable();

                    rating.setRating(question.getCheckedId());
                    switch (question.getCheckedId()) {
                        case 1:
                            LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
                            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                            tvIndicator.setText("Unacceptable");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.red));
                            break;
                        case 2:
                            tvIndicator.setText("Below Expectation");
                            stars = (LayerDrawable) rating.getProgressDrawable();
                            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.yellow));
                            break;
                        case 3:
                            tvIndicator.setText("Meet Expectation");
                            stars = (LayerDrawable) rating.getProgressDrawable();
                            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.green));
                            break;
                        case 4:
                            tvIndicator.setText("Exceed Expectation");
                            stars = (LayerDrawable) rating.getProgressDrawable();
                            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.darkGreen));
                            break;
                        case 5:
                            tvIndicator.setText("Excelllent");
                            stars = (LayerDrawable) rating.getProgressDrawable();
                            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.blue));
                            break;
                        default:
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.white));
                            stars = (LayerDrawable) rating.getProgressDrawable();
                            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);
                            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);
                    }

                } else {
                    rating.setRating(0);
                    tvIndicator.setText("-");
                }


            imgBtnHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHintKPI(questions.get(position).getHint(), questions.get(position).getKPIDesc());
                }
            });

            if(kpiHeader.getSemester().equals("1")){
                listener.setQuestion(kpiHeader,1);
            }
            else{
                listener.setQuestion(kpiHeader,2);
            }

            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float ratings, boolean fromUser) {

                        KPIQuestions que = questions.get(position);
                        que.setAnswered(true);
                        que.setCheckedId((int) ratings);

                        listener.setUbah(true);


                    if(kpiHeader.getSemester().equals("1")){
                            listener.setQuestion(kpiHeader,1);
                        }
                        else{
                            listener.setQuestion(kpiHeader,2);
                        }

                        switch((int)ratings){
                        case 0:
                            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
                            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);
                            tvIndicator.setText("Field Cannot Empty!");
                                tvIndicator.setTextColor(context.getResources().getColor(R.color.white));
                            stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                            break;
                            case 1:
                                stars = (LayerDrawable) ratingBar.getProgressDrawable();
                                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                                tvIndicator.setText("Unacceptable");
                                tvIndicator.setTextColor(context.getResources().getColor(R.color.red));
                                stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                                break;
                            case 2:
                                tvIndicator.setText("Below Expectation");
                                tvIndicator.setTextColor(context.getResources().getColor(R.color.yellow));
                                stars = (LayerDrawable) ratingBar.getProgressDrawable();
                                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                                stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                                break;
                            case 3:
                                tvIndicator.setText("Meet Expectation");
                                stars = (LayerDrawable) ratingBar.getProgressDrawable();
                                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                                stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                                tvIndicator.setTextColor(context.getResources().getColor(R.color.green));
                                break;
                            case 4:
                                tvIndicator.setText("Exceed Expectation");
                                stars = (LayerDrawable) ratingBar.getProgressDrawable();
                                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                                stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);
                                tvIndicator.setTextColor(context.getResources().getColor(R.color.darkGreen));
                                break;
                            case 5:
                                tvIndicator.setText("Excelllent");
                                stars = (LayerDrawable) ratingBar.getProgressDrawable();
                                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                                stars.getDrawable(0).setColorFilter(context.getResources().getColor(R.color.light_grey), PorterDuff.Mode.SRC_ATOP);

                                tvIndicator.setTextColor(context.getResources().getColor(R.color.blue));
                                break;
                    }
                }
            });
        }
    }

    public void showUploadDialog(List<ImageUploadModelPA> imageUploadModels, int position, String kpiId, String paId, String kpiType){
        dialog = new Dialog(context);
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.upload_evidence_pa_dialog);
        dialog.setTitle("Title...");
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        EditText edtEvidence = dialog.findViewById(R.id.edtEvidence);
        RecyclerView recyclerViewFile = (RecyclerView) dialog.findViewById(R.id.recycler_view_image_upload);
        ImageButton imgBtnUploadFile = dialog.findViewById(R.id.imgBtnFile);
        ImageButton imgBtnUploadPhoto = dialog.findViewById(R.id.imgBtnPhoto);
        TextView tvTarget = dialog.findViewById(R.id.tvTarget);
        TextView tvActual = dialog.findViewById(R.id.tvActual);
        TextView tvMaxText = dialog.findViewById(R.id.tvMaxText);
        LinearLayout lnProgressBar = dialog.findViewById(R.id.linlaHeaderProgressImage);

        EditText edtTarget = dialog.findViewById(R.id.edtTarget);
        edtTarget.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        EditText edtActual = dialog.findViewById(R.id.edtActual);
        edtActual.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        if(imageUploadModels != null) {

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 6);
            recyclerViewFile.setLayoutManager(mLayoutManager);
            adapter = new KPIImageUploadAdapterTahunan(imageUploadModels, context, activity, isEditable,questions.get(position).getPaId(), questions.get(position).getKpiId(), questions.get(position).getKpiId(), questions.get(position).getSemester(), questions.get(position).getKPIcategory());
            recyclerViewFile.setAdapter(adapter);
        }
        else{
            ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
            lnProgressBar.setVisibility(View.VISIBLE);
            Call<List<ImageUploadModelPA>> call = apiService.getKPIEvidencePA(questions.get(position).getPaId(),questions.get(position).getKpiId(),kpiType,questions.get(position).getSemester(),"Bearer "+usr.get(0).getToken());
            call.enqueue(new Callback<List<ImageUploadModelPA>>() {
                @Override
                public void onResponse(Call<List<ImageUploadModelPA>> call, Response<List<ImageUploadModelPA>> response) {
                    //int statusCode = response.code();
                    List<ImageUploadModelPA> imageUploadModelPAList = new ArrayList<>();
                    if(response.body() !=null){
                        try{
                            questions.get(position).setPhotoCapture(response.body());
                            //Toast.makeText(context, Integer.toString(response.body().size()), Toast.LENGTH_LONG).show();

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 6);
                            recyclerViewFile.setLayoutManager(mLayoutManager);
                            adapter = new KPIImageUploadAdapterTahunan(questions.get(position).getPhotoCapture(), context, activity, isEditable,questions.get(position).getPaId(), questions.get(position).getKpiId(), questions.get(position).getKpiId(), questions.get(position).getSemester(), questions.get(position).getKPIcategory());

                            recyclerViewFile.setAdapter(adapter);
                            lnProgressBar.setVisibility(View.GONE);

                        }
                        catch (Exception e){
                            //Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                            lnProgressBar.setVisibility(View.GONE);

                        }
                    }
                }
                @Override
                public void onFailure(Call<List<ImageUploadModelPA>> call, Throwable t) {
                    Toast.makeText(context,t.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }

        //Toast.makeText(context, questions.get(position).getEvidence(), Toast.LENGTH_SHORT).show();
        if(questions.get(position).getAnsweredEvidence()) {
            edtEvidence.setText(questions.get(position).getEvidence());
        } else {
            edtEvidence.setText("");
        }
        if(questions.get(position).getAnsweredActual()) {
            edtActual.setText(questions.get(position).getActual());
        } else {
            edtActual.setText("0");
        }
        if(questions.get(position).getAnsweredTarget()) {
            edtTarget.setText(questions.get(position).getTarget());
        } else {
            edtTarget.setText("0");
        }

        imgBtnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //Toast.makeText(context,questions.get(position).getPhotoCapture().size()+"",Toast.LENGTH_SHORT).show();
                    if (questions.get(position).getPhotoCapture().size() >= 4) {
                        Toast.makeText(context,"File yang diupload sudah mencapai batas maksimum 4 file",Toast.LENGTH_LONG).show();
                    } else {
                        listener.onEvent(position, questions,0);
                    }
                }
                catch(Exception e){
                    //listener.onEvent(position, questions,0);
                }


            }
        });

        imgBtnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (questions.get(position).getPhotoCapture().size() >= 4) {
                        Toast.makeText(context,"File yang diupload sudah mencapai batas maksimum 4 file",Toast.LENGTH_LONG).show();
                    } else {
                        listener.onEvent(position, questions,1); //masih blm ke
                    }
                }
                catch(Exception e){
                    listener.onEvent(position, questions,1); //masih blm ke
                }
            }
        });


        if(kpiType.equals("KUALITATIF")){
            edtTarget.setVisibility(View.GONE);
            tvActual.setVisibility(View.GONE);
            tvTarget.setVisibility(View.GONE);
            edtActual.setVisibility(View.GONE);
        }


        edtEvidence.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listener.setUbah(true);
                KPIQuestions que = questions.get(position);
                if(edtEvidence.getText().toString().length()>0) {
                    que.setAnsweredEvidence(true);
                    que.setEvidence(edtEvidence.getText().toString());
                    if(kpiHeader.getSemester().equals("1")){
                        listener.setQuestion(kpiHeader,1);
                    }
                    else{
                        listener.setQuestion(kpiHeader,2);
                    }
                }
                else{
                    que.setAnsweredEvidence(false);
                    que.setEvidence("");
                    if(kpiHeader.getSemester().equals("1")){
                        listener.setQuestion(kpiHeader,1);
                    }
                    else{
                        listener.setQuestion(kpiHeader,2);
                    }
                }
                tvMaxText.setText(s.length()+"/1000");
            }
        });


        edtActual.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listener.setUbah(true);
                KPIQuestions que = questions.get(position);
                if(edtActual.getText().toString().length()>0) {
                    que.setAnsweredActual(true);
                    que.setActual(edtActual.getText().toString());
                    if(kpiHeader.getSemester().equals("1")){
                        listener.setQuestion(kpiHeader,1);
                    }
                    else{
                        listener.setQuestion(kpiHeader,2);
                    }
                }
                else{
                    //edtActual.setText("");
                }
            }
        });

        edtTarget.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listener.setUbah(true);

                KPIQuestions que = questions.get(position);
                if(edtTarget.getText().toString().length()>0) {
                    que.setAnsweredTarget(true);
                    que.setTarget(edtTarget.getText().toString());
                    if(kpiHeader.getSemester().equals("1")){
                        listener.setQuestion(kpiHeader,1);
                    }
                    else{
                        listener.setQuestion(kpiHeader,2);
                    }
                }
                else{
                    //edtTarget.setText("");
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showHintKPI(List<KPIHint> hint, String title){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.kpi_hint_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        TextView tvKeterangan = dialog.findViewById(R.id.tvKeterangan);
        tvKeterangan.setText(title);
        //Toast.makeText(context,Integer.toString(hint.size()),Toast.LENGTH_LONG).show();
        RecyclerView recyclerViewKPIHint = (RecyclerView) dialog.findViewById(R.id.recycler_view_kpi_hint);
        recyclerViewKPIHint.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.Adapter kpiAdapter = new KPIHintAdapterTahunan(hint, context, activity);
        recyclerViewKPIHint.setAdapter(kpiAdapter);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void refresh(){
        notifyDataSetChanged();
    }




}
