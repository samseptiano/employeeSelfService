package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
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
                headerHolder.status.setText(kpiHeader.getStatus());
                headerHolder.location.setText(kpiHeader.getLocationName());
                headerHolder.namaKaryawan.setText(kpiHeader.getEmpName());
                headerHolder.nik.setText(kpiHeader.getNIK());
                headerHolder.dept.setText(kpiHeader.getDept());
                headerHolder.tvAtasan1.setText(kpiHeader.getAtasanLangsung());
                headerHolder.tvAtasan2.setText(kpiHeader.getAtasanTakLangsung());
                headerHolder.tvStatus1.setText(kpiHeader.getStatus1());
                headerHolder.tvStatus2.setText(kpiHeader.getStatus2());
                headerHolder.tvCompany.setText(kpiHeader.getCompany());

                //if(!type.equals("Approve")){
//                    headerHolder.tvAtasan2.setVisibility(View.GONE);
//                    headerHolder.tvStatus2.setVisibility(View.GONE);
//                    headerHolder.imgApprover2.setVisibility(View.GONE);
//                    headerHolder.tvLevel2.setVisibility(View.GONE);
//                    headerHolder.lnApprover2.setVisibility(View.GONE);
               // }



                try {
                    byte [] encodeByte=Base64.decode(kpiHeader.getFotoBawahan(),Base64.DEFAULT);
                    Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                    Display display = activity.getWindowManager().getDefaultDisplay();
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

                    ((HeaderViewHolder) holder).imgbawahan.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                    //return null;
                }

                //================================ foto atasan1 =======================
                try {
                    byte [] encodeByte= Base64.decode(kpiHeader.getFotoAtasanLangsung(),Base64.DEFAULT);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                    Display display = activity.getWindowManager().getDefaultDisplay();
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

                    ((HeaderViewHolder) holder).imgApprover1.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                    //return null;
                }
                //===========================================================================

                //================================ foto atasan2 =======================
                try {
                    byte [] encodeByte= Base64.decode(kpiHeader.getFotoAtasanTakLangsung(),Base64.DEFAULT);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                    Display display = activity.getWindowManager().getDefaultDisplay();
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

                    ((HeaderViewHolder) holder).imgApprover2.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                    //return null;
                }
                //===========================================================================


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
                    ((HeaderViewHolder) holder).status.setVisibility(View.GONE);
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
                                listener.redirect(2);
                                    listener.getQuestionSmt();
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
                    ((ItemViewHolder) holder).btnChat .setVisibility(View.GONE);
                }
                else if(questions.get(position-1).getKPIcategory().equals("Kuantitatif")){
                    ((ItemViewHolder) holder).btnMore.setVisibility(View.VISIBLE);
                }


                ((ItemViewHolder) holder).btnChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //room chat sudah ada
                        try {

                            String nik = usr.get(0).getEmpNIK();
                            String nama = usr.get(0).getEmpName();


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

                                    if (dataSnapshot.hasChild(nik+"-"+ friendNIK+"-"+friendName+"-YEAR")) {
                                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                        routingHomeDetailInterface.routingChat(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR",friendName, friendNIK,context);

                                    }
                                    else if(dataSnapshot.hasChild(friendNIK+"-"+nik+"-"+friendName+"-YEAR")){
                                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                        routingHomeDetailInterface.routingChat(friendNIK +"-"+nik+"-"+nama+"-"+friendName+"-YEAR",friendName,friendNIK,context);
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
        TextView tahun,namaKaryawan;
        TextView semester,dept;
        TextView status,nik, location;
        ImageView editBtn, imgApprover2, imgApprover1,imgbawahan;
        LinearLayout lnNama,lnNIK,lnDept,lnLocation, lnCompany, lnApprover1,lnApprover2;
        TextView tvAtasan1,tvAtasan2,tvStatus1,tvStatus2, tvCompany, tvLevel1,tvLevel2;


        public HeaderViewHolder(View view) {
            super(view);
//            tahun = (TextView) view.findViewById(R.id.tvTahun);
//            semester = (TextView) view.findViewById(R.id.tvSemester);
            status = (TextView) view.findViewById(R.id.tvStatus);
            dept = (TextView) view.findViewById(R.id.tvDept);
            nik = (TextView) view.findViewById(R.id.tvNIK);
            namaKaryawan = (TextView) view.findViewById(R.id.tvEmpName);
            location = (TextView) view.findViewById(R.id.tvLocation);
            lnDept = (LinearLayout) view.findViewById(R.id.lnDept);
            lnNama = (LinearLayout) view.findViewById(R.id.lnNama);
            lnCompany = (LinearLayout) view.findViewById(R.id.lnCompany);
            lnLocation = (LinearLayout) view.findViewById(R.id.lnLocation);
            lnApprover1 = (LinearLayout) view.findViewById(R.id.lnApprover1);
            lnApprover2 = (LinearLayout) view.findViewById(R.id.lnApprover2);
            lnNIK = (LinearLayout) view.findViewById(R.id.lnNIK);
            editBtn = (ImageView) view.findViewById(R.id.editBtn);
            tvCompany = (TextView) itemView.findViewById(R.id.tvCompany);
            imgApprover1 = itemView.findViewById(R.id.img_approver_1);
            imgApprover2 = itemView.findViewById(R.id.img_approver_2);
            tvLevel1 = itemView.findViewById(R.id.tvLevel1);
            tvLevel2 = itemView.findViewById(R.id.tvLevel2);
            imgbawahan = itemView.findViewById(R.id.img_user);

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
