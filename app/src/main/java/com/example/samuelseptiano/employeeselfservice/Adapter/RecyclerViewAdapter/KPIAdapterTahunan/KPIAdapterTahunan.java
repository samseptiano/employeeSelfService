package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPDetailModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPLayoutListModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.IDPModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lecho.lib.hellocharts.model.Line;

public class KPIAdapterTahunan extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "KPIAdapterTahunan";
    private LayoutInflater inflater;
    KPIHeader kpiHeader;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    KPIImageUploadAdapterTahunan adapter;
    public Dialog dialog;
    private Context context;

    DatePickerDialog  datePickerDialog;
    SimpleDateFormat dateFormatter;

    List<IDPLayoutListModel> listIDP = new ArrayList<>();
    IDPLayoutListModel idpLayoutListModel = new IDPLayoutListModel();


    List<Button> btnevplan = new ArrayList<>();
    List<CheckBox> cbDevplan = new ArrayList<>();
    List<TextView> tvDevplan = new ArrayList<>();
    List<LinearLayout> lnDevplans = new ArrayList<>();
    List<LinearLayout> lnDevplansDetail = new ArrayList<>();

    List<Dialog> dialogList = new ArrayList<>();



    protected String friendName = "";
    protected String friendNIK = "";
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


    private List<KPIQuestions> questions;

    //semester 2


    private LinearLayout linearLayoutContainer;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;


    EventListener listener;

    public interface EventListener {
        void onEvent(int position, List<KPIQuestions> questions, int action);
        List <ImageUploadModel> onResult();
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
    }

    public KPIAdapterTahunan(Activity activity, Context context, KPIHeader kpiHeader, String id, String type, EventListener listener, String Jenis) {
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
        usr = userRealmHelper.findAllArticle();
        isEditable = listener.isEditables();
        SEMESTER = listener.getSemster();
        //Toast.makeText(context,Integer.toString(kpiHeader.getKpiQuestionsList().size()),Toast.LENGTH_LONG).show();
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
                        if (item.toString().equals("Semester 1")) {
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

                    footerHolder.edtRencanaTindaklanjut.setEnabled(false);
                    footerHolder.edtKelebihan.setEnabled(false);
                    footerHolder.edtkekurangan.setEnabled(false);
                }
                else if(listener.isEditables().equals("Y") && type.equals("Approve")){
                    footerHolder.edtRencanaTindaklanjut.setEnabled(true);
                    footerHolder.edtKelebihan.setEnabled(true);
                    footerHolder.edtkekurangan.setEnabled(true);
                }
                else{
                    footerHolder.edtRencanaTindaklanjut.setEnabled(true);
                    footerHolder.edtKelebihan.setEnabled(true);
                    footerHolder.edtkekurangan.setEnabled(true);
                }
                UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                String NIK = usr.get(0).getEmpNIK();


                //buat nampilin view pake loop

//                for(int x=0;x<7;x++){
//                    View child = activity.getLayoutInflater().inflate(R.layout.improvement_area_development_plan, null);
//                    footerHolder.ln = child.findViewById(R.id.lnDevPlan);
//                    footerHolder.ln.setId(x);
//                    lnDevplans.add(footerHolder.ln);
//                    footerHolder.cb = child.findViewById(R.id.cbDevPlan);
//                    footerHolder.cb.setId(x);
//                    cbDevplan.add(footerHolder.cb);
//                    footerHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            if(isChecked){
//                            }
//                            else{
//                            }
//                        }
//                    });
//                    footerHolder.tv = child.findViewById(R.id.tvDevPlan);
//                    footerHolder.tv.setId(x);
//                    footerHolder.tv.setText("Hiya hiya "+x);
//
//                    footerHolder.btn = child.findViewById(R.id.btnIdp);
//                    footerHolder.btn.setId(x);
//                    footerHolder.btn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //INTENT TO IDP LAYOUT
//                        }
//                    });
//                    ((FooterViewHolder) holder).lnDevPlan.addView(child);
//                    Toast.makeText(context,"view added!",Toast.LENGTH_SHORT).show();
//                }


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

                ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.GONE);
                ((ItemViewHolder) holder).imgExpand.setImageResource(R.drawable.expand_down);

                if(questions.get(position-1).getKPIcategory().equals("Kualitatif")){
                    ((ItemViewHolder) holder).btnMore.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).btnChat .setVisibility(View.VISIBLE);
                }
                else if(questions.get(position-1).getKPIcategory().equals("Kuantitatif")){
                    ((ItemViewHolder) holder).btnMore.setVisibility(View.VISIBLE);
                }

                if(questions.get(position-1).getSemester().equals(listener.getSemster())){
                   ((ItemViewHolder) holder).lnKPIItem.setVisibility(View.VISIBLE);
                }
                else{
                    ((ItemViewHolder) holder).lnKPIItem.setVisibility(View.GONE);
                }

                if(type.equals("Approve") && listener.isEditables().equals("N")){
                    ((ItemViewHolder) holder).rating.setIsIndicator(true);
                    ((ItemViewHolder) holder).edtCatatan.setEnabled(false);
                    ((ItemViewHolder) holder).edtPendukung.setEnabled(false);
                    ((ItemViewHolder) holder).edtPenghambat.setEnabled(false);
                    ((ItemViewHolder) holder).btnUpload.setEnabled(false);
//                btnUpload.setVisibility(View.GONE);
                }
                else if(type.equals("Approve") && listener.isEditables().equals("Y")){
                    ((ItemViewHolder) holder).rating.setIsIndicator(false);
                    ((ItemViewHolder) holder).edtCatatan.setEnabled(true);
                    ((ItemViewHolder) holder).edtPendukung.setEnabled(true);
                    ((ItemViewHolder) holder).edtPenghambat.setEnabled(true);
                    ((ItemViewHolder) holder).btnUpload.setEnabled(true);
//                btnUpload.setVisibility(View.GONE);
                }



                ((ItemViewHolder) holder).imgBtnUpload.setOnClickListener(v -> showUploadDialog(questions.get(position-1).getPhotoCapture(),position-1));

                ((ItemViewHolder) holder).btnChat.setOnClickListener(v -> {
                    //room chat sudah ada
                    try {
                        String nik = usr.get(0).getEmpNIK();
                        String nama = usr.get(0).getEmpName();
                        String semester = questions.get(position - 1).getSemester();
                        String tahun =  Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

                        if (questions.get(position - 1).getKPIcategory().equals("kualitatif")){
                            kualitatif = questions.get(position - 1).getPerspective();
                        }
                        if (questions.get(position - 1).getKPIcategory().equals("Kuantitatif")){
                            kuantitatif = questions.get(position - 1).getPerspective();
                        }

                        //user berperan sebagai atasan langsung
                        if(usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanLangsung())){
//                                friendName = kpiHeader.getAtasanTakLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanTakLangsung();
                            friendName = kpiHeader.getEmpName();
                            friendNIK = kpiHeader.getNIK();

                        }
                        //user berperan sebagai atasan tak langsung
                        if(usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanTakLangsung())){
//                                friendName = kpiHeader.getAtasanLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanLangsung();
                            friendName = kpiHeader.getEmpName();
                            friendNIK = kpiHeader.getNIK();

                        }
                        // user berperan sebagai karyawan
                        if(!usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanTakLangsung()) && !usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanLangsung())){
//                                friendName = kpiHeader.getAtasanLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanLangsung();
                            friendName = usr.get(0).getNamaAtasanLangsung();
                            friendNIK =  usr.get(0).getNikAtasanLangsung();
                        }


                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                                if (dataSnapshot.hasChild(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR")) {
                                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                    routingHomeDetailInterface.routingChat(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR",friendName, friendNIK,semester,tahun,kualitatif,kuantitatif,context);

                                }
                                else if(dataSnapshot.hasChild(friendNIK+"-"+nik+"-"+friendName+"-"+nama+"-YEAR")){
                                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                    routingHomeDetailInterface.routingChat(friendNIK +"-"+nik+"-"+nama+"-"+friendName+"-YEAR",friendName,friendNIK,semester,tahun,kualitatif,kuantitatif,context);
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
                        map.put(nik+"-"+nikLawan+"-YEAR",""); //diganti room name random uniqueID
                        root.updateChildren(map);

                    }


                });



                //if the position is equals to the item position which is to be expanded
                if(questions.get(position-1).getKPIcategory().equals("Kuantitatif")) {
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
                itemViewHolder.setNumber(position);
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
            return questions.size()+2;
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
            tahuns.add("Semester 1");
            tahuns.add("Semester 2");
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
        TextView footertext;
        LinearLayout lnKualitatif, lnDevPlan;
        ViewPager viewPager;
        MaterialSpinner spinnerDevPlan;
        Button addBtn, delBtn;

        CheckBox cb;
        LinearLayout ln;
        Button btn;
        TextView tv;
        List<IDPModel> idpModelList = new ArrayList<>();
        List<IDPDetailModel> idpDetailModelList = new ArrayList<>();
        IDPModel idpModel;
        IDPDetailModel idpDetailModel;


        int ii=0;

        public FooterViewHolder(View view) {
            super(view);

            List<String> Devplans = new ArrayList<>();
            String Devplan = new String("");

            footerButton = (Button) view.findViewById(R.id.btnSubmitKPI);
            btnApprove = (Button) view.findViewById(R.id.btnApproveKPI);
            footertext = (TextView) view.findViewById(R.id.tvFooterTitle);
            edtkekurangan = view.findViewById(R.id.edtKekurangan);
            edtKelebihan = view.findViewById(R.id.edtKelebihan);
            edtRencanaTindaklanjut = view.findViewById(R.id.edtRencanaTindaklanjut);
            spinnerDevPlan = view.findViewById(R.id.spinnerImprovement);
            addBtn = view.findViewById(R.id.btnAdd);
            lnDevPlan = view.findViewById(R.id.lnAddDevPlan);
            delBtn = view.findViewById(R.id.btnDelete);
            lnKualitatif = view.findViewById(R.id.lnKualitatif);

            List<String> tahuns = new ArrayList<String>();
            tahuns.add("Dummy 1");
            tahuns.add("Dummy 2");
            tahuns.add("Dummy 3");

            // Creating adapter for spinner
            // attaching data adapter to spinner
            spinnerDevPlan.setItems(tahuns);

            spinnerDevPlan.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                    Devplan = spinnerDevPlan.getItems().get(spinnerDevPlan.getSelectedIndex()).toString();
                }
            });


            View child = activity.getLayoutInflater().inflate(R.layout.improvement_area_development_plan, null);



            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int xx = cbDevplan.size();
//                    View child = activity.getLayoutInflater().inflate(R.layout.improvement_area_development_plan, null);
                    for (int j=0;j<lnDevplans.size();j++) {
                        if(cbDevplan.get(j).isChecked()){
//                            lnDevPlan.removeView(lnDevplans.get(j));
                            lnDevplans.get(j).setVisibility(View.GONE);
//                            tahuns.add(tvDevplan.get(j).getText().toString());
                            //Toast.makeText(context,Boolean.toString(cbDevplan.get(j).isChecked()),Toast.LENGTH_LONG).show();

                        }
                }

                }
            });

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    View child = activity.getLayoutInflater().inflate(R.layout.improvement_area_development_plan, null);

//                        int done = 0;
                    for (int i=0;i<tvDevplan.size();i++){
                            if(tvDevplan.get(i).getText().equals(spinnerDevPlan.getText())){
//                                done=1;
                            }
                        }
//                    if(done==0){
                    idpModel = new IDPModel();


                   ln = child.findViewById(R.id.lnDevPlan);
                    ln.setId(ii);
                    lnDevplans.add(ln);
                    cb = child.findViewById(R.id.cbDevPlan);
                    cb.setId(ii);
                    cbDevplan.add(cb);
                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                            }
                            else{
                            }
                        }
                    });

                    tv = child.findViewById(R.id.tvDevPlan);
                    tv.setId(ii);
                    tv.setText(spinnerDevPlan.getText());
//                    tahuns.remove(spinnerDevPlan.getText());

                    tvDevplan.add(tv);
                    btn =  child.findViewById(R.id.btnIdp);
                    btn.setId(ii);
                    btnevplan.add(btn);
                    final int index = ii;
                    btnevplan.get(ii).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //INTENT TO IDP LAYOUT
                            Dialog aa= new Dialog(context);
                            dialogList.add(aa);
                            showIDPDialog(aa,tvDevplan.get(index).getText().toString(),ii);
                        }
                    });
                    lnDevPlan.addView(child);


                    //Insert IDP to object
//                    idpModel.setEmpNIK(usr.get(0).getEmpNIK());
//                    idpModel.setId("");
//                    idpModel.setIDPTitle(tv.getText().toString());
//                    idpModel.setPaTransId(questions.get(0).getIdKPI());
//                    idpModel.setIdpDetailModelList();
//                    idpModelList.add(idpModel);

                    ii++;
                }
//                }
            });


            if(kpiHeader.getSemester().equals("1")) {
                btnApprove.setText("Next");
                footerButton.setText("Next");
            }
            else if(kpiHeader.getSemester().equals("2")){
                btnApprove.setText("Submit KPI");
            }
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutContainer,linearLayoutContainer2, lnKPIItem;
        private TextView textViewQuestion, textViewNumber, tvIndicator,tvBobot, tvCategory, tvPerspective;
        private RatingBar rating;
        private ImageButton imgBtnHint, imgExpand, imgBtnUpload;
        private EditText  edtPendukung,edtPenghambat,edtCatatan;
        private View hrLine, hrLine2;
        Button btnUpload, btnMore;
        ImageButton btnChat;
        LinearLayout lnKuantitatif,lnKPIName;
        RecyclerView recyclerViewImg;

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
                KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.getPhotoCapture(), context, activity, isEditable);
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
                            que.setCatatanLain(edtCatatan.getText().toString());
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
                    tvCategory.setText(category);
                }

                if(questions.get(position - 1).getPerspective().equals(perspective)){
                    tvPerspective.setVisibility(View.GONE);
                }
                else {
                    tvPerspective.setText(perspective);
                }
            }
            else{
                tvCategory.setText(category);
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
                    rating.setRating(question.getCheckedId());
                    switch (question.getCheckedId()) {
                        case 1:
                            tvIndicator.setText("Unacceptable");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.red));
                            break;
                        case 2:
                            tvIndicator.setText("Below Expectation");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.orange));
                            break;
                        case 3:
                            tvIndicator.setText("Meet Expectation");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.yellow));
                            break;
                        case 4:
                            tvIndicator.setText("Exceed Expectation");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.blue));
                            break;
                        case 5:
                            tvIndicator.setText("Excelllent");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.green));
                            break;
                    }

                } else {
                    rating.setRating(0);
                    tvIndicator.setText("-");
                }


            imgBtnHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHintKPI(questions.get(position).getHint());
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
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        KPIQuestions que = questions.get(position);
                        que.setAnswered(true);
                        que.setCheckedId((int) rating);

                        if(kpiHeader.getSemester().equals("1")){
                            listener.setQuestion(kpiHeader,1);
                        }
                        else{
                            listener.setQuestion(kpiHeader,2);
                        }

                        switch((int)rating){
                        case 1:
                            tvIndicator.setText("Unacceptable");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.red));
                            break;
                        case 2:
                            tvIndicator.setText("Below Expectation");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.orange));
                            break;
                        case 3:
                            tvIndicator.setText("Meet Expectation");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.yellow));
                            break;
                        case 4:
                            tvIndicator.setText("Exceed Expectation");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.blue));
                            break;
                        case 5:
                            tvIndicator.setText("Excelllent");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.green));
                            break;
                    }
                }
            });
        }
    }

    public void showUploadDialog(List<ImageUploadModel> imageUploadModels, int position){
        dialog = new Dialog(context);
//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.upload_evidence_pa_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        EditText edtEvidence = dialog.findViewById(R.id.edtEvidence);
        RecyclerView recyclerViewFile = (RecyclerView) dialog.findViewById(R.id.recycler_view_image_upload);
        ImageButton imgBtnUploadFile = dialog.findViewById(R.id.imgBtnFile);
        ImageButton imgBtnUploadPhoto = dialog.findViewById(R.id.imgBtnPhoto);

        if(imageUploadModels != null) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 6);
            recyclerViewFile.setLayoutManager(mLayoutManager);
            adapter = new KPIImageUploadAdapterTahunan(imageUploadModels, context, activity, isEditable);
            recyclerViewFile.setAdapter(adapter);
        }


        if(questions.get(position).getAnsweredPendukung()) {
            edtEvidence.setText(questions.get(position).getEvidence());
        } else {
            edtEvidence.setText("");
        }
        imgBtnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEvent(position, questions,0);
            }
        });

        imgBtnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEvent(position, questions,1); //masih blm ke
                //showUploadDialog(imageUploadModels,position);

            }
        });



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

    private void showHintKPI(List<String> hint){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.kpi_hint_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);

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

    private void showIDPDialog(Dialog dialogs, String idpTitle, int DevPlanPosition){
        dialogs = new Dialog(context);

        dialogs.setContentView(R.layout.idp_dialog);
        dialogs.setTitle("Title...");
        dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogs.setCanceledOnTouchOutside(false);
        dialogs.setCancelable(false);

        WindowManager manager = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
        int width, height;
        ViewGroup.LayoutParams params;

        final boolean[] input = {false};

        final int[] ctr = {1};

        View myView;
        ViewGroup parent;
        LinearLayout formLayout;

//        myView = dialogs.findViewById(R.id.lnIdpDetail);
//        parent = (ViewGroup) myView.getParent();
//        parent.removeView(myView);

        formLayout = (LinearLayout)dialogs.findViewById(R.id.formLayout);
        formLayout.removeAllViews();
        formLayout.setOrientation(LinearLayout.VERTICAL);

        ImageButton closeButton = (ImageButton) dialogs.findViewById(R.id.ib_close);
        TextView tvIdpTitle = dialogs.findViewById(R.id.tvIdpTitle);
        Button btnAdd = dialogs.findViewById(R.id.btnAdd);
        Button btnDelete = dialogs.findViewById(R.id.btnDelete);

        MaterialSpinner spinnerImprovement = dialogs.findViewById(R.id.spinnerImprovement);
        List<View> lnList = new ArrayList<>();

        List<CheckBox> cbList = new ArrayList<>();
        List<TextView> tvNumbList = new ArrayList<>();
        List<TextView> tvTitleList = new ArrayList<>();
         List<EditText>edtDevelopmentActivitiesList = new ArrayList<>();
        List<ImageButton>imgExpandList = new ArrayList<>();
        List<LinearLayout>lnExpandList = new ArrayList<>();
         List<EditText>edtKPIList = new ArrayList<>();
         List<EditText>edtDueDateList = new ArrayList<>();
         List<EditText>edtMentorsList = new ArrayList<>();
         List<EditText>edtAchievementRecommendationList = new ArrayList<>();


        final int[] ii = {0};


        List<String> improvement = new ArrayList<String>();
        improvement.add("Dummy 1");
        improvement.add("Dummy 2");
        improvement.add("Dummy 3");
        spinnerImprovement.setItems(improvement);

        tvIdpTitle.setText(idpTitle);


        try {

            idpLayoutListModel =  new IDPLayoutListModel();
            for (int u=0;u<listIDP.size();u++){
                if(listIDP.get(u).getIdpTitle().equals(idpTitle)){
                for(int y=0;y<listIDP.get(u).getLnIdpDetail().size();y++){

            View childs = inflater.inflate(R.layout.idp_item_detail, formLayout, false);

            if(listIDP.get(u).getLnIdpDetail().get(y).getVisibility() == View.VISIBLE) {
                CheckBox cbIdp = childs.findViewById(R.id.checkBoxIdp);
                //listIDP.get(u).getCbIdpDetail().get(y);
                int finalU = u;
                int finalY = y;
                cbIdp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            listIDP.get(finalU).getCbIdpDetail().get(finalY).setChecked(true);
                        } else {
                            listIDP.get(finalU).getCbIdpDetail().get(finalY).setChecked(true);
                        }
                    }
                });


                EditText edtDueDate = childs.findViewById(R.id.edtDueDate);
                edtDueDate.setText(listIDP.get(u).getEdtDueDate().get(y).getText());
                listIDP.get(u).getEdtDueDate().get(y);


                LinearLayout lnExpand =  childs.findViewById(R.id.lnExpanded);
                listIDP.get(u).getLnExpanded().get(y);

                int finalY1 = y;
                int finalU1 = u;
                ImageButton imgExpand = childs.findViewById(R.id.imgExpand);
                imgExpand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context,Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();
                        if(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility() == View.VISIBLE){
                            lnExpand.setVisibility(View.GONE);
                            imgExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_down));
                            listIDP.get(finalU1).getLnExpanded().get(finalY1).setVisibility(View.GONE);
                            listIDP.get(finalU1).getImgExpand().get(finalY1).setImageDrawable(context.getResources().getDrawable(R.drawable.expand_down));
                            Toast.makeText(context,Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();

                        }
                        else{
                            lnExpand.setVisibility(View.VISIBLE);
                            imgExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_up));
                            listIDP.get(finalU1).getLnExpanded().get(finalY1).setVisibility(View.VISIBLE);
                            listIDP.get(finalU1).getImgExpand().get(finalY1).setImageDrawable(context.getResources().getDrawable(R.drawable.expand_up));
                            Toast.makeText(context,Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();

                        }
                    }
                });

                ImageButton btnCalendar = childs.findViewById(R.id.btnCalendar);


                btnCalendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCalendar(edtDueDate, listIDP.get(finalU1).getEdtDueDate().get(finalY1));
                        listIDP.get(finalU1).getEdtDueDate().get(finalY1).setText(edtDueDate.getText());

                    }
                });


                TextView tvNumber = childs.findViewById(R.id.tvNumber);
                tvNumber.setText(Integer.toString(ctr[0]) + ". ");
                listIDP.get(u).getTvIdpNumber().get(y);

                TextView tvIdpDetailTitle = childs.findViewById(R.id.tvidp_title);
                tvIdpDetailTitle.setText(listIDP.get(u).getTvIdpTitleDetail().get(y).getText().toString());
                listIDP.get(u).getTvIdpTitleDetail().get(y);

                formLayout.addView(childs);
                ctr[0]++;
            }
               }
            }

        }
        }catch (Exception e){}


        Dialog finalDialogs = dialogs;
        final int[] finalCtr = {ctr[0]};
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idpLayoutListModel =  new IDPLayoutListModel();

                TextView tvNumber,tvIdpDetailTitle;
                ImageButton btnCalendar, btnExpand;
                LinearLayout lnExpand;
                EditText edtDevelopmentActivities,edtMentor, edtDueDate,edtKPI,edtAchievementRecommendation;
                CheckBox cbIdp;

                View child = inflater.inflate(R.layout.idp_item_detail, formLayout, false);

                        cbIdp = child.findViewById(R.id.checkBoxIdp);
                        cbIdp.setId(ii[0]);
                        cbIdp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked){
                                }
                                else{
                                }
                            }
                        });
                cbList.add(cbIdp);

                idpLayoutListModel.setCbIdpDetail(cbList);

                        tvNumber = child.findViewById(R.id.tvNumber);
                        tvNumber.setText(Integer.toString(finalCtr[0]));
                        tvNumber.setId(ii[0]);
                        tvNumbList.add(tvNumber);
                        idpLayoutListModel.setTvIdpNumber(tvNumbList);

                        tvIdpDetailTitle = child.findViewById(R.id.tvidp_title);
                        tvIdpDetailTitle.setText(spinnerImprovement.getText());
                        tvIdpDetailTitle.setId(ii[0]);
                        tvTitleList.add(tvIdpDetailTitle);
                        idpLayoutListModel.setTvIdpTitleDetail(tvTitleList);

                        edtDevelopmentActivities = child.findViewById(R.id.edtDevelopmentActivities);
                        edtDevelopmentActivities.setId(ii[0]);
                        edtDevelopmentActivitiesList.add(edtDevelopmentActivities);
                        idpLayoutListModel.setEdtDevelopmentActivities(edtDevelopmentActivitiesList);


                        btnExpand = child.findViewById(R.id.imgExpand);
                        btnExpand.setId(ii[0]);
                        imgExpandList.add(btnExpand);
                        idpLayoutListModel.setImgExpand(imgExpandList);

                        lnExpand = child.findViewById(R.id.lnExpanded);
                        lnExpand.setId(ii[0]);
                        lnExpandList.add(lnExpand);
                        idpLayoutListModel.setLnExpanded(lnExpandList);

                        edtKPI = child.findViewById(R.id.edtKPI);
                        edtKPI.setId(ii[0]);
                        edtKPIList.add(edtKPI);
                        idpLayoutListModel.setEdtKPI(edtKPIList);

                        edtMentor = child.findViewById(R.id.edtMentor);
                        edtMentor.setId(ii[0]);
                        edtMentorsList.add(edtMentor);
                        idpLayoutListModel.setEdtMentors(edtMentorsList);

                        edtDueDate = child.findViewById(R.id.edtDueDate);
                        edtDueDate.setId(ii[0]);
                        edtDueDateList.add(edtDueDate);
                        idpLayoutListModel.setEdtDueDate(edtDueDateList);

                        edtAchievementRecommendation = child.findViewById(R.id.edtAchevementRecommendation);
                        edtAchievementRecommendation.setId(ii[0]);
                        edtAchievementRecommendationList.add(edtAchievementRecommendation);
                        idpLayoutListModel.setEdtAchievementRecommendation(edtAchievementRecommendationList);

                        btnCalendar = child.findViewById(R.id.btnCalendar);
                        btnCalendar.setId(ii[0]);

                        formLayout.addView(child);

                        lnList.add(child);
                        idpLayoutListModel.setIdpTitle(idpTitle);
                        idpLayoutListModel.setLnIdpDetail(lnList);
                        idpLayoutListModel.setLinLaIdpDetail(formLayout);

                        ii[0]++;
                        input[0] = true;
                        ctr[0] = finalCtr[0]++;


                        if(input[0]) {
                            listIDP.add(idpLayoutListModel);
                            input[0] = false;
                        }
                    finalDialogs.dismiss();
                    showIDPDialog(finalDialogs, idpTitle, 0);
        //Toast.makeText(context,Integer.toString(formLayout.getChildCount()),Toast.LENGTH_LONG).show();


            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int u=0;u<listIDP.size();u++){
                    for (int j=0;j<listIDP.get(u).getCbIdpDetail().size();j++) {
                        if(listIDP.get(u).getCbIdpDetail().get(j).isChecked()){
                            listIDP.get(u).getLnIdpDetail().get(j).setVisibility(View.GONE);
                            //Toast.makeText(context,Boolean.toString(listIDP.get(u).getCbIdpDetail().get(j).isChecked()),Toast.LENGTH_LONG).show();

                        }
                    }
                }
                if(input[0]) {
                    listIDP.add(idpLayoutListModel);
                    input[0] = false;
                    //idpLayoutListModel = new IDPLayoutListModel();
                }
                finalDialogs.dismiss();
                showIDPDialog(finalDialogs, idpTitle, 0);
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window

                if(input[0]) {
                    listIDP.add(idpLayoutListModel);
                    input[0] = false;
                    //idpLayoutListModel = new IDPLayoutListModel();
                }
                finalDialogs.dismiss();

            }
        });

        dialogs.show();
    }


    public void refresh(){
        notifyDataSetChanged();
    }

    private void submitKPI(String NIK, FooterViewHolder footerHolder){
//        kpiUserAnswerList = new KPIUserAnswerList();
//        List<String> tempImg = new ArrayList<>();
//        int i = 0;
//
//        if(listener.onResult().size()>0) {
//            for (i = 0; i < aaa.size(); i++) {
//                tempImg.add(aaa.get(i).getImgString());
//            }
//        }
//
//        kpiUserAnswerList.setDept(kpiHeader.getDept());
//        kpiUserAnswerList.setSemester(kpiHeader.getSemester());
//        kpiUserAnswerList.setStatus(kpiHeader.getStatus());
//        kpiUserAnswerList.setEmpNIK(NIK);
//        kpiUserAnswerList.setKPIType(Jenis);
//        kpiUserAnswerList.setKpiUserAnswerList(questions);
//
//        //kpiUserAnswerList.setImgCapture(tempImg);
//
//        kpiUserAnswerList.setRencanaTindaklanjut("");
//        kpiUserAnswerList.setKekurangan("");
//        kpiUserAnswerList.setKelebihan("");
//        kpiUserAnswerList.setKelebihan(footerHolder.edtKelebihan.getText().toString());
//        kpiUserAnswerList.setKekurangan(footerHolder.edtkekurangan.getText().toString());
//        kpiUserAnswerList.setRencanaTindaklanjut(footerHolder.edtRencanaTindaklanjut.getText().toString());


        test = Integer.toString(listener.getQuestionSmt1().getKpiQuestionsList().get(0).getCheckedId());
        test2 = Integer.toString(listener.getQuestionSmt2().getKpiQuestionsList().get(0).getCheckedId());
        Toast.makeText(context,"questions2:"+test2,Toast.LENGTH_LONG).show();
        Toast.makeText(context,"questions:"+test,Toast.LENGTH_LONG).show();
        Toast.makeText(context,listener.getQuestionSmt1().getCompany(),Toast.LENGTH_LONG).show();
    }


    private void showCalendar (EditText editText, EditText editTexts){

        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                try {
                    editText.setText(dateFormatter.format(newDate.getTime()));
                    editTexts.setText(dateFormatter.format(newDate.getTime()));
                    Toast.makeText(context, "Tanggal dipilih : " + dateFormatter.format(newDate.getTime()), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

}
