package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIQuestionsPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIUserAnswerListPJ;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.firebase.client.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KPIAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    private static final String TAG = "KPIAdapter";
    private LayoutInflater inflater;
    KPIHeaderPJ kpiHeader;

    protected String friendName = "";
    protected String friendNIK = "";

    private Context context;

    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
    ArrayList<UserRealmModel> usr;

    private static int currentPosition = 0;

    List<ImageUploadModel> aaa = new ArrayList<ImageUploadModel>();

    Activity activity;

    KPIUserAnswerListPJ kpiUserAnswerList;

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    static boolean isVisible = false;

    String id,type;
    String Jenis = "";


    private List<KPIQuestionsPJ> questions;



    private LinearLayout linearLayoutContainer;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;


    EventListener listener;
    String isEditable;

    public interface EventListener {
        void onEvent(int position, List<KPIQuestionsPJ> questions);
        List <ImageUploadModel> onResult();
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void redirect(int tabPos);
        String isEditables();
        void setEditables(String edit, KPIHeaderPJ kh);
    }

    public KPIAdapter(Activity activity, Context context, KPIHeaderPJ kpiHeader, String id, String type, EventListener listener, String Jenis) {
        this.inflater = LayoutInflater.from(context);
        this.questions = kpiHeader.getKpiQuestionsList();
        this.context = context;
        this.activity=activity;
        this.kpiHeader=kpiHeader;
        this.id=id;
        this.type=type;
        this.listener = listener;
        this.Jenis = Jenis;
        isEditable = listener.isEditables();
        usr = userRealmHelper.findAllArticle();


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_list, parent, false);
            return new KPIAdapter.ItemViewHolder(itemView);
        }else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_header_pj, parent, false);
            return new KPIAdapter.HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_footer_pj, parent, false);
            return new KPIAdapter.FooterViewHolder(itemView);
        } else return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof KPIAdapter.HeaderViewHolder) {
                KPIAdapter.HeaderViewHolder headerHolder = (KPIAdapter.HeaderViewHolder) holder;
                headerHolder.status.setText(kpiHeader.getStatus());
                headerHolder.namaKaryawan.setText(kpiHeader.getEmpName());
                headerHolder.nik.setText(kpiHeader.getNIK());
                headerHolder.dept.setText(kpiHeader.getDept());
                headerHolder.tvAtasan1.setText(kpiHeader.getAtasanLangsung());
                headerHolder.tvAtasan2.setText(kpiHeader.getAtasanTakLangsung());
                headerHolder.tvStatus1.setText(kpiHeader.getStatus1());
                headerHolder.tvStatus2.setText(kpiHeader.getStatus2());
                headerHolder.tvPeriode.setText(kpiHeader.getPeriodeAwal()+" s/d "+kpiHeader.getPeriodeAkhir());
                headerHolder.tvLocation.setText(kpiHeader.getLocationName());

                if(kpiHeader.getStatus().equals("Not Approved")){
                    ((HeaderViewHolder) holder).status.setTextColor(Color.RED);
                }
                else if(kpiHeader.getStatus().equals("Approved")){
                    ((HeaderViewHolder) holder).status.setTextColor(Color.GREEN);
                }

                if(kpiHeader.getStatus1().equals("Not Approved")){
                    ((HeaderViewHolder) holder).tvStatus1.setTextColor(Color.RED);
                }
                else if(kpiHeader.getStatus1().equals("Approved")){
                    ((HeaderViewHolder) holder).tvStatus1.setTextColor(Color.GREEN);
                }

                if(kpiHeader.getStatus2().equals("Not Approved")){
                    ((HeaderViewHolder) holder).tvStatus2.setTextColor(Color.RED);
                }
                else if(kpiHeader.getStatus2().equals("Approved")){
                    ((HeaderViewHolder) holder).tvStatus2.setTextColor(Color.GREEN);
                }


                if(id.length()== 0 && !type.equals("Approve")){
                    ((HeaderViewHolder) holder).dept.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).namaKaryawan.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).nik.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnDept.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnNIK.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnNama.setVisibility(View.GONE);
                }

                if(listener.isEditables().equals("N")){
                    isEditable="N";
                }
                else if(listener.isEditables().equals("Y")){
                    isEditable = "Y";
                }



            } else if (holder instanceof KPIAdapter.FooterViewHolder) {
                final KPIAdapter.FooterViewHolder footerHolder = (KPIAdapter.FooterViewHolder) holder;

                if(listener.isEditables().equals("N")){
                    isEditable="N";
                }
                else if(listener.isEditables().equals("Y")){
                    isEditable = "Y";
                }


                final int requestCode = 20;

                if(id.length()>0 && type.equals("Approve")){
                    ((FooterViewHolder) holder).footerButton.setVisibility(View.GONE);
                }
                else{
                    ((FooterViewHolder) holder).btnApprove.setVisibility(View.GONE);
                }

                if(isEditable.equals("N") && type.equals("Approve")){

//                    footerHolder.edtRencanaTindaklanjut.setEnabled(false);
//                    footerHolder.edtKelebihan.setEnabled(false);
//                    footerHolder.edtkekurangan.setEnabled(false);
                }
                else if(isEditable.equals("Y") && type.equals("Approve")){
//                    footerHolder.edtRencanaTindaklanjut.setEnabled(true);
//                    footerHolder.edtKelebihan.setEnabled(true);
//                    footerHolder.edtkekurangan.setEnabled(true);
                }
                else{
//                    footerHolder.edtRencanaTindaklanjut.setEnabled(true);
//                    footerHolder.edtKelebihan.setEnabled(true);
//                    footerHolder.edtkekurangan.setEnabled(true);
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
                                submitKPI(kpiHeader.getNIK(), footerHolder);
                            }
                        }
                    }
                });

                footerHolder.footerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!type.equals("Approve")){
                            if(kpiHeader.getSemester().equals("1")) {
                                submitKPI(NIK,footerHolder);
                            }

                            // code block
                            //RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                            //routingHomeDetailInterface.routingKPI("Fill PA",context,"","",2);

                        }
                    }
                });
            } else if (holder instanceof KPIAdapter.ItemViewHolder) {
                holder.setIsRecyclable(false);

                if(listener.isEditables().equals("N")){
                    isEditable="N";
                }
                else if(listener.isEditables().equals("Y")){
                    isEditable = "Y";
                }

                ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.GONE);
                ((ItemViewHolder) holder).imgExpand.setImageResource(R.drawable.expand_down);

                ((ItemViewHolder) holder).btnChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //room chat sudah ada
                        try {

                            String nik = usr.get(0).getEmpNIK();


                            //user berperan sebagai atasan langsung
                            if(usr.get(0).getEmpNIK().equals(kpiHeader.getNIKatasanLangsung())){
                                friendName = kpiHeader.getAtasanTakLangsung();
                                friendNIK = kpiHeader.getNIKatasanTakLansung();

                            }
                            //user berperan sebagai atasan tak langsung
                            if(usr.get(0).getEmpNIK().equals(kpiHeader.getNIKatasanTakLansung())){
                                friendName = kpiHeader.getAtasanLangsung();
                                friendNIK = kpiHeader.getNIKatasanLangsung();
                            }


                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.hasChild(nik+"-"+ friendNIK)) {
                                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                        routingHomeDetailInterface.routingChat(nik+"-"+ friendNIK,friendName,friendNIK,context);

                                    }
                                    else if(dataSnapshot.hasChild(friendNIK +"-"+nik)){
                                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                        routingHomeDetailInterface.routingChat(friendNIK +"-"+nik,friendName,friendNIK,context);
                                    }
                                    else{
                                        Map<String,Object> map = new HashMap<String,Object>();
                                        map.put(nik+"-"+ friendNIK,""); //diganti room name random uniqueID
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
                            String nikLawan = kpiHeader.getNIKatasanLangsung();

                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put(nik+"-"+nikLawan,""); //diganti room name random uniqueID
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

                final KPIAdapter.ItemViewHolder itemViewHolder = (KPIAdapter.ItemViewHolder) holder;
                KPIQuestionsPJ current = questions.get(position-1);
                itemViewHolder.setQuestion(current.getKPIDesc());
                itemViewHolder.setNumber(position);
                itemViewHolder.setOptions(current, position-1);
                itemViewHolder.setBobot(current.getBobot());
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
        TextView namaKaryawan;
        TextView dept;
        TextView status,nik;
        LinearLayout lnNama,lnNIK,lnDept;
        TextView tvAtasan1,tvAtasan2,tvStatus2,tvStatus1, tvPeriode, tvLocation, position;

        public HeaderViewHolder(View view) {
            super(view);
            status = (TextView) view.findViewById(R.id.tvStatus);
            dept = (TextView) view.findViewById(R.id.tvDept);
            nik = (TextView) view.findViewById(R.id.tvNIK);
            namaKaryawan = (TextView) view.findViewById(R.id.tvEmpName);
            lnDept = (LinearLayout) view.findViewById(R.id.lnDept);
            lnNama = (LinearLayout) view.findViewById(R.id.lnNama);
            lnNIK = (LinearLayout) view.findViewById(R.id.lnNIK);
            tvAtasan1 = (TextView) itemView.findViewById(R.id.tv_approver_name_1);
            tvAtasan2 =(TextView) itemView.findViewById(R.id.tv_approver_name_2);
            tvStatus1 =(TextView) itemView.findViewById(R.id.tv_approver_status_1);
            tvStatus2 =(TextView) itemView.findViewById(R.id.tv_approver_status_2);
            tvPeriode =(TextView) itemView.findViewById(R.id.tvPeriode);
            tvLocation =(TextView) itemView.findViewById(R.id.tvLocation);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        Button footerButton, btnApprove;TextView footertext;
        LinearLayout lnKualitatif;
        ViewPager viewPager;

        public FooterViewHolder(View view) {
            super(view);

            footerButton = (Button) view.findViewById(R.id.btnSubmitKPI);
            btnApprove = (Button) view.findViewById(R.id.btnApproveKPI);
            footertext = (TextView) view.findViewById(R.id.tvFooterTitle);


            lnKualitatif = view.findViewById(R.id.lnKualitatif);
        }
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutContainer,linearLayoutContainer2;
        private TextView textViewQuestion, textViewNumber, tvIndicator,tvBobot, tvCategory, tvPerspective;
        private RatingBar rating;
        private ImageButton imgBtnHint, imgExpand;
        private EditText  edtEvidence;
        private View hrLine;
        Button btnUpload, btnMore;
         ImageButton btnChat;
        LinearLayout lnKuantitatif,lnKPIName;
        RecyclerView recyclerViewImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            btnUpload = itemView.findViewById(R.id.btnUploadImage);
            btnMore = itemView.findViewById(R.id.btnMore);
            btnChat = itemView.findViewById(R.id.btnChat);
            lnKuantitatif = itemView.findViewById(R.id.lnKuantitatif);
           linearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linear_layout_container);
            lnKPIName = (LinearLayout) itemView.findViewById(R.id.lnKPIName);
            linearLayoutContainer2 = (LinearLayout) itemView.findViewById(R.id.linear_layout_container2);
            textViewQuestion = (TextView) itemView.findViewById(R.id.tvKPIDesc);
            textViewNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            tvIndicator = (TextView) itemView.findViewById(R.id.tvRatingDesc);
            tvBobot = (TextView) itemView.findViewById(R.id.tvBobot);




            imgBtnHint = (ImageButton) itemView.findViewById(R.id.imgBtnHint);
            edtEvidence = itemView.findViewById(R.id.edtEvidence);
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
                edtEvidence.setEnabled(false);

//                btnUpload.setEnabled(false);
//                btnUpload.setVisibility(View.GONE);
            }
            else if(type.equals("Approve") && isEditable.equals("Y")){
                rating.setIsIndicator(false);
                edtEvidence.setEnabled(true);

//                btnUpload.setEnabled(false);
//                btnUpload.setVisibility(View.GONE);
            }

        }

        public void setUpload(String category, KPIQuestionsPJ question, int position){

           // if(isEditable.equals("N")){
                //recyclerViewImg.setEnabled(false);
            //}

            if(question.getKPIcategory().equals("Kualitatif")){
                imgExpand.setVisibility(View.GONE );
            }

            KPIQuestionsPJ que = questions.get(position);
            List<String> tempImg = new ArrayList<>();

            if(category.equals("Kualitatif")){
                btnUpload.setVisibility(View.GONE);
                edtEvidence.setVisibility(View.GONE);
                }

            if(question.getPhotoCapture()!=null) {
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 6);
                recyclerViewImg.setLayoutManager(mLayoutManager);
                KPIImageUploadAdapter adapter = new KPIImageUploadAdapter(question.getPhotoCapture(), context, activity, isEditable);
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

        public void setAnswer(KPIQuestionsPJ question, int position) {

            if(question.isAnswerenEvidence()) {
                edtEvidence.setText(question.getEvidence());

            } else {
                edtEvidence.setText("");
            }

            edtEvidence.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        KPIQuestionsPJ que = questions.get(position);
                        if(edtEvidence.getText().toString().length()>0) {
                            que.setAnswerenEvidence(true);
                            que.setEvidence(edtEvidence.getText().toString());
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

        public void setOptions(KPIQuestionsPJ question, int position) {
//

            Log.e(TAG, position + " :setOptions: " + question.toString());

                                                                                                                                         if(question.isAnswered()) {
                rating.setRating(question.getCheckedId());
                switch(question.getCheckedId()){
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

            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    KPIQuestionsPJ que = questions.get(position);
                    que.setAnswered(true);
                    que.setCheckedId((int)rating);
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
        RecyclerView.Adapter kpiAdapter = new KPIHintAdapter(hint, context, activity);
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
        kpiUserAnswerList = new KPIUserAnswerListPJ();
        List<String> tempImg = new ArrayList<>();
        int i = 0;

        if(listener.onResult().size()>0) {
            for (i = 0; i < aaa.size(); i++) {
                tempImg.add(aaa.get(i).getImgString());
            }
        }

        kpiUserAnswerList.setDept(kpiHeader.getDept());
        kpiUserAnswerList.setSemester(kpiHeader.getSemester());
        kpiUserAnswerList.setStatus(kpiHeader.getStatus());
        kpiUserAnswerList.setEmpNIK(NIK);
        kpiUserAnswerList.setKPIType(Jenis);
        kpiUserAnswerList.setKpiUserAnswerList(questions);

        //kpiUserAnswerList.setImgCapture(tempImg);


        Toast.makeText(context,"Submitted!",Toast.LENGTH_LONG).show();

        listener.setEditables("N", kpiHeader);
    }

}
