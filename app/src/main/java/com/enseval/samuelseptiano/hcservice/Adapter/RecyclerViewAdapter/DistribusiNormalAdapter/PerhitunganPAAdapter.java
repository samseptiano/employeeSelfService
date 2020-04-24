package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAModel;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class PerhitunganPAAdapter extends RecyclerView.Adapter<PerhitunganPAAdapter.MyViewHolder> {

    private List<PerhitunganPAModel> perhitunganPAModelList;
    private Context context;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvN,tvRange,tvBA,tvJumlah,tvPersen,tvJumlah2,tvpersen2;
        public MaterialRatingBar ratingStar;
        LinearLayout lnEvent;

        public MyViewHolder(View view) {
            super(view);
            tvN = (TextView) view.findViewById(R.id.tv_N);
            tvRange = (TextView) view.findViewById(R.id.tv_range);
            tvBA = (TextView) view.findViewById(R.id.tv_ba);
            tvJumlah = (TextView) view.findViewById(R.id.tv_jumlah);
            tvPersen = (TextView) view.findViewById(R.id.tv_persen);
            tvJumlah2 = (TextView) view.findViewById(R.id.tv_jumlah2);
            tvpersen2 = (TextView) view.findViewById(R.id.tv_persen2);
            ratingStar =  view.findViewById(R.id.ratingStar);

        }

    }


    public PerhitunganPAAdapter(List<PerhitunganPAModel> perhitunganPAModelList, Context context, Boolean isConnected, Activity activity) {
        this.context = context;
        this.perhitunganPAModelList = perhitunganPAModelList;
        this.isConnected = isConnected;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.perhitungan_pa_table_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PerhitunganPAModel perhitungaanList = perhitunganPAModelList.get(position);
        holder.ratingStar.setRating(perhitungaanList.getStar());
        holder.tvN.setText(perhitungaanList.getN());
        if(position<4) {
            holder.tvBA.setText(Double.toString(Math.round(Double.parseDouble(perhitungaanList.getBA().replace(",", ".")) * 100.0) / 100.0));
        }
        else{
            holder.tvBA.setText("");
        }
        holder.tvJumlah.setText(perhitungaanList.getJumlah());
        holder.tvPersen.setText(perhitungaanList.getPersen()+"%");
        holder.tvJumlah2.setText(perhitungaanList.getJumlah2());
        holder.tvpersen2.setText(perhitungaanList.getPersen2()+"%");
        holder.tvRange.setText(perhitungaanList.getRange());

        switch (perhitungaanList.getStar()) {
            case 1:
                LayerDrawable stars = (LayerDrawable) holder.ratingStar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                break;
            case 2:
                stars = (LayerDrawable) holder.ratingStar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                break;
            case 3:
                stars = (LayerDrawable) holder.ratingStar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                break;
            case 4:
                stars = (LayerDrawable) holder.ratingStar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                break;
            case 5:
                stars = (LayerDrawable) holder.ratingStar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return perhitunganPAModelList.size();
    }

}