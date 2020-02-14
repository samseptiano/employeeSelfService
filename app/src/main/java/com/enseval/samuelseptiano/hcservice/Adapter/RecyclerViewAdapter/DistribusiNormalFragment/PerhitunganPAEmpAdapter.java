package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.SortDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Home.Home;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerhitunganPAEmpAdapter extends RecyclerView.Adapter<PerhitunganPAEmpAdapter.MyViewHolder> {

    private List<PerhitunganPAEMPModel> perhitunganPAEmpAdapterList;
    private List<PerhitunganPAEMPModel> perhitunganPAEmpAdapterListFilter = new ArrayList<>();

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
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.perhitungan_pa_employee_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PerhitunganPAEMPModel perhitunganPAEMPModel = perhitunganPAEmpAdapterList.get(position);
        holder.ratingAdjistment.setRating(perhitunganPAEMPModel.getStarAdj());
        holder.ratingDN.setRating(perhitunganPAEMPModel.getStarDN());


        holder.tvEmpName.setText(perhitunganPAEMPModel.getEmpName());
        holder.tvScore.setText(perhitunganPAEMPModel.getScore());
        holder.tvEmpJobTitle.setText(perhitunganPAEMPModel.getEmpJobTitle());
        holder.nilaiAdjustment.setText("("+perhitunganPAEMPModel.getNilaiAdj()+")");
        holder.nilaiDN.setText("("+perhitunganPAEMPModel.getNilaiDN()+")");

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


        tvTitle.setText("Penilaian "+ pa.getEmpName());
        ratingDN.setRating(pa.getStarDN());

        ratingAdj.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                perhitunganPAEmpAdapterList.get(position).setStarAdj((int)rating);
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
                notifyDataSetChanged();
                dialog.dismiss();
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
                if (wp.getEmpName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getEmpJobTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    perhitunganPAEmpAdapterList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}