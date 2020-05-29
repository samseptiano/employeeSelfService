package com.enseval.samuelseptiano.hcservice.MVVM.DistNormal;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter.PerhitunganPAEmpAdapter;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment.FinalPAFragments.DistribusiNormalFragment;
import com.enseval.samuelseptiano.hcservice.Helper.EventAbsentUserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PostDistNormalD;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.SortDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.enseval.samuelseptiano.hcservice.utils.DateFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends PagedListAdapter<PerhitunganPAEMPModel, ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<PerhitunganPAEMPModel> homeList;
    private List<PerhitunganPAEMPModel> perhitunganPAEmpAdapterListFilter;
    boolean canAdjust;

    private Context context;
//    private ArrayList<Home> homeListFilter = new ArrayList<>();  // for loading  filter data
    //    private ArrayList<EventAbsentUser> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    EventAbsentUserRealmHelper eventAbsentUserRealmHelper;


    EventListener listener;


    public interface EventListener{
        void reCallAPI();
        boolean getCanAdjust();
    }


    public ItemAdapter(Context mCtx,List<PerhitunganPAEMPModel> homeList, Context context, Boolean isConnected,EventListener listener,boolean canAdjust) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
       this.context = context;
        this.homeList = homeList;
        this.isConnected = isConnected;
        eventAbsentUserRealmHelper = new EventAbsentUserRealmHelper(mCtx);
        this.listener = listener;
        this.canAdjust = canAdjust;
        for(int i=0;i<getItemCount();i++) {
            perhitunganPAEmpAdapterListFilter.add(getItem(i));
        }

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.perhitungan_pa_employee_item, parent, false);

        //        for (int i=0;i<getItemCount();i++) {
//            eventAbsentUserRealmHelper.addArticle(getItem(i));
            //Toast.makeText(mCtx,Integer.toString(getItemCount()),Toast.LENGTH_LONG).show();
//        }

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        PerhitunganPAEMPModel perhitunganPAEMPModel = getItem(position);
        //homeList.add(perhitunganPAEMPModel);
        //eventAbsentUserRealmHelper.addArticle(home);
        try {
            holder.ratingAdjistment.setRating((int)Float.parseFloat(perhitunganPAEMPModel.getANGKANILAIADJUSTMENT()));
            switch (Integer.parseInt(perhitunganPAEMPModel.getANGKANILAIADJUSTMENT())) {
                case 1:
                    LayerDrawable stars = (LayerDrawable) holder.ratingAdjistment.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 2:
                    stars = (LayerDrawable) holder.ratingAdjistment.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 3:
                    stars = (LayerDrawable) holder.ratingAdjistment.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 4:
                    stars = (LayerDrawable) holder.ratingAdjistment.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 5:
                    stars = (LayerDrawable) holder.ratingAdjistment.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                    break;
            }
        }catch (Exception e){
            holder.ratingAdjistment.setRating(0);
        }
        try{
            holder.ratingDN.setRating((int)Float.parseFloat(perhitunganPAEMPModel.getANGKANILAI()));
            switch (Integer.parseInt(perhitunganPAEMPModel.getANGKANILAI())) {
                case 1:
                    LayerDrawable stars = (LayerDrawable) holder.ratingDN.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 2:
                    stars = (LayerDrawable) holder.ratingDN.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 3:
                    stars = (LayerDrawable) holder.ratingDN.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 4:
                    stars = (LayerDrawable) holder.ratingDN.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                    break;
                case 5:
                    stars = (LayerDrawable) holder.ratingDN.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                    break;
            }
        }catch (Exception e){
                holder.ratingAdjistment.setRating(0);
            }

        holder.tvEmpName.setText(perhitunganPAEMPModel.getEMPNAME());
        holder.tvScore.setText(perhitunganPAEMPModel.getDNSCORE());
        holder.tvEmpJobTitle.setText(perhitunganPAEMPModel.getJOBTTLNAME());
        holder.nilaiDN.setText("("+perhitunganPAEMPModel.getDNNILAI()+")");

        if(!perhitunganPAEMPModel.getDNNILAIADJUSTMENT().equals("")) {
            holder.nilaiAdjustment.setText("(" + perhitunganPAEMPModel.getDNNILAIADJUSTMENT() + ")");
        }

        holder.imgBtnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRatingDialog(perhitunganPAEMPModel,position);
            }
        });

    }


    private static DiffUtil.ItemCallback<PerhitunganPAEMPModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PerhitunganPAEMPModel>() {
                @Override
                public boolean areItemsTheSame(PerhitunganPAEMPModel oldItem, PerhitunganPAEMPModel newItem) {
                    return oldItem.getDNID() == newItem.getDNID();
                }

                @Override
                public boolean areContentsTheSame(PerhitunganPAEMPModel oldItem, PerhitunganPAEMPModel newItem) {
                    return oldItem.equals(newItem);
                }
            };


    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmpName,tvEmpJobTitle,tvScore,nilaiDN,nilaiAdjustment;
        public MaterialRatingBar ratingDN, ratingAdjistment;
        public ImageButton imgBtnPopup;

        public ItemViewHolder(View view) {
            super(view);
            tvEmpName = (TextView) view.findViewById(R.id.tv_empName);
            tvEmpJobTitle = (TextView) view.findViewById(R.id.tv_empJobTitle);
            tvScore = (TextView) view.findViewById(R.id.tv_score);
            nilaiDN = (TextView) view.findViewById(R.id.tv_nilaiDN);
            nilaiAdjustment = (TextView) view.findViewById(R.id.tv_nilaiAdjustment);
            ratingDN = view.findViewById(R.id.ratingDN);
            ratingAdjistment =  view.findViewById(R.id.ratingAdjusment);
            imgBtnPopup =  view.findViewById(R.id.imgbtnPopup);

        }

    }

    public void showRatingDialog(PerhitunganPAEMPModel pa, int position){
        Dialog dialog = new Dialog(context);
        ArrayList<UserRealmModel> usr = new ArrayList<>();
        UserRealmHelper userRealmHelper = new UserRealmHelper(context);
        usr = userRealmHelper.findAllArticle();

//        dialog = new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.penilaian_dn_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        MaterialRatingBar ratingDN = dialog.findViewById(R.id.ratingBarDN);
        MaterialRatingBar ratingAdj = dialog.findViewById(R.id.ratingBarAdjustment);
        EditText edtPenilaian = dialog.findViewById(R.id.edtPenilaian);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSave = dialog.findViewById(R.id.btnProcessDN);


        tvTitle.setText("Penilaian "+ getItem(position).getEMPNAME());
        ratingDN.setRating(Float.parseFloat(getItem(position).getANGKANILAI().replace(",",".")));
        ratingAdj.setRating(Float.parseFloat(getItem(position).getANGKANILAIADJUSTMENT().replace(",",".")));
        edtPenilaian.setText(pa.getALASANREVISI());
        switch ((int)Float.parseFloat(pa.getANGKANILAI())) {
            case 1:
                LayerDrawable stars = (LayerDrawable) ratingDN.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                break;
            case 2:
                stars = (LayerDrawable) ratingDN.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                break;
            case 3:
                stars = (LayerDrawable) ratingDN.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                break;
            case 4:
                stars = (LayerDrawable) ratingDN.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                break;
            case 5:
                stars = (LayerDrawable) ratingDN.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                break;
        }
        switch ((int)Float.parseFloat(pa.getANGKANILAIADJUSTMENT())) {
            case 1:
                LayerDrawable stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                break;
            case 2:
                stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                break;
            case 3:
                stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                break;
            case 4:
                stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                break;
            case 5:
                stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                break;
        }
        ratingAdj.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                getItem(position).setANGKANILAIADJUSTMENT(Float.toString(rating));
                if(rating==5){
                    getItem(position).setDNNILAIADJUSTMENT("K");
                    LayerDrawable stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                    pa.setANGKANILAIADJUSTMENT("5");

                }
                else if(rating==4){
                    getItem(position).setDNNILAIADJUSTMENT("A");
                    LayerDrawable stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                    pa.setANGKANILAIADJUSTMENT("4");

                }
                if(rating==3){
                    getItem(position).setDNNILAIADJUSTMENT("L");
                    LayerDrawable stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                    pa.setANGKANILAIADJUSTMENT("3");

                }
                if(rating==2){
                    getItem(position).setDNNILAIADJUSTMENT("B");
                    LayerDrawable stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                    pa.setANGKANILAIADJUSTMENT("2");

                }
                if(rating==1){
                    getItem(position).setDNNILAIADJUSTMENT("E");
                    LayerDrawable stars = (LayerDrawable) ratingAdj.getProgressDrawable();
                    stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                    pa.setANGKANILAIADJUSTMENT("1");

                }
            }
        });

        if(!listener.getCanAdjust() || usr.get(0).getPrivilege().contains("A")){
            btnSave.setVisibility(View.GONE);
            ratingAdj.setIsIndicator(true);
            edtPenilaian.setEnabled(false);
        }

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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtPenilaian.getText().toString().length() > 0 && ratingAdj.getRating()>0) {


                    ArrayList<UserRealmModel> usr = new ArrayList<>();
                    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                    usr = userRealmHelper.findAllArticle();

                    ApiInterface apiService = ApiClient.getClientTest(context).create(ApiInterface.class);
                    //============================ HARDCODE =====================
                    PostDistNormalD postDistNormalD = new PostDistNormalD();
                    postDistNormalD.setDnID(pa.getDNID());
                    postDistNormalD.setEmpNIK(pa.getEMPNIK());
                    postDistNormalD.setUpdUser(usr.get(0).getUsername());
                    postDistNormalD.setDnAdj(pa.getDNNILAIADJUSTMENT());
                    postDistNormalD.setAlasanAdjustment(edtPenilaian.getText().toString());
                    Call<Void> call = apiService.updateDistNormalD(postDistNormalD, "Bearer " + usr.get(0).getToken());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            int statusCode = response.code();
                            Toast.makeText(context, "Score Adjusted Completely", Toast.LENGTH_SHORT).show();
                            listener.reCallAPI();
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });
                }
                else{
                    Toast.makeText(context,"Alasan untuk revisi wajib diisi..",Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        perhitunganPAEmpAdapterListFilter.clear();
        if (charText.length() == 0) {
            perhitunganPAEmpAdapterListFilter.addAll(perhitunganPAEmpAdapterListFilter);
        }
        else
        {
            for (PerhitunganPAEMPModel wp : perhitunganPAEmpAdapterListFilter) {
                if (wp.getEMPNAME().toLowerCase(Locale.getDefault()).contains(charText) || wp.getJOBTTLNAME().toLowerCase(Locale.getDefault()).contains(charText)) {
                    perhitunganPAEmpAdapterListFilter.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
