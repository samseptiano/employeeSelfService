package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.TotalFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class KPIApproveListAdapterTahunan extends RecyclerView.Adapter<KPIApproveListAdapterTahunan.MyViewHolder> {

    private List<KPIApproveList> KPIList;
    private Context context;
    private Activity activity;
    private ArrayList<KPIApproveList> KPIListFilter = new ArrayList<>();
    EventListener listener;

    String tahun,direktorat,site;

    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
    ArrayList<UserRealmModel> usr;

    String kualitatif = "";
    String kuantitatif="";
    String friendName = "";
    String foto = "";
    String friendNIK = "";
    String friendBranchName = "";
    String friendDept = "";
    String friendJobTitle = "";
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    public interface EventListener {
        void onEvent(int position, String Tahun, String Direktorat, String Site, String Semester);
        String getTahun();
        String getDirektorat();
        String getSite();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvNIK, tvDept, tvjobtitle, tvScore, tvEmpPeriode;
        ImageView imgStatus;
        CircleImageView empPhoto;
        RatingBar rating;
        LinearLayout lnApprove;
        ImageButton imgBtnChat;
        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_employeeName);
            tvNIK = (TextView) view.findViewById(R.id.tv_nik);
            tvDept = (TextView) view.findViewById(R.id.tv_dept);
            tvjobtitle = (TextView) view.findViewById(R.id.tv_job_title);
            tvScore = (TextView) view.findViewById(R.id.tv_score);
            tvEmpPeriode = (TextView) view.findViewById(R.id.tv_empPeriode);
            imgStatus = (ImageView) view.findViewById(R.id.imgStatus);
            empPhoto = view.findViewById(R.id.imgKPIApprove);
            lnApprove = (LinearLayout) view.findViewById(R.id.lnKPIApprove);
            rating = view.findViewById(R.id.ratingScore);
            imgBtnChat = view.findViewById(R.id.imgBtnChat);
        }
    }

    public KPIApproveListAdapterTahunan(List<KPIApproveList> KPIList, Context context, Activity activity, EventListener listener) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.listener = listener;
        this.KPIListFilter.addAll(KPIList);
        tahun = listener.getTahun();
        direktorat = listener.getDirektorat();
        site = listener.getSite();
        usr = userRealmHelper.findAllArticle();
    }

    @Override
    public KPIApproveListAdapterTahunan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kpi_approve_item_list_tahunan, parent, false);

        return new KPIApproveListAdapterTahunan.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KPIApproveListAdapterTahunan.MyViewHolder holder, int position) {
        try {
            KPIApproveList klist = KPIList.get(position);
            UserList userList = new UserList();
            userList.setEmpNiK(klist.getNIK());
            userList.setOrgName(klist.getDept());
            userList.setEmpName(klist.getEmpName());
            userList.setJobTitleName(klist.getJobTitle());
            userList.setBranchName(klist.getBranchName());
            userList.setDept(klist.getDept());
            userList.setNamaAtasanLangsung(klist.getNamaAtasan1());
            userList.setNamaAtasanTakLangsung(klist.getNamaAtasan2());
            userList.setNIKAtasanLangsung(klist.getNIKAtasan1());
            userList.setNIKAtasanTakLangsung(klist.getNIKAtasan2());
            userList.setEmpPhoto(klist.getEmpPhoto());
            userList.setJobTitleAtasan1(klist.getJobTitleAtasan1());
            userList.setJobTitleAtasan2(klist.getJobTitleAtasan2());
            userList.setOrgName(klist.getOrgName());
            userList.setTahun(tahun);
            userList.setFotoAtasanLangsung(klist.getFotoAtasan1());
            userList.setFotoAtasanTakLangsung(klist.getFotoAtasan2());

//        Toast.makeText(context,listener.getTahun()+" "+listener.getDirektorat()+" "+listener.getSite(),Toast.LENGTH_SHORT).show();

            userList.setLocationName("EPM - Makassar");
            userList.setCompanyName("PT. Enseval Putera Megatrading Tbk");

            //Toast.makeText(context,klist.getStatus(),Toast.LENGTH_LONG).show();
            if (klist.getNIKAtasan1().equals(usr.get(0).getEmpNIK())) {
                if (klist.getStatus().equals("C")) {
                    holder.imgStatus.setVisibility(View.VISIBLE);
                } else {
                    holder.imgStatus.setVisibility(View.GONE);
                }
            } else if (klist.getNIKAtasan2().equals(usr.get(0).getEmpNIK())) {
                if (klist.getStatus().equals("C")) {
                    holder.imgStatus.setVisibility(View.VISIBLE);
                } else {
                    holder.imgStatus.setVisibility(View.GONE);
                }
            }


//            //================================ foto bawahan =======================
//            try {
//                //Toast.makeText(context,userList.getEmpPhoto(),Toast.LENGTH_LONG).show();
//                byte[] encodeByte = Base64.decode(userList.getEmpPhoto(), Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//
//                Display display = activity.getWindowManager().getDefaultDisplay();
//                Point size = new Point();
//                display.getSize(size);
//
//                if (size.x > 1081) {
//                    int imageWidth = bitmap.getWidth();
//                    int imageHeight = bitmap.getHeight();
//
//                    //Display display = getActivity().getWindowManager().getDefaultDisplay();
//                    //Point size = new Point();
//                    display.getSize(size);
//                    int width = size.x - (size.x / 3);
//                    int height = size.y - (size.y / 3);
//
//                    int newWidth = width; //this method should return the width of device screen.
//                    float scaleFactor = (float) newWidth / (float) imageWidth;
//                    int newHeight = (int) (imageHeight * scaleFactor);
//
//                    bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
//                    //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
//                } else {
//                    int imageWidth = bitmap.getWidth();
//                    int imageHeight = bitmap.getHeight();
//
//                    //Display display = getActivity().getWindowManager().getDefaultDisplay();
//                    //Point size = new Point();
//                    display.getSize(size);
//                    int width = size.x - (size.x / 3);
//                    int height = size.y - (size.y / 3);
//
//                    int newWidth = width; //this method should return the width of device screen.
//                    float scaleFactor = (float) newWidth / (float) imageWidth;
//                    int newHeight = (int) (imageHeight * scaleFactor);
//
//                    bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
//                    //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
//                }
//
//                holder.empPhoto.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.getMessage();
//                //return null;
//            }
//            //===========================================================================
//
//                Glide
//                    .with(context)
//                    .load(userList.getEmpPhoto())
//                    .centerCrop()
//                    .placeholder(R.drawable.imgalt)
//                    .into(holder.empPhoto);

            Picasso.get()
                    .load(userList.getEmpPhoto())
                    .placeholder(R.drawable.user)
                    .error(R.drawable.imgalt)
                    .into(holder.empPhoto);


            holder.imgBtnChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        holder.imgBtnChat.setEnabled(false);

                        List <String> KPIItems = new ArrayList<>();

                        KPIItems.add("Overall Performance");
//                        for(int i=0;i< questions.size();i++){
//                            KPIItems.add(questions.get(i).getKPIDesc());
//                        }

                        String nik = usr.get(0).getEmpNIK();
                        String nama = usr.get(0).getEmpName().replace(".", "");
                        String semester = "1";
                        String tahun =  Integer.toString(Calendar.getInstance().get(Calendar.YEAR));



                        //user berperan sebagai atasan langsung
                        if(usr.get(0).getEmpNIK().equals(klist.getNIKAtasan1())){
//                                friendName = kpiHeader.getAtasanTakLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanTakLangsung();
                            friendName = klist.getEmpName().replace(".", "");
                            friendNIK = klist.getNIK();
                            friendBranchName = klist.getBranchName();
                            friendDept = klist.getDept();
                            friendJobTitle = klist.getJobTitle();
                            foto = klist.getEmpPhoto();



                        }
                        //user berperan sebagai atasan tak langsung
                        if(usr.get(0).getEmpNIK().equals(klist.getNIKAtasan2())){
//                                friendName = kpiHeader.getAtasanLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanLangsung();
                            friendName = klist.getEmpName().replace(".", "");
                            friendNIK = klist.getNIK();
                            friendBranchName = klist.getBranchName();
                            friendDept = klist.getDept();
                            friendJobTitle = klist.getJobTitle();
                            foto = klist.getEmpPhoto();

                        }
                        // user berperan sebagai karyawan
                        if(!usr.get(0).getEmpNIK().equals(klist.getNIKAtasan1()) && !usr.get(0).getEmpNIK().equals(klist.getNIKAtasan2())){
//                      friendName = kpiHeader.getAtasanLangsung();
//                      friendNIK = kpiHeader.getNIKAtasanLangsung();
                            friendName = usr.get(0).getNamaAtasanLangsung().replace(".", "");
                            friendNIK =  usr.get(0).getNikAtasanLangsung();
                            friendBranchName = usr.get(0).getBranchName();
                            friendDept = usr.get(0).getDept();
                            friendJobTitle = usr.get(0).getJobTitleName();
                            foto = klist.getFotoAtasan1();

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
                        String nikLawan = klist.getNIKAtasan1();

                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put(nik+"-"+ friendNIK+"-"+usr.get(0).getEmpName()+"-"+friendName+"-YEAR",""); //diganti room name random uniqueID
                        root.updateChildren(map);

                    }
                }
            });

            holder.tvName.setText(klist.getEmpName());
            holder.tvScore.setText("Score: " + klist.getScore());

            holder.tvjobtitle.setText(klist.getJobTitle());
            try {
                holder.rating.setRating((Float.parseFloat(klist.getStar())));
            } catch (Exception e) {

            }
//            holder.tvDept.setText(klist.getOrgName() + " - " + klist.getJobStatus());
            holder.tvDept.setText(klist.getOrgName());

//        holder.tvDept.setText(klist.getDept()+" - "+klist.getBranchName()+" - "+klist.getJobStatus());
            holder.tvNIK.setText(klist.getNIK());

            holder.tvEmpPeriode.setText(klist.getDateStart().split(" ")[0] + " - " + klist.getDateEnd());
            holder.lnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingKPI("Approve PA tahunan", context, userList, "Approve",tahun);
                }
            });
        }
        catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return KPIList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        KPIList.clear();
        if (charText.length() == 0) {
            KPIList.addAll(KPIListFilter);
        }
        else
        {
            for (KPIApproveList wp : KPIListFilter) {
                if (wp.getEmpName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getNIK().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStatus1().toLowerCase(Locale.getDefault()).contains(charText)) {
                    KPIList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}