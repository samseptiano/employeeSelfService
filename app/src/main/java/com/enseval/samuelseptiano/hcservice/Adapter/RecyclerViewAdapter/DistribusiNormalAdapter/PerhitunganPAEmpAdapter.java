package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PostDistNormalD;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ProcessDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.SortDNModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.softrunapps.paginatedrecyclerview.PaginatedAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerhitunganPAEmpAdapter extends PaginatedAdapter<PerhitunganPAEMPModel,PerhitunganPAEmpAdapter.MyViewHolder> {

    private List<PerhitunganPAEMPModel> perhitunganPAEmpAdapterList;
    private List<PerhitunganPAEMPModel> perhitunganPAEmpAdapterListFilter = new ArrayList<>();
    ArrayList<UserRealmModel> usr = new ArrayList<>();

    private Context context;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;

    EventListener listener;


    public interface EventListener{
        List<SortDNModel> getSort();
        void setSort(List<SortDNModel> sortDNModelList);
        void SetValue(List<PerhitunganPAEMPModel> perhitunganPAEMPModelList);
        void onBottomReached(int position);
        void reCallAPI();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEmpName,tvEmpJobTitle,tvScore,nilaiDN,nilaiAdjustment;
        public MaterialRatingBar ratingDN, ratingAdjistment;
        public ImageButton imgBtnPopup;

        public MyViewHolder(View view) {
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


    public PerhitunganPAEmpAdapter(List<PerhitunganPAEMPModel> perhitunganPAEmpAdapterList, Context context, Boolean isConnected, Activity activity, EventListener listener) {
        this.context = context;
        this.perhitunganPAEmpAdapterList = perhitunganPAEmpAdapterList;
        this.isConnected = isConnected;
        this.activity = activity;
        this.listener = listener;
        this.perhitunganPAEmpAdapterListFilter.addAll(perhitunganPAEmpAdapterList);
        UserRealmHelper userRealmHelper = new UserRealmHelper(context);
        usr = userRealmHelper.findAllArticle();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perhitungan_pa_employee_item, parent, false);

        return new PerhitunganPAEmpAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PerhitunganPAEMPModel perhitunganPAEMPModel = perhitunganPAEmpAdapterList.get(position);
        if (position == perhitunganPAEmpAdapterList.size() - 1){

            listener.onBottomReached(position);

        }
        try {
            holder.ratingAdjistment.setRating(Integer.parseInt(perhitunganPAEMPModel.getANGKANILAIADJUSTMENT()));

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
            holder.ratingDN.setRating(Integer.parseInt(perhitunganPAEMPModel.getANGKANILAI()));

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
            holder.ratingDN.setRating(0);
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

    @Override
    public int getItemCount() {
        return perhitunganPAEmpAdapterList.size();
    }

    public void showRatingDialog(PerhitunganPAEMPModel pa, int position){
        Dialog dialog = new Dialog(context);
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

        if(usr.get(0).getPrivilege().contains("A")){
            edtPenilaian.setEnabled(false);
            ratingAdj.setIsIndicator(true);
        }

        tvTitle.setText("Penilaian "+ pa.getEMPNAME());
        ratingDN.setRating(Float.parseFloat(pa.getANGKANILAI().replace(",",".")));
        switch (Integer.parseInt(pa.getANGKANILAI())) {
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

        ratingAdj.setRating(Float.parseFloat(pa.getANGKANILAIADJUSTMENT().replace(",",".")));
        switch (Integer.parseInt(pa.getANGKANILAIADJUSTMENT())) {
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

        edtPenilaian.setText(pa.getALASANREVISI());
        ratingAdj.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                perhitunganPAEmpAdapterList.get(position).setANGKANILAIADJUSTMENT(Float.toString(rating));
                if(rating==5){
                    perhitunganPAEmpAdapterList.get(position).setDNNILAIADJUSTMENT("K");
                }
                else if(rating==4){
                    perhitunganPAEmpAdapterList.get(position).setDNNILAIADJUSTMENT("A");
                }
                if(rating==3){
                    perhitunganPAEmpAdapterList.get(position).setDNNILAIADJUSTMENT("L");
                }
                if(rating==2){
                    perhitunganPAEmpAdapterList.get(position).setDNNILAIADJUSTMENT("B");
                }
                if(rating==1){
                    perhitunganPAEmpAdapterList.get(position).setDNNILAIADJUSTMENT("E");
                }
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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtPenilaian.getText().toString().length() > 0) {

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
        perhitunganPAEmpAdapterList.clear();
        if (charText.length() == 0) {
            perhitunganPAEmpAdapterList.addAll(perhitunganPAEmpAdapterListFilter);
        }
        else
        {
            for (PerhitunganPAEMPModel wp : perhitunganPAEmpAdapterListFilter) {
                if (wp.getEMPNAME().toLowerCase(Locale.getDefault()).contains(charText) || wp.getJOBTTLNAME().toLowerCase(Locale.getDefault()).contains(charText)) {
                    perhitunganPAEmpAdapterList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}