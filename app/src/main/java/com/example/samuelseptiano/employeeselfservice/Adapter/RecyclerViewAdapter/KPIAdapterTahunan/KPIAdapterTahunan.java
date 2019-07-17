package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment.KPIFillFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIFillTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIKuantitatifTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPISummaryFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KPIAdapterTahunan extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "KPIAdapterTahunan";
    private LayoutInflater inflater;
    KPIHeader kpiHeader;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();


    private Context context;


    protected String friendName = "";
    protected String friendNIK = "";
    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
    ArrayList<UserRealmModel> usr;

    private static int currentPosition;

    List<ImageUploadModel> aaa = new ArrayList<ImageUploadModel>();

    Activity activity;

    KPIUserAnswerList kpiUserAnswerList;
    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    String test="",test2="";

    static boolean isVisible = false;

    String id,type;
    String isEditable="N";
    String Jenis = "";


    private List<KPIQuestions> questions;

    //semester 2


    private LinearLayout linearLayoutContainer;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;


    EventListener listener;

    public interface EventListener {
        void onEvent(int position, List<KPIQuestions> questions);
        List <ImageUploadModel> onResult();
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void redirect(int tabPos);
        void setQuestion(KPIHeader a, int semester);
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
                headerHolder.status.setText(kpiHeader.getStatus());

                headerHolder.namaKaryawan.setText(kpiHeader.getEmpName());
                headerHolder.nik.setText(kpiHeader.getNIK());
                headerHolder.dept.setText(kpiHeader.getDept());
                headerHolder.tvAtasan1.setText(kpiHeader.getAtasanLangsung());
                headerHolder.tvAtasan2.setText(kpiHeader.getAtasanTakLangsung());
                headerHolder.tvStatus1.setText(kpiHeader.getStatus1());
                headerHolder.tvStatus2.setText(kpiHeader.getStatus2());
                headerHolder.tvCompany.setText(kpiHeader.getCompany());

                if(kpiHeader.getStatus().equals("Not Approved")){
                    ((KPIAdapterTahunan.HeaderViewHolder) holder).status.setTextColor(Color.RED);
                }
                else if(kpiHeader.getStatus().equals("Approved")){
                    ((KPIAdapterTahunan.HeaderViewHolder) holder).status.setTextColor(Color.GREEN);
                }

                if(kpiHeader.getStatus1().equals("Not Approved")){
                    ((KPIAdapterTahunan.HeaderViewHolder) holder).tvStatus1.setTextColor(Color.RED);
                }
                else if(kpiHeader.getStatus1().equals("Approved")){
                    ((KPIAdapterTahunan.HeaderViewHolder) holder).tvStatus1.setTextColor(Color.GREEN);
                }

                if(kpiHeader.getStatus2().equals("Not Approved")){
                    ((KPIAdapterTahunan.HeaderViewHolder) holder).tvStatus2.setTextColor(Color.RED);
                }
                else if(kpiHeader.getStatus2().equals("Approved")){
                    ((KPIAdapterTahunan.HeaderViewHolder) holder).tvStatus2.setTextColor(Color.GREEN);
                }

                if(id.length()== 0 && !type.equals("Approve")){
                    ((HeaderViewHolder) holder).dept.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).namaKaryawan.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).nik.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnDept.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnNIK.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnNama.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnCompany.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnLocation.setVisibility(View.GONE);
                }
                ((HeaderViewHolder) holder).editBtn.setOnClickListener(new View.OnClickListener() {
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


            } else if (holder instanceof KPIAdapterTahunan.FooterViewHolder) {
                final KPIAdapterTahunan.FooterViewHolder footerHolder = (KPIAdapterTahunan.FooterViewHolder) holder;

                final int requestCode = 20;

                if(id.length()>0 && type.equals("Approve")){
                    ((FooterViewHolder) holder).footerButton.setVisibility(View.GONE);
                }
                else{
                    ((FooterViewHolder) holder).btnApprove.setVisibility(View.GONE);
                }

                if(isEditable.equals("N") && type.equals("Approve")){

                    footerHolder.edtRencanaTindaklanjut.setEnabled(false);
                    footerHolder.edtKelebihan.setEnabled(false);
                    footerHolder.edtkekurangan.setEnabled(false);
                }
                else if(isEditable.equals("Y") && type.equals("Approve")){
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

                footerHolder.btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(type.equals("Approve")){

                            if(kpiHeader.getSemester().equals("1")) {
                                listener.redirect(1);
                                footerHolder.btnApprove.setText("Next");
                            }
                            else {
                                listener.redirect(2);
                                listener.getQuestionSmt();

                                footerHolder.btnApprove.setText("Submit KPI");

                            }
                        }
                    }
                });

                footerHolder.footerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!type.equals("Approve")){
                            if(kpiHeader.getSemester().equals("1")) {
                                listener.redirect(1);
                                footerHolder.footerButton.setText("Next");

//                                listener.setQuestion(questions,1);

                            }
                            else{
                                    listener.getQuestionSmt();
                                //submitKPI(NIK,footerHolder);
                            }


                            // code block
                            //RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                            //routingHomeDetailInterface.routingKPI("Fill PA",context,"","",2);

                        }
                    }
                });
            } else if (holder instanceof KPIAdapterTahunan.ItemViewHolder) {
                holder.setIsRecyclable(false);

                ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.GONE);
                ((ItemViewHolder) holder).imgExpand.setImageResource(R.drawable.expand_down);


                ((ItemViewHolder) holder).btnChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //room chat sudah ada
                        try {

                            String nik = usr.get(0).getEmpNIK();


                            //user berperan sebagai atasan langsung
                            if(usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanLangsung())){
                                friendName = kpiHeader.getAtasanTakLangsung();
                                friendNIK = kpiHeader.getNIKAtasanTakLangsung();

                            }
                            //user berperan sebagai atasan tak langsung
                            if(usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanTakLangsung())){
                                friendName = kpiHeader.getAtasanLangsung();
                                friendNIK = kpiHeader.getNIKAtasanLangsung();
                            }
                            // user perberan sebagai karyawan
                            if(!usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanTakLangsung()) && !usr.get(0).getEmpNIK().equals(kpiHeader.getNIKAtasanLangsung())){
                                friendName = kpiHeader.getAtasanLangsung();
                                friendNIK = kpiHeader.getNIKAtasanLangsung();
                            }


                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.hasChild(nik+"-"+ friendNIK+"-YEAR")) {
                                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                        routingHomeDetailInterface.routingChat(nik+"-"+ friendNIK+"-YEAR",friendName, friendNIK,context);

                                    }
                                    else if(dataSnapshot.hasChild(friendNIK +"-"+nik+"-YEAR")){
                                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                        routingHomeDetailInterface.routingChat(friendNIK +"-"+nik+"-YEAR",friendName,friendNIK,context);
                                    }
                                    else{
                                        Map<String,Object> map = new HashMap<String,Object>();
                                        map.put(nik+"-"+ friendNIK+"-YEAR",""); //diganti room name random uniqueID
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


                    }

                });



                //if the position is equals to the item position which is to be expanded
                if(questions.get(position-1).getKPIcategory().equals("Kuantitatif")) {
                    if (currentPosition == position) {
                        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.anim_drop_down_recycler);
                        Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.anim_drop_up_recycler);

                        //creating an animation

                    if(isVisible == false){


//                        //toggling visibility
                        ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.VISIBLE);

                        ((ItemViewHolder) holder).imgExpand.setImageResource(R.drawable.expand_up);

                        //adding sliding effect
                        ((ItemViewHolder) holder).lnKuantitatif.startAnimation(slideDown);
                        isVisible = true;
                    }
                    else if(isVisible == true){

                            ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.GONE);
                        ((ItemViewHolder) holder).lnKuantitatif.startAnimation(slideUp);
                            isVisible=false;
                    }
                    }

                    ((ItemViewHolder) holder).btnMore.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).btnMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //getting the position of the item to expand_down it

                            currentPosition = position;
                            //reloding the list

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

//                if(kpiHeader.getSemester().equals("1")) {
                    KPIQuestions current = questions.get(position - 1);
                    //KPIQuestions current2 = questions2.get(position-1);
                    itemViewHolder.setQuestion(current.getKPIDesc());
                    itemViewHolder.setNumber(position);
                    itemViewHolder.setOptions(current, position - 1);
                    itemViewHolder.setBobot(current.getBobot());
                    itemViewHolder.setCategory(current.getKPIcategory(), current.getPerspective(), position - 1);
                    itemViewHolder.setUpload(current.getKPIcategory(), current, position - 1);
                    itemViewHolder.setAnswer(current, position - 1);
//                }
//                else if(kpiHeader.getSemester().equals("2")) {
//                    KPIQuestions current2 = questions2.get(position - 1);
//                    //KPIQuestions current2 = questions2.get(position-1);
//                    itemViewHolder.setQuestion(current2.getKPIDesc());
//                    itemViewHolder.setNumber(position);
//                    itemViewHolder.setOptions(current2, position - 1);
//                    itemViewHolder.setBobot(current2.getBobot());
//                    itemViewHolder.setCategory(current2.getKPIcategory(), current2.getPerspective(), position - 1);
//                    itemViewHolder.setUpload(current2.getKPIcategory(), current2, position - 1);
//                    itemViewHolder.setAnswer(current2, position - 1);
//                }
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
        TextView tahun,namaKaryawan;
        TextView semester,dept;
        TextView status,nik;
        ImageView editBtn;
        LinearLayout lnNama,lnNIK,lnDept,lnLocation, lnCompany;
        TextView tvAtasan1,tvAtasan2,tvStatus1,tvStatus2, tvCompany;


        public HeaderViewHolder(View view) {
            super(view);
//            tahun = (TextView) view.findViewById(R.id.tvTahun);
//            semester = (TextView) view.findViewById(R.id.tvSemester);
            status = (TextView) view.findViewById(R.id.tvStatus);
            dept = (TextView) view.findViewById(R.id.tvDept);
            nik = (TextView) view.findViewById(R.id.tvNIK);
            namaKaryawan = (TextView) view.findViewById(R.id.tvEmpName);
            lnDept = (LinearLayout) view.findViewById(R.id.lnDept);
            lnNama = (LinearLayout) view.findViewById(R.id.lnNama);
            lnCompany = (LinearLayout) view.findViewById(R.id.lnCompany);
            lnLocation = (LinearLayout) view.findViewById(R.id.lnLocation);
            lnNIK = (LinearLayout) view.findViewById(R.id.lnNIK);
            editBtn = (ImageView) view.findViewById(R.id.editBtn);
            tvCompany = (TextView) itemView.findViewById(R.id.tvCompany);

            if(!type.equals("Approve")){
                editBtn.setVisibility(View.GONE);
            }

            tvAtasan1 = (TextView) itemView.findViewById(R.id.tv_approver_name_1);
            tvAtasan2 =(TextView) itemView.findViewById(R.id.tv_approver_name_2);
            tvStatus1 =(TextView) itemView.findViewById(R.id.tv_approver_status_1);
            tvStatus2 =(TextView) itemView.findViewById(R.id.tv_approver_status_2);

        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        Button footerButton, btnApprove;
        EditText edtKelebihan, edtkekurangan, edtRencanaTindaklanjut;
        TextView footertext;
        LinearLayout lnKualitatif;
        ViewPager viewPager;

        public FooterViewHolder(View view) {
            super(view);

            footerButton = (Button) view.findViewById(R.id.btnSubmitKPI);
            btnApprove = (Button) view.findViewById(R.id.btnApproveKPI);
            footertext = (TextView) view.findViewById(R.id.tvFooterTitle);
            edtkekurangan = view.findViewById(R.id.edtKekurangan);
            edtKelebihan = view.findViewById(R.id.edtKelebihan);
            edtRencanaTindaklanjut = view.findViewById(R.id.edtRencanaTindaklanjut);

            lnKualitatif = view.findViewById(R.id.lnKualitatif);
            if(type.equals("Approve") && isEditable.equals("N")){

                edtRencanaTindaklanjut.setEnabled(false);
                edtKelebihan.setEnabled(false);
                edtkekurangan.setEnabled(false);
            }
            else{
                edtRencanaTindaklanjut.setEnabled(true);
                edtKelebihan.setEnabled(true);
                edtkekurangan.setEnabled(true);
            }

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
        private LinearLayout linearLayoutContainer,linearLayoutContainer2;
        private TextView textViewQuestion, textViewNumber, tvIndicator,tvBobot, tvCategory, tvPerspective;
        private RatingBar rating;
        private ImageButton imgBtnHint, imgExpand;
        private EditText  edtPendukung,edtPenghambat,edtCatatan;
        private View hrLine;
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
            hrLine = itemView.findViewById(R.id.HrLine);
            tvPerspective = itemView.findViewById(R.id.tvPerspective);
            recyclerViewImg = itemView.findViewById(R.id.recycler_view_image_upload);
            imgExpand = itemView.findViewById(R.id.imgExpand);

            rating.setNumStars(5);
            //rating.setRating(numbRadio);
            rating.setStepSize(1);
            if(type.equals("Approve") && isEditable.equals("N")){
                rating.setIsIndicator(true);
                edtCatatan.setEnabled(false);
                edtPendukung.setEnabled(false);
                edtPenghambat.setEnabled(false);
                btnUpload.setEnabled(false);
//                btnUpload.setVisibility(View.GONE);
            }
            else if(type.equals("Approve") && isEditable.equals("Y")){
                rating.setIsIndicator(false);
                edtCatatan.setEnabled(true);
                edtPendukung.setEnabled(true);
                edtPenghambat.setEnabled(true);
                btnUpload.setEnabled(true);
//                btnUpload.setVisibility(View.GONE);
            }

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
                    listener.onEvent(position, questions);
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
                            tvIndicator.setText("BAD");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.red));
                            break;
                        case 2:
                            tvIndicator.setText("BELOW");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.orange));
                            break;
                        case 3:
                            tvIndicator.setText("ACHIEVE");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.yellow));
                            break;
                        case 4:
                            tvIndicator.setText("ABOVE");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.blue));
                            break;
                        case 5:
                            tvIndicator.setText("EXCELLENT");
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
//                    if(kpiHeader.getSemester().equals("2")){
//                        KPIQuestions que2 = questions2.get(position);
//                        que2.setAnswered(true);
//                        que2.setCheckedId((int)rating);
//                        test2 = Integer.toString((int) rating);
//                        Toast.makeText(context,test,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context,test2,Toast.LENGTH_SHORT).show();

//                        Toast.makeText(context,Integer.toString(position)+" "+Integer.toString(questions2.get(position).getCheckedId()),Toast.LENGTH_LONG).show();
//                        Toast.makeText(context,Integer.toString(position)+" "+Integer.toString(questions.get(position).getCheckedId()),Toast.LENGTH_LONG).show();

//                    }
//                    if(kpiHeader.getSemester().equals("1")) {
                        KPIQuestions que = questions.get(position);
                        que.setAnswered(true);
                        que.setCheckedId((int) rating);

                        if(kpiHeader.getSemester().equals("1")){
                            listener.setQuestion(kpiHeader,1);
                        }
                        else{
                            listener.setQuestion(kpiHeader,2);
                        }
//                        test = Integer.toString((int) rating);
//                        Toast.makeText(context,test,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context,test2,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context,Integer.toString(position)+" "+Integer.toString(questions2.get(position).getCheckedId()),Toast.LENGTH_LONG).show();
//                        Toast.makeText(context,Integer.toString(position)+" "+Integer.toString(questions.get(position).getCheckedId()),Toast.LENGTH_LONG).show();
//                    }


//                    Toast.makeText(context,questions.get(position+7).getCheckedId(),Toast.LENGTH_SHORT).show();
                    switch((int)rating){
                        case 1:
                            tvIndicator.setText("BAD");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.red));
                            break;
                        case 2:
                            tvIndicator.setText("BELOW");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.orange));
                            break;
                        case 3:
                            tvIndicator.setText("ACHIEVE");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.yellow));
                            break;
                        case 4:
                            tvIndicator.setText("ABOVE");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.blue));
                            break;
                        case 5:
                            tvIndicator.setText("EXCELLENT");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.green));
                            break;
                    }
                }
            });
        }
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

}
