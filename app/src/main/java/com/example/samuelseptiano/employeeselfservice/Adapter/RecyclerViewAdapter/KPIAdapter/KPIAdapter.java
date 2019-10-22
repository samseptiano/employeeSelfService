package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;

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

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHint;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIQuestionsPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIQuestionsPostPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIUserAnswerListPJ;
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

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class KPIAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    private static final String TAG = "KPIAdapter";
    private LayoutInflater inflater;
    KPIHeaderPJ kpiHeader;

    protected String friendName = "";
    protected String friendNIK = "";

    String xxx="";

    private Context context;

    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
    ArrayList<UserRealmModel> usr;

    public static int currentPosition;

    List<ImageUploadModel> aaa = new ArrayList<ImageUploadModel>();

    Activity activity;

    KPIUserAnswerListPJ kpiUserAnswerList;

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    static boolean isVisible = true;

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
        void getFileEvidences(int position, List<ImageUploadModel> imgList);
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

        currentPosition=-1;


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
                headerHolder.positionName.setText(kpiHeader.getPositionName());
                headerHolder.tvAtasan1.setText(kpiHeader.getAtasanLangsung());
                headerHolder.tvAtasan2.setText(kpiHeader.getAtasanTakLangsung());
                headerHolder.tvPeriode.setText(kpiHeader.getPeriodeAwal()+" s/d "+kpiHeader.getPeriodeAkhir());
                headerHolder.tvLocation.setText(kpiHeader.getLocationName());


                if(kpiHeader.getStatus1().equals("0")){
                    ((HeaderViewHolder) holder).tvStatus1.setText("Not Approved");
                    ((HeaderViewHolder) holder).tvStatus1.setTextColor(Color.RED);
                }
                else if(kpiHeader.getStatus1().equals("1")){
                    ((HeaderViewHolder) holder).tvStatus1.setText("Approved");
                    ((HeaderViewHolder) holder).tvStatus1.setTextColor(Color.GREEN);
                }

                if(kpiHeader.getStatus2().equals("0")){
                    ((HeaderViewHolder) holder).tvStatus2.setText("Not Approved");
                    ((HeaderViewHolder) holder).tvStatus2.setTextColor(Color.RED);
                }
                else if(kpiHeader.getStatus2().equals("1")){
                    ((HeaderViewHolder) holder).tvStatus2.setText("Approved");
                    ((HeaderViewHolder) holder).tvStatus2.setTextColor(Color.GREEN);
                }


                if(id.length()== 0 && !type.equals("Approve")){
                    ((HeaderViewHolder) holder).positionName.setVisibility(View.GONE);
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


                //================================ foto bawahan =======================
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

                    ((HeaderViewHolder) holder).imgBawahan.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                    //return null;
                }
                //===========================================================================

                //================================ foto atasan 1 =======================
                try {
                    byte [] encodeByte=Base64.decode(kpiHeader.getFotoAtasanLangsung(),Base64.DEFAULT);
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

                    ((HeaderViewHolder) holder).imgApprover1.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                    //return null;
                }
                //===========================================================================
                //============================== foto atasan 2 ==============================
                try {
                    byte [] encodeByte=Base64.decode(kpiHeader.getFotoAtasanTakLangsung(),Base64.DEFAULT);
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

                    ((HeaderViewHolder) holder).imgApprover2.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                    //return null;
                }
                //=============================================================================================



            }
            else if (holder instanceof KPIAdapter.FooterViewHolder) {
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
            }
            else if (holder instanceof KPIAdapter.ItemViewHolder) {
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

//                        //room chat sudah ada
                        try {

                            String nik = usr.get(0).getEmpNIK();
                            String nama = usr.get(0).getEmpName();


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
                                    //Toast.makeText(context,friendName+friendNIK+nik+nama,Toast.LENGTH_LONG).show();
                                    if (dataSnapshot.hasChild(nik+"-"+ friendNIK+"-"+nama+"-"+friendName)) {
                                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                        routingHomeDetailInterface.routingChat(nik+"-"+ friendNIK+"-"+nama+"-"+friendName,friendName,friendNIK,context);

                                    }
                                    else if(dataSnapshot.hasChild(friendNIK +"-"+nik+"-"+friendName+"-"+nama)){
                                        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                        routingHomeDetailInterface.routingChat(friendNIK +"-"+nik+"-"+nama+"-"+friendName,friendName,friendNIK,context);
                                    }
                                    else{
                                        Map<String,Object> map = new HashMap<String,Object>();
                                        map.put(nik+"-"+ friendNIK+"-"+nama+"-"+friendName,""); //diganti room name random uniqueID
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

                        KPIImageUploadAdapter adapter = new KPIImageUploadAdapter(questions.get(position-1).getPhotoCapture(), context, activity, isEditable, questions.get(position-1).getKpiNo(),questions.get(position).getTransId(),listener,position);
                        ((ItemViewHolder) holder).recyclerViewImg.setAdapter(adapter);

                        //creating an animation

//                        if(questions.get(position-1).getPhotoCapture().size()<1){
//                            ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.GONE);
//                            ((ItemViewHolder) holder).lnKuantitatif.startAnimation(slideUp);
//                        }
//                        else {
                            if (isVisible == false) {


//                        //toggling visibility
                                ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.VISIBLE);

                                ((ItemViewHolder) holder).imgExpand.setImageResource(R.drawable.expand_up);
                                ((ItemViewHolder) holder).btnMore.setText("Less...");

                                //adding sliding effect
                                ((ItemViewHolder) holder).lnKuantitatif.startAnimation(slideDown);
//                        isVisible = true;
                            } else if (isVisible == true) {

//                        notifyDataSetChanged();

                                ((ItemViewHolder) holder).lnKuantitatif.setVisibility(View.GONE);
                                ((ItemViewHolder) holder).lnKuantitatif.startAnimation(slideUp);
//                        isVisible = false;
//                            }
                        }
                    }

                    ((ItemViewHolder) holder).btnMore.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).btnMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                            apiService.getEvidences(questions.get(position).getTransId(),questions.get(position-1).getKpiNo(),"Bearer "+usr.get(0).getToken())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .unsubscribeOn(Schedulers.io())
                                    .subscribe(new Observer<List<ImageUploadModel>>() {


                                        @Override
                                        public void onSubscribe(Disposable d) {
                                        }

                                        @Override
                                        public void onNext(List<ImageUploadModel> lKpiUploadModel) {
                                            questions.get(position-1).setPhotoCapture(lKpiUploadModel);
                                            kpiHeader.getKpiQuestionsList().get(position).setPhotoCapture(lKpiUploadModel);
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {
                                                //Toast.makeText(context,"File Get Successfully", Toast.LENGTH_LONG).show();
                                                    //getting the position of the item to expand_down it
                                                    currentPosition = position;
                                                    //reloding the list

                                                    if(isVisible){
                                                        isVisible=false;
                                                    }
                                                    else{
                                                        isVisible=true;
                                                    }

                                            KPIImageUploadAdapter adapter = new KPIImageUploadAdapter(questions.get(position-1).getPhotoCapture(), context, activity, isEditable, questions.get(position).getKpiNo(),questions.get(position).getTransId(),listener,position);
                                            ((ItemViewHolder) holder).recyclerViewImg.setAdapter(adapter);
                                            notifyDataSetChanged();

                                        }
                                    });



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
        TextView positionName;
        TextView status,nik;
        LinearLayout lnNama,lnNIK,lnDept;
        TextView tvAtasan1,tvAtasan2,tvStatus2,tvStatus1, tvPeriode, tvLocation;
        ImageView imgApprover1,imgApprover2, imgBawahan;


        public HeaderViewHolder(View view) {
            super(view);
            status = (TextView) view.findViewById(R.id.tvStatus);
            positionName = (TextView) view.findViewById(R.id.tvPositionName);
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
            imgApprover1 = itemView.findViewById(R.id.img_approver_1);
            imgApprover2 = itemView.findViewById(R.id.img_approver_2);
            imgBawahan = itemView.findViewById(R.id.imgKPIApprove);

        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        Button footerButton, btnApprove;TextView footertext;
        LinearLayout lnKualitatif, lnProgressFooter;
        ViewPager viewPager;


        public FooterViewHolder(View view) {
            super(view);

            footerButton = (Button) view.findViewById(R.id.btnSubmitKPI);
            btnApprove = (Button) view.findViewById(R.id.btnApproveKPI);
            footertext = (TextView) view.findViewById(R.id.tvFooterTitle);


            lnKualitatif = view.findViewById(R.id.lnKualitatif);
            lnProgressFooter = view.findViewById(R.id.linlaHeaderProgressFooter);
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

                btnUpload.setEnabled(false);
//                btnUpload.setVisibility(View.GONE);
            }
            else if(type.equals("Approve") && isEditable.equals("Y")){
                rating.setIsIndicator(false);
                edtEvidence.setEnabled(true);

                btnUpload.setEnabled(true);
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
                btnMore.setVisibility(View.GONE);
                }

            if(question.getPhotoCapture()!=null) {
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 6);
                recyclerViewImg.setLayoutManager(mLayoutManager);
                KPIImageUploadAdapter adapter = new KPIImageUploadAdapter(question.getPhotoCapture(), context, activity, isEditable, questions.get(position).getKpiNo(),questions.get(position).getTransId(),listener,position);
                recyclerViewImg.setAdapter(adapter);
            }


            btnUpload.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onEvent(position, questions);
                    //Toast.makeText(context, Integer.toString(position),Toast.LENGTH_LONG).show();
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
                    case 0:
                        tvIndicator.setText("");
                        tvIndicator.setTextColor(context.getResources().getColor(R.color.black_overlay));
                        break;
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
                        case 0:
                            tvIndicator.setText("");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.black_overlay));
                            break;
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

    private void showHintKPI(List<KPIHint> hint){
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

        boolean stillEmpty= false;
        List<KPIQuestionsPostPJ> lkpi = new ArrayList<>();

        for(int j=0;j< questions.size();j++ ){
            KPIQuestionsPostPJ kpiQuestionsPostPJ = new KPIQuestionsPostPJ();
            if(questions.get(j).getCheckedId()< 1){
                stillEmpty = true;
            }

            kpiQuestionsPostPJ.setEvidence(questions.get(j).getEvidence());
            kpiQuestionsPostPJ.setGradeScore(Integer.toString(questions.get(j).getCheckedId()));
            kpiQuestionsPostPJ.setKpiCategory(questions.get(j).getKPIcategory());


//            List<ImageUploadModel> tempImg = new ArrayList<>();
//            int i = 0;
//
//            if(listener.onResult().size()>0) {
//                for (i = 0; i < aaa.size(); i++) {
//                    tempImg.add(aaa.get(i));
//                }
//            }

//            kpiQuestionsPostPJ.setFileEvidence(questions.get(j).getPhotoCapture());
            kpiQuestionsPostPJ.setKPINO(questions.get(j).getKpiNo());
            lkpi.add(kpiQuestionsPostPJ);


        }

        if(stillEmpty){
            Toast.makeText(context,"Masih ada field yang masih kosong",Toast.LENGTH_LONG).show();
        }
        else{
            kpiUserAnswerList = new KPIUserAnswerListPJ();


            kpiUserAnswerList.setTransID(kpiHeader.getTransId());

            kpiUserAnswerList.setEmpNik(usr.get(0).getEmpNIK());

            kpiUserAnswerList.setKpiUserAnswerList(lkpi);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            footerHolder.lnProgressFooter.setVisibility(View.VISIBLE);
            apiService.postKPIAnswerPJ(kpiUserAnswerList,"Bearer "+usr.get(0).getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<Response<Void>>() {


                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Response<Void> v) {
                        }

                        @Override
                        public void onError(Throwable e) {
                            footerHolder.lnProgressFooter.setVisibility(View.GONE);
                            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                            listener.setEditables("N", kpiHeader);
                            //Toast.makeText(context,Integer.toString(kpiUserAnswerList.getKpiUserAnswerList().get(1).getFileEvidence().size()),Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onComplete() {
                            footerHolder.lnProgressFooter.setVisibility(View.GONE);
                            Toast.makeText(context,"Submitted!",Toast.LENGTH_LONG).show();
                            listener.setEditables("N", kpiHeader);
                            currentPosition=-1;
//                            Toast.makeText(context,"Total: "+Integer.toString(questions.get(0).getPhotoCapture().size()),Toast.LENGTH_LONG).show();
                            //Toast.makeText(context,Integer.toString(kpiUserAnswerList.getKpiUserAnswerList().get(1).getFileEvidence().size()),Toast.LENGTH_LONG).show();
                        }
                    });




        }

    }

}
